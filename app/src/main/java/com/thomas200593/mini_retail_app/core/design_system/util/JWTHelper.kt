package com.thomas200593.mini_retail_app.core.design_system.util

import com.auth0.android.jwt.DecodeException
import com.auth0.android.jwt.JWT
import com.thomas200593.mini_retail_app.BuildConfig
import com.thomas200593.mini_retail_app.features.auth.entity.AuthSessionToken
import com.thomas200593.mini_retail_app.features.auth.entity.OAuth2UserMetadata
import com.thomas200593.mini_retail_app.features.auth.entity.OAuthProvider
import com.thomas200593.mini_retail_app.features.auth.entity.UserData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.security.MessageDigest
import java.util.UUID

object JWTHelper {
    private val algList = listOf(
        "HS256", "HS384", "HS512", "RS256", "RS384", "RS512",
        "ES256", "ES384", "ES512", "PS256", "PS384", "PS512"
    )
    private val typList = listOf("JWT")

    object GoogleOAuth2{
        suspend fun validateToken(authSessionToken: AuthSessionToken) = withContext(Dispatchers.IO) {
            try {
                if (authSessionToken.idToken.isNullOrBlank() || authSessionToken.authProvider?.name != OAuthProvider.GOOGLE.name)
                { return@withContext false }
                val jwt = JWT(authSessionToken.idToken)
                val hdrAlg = jwt.header["alg"]
                val hdrTyp = jwt.header["typ"]
                val name = jwt.getClaim("name").asString()
                val email = jwt.getClaim("email").asString()
                val iss = jwt.getClaim("iss").asString()
                val aud = jwt.getClaim("aud").asString()
                val exp = jwt.getClaim("exp").asLong()
                val validationResult =
                    (hdrAlg in algList) &&
                    (hdrTyp in typList) &&
                    !name.isNullOrEmpty() &&
                    !email.isNullOrEmpty() &&
                    (aud in listOf(BuildConfig.GOOGLE_AUTH_WEB_ID)) &&
                    (iss in listOf("accounts.google.com", "https://accounts.google.com")) &&
                    (exp != null && (Constants.NOW_EPOCH_SECOND <= exp)) &&
                    (authSessionToken.authProvider.name == OAuthProvider.GOOGLE.name)
                return@withContext validationResult
            }
            catch (e: DecodeException) { return@withContext false }
            catch (e: Exception) { return@withContext false }
        }

        suspend fun generateTokenNonce() = withContext(Dispatchers.IO) {
            val rawNonce = UUID.randomUUID().toString()
            val bytes = rawNonce.toByteArray()
            val md = MessageDigest.getInstance("SHA-256")
            val digest = md.digest(bytes)
            val hashedNonce = digest.fold("") { str, it -> str + "%02x".format(it) }
            hashedNonce
        }

        suspend fun mapToUserData(authSessionToken: AuthSessionToken) =
            withContext(Dispatchers.IO) {
                if (validateToken(authSessionToken)) {
                    val jwt = JWT(authSessionToken.idToken.orEmpty())
                    val email = jwt.getClaim("email").asString()
                    val name = jwt.getClaim("name").asString()
                    val emailVerified = jwt.getClaim("email_verified").asString()
                    val picture = jwt.getClaim("picture").asString()
                    val exp = jwt.getClaim("exp").asString()
                    UserData(
                        authSessionToken = authSessionToken,
                        oAuth2UserMetadata = OAuth2UserMetadata.Google(
                            email = email.orEmpty(),
                            name = name.orEmpty(),
                            emailVerified = emailVerified.orEmpty(),
                            pictureUri = picture.orEmpty(),
                            expiredAt = exp.orEmpty()
                        )
                    )
                }
                else { null }
            }
    }
}
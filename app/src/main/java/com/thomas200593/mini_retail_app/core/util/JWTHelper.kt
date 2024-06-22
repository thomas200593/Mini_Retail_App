package com.thomas200593.mini_retail_app.core.util

import com.auth0.android.jwt.DecodeException
import com.auth0.android.jwt.JWT
import com.thomas200593.mini_retail_app.BuildConfig
import com.thomas200593.mini_retail_app.features.auth.entity.AuthSessionToken
import com.thomas200593.mini_retail_app.features.auth.entity.OAuth2UserMetadata
import com.thomas200593.mini_retail_app.features.auth.entity.OAuthProvider
import com.thomas200593.mini_retail_app.features.auth.entity.UserData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.security.MessageDigest
import java.time.Instant
import java.util.UUID

private val TAG = JWTHelper::class.simpleName

object JWTHelper {
    private val algList = listOf(
        "HS256",
        "HS384",
        "HS512",
        "RS256",
        "RS384",
        "RS512",
        "ES256",
        "ES384",
        "ES512",
        "PS256",
        "PS384",
        "PS512",
    )
    private val typList = listOf(
        "JWT",
    )

    private val TAG_GOOGLE_OAUTH2 = GoogleOAuth2::class.simpleName
    object GoogleOAuth2{
        suspend fun validateToken(
            authSessionToken: AuthSessionToken
        ) = withContext(Dispatchers.IO){
            Timber.d("Called : fun $TAG.$TAG_GOOGLE_OAUTH2.validateToken(authSessionToken = $authSessionToken)")
            try {
                if (authSessionToken.idToken.isNullOrBlank() || authSessionToken.authProvider?.name != OAuthProvider.GOOGLE.name) {
                    Timber.d("fun $TAG.$TAG_GOOGLE_OAUTH2.validateToken() returned : false")
                    return@withContext false
                }
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
                        (aud in listOf(BuildConfig.GOOGLE_AUTH_WEB_ID))&&
                        (iss in listOf("accounts.google.com", "https://accounts.google.com")) &&
                        (exp != null && (Instant.now().epochSecond <= exp)) &&
                        (authSessionToken.authProvider.name == OAuthProvider.GOOGLE.name)
                if (validationResult) {
                    Timber.d("fun $TAG.$TAG_GOOGLE_OAUTH2.validateToken() returned : true")
                    return@withContext true
                } else {
                    Timber.d("fun $TAG.$TAG_GOOGLE_OAUTH2.validateToken() returned : false")
                    return@withContext false
                }
            } catch (e: DecodeException) {
                Timber.e("fun $TAG.$TAG_GOOGLE_OAUTH2.validateToken() returned : DecodeException -> $e")
                return@withContext false
            } catch (e: Exception) {
                Timber.e("fun $TAG.$TAG_GOOGLE_OAUTH2.validateToken() returned : Exception -> $e")
                return@withContext false
            }
        }

        suspend fun generateTokenNonce() = withContext(Dispatchers.IO){
            val rawNonce = UUID.randomUUID().toString()
            val bytes = rawNonce.toByteArray()
            val md = MessageDigest.getInstance("SHA-256")
            val digest = md.digest(bytes)
            val hashedNonce = digest.fold(""){ str, it -> str + "%02x".format(it) }
            hashedNonce
        }

        suspend fun mapToUserData(authSessionToken: AuthSessionToken) = withContext(Dispatchers.IO){
            if(validateToken(authSessionToken)){
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
            }else{
                null
            }
        }
    }
}
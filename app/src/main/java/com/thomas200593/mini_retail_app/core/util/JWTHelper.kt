package com.thomas200593.mini_retail_app.core.util

import com.auth0.android.jwt.DecodeException
import com.auth0.android.jwt.JWT
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

private const val TAG = "JWTHelper"
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
        "JWT"
    )

    private const val GOOGLE_OAUTH2_TAG = "GoogleOAuth2"
    object GoogleOAuth2{
        suspend fun validateToken(
            authSessionToken: AuthSessionToken
        ) = withContext(Dispatchers.IO){
            try {
                Timber.d("Called %s.%s.validateToken()", TAG, GOOGLE_OAUTH2_TAG)
                if(authSessionToken.idToken?.isEmpty() == true || authSessionToken.idToken?.isBlank() == true ){
                    Timber.d("Token is empty!")
                    false
                }else{
                    Timber.d("Validating Token...")
                    val jwt = JWT(authSessionToken.idToken.orEmpty())
                    val hdrAlg = jwt.header["alg"]
                    val hdrTyp = jwt.header["typ"]
                    val authProvider = authSessionToken.authProvider
                    val name = jwt.getClaim("name").asString()
                    val email = jwt.getClaim("email").asString()
                    val iss = jwt.getClaim("iss").asString()
                    val exp = jwt.getClaim("exp").asLong()
                    (hdrAlg!! in algList) &&
                            (hdrTyp!! in typList) &&
                            name!!.isNotEmpty() &&
                            email!!.isNotEmpty() &&
                            (iss!! in listOf("accounts.google.com", "https://accounts.google.com")) &&
                            ((exp!! > 0) && ((Instant.now().toEpochMilli() / 1000) <= exp)) &&
                            authProvider?.name == OAuthProvider.GOOGLE.name
                }
            } catch (e: DecodeException) {
                Timber.e("DecodeException: %s", e)
                false
            } catch (e: Exception) {
                Timber.e("Exception: %s", e)
                false
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
                UserData(
                    authSessionToken = authSessionToken,
                    oAuth2UserMetadata = OAuth2UserMetadata.Google(
                        email = email.orEmpty(),
                        name = name.orEmpty()
                    )
                )
            }else{
                null
            }
        }
    }
}
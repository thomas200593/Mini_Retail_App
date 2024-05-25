package com.thomas200593.mini_retail_app.core.util

import com.auth0.android.jwt.DecodeException
import com.auth0.android.jwt.JWT
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.time.Instant

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

    suspend fun isJWTTokenValid(idToken: String) = withContext(Dispatchers.IO){
        try {
            Timber.d("Called %s.isValidToken()", TAG)
            if(idToken.isEmpty() || idToken.isBlank()){
                Timber.d("Token is empty!")
                false
            }else{
                Timber.d("Validating Token...")
                val jwt = JWT(idToken)
                val hdrAlg = jwt.header["alg"]
                val hdrTyp = jwt.header["typ"]
                val name = jwt.getClaim("name").asString()
                val email = jwt.getClaim("email").asString()
                val iss = jwt.getClaim("iss").asString()
                val exp = jwt.getClaim("exp").asLong()
                (hdrAlg!! in algList) &&
                        (hdrTyp!! in typList) &&
                        name!!.isNotEmpty() &&
                        email!!.isNotEmpty() &&
                        (iss!! in listOf("accounts.google.com", "https://accounts.google.com")) &&
                        ((exp!! > 0) && ((Instant.now().toEpochMilli() / 1000) <= exp))
            }
        } catch (e: DecodeException) {
            Timber.e("DecodeException: %s", e)
            false
        } catch (e: Exception) {
            Timber.e("Exception: %s", e)
            false
        }
    }
}
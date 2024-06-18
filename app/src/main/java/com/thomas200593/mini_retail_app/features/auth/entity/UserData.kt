package com.thomas200593.mini_retail_app.features.auth.entity

data class UserData(
    val authSessionToken: AuthSessionToken?,
    val oAuth2UserMetadata: OAuth2UserMetadata?,
)

sealed class OAuth2UserMetadata{
    data class Google(
        val email: String,
        val name: String,
        val emailVerified: String,
        val pictureUri: String,
        val expiredAt: String
    ) : OAuth2UserMetadata()
}
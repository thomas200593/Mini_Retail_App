package com.thomas200593.mini_retail_app.features.auth.entity

data class UserData(
    val authSessionToken: AuthSessionToken?,
    val oAuth2UserMetadata: OAuth2UserMetadata?,
)
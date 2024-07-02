package com.thomas200593.mini_retail_app.features.auth.entity

data class AuthSessionToken(
    val authProvider: OAuthProvider? = OAuthProvider.GOOGLE,
    val idToken: String? = null,
)
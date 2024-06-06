package com.thomas200593.mini_retail_app.features.auth.entity

import com.thomas200593.mini_retail_app.features.auth.entity.OAuthProvider.GOOGLE

data class AuthSessionToken(
    val authProvider: OAuthProvider? = GOOGLE,
    val idToken: String? = null,
)
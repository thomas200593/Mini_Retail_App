package com.thomas200593.mini_retail_app.features.auth.entity

enum class OAuthProvider(
    val code: String,
    val title: String
) {
    GOOGLE(
        code = "GOOGLE_OAUTH2",
        title = "Social (Google)"
    )
}
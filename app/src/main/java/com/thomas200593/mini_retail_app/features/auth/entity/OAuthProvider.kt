package com.thomas200593.mini_retail_app.features.auth.entity

enum class OAuthProvider(
    name: String,
    label: String
) {
    GOOGLE(
        name = "GOOGLE_OAUTH2",
        label = "Social (Google)"
    )
}
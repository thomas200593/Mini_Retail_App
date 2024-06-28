package com.thomas200593.mini_retail_app.features.app_config.entity

import kotlinx.serialization.Serializable

@Serializable
data class Country(
    val isoCode: String,
    val iso03Country: String,
    val displayName: String,
)
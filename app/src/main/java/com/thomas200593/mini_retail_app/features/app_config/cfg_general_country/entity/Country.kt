package com.thomas200593.mini_retail_app.features.app_config.cfg_general_country.entity

import kotlinx.serialization.Serializable

@Serializable
data class Country(
    val isoCode: String,
    val iso03Country: String,
    val displayName: String,
)
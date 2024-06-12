package com.thomas200593.mini_retail_app.features.app_config.entity

data class Currency(
    val code: String,
    val displayName: String,
    val symbol: String,
    val defaultFractionDigits: Int,
    val decimalPlaces: Int
)

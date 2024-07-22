package com.thomas200593.mini_retail_app.features.app_config.app_cfg_general_currency.entity

data class Currency(
    val code: String,
    val displayName: String,
    val symbol: String,
    val defaultFractionDigits: Int,
    val decimalPlaces: Int
)

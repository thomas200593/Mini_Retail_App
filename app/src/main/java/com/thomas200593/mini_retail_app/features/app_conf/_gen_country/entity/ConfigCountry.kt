package com.thomas200593.mini_retail_app.features.app_conf._gen_country.entity

import com.thomas200593.mini_retail_app.features.app_conf.app_config.entity.AppConfig.ConfigCurrent

data class ConfigCountry(
    val configCurrent: ConfigCurrent,
    val countries: List<Country>
)
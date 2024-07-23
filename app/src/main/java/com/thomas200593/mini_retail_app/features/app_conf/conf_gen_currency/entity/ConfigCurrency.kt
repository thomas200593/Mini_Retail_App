package com.thomas200593.mini_retail_app.features.app_conf.conf_gen_currency.entity

import com.thomas200593.mini_retail_app.features.app_conf.app_config.entity.AppConfig.ConfigCurrent

data class ConfigCurrency(
    val configCurrent: ConfigCurrent,
    val currencies: List<Currency>
)
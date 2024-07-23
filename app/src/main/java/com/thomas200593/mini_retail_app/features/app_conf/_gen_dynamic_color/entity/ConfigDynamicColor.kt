package com.thomas200593.mini_retail_app.features.app_conf._gen_dynamic_color.entity

import com.thomas200593.mini_retail_app.features.app_conf.app_config.entity.AppConfig.ConfigCurrent

data class ConfigDynamicColor(
    val configCurrent: ConfigCurrent,
    val dynamicColors: Set<DynamicColor>
)
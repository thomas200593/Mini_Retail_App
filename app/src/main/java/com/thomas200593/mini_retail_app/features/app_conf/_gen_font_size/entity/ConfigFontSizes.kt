package com.thomas200593.mini_retail_app.features.app_conf._gen_font_size.entity

import com.thomas200593.mini_retail_app.features.app_conf.app_config.entity.AppConfig.ConfigCurrent

data class ConfigFontSizes(
    val configCurrent: ConfigCurrent,
    val fontSizes: Set<FontSize>
)
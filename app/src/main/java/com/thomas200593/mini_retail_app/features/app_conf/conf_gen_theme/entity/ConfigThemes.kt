package com.thomas200593.mini_retail_app.features.app_conf.conf_gen_theme.entity

import com.thomas200593.mini_retail_app.features.app_conf.app_config.entity.AppConfig.ConfigCurrent

data class ConfigThemes(
    val configCurrent: ConfigCurrent,
    val themes: Set<Theme>
)
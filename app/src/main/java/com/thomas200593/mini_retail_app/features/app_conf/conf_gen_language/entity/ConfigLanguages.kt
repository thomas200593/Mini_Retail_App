package com.thomas200593.mini_retail_app.features.app_conf.conf_gen_language.entity

import com.thomas200593.mini_retail_app.features.app_conf.app_config.entity.AppConfig.ConfigCurrent

data class ConfigLanguages(
    val configCurrent: ConfigCurrent,
    val languages: Set<Language>
)
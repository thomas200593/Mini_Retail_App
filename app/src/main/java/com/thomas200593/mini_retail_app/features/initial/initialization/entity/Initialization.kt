package com.thomas200593.mini_retail_app.features.initial.initialization.entity

import com.thomas200593.mini_retail_app.features.app_conf.app_config.entity.AppConfig.ConfigCurrent
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_language.entity.Language

data class Initialization(
    val configCurrent: ConfigCurrent,
    val languages: Set<Language>,
    val industries: Map<String, String>,
    val legalType: Map<String, String>
)
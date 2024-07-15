package com.thomas200593.mini_retail_app.features.initial.entity

import com.thomas200593.mini_retail_app.features.app_config.entity.AppConfig
import com.thomas200593.mini_retail_app.features.app_config.entity.Language

data class Initialization(
    val configCurrent: AppConfig.ConfigCurrent,
    val languages: Set<Language>
)

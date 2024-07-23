package com.thomas200593.mini_retail_app.features.initial.initial.entity

import com.thomas200593.mini_retail_app.features.app_conf.app_config.entity.AppConfig
import com.thomas200593.mini_retail_app.features.auth.entity.UserData

data class Initial(
    val isFirstTime: FirstTimeStatus,
    val configCurrent: AppConfig.ConfigCurrent,
    val session: UserData?,
)
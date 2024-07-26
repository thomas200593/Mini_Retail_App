package com.thomas200593.mini_retail_app.features.initial.initial.entity

import com.thomas200593.mini_retail_app.features.app_conf.app_config.entity.AppConfig.ConfigCurrent
import com.thomas200593.mini_retail_app.features.auth.entity.UserData

data class Initial(
    val firstTimeStatus: FirstTimeStatus,
    val configCurrent: ConfigCurrent,
    val session: UserData?,
)
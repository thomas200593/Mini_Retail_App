package com.thomas200593.mini_retail_app.features.initial.entity

import com.thomas200593.mini_retail_app.features.app_config.entity.ConfigCurrent
import com.thomas200593.mini_retail_app.features.auth.entity.UserData

data class Initial(
    val isFirstTime: FirstTimeStatus,
    val configCurrent: ConfigCurrent,
    val session: UserData?,
)

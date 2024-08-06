package com.thomas200593.mini_retail_app.features.business.biz_profile.entity

import com.thomas200593.mini_retail_app.features.app_conf.app_config.entity.AppConfig.ConfigCurrent

data class BizProfileDtl(
    val bizProfile: BizProfile,
    val configCurrent: ConfigCurrent
)
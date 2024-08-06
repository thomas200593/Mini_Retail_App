package com.thomas200593.mini_retail_app.features.business.biz_profile.entity

import com.thomas200593.mini_retail_app.features.app_conf.app_config.entity.AppConfig.ConfigCurrent
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_timezone.entity.Timezone

data class BizProfileDtl(
    val bizProfile: BizProfile,
    val configCurrent: ConfigCurrent,
    val timezones: List<Timezone>
)
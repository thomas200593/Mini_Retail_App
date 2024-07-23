package com.thomas200593.mini_retail_app.features.app_conf._g_timezone.entity

import com.thomas200593.mini_retail_app.features.app_conf.app_config.entity.AppConfig.ConfigCurrent

data class ConfigTimezones(
    val configCurrent: ConfigCurrent,
    val timezones: List<Timezone>
)
package com.thomas200593.mini_retail_app.features.app_conf.app_config.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.thomas200593.mini_retail_app.R.string
import com.thomas200593.mini_retail_app.app.navigation.ScrGraphs
import com.thomas200593.mini_retail_app.app.navigation.ScrGraphs.ConfigData
import com.thomas200593.mini_retail_app.app.navigation.ScrGraphs.ConfigGeneral
import com.thomas200593.mini_retail_app.core.ui.common.CustomIcons.Setting.settings_data
import com.thomas200593.mini_retail_app.core.ui.common.CustomIcons.Setting.settings_general

enum class DestAppConfig(
    val route: String,
    @DrawableRes val iconRes: Int,
    @StringRes val title: Int,
    @StringRes val description: Int,
    val usesAuth: Boolean
){
    CONFIG_GENERAL(
        route = ConfigGeneral.route,
        iconRes = settings_general,
        title = string.str_configuration_general,
        description = string.str_configuration_general,
        usesAuth = false
    ),
    CONFIG_DATA(
        route = ConfigData.route,
        iconRes = settings_data,
        title = string.str_configuration_data,
        description = string.str_configuration_data,
        usesAuth = true
    )
}

enum class DestAppConfig2(val scrGraphs: ScrGraphs){
    CONFIG_GENERAL(scrGraphs = ConfigGeneral),
    CONFIG_DATA(scrGraphs = ConfigData)
}

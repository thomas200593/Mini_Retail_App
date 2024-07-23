package com.thomas200593.mini_retail_app.features.app_conf.app_config.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.thomas200593.mini_retail_app.R.string.str_configuration_data
import com.thomas200593.mini_retail_app.R.string.str_configuration_general
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
        title = str_configuration_general,
        description = str_configuration_general,
        usesAuth = false
    ),
    CONFIG_DATA(
        route = ConfigData.route,
        iconRes = settings_data,
        title = str_configuration_data,
        description = str_configuration_data,
        usesAuth = true
    )
}

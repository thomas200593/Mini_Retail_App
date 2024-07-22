package com.thomas200593.mini_retail_app.features.app_conf.app_config.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.thomas200593.mini_retail_app.R
import com.thomas200593.mini_retail_app.app.navigation.ScrGraphs
import com.thomas200593.mini_retail_app.core.ui.common.Icons

enum class DestinationAppConfig(
    val route: String,
    @DrawableRes val iconRes: Int,
    @StringRes val title: Int,
    @StringRes val description: Int,
    val usesAuth: Boolean
){
    CONFIG_GENERAL(
        route = ScrGraphs.ConfigGeneral.route,
        iconRes = Icons.Setting.settings_general,
        title = R.string.str_configuration_general,
        description = R.string.str_configuration_general,
        usesAuth = false
    ),
    CONFIG_DATA(
        route = ScrGraphs.ConfigData.route,
        iconRes = Icons.Setting.settings_data,
        title = R.string.str_configuration_data,
        description = R.string.str_configuration_data,
        usesAuth = true
    )
}

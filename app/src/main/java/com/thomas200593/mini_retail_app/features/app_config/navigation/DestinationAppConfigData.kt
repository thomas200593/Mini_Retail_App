package com.thomas200593.mini_retail_app.features.app_config.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.thomas200593.mini_retail_app.R
import com.thomas200593.mini_retail_app.app.navigation.ScreenGraphs
import com.thomas200593.mini_retail_app.core.ui.common.Icons

enum class DestinationAppConfigData(
    val route: String,
    @DrawableRes val iconRes: Int,
    @StringRes val title: Int,
    @StringRes val description: Int,
    val usesAuth: Boolean
){
    BACKUP_DATA(
        route = ScreenGraphs.AppConfigDataBackup.route,
        iconRes = Icons.Data.backup,
        title = R.string.str_backup,
        description = R.string.str_backup_desc,
        usesAuth = true
    )
}
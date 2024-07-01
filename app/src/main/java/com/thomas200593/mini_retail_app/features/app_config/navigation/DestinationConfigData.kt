package com.thomas200593.mini_retail_app.features.app_config.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.thomas200593.mini_retail_app.R.string.str_backup
import com.thomas200593.mini_retail_app.R.string.str_backup_desc
import com.thomas200593.mini_retail_app.app.navigation.ScreenGraphs.DataBackup
import com.thomas200593.mini_retail_app.core.ui.common.Icons.Data.backup

enum class DestinationConfigData(
    val route: String,
    @DrawableRes val iconRes: Int,
    @StringRes val title: Int,
    @StringRes val description: Int,
    val usesAuth: Boolean
){
    DATA_BACKUP(
        route = DataBackup.route,
        iconRes = backup,
        title = str_backup,
        description = str_backup_desc,
        usesAuth = true
    )
}
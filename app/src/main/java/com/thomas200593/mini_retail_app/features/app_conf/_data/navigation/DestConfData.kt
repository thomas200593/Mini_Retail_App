package com.thomas200593.mini_retail_app.features.app_conf._data.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.thomas200593.mini_retail_app.R
import com.thomas200593.mini_retail_app.app.navigation.ScrGraphs.DataBackup
import com.thomas200593.mini_retail_app.core.ui.common.Icons

enum class DestConfData(
    val route: String,
    @DrawableRes val iconRes: Int,
    @StringRes val title: Int,
    @StringRes val description: Int,
    val usesAuth: Boolean
){
    DATA_BACKUP(
        route = DataBackup.route,
        iconRes = Icons.Data.backup,
        title = R.string.str_backup,
        description = R.string.str_backup_desc,
        usesAuth = true
    )
}
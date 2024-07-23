package com.thomas200593.mini_retail_app.features.app_conf._data.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.thomas200593.mini_retail_app.R.string.str_backup
import com.thomas200593.mini_retail_app.R.string.str_backup_desc
import com.thomas200593.mini_retail_app.app.navigation.ScrGraphs.DataBackup
import com.thomas200593.mini_retail_app.core.ui.common.CustomIcons.Data.backup

enum class DestConfData(
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
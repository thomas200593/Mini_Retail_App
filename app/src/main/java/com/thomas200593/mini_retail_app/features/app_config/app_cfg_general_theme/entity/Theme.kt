package com.thomas200593.mini_retail_app.features.app_config.app_cfg_general_theme.entity

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.thomas200593.mini_retail_app.R
import com.thomas200593.mini_retail_app.R.string.str_theme_dark
import com.thomas200593.mini_retail_app.core.ui.common.Icons

enum class Theme (
    val code: String,
    @StringRes val title: Int,
    @DrawableRes val iconRes: Int,
    @StringRes val description: Int
){
    SYSTEM(
        code = "theme_system",
        title = R.string.str_theme_system,
        iconRes = Icons.Theme.system,
        description = R.string.str_theme_system_desc
    ),
    LIGHT(
        code = "theme_light",
        title = R.string.str_theme_light,
        iconRes = Icons.Theme.light,
        description = R.string.str_theme_light_desc
    ),
    DARK(
        code = "theme_dark",
        title = str_theme_dark,
        iconRes = Icons.Theme.dark,
        description = R.string.str_theme_dark_desc
    )
}

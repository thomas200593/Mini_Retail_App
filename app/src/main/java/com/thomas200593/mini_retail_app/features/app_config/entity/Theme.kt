package com.thomas200593.mini_retail_app.features.app_config.entity

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.thomas200593.mini_retail_app.R
import com.thomas200593.mini_retail_app.core.ui.common.Icons.Theme.dark
import com.thomas200593.mini_retail_app.core.ui.common.Icons.Theme.light
import com.thomas200593.mini_retail_app.core.ui.common.Icons.Theme.system

enum class Theme (
    val code: String,
    @StringRes val title: Int,
    @DrawableRes val iconRes: Int,
    @StringRes val description: Int
){
    SYSTEM(
        code = "theme_system",
        title = R.string.str_theme_system,
        iconRes = system,
        description = R.string.str_theme_system_desc
    ),
    LIGHT(
        code = "theme_light",
        title = R.string.str_theme_light,
        iconRes = light,
        description = R.string.str_theme_light_desc
    ),
    DARK(
        code = "theme_dark",
        title = R.string.str_theme_dark,
        iconRes = dark,
        description = R.string.str_theme_dark_desc
    )
}

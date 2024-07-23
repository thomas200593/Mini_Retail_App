package com.thomas200593.mini_retail_app.features.app_conf.conf_gen_theme.entity

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.thomas200593.mini_retail_app.R.string.str_theme_dark
import com.thomas200593.mini_retail_app.R.string.str_theme_dark_desc
import com.thomas200593.mini_retail_app.R.string.str_theme_light
import com.thomas200593.mini_retail_app.R.string.str_theme_light_desc
import com.thomas200593.mini_retail_app.R.string.str_theme_system
import com.thomas200593.mini_retail_app.R.string.str_theme_system_desc
import com.thomas200593.mini_retail_app.core.ui.common.CustomIcons.Theme.dark
import com.thomas200593.mini_retail_app.core.ui.common.CustomIcons.Theme.light
import com.thomas200593.mini_retail_app.core.ui.common.CustomIcons.Theme.system

enum class Theme (
    val code: String,
    @StringRes val title: Int,
    @DrawableRes val iconRes: Int,
    @StringRes val description: Int
){
    SYSTEM(
        code = "theme_system",
        title = str_theme_system,
        iconRes = system,
        description = str_theme_system_desc
    ),
    LIGHT(
        code = "theme_light",
        title = str_theme_light,
        iconRes = light,
        description = str_theme_light_desc
    ),
    DARK(
        code = "theme_dark",
        title = str_theme_dark,
        iconRes = dark,
        description = str_theme_dark_desc
    )
}

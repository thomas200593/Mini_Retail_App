package com.thomas200593.mini_retail_app.features.app_config.entity

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.thomas200593.mini_retail_app.R
import com.thomas200593.mini_retail_app.core.ui.common.Icons.Theme.dark
import com.thomas200593.mini_retail_app.core.ui.common.Icons.Theme.light
import com.thomas200593.mini_retail_app.core.ui.common.Icons.Theme.system

enum class Theme (
    val codeValue: String,
    @StringRes val nameValue: Int,
    @DrawableRes val iconValue: Int,
){
    SYSTEM(
        codeValue = "theme_system",
        nameValue = R.string.str_theme_system,
        iconValue = system
    ),
    LIGHT(
        codeValue = "theme_light",
        nameValue = R.string.str_theme_light,
        iconValue = light
    ),
    DARK(
        codeValue = "theme_dark",
        nameValue = R.string.str_theme_dark,
        iconValue = dark
    )
}

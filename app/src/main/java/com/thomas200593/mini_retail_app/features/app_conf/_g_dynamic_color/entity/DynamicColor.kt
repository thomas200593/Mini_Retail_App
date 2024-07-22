package com.thomas200593.mini_retail_app.features.app_conf._g_dynamic_color.entity

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.thomas200593.mini_retail_app.R
import com.thomas200593.mini_retail_app.core.ui.common.CustomIcons

enum class DynamicColor (
    val code: String,
    @StringRes val title: Int,
    @DrawableRes val iconRes: Int,
){
    ENABLED(
        code = "dynamic_color_enabled",
        title = R.string.str_dynamic_color_enabled,
        iconRes = CustomIcons.DynamicColor.enabled
    ),
    DISABLED(
        code = "dynamic_color_disabled",
        title = R.string.str_dynamic_color_disabled,
        iconRes = CustomIcons.DynamicColor.disabled
    )
}
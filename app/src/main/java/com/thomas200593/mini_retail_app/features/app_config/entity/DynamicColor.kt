package com.thomas200593.mini_retail_app.features.app_config.entity

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.thomas200593.mini_retail_app.R
import com.thomas200593.mini_retail_app.core.ui.common.Icons.DynamicColor.enabled
import com.thomas200593.mini_retail_app.core.ui.common.Icons.DynamicColor.disabled

enum class DynamicColor (
    val code: String,
    @StringRes val title: Int,
    @DrawableRes val iconRes: Int,
){
    ENABLED(
        code = "dynamic_color_enabled",
        title = R.string.str_dynamic_color_enabled,
        iconRes = enabled
    ),
    DISABLED(
        code = "dynamic_color_disabled",
        title = R.string.str_dynamic_color_disabled,
        iconRes = disabled
    )
}
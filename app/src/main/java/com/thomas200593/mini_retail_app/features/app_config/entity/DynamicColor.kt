package com.thomas200593.mini_retail_app.features.app_config.entity

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.thomas200593.mini_retail_app.R
import com.thomas200593.mini_retail_app.core.ui.common.AppIcon.DynamicColor.disabled
import com.thomas200593.mini_retail_app.core.ui.common.AppIcon.DynamicColor.enabled

enum class DynamicColor (
    val code: String,
    @StringRes val stringResource: Int,
    @DrawableRes val iconDrawableResource: Int,
){
    ENABLED(
        code = "dynamic_color_enabled",
        stringResource = R.string.str_dynamic_color_enabled,
        iconDrawableResource = enabled
    ),
    DISABLED(
        code = "dynamic_color_disabled",
        stringResource = R.string.str_dynamic_color_disabled,
        iconDrawableResource = disabled
    )
}
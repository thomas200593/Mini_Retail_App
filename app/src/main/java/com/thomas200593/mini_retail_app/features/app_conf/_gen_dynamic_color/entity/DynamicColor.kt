package com.thomas200593.mini_retail_app.features.app_conf._gen_dynamic_color.entity

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.thomas200593.mini_retail_app.R.string.str_dynamic_color_disabled
import com.thomas200593.mini_retail_app.R.string.str_dynamic_color_enabled
import com.thomas200593.mini_retail_app.core.ui.common.CustomIcons.DynamicColor.disabled
import com.thomas200593.mini_retail_app.core.ui.common.CustomIcons.DynamicColor.enabled

enum class DynamicColor (
    val code: String,
    @StringRes val title: Int,
    @DrawableRes val iconRes: Int,
){
    ENABLED(
        code = "dynamic_color_enabled",
        title = str_dynamic_color_enabled,
        iconRes = enabled
    ),
    DISABLED(
        code = "dynamic_color_disabled",
        title = str_dynamic_color_disabled,
        iconRes = disabled
    )
}
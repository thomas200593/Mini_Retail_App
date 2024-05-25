package com.thomas200593.mini_retail_app.features.app_config.entity

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.thomas200593.mini_retail_app.R
import com.thomas200593.mini_retail_app.core.ui.common.AppIcon.Onboarding.hide
import com.thomas200593.mini_retail_app.core.ui.common.AppIcon.Onboarding.show

enum class Onboarding(
    val code: String,
    @StringRes val stringResource: Int,
    @DrawableRes val iconDrawableResource: Int,
) {
    SHOW(
        code = "onboarding_pages_show",
        stringResource = R.string.str_onboarding_show,
        iconDrawableResource = show
    ),
    HIDE(
        code = "onboarding_pages_hide",
        stringResource = R.string.str_onboarding_hide,
        iconDrawableResource = hide
    )
}
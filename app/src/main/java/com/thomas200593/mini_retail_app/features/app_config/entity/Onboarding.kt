package com.thomas200593.mini_retail_app.features.app_config.entity

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.thomas200593.mini_retail_app.R
import com.thomas200593.mini_retail_app.core.ui.common.Icons.Onboarding.hide
import com.thomas200593.mini_retail_app.core.ui.common.Icons.Onboarding.show

enum class Onboarding(
    val code: String,
    @StringRes val title: Int,
    @DrawableRes val iconRes: Int,
) {
    SHOW(
        code = "onboarding_pages_show",
        title = R.string.str_onboarding_show,
        iconRes = show
    ),
    HIDE(
        code = "onboarding_pages_hide",
        title = R.string.str_onboarding_hide,
        iconRes = hide
    )
}
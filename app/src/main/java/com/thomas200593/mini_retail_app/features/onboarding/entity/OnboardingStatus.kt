package com.thomas200593.mini_retail_app.features.onboarding.entity

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.thomas200593.mini_retail_app.R
import com.thomas200593.mini_retail_app.core.ui.common.Icons

enum class OnboardingStatus(
    val code: String,
    @StringRes val title: Int,
    @DrawableRes val iconRes: Int,
) {
    SHOW(
        code = "onboarding_status_show",
        title = R.string.str_onboarding_show,
        iconRes = Icons.Onboarding.show
    ),
    HIDE(
        code = "onboarding_status_hide",
        title = R.string.str_onboarding_hide,
        iconRes = Icons.Onboarding.hide
    )
}
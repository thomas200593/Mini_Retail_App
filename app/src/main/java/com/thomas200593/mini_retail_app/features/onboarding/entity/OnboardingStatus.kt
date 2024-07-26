package com.thomas200593.mini_retail_app.features.onboarding.entity

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.thomas200593.mini_retail_app.R.string.str_onboarding_hide
import com.thomas200593.mini_retail_app.R.string.str_onboarding_show
import com.thomas200593.mini_retail_app.core.ui.common.CustomIcons.Onboarding.hide
import com.thomas200593.mini_retail_app.core.ui.common.CustomIcons.Onboarding.show

enum class OnboardingStatus(
    val code: String,
    @StringRes val title: Int,
    @DrawableRes val iconRes: Int,
) {
    SHOW(
        code = "onboarding_status_show",
        title = str_onboarding_show,
        iconRes = show
    ),
    HIDE(
        code = "onboarding_status_hide",
        title = str_onboarding_hide,
        iconRes = hide
    )
}
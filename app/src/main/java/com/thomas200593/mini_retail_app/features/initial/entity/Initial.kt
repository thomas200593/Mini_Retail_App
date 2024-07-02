package com.thomas200593.mini_retail_app.features.initial.entity

import com.thomas200593.mini_retail_app.features.app_config.entity.OnboardingStatus
import com.thomas200593.mini_retail_app.features.auth.entity.UserData

data class Initial(
    val userData: UserData?,
    val onboardingPageStatus: OnboardingStatus
)

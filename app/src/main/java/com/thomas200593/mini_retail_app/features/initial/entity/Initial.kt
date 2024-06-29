package com.thomas200593.mini_retail_app.features.initial.entity

import com.thomas200593.mini_retail_app.features.app_config.entity.OnboardingStatus

data class Initial(
    val isSessionValid: Boolean,
    val onboardingPageStatus: OnboardingStatus
)

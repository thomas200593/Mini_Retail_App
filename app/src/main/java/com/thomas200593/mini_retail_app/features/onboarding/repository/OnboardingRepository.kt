package com.thomas200593.mini_retail_app.features.onboarding.repository

import com.thomas200593.mini_retail_app.features.onboarding.entity.Onboarding

interface OnboardingRepository {
    suspend fun hideOnboarding()
    suspend fun getOnboardingPages(): List<Onboarding.OnboardingPage>
}
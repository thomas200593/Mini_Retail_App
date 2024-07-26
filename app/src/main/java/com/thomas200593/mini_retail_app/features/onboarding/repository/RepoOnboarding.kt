package com.thomas200593.mini_retail_app.features.onboarding.repository

import com.thomas200593.mini_retail_app.core.data.local.datastore.DataStorePreferences
import com.thomas200593.mini_retail_app.features.onboarding.entity.Onboarding
import com.thomas200593.mini_retail_app.features.onboarding.entity.Onboarding.OnboardingPage
import javax.inject.Inject

interface RepoOnboarding {
    suspend fun hideOnboarding()
    suspend fun getOnboardingPages(): List<OnboardingPage>
}

class RepoImplOnboarding @Inject constructor(
    private val appDataStore: DataStorePreferences,
): RepoOnboarding {
    override suspend fun hideOnboarding() { appDataStore.hideOnboarding() }
    override suspend fun getOnboardingPages(): List<OnboardingPage> = Onboarding.getOnboardingPages()
}
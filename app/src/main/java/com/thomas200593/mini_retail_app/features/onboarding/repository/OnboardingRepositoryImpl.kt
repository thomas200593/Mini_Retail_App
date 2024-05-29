package com.thomas200593.mini_retail_app.features.onboarding.repository

import com.thomas200593.mini_retail_app.core.data.local.datastore.AppDataStorePreferences
import javax.inject.Inject

class OnboardingRepositoryImpl @Inject constructor(
    private val appDataStore: AppDataStorePreferences,
): OnboardingRepository {
    override suspend fun hideOnboarding(){
        appDataStore.hideOnboarding()
    }
}
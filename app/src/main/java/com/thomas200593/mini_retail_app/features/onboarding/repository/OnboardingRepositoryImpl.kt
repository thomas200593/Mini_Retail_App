package com.thomas200593.mini_retail_app.features.onboarding.repository

import com.thomas200593.mini_retail_app.core.data.local.datastore.DataStorePreferences
import javax.inject.Inject

class OnboardingRepositoryImpl @Inject constructor(
    private val appDataStore: DataStorePreferences,
): OnboardingRepository {
    override suspend fun hideOnboarding(){
        appDataStore.hideOnboarding()
    }
}
package com.thomas200593.mini_retail_app.features.onboarding.repository

import com.thomas200593.mini_retail_app.core.data.local.datastore.AppDataStorePreferences
import com.thomas200593.mini_retail_app.core.design_system.dispatchers.AppDispatchers
import com.thomas200593.mini_retail_app.core.design_system.dispatchers.Dispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class OnboardingRepositoryImpl @Inject constructor(
    private val appDataStore: AppDataStorePreferences,
    @Dispatcher(AppDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
): OnboardingRepository {
    override suspend fun hideOnboarding(){
        appDataStore.hideOnboarding()
    }
}
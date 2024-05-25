package com.thomas200593.mini_retail_app.features.app_config.repository

import com.thomas200593.mini_retail_app.core.data.local.datastore.AppDataStorePreferences
import com.thomas200593.mini_retail_app.features.app_config.entity.CurrentAppConfig
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class AppConfigRepositoryImpl @Inject constructor(
    appDataStore: AppDataStorePreferences,
):AppConfigRepository {
    override val currentAppConfigData: Flow<CurrentAppConfig> =
        appDataStore.currentAppConfigData
}
package com.thomas200593.mini_retail_app.features.app_config.repository

import com.thomas200593.mini_retail_app.core.data.local.datastore.DataStorePreferences
import com.thomas200593.mini_retail_app.features.app_config.entity.CurrentGeneralAppConfig
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class AppConfigRepositoryImpl @Inject constructor(
    appDataStore: DataStorePreferences,
):AppConfigRepository {
    override val currentGeneralAppConfigData: Flow<CurrentGeneralAppConfig> =
        appDataStore.currentGeneralAppConfigData
}
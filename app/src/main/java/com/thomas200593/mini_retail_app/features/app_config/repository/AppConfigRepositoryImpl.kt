package com.thomas200593.mini_retail_app.features.app_config.repository

import com.thomas200593.mini_retail_app.core.data.local.datastore.DataStorePreferences
import com.thomas200593.mini_retail_app.core.design_system.dispatchers.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.dispatchers.Dispatchers.Dispatchers
import com.thomas200593.mini_retail_app.features.app_config.entity.AppConfigGeneralMenu
import com.thomas200593.mini_retail_app.features.app_config.entity.CurrentGeneralAppConfig
import com.thomas200593.mini_retail_app.features.app_config.entity.getAppConfigGeneralMenu
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class AppConfigRepositoryImpl @Inject constructor(
    appDataStore: DataStorePreferences,
    @Dispatcher(Dispatchers.IO) private val ioDispatcher: CoroutineDispatcher
):AppConfigRepository {
    override val currentGeneralAppConfigData: Flow<CurrentGeneralAppConfig> =
        appDataStore.currentGeneralAppConfigData

    override suspend fun getAppConfigGeneralMenuData(): Set<AppConfigGeneralMenu> =
        withContext(ioDispatcher){
            getAppConfigGeneralMenu()
        }
}
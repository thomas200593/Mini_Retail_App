package com.thomas200593.mini_retail_app.features.app_config.repository

import com.thomas200593.mini_retail_app.core.data.local.datastore.DataStorePreferences
import com.thomas200593.mini_retail_app.core.data.local.session.SessionState
import com.thomas200593.mini_retail_app.core.design_system.dispatchers.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.dispatchers.Dispatchers.Dispatchers
import com.thomas200593.mini_retail_app.features.app_config.entity.ConfigCurrent
import com.thomas200593.mini_retail_app.features.app_config.navigation.DestinationAppConfig
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

private val TAG = AppConfigRepositoryImpl::class.simpleName

internal class AppConfigRepositoryImpl @Inject constructor(
    appDataStore: DataStorePreferences,
    @Dispatcher(Dispatchers.IO) private val ioDispatcher: CoroutineDispatcher
):AppConfigRepository {
    override val configCurrentData: Flow<ConfigCurrent> =
        appDataStore.configCurrentData

    override suspend fun getAppConfigMenuData(
        sessionState: SessionState
    ): Set<DestinationAppConfig> = withContext(ioDispatcher){
        Timber.d("Called : fun $TAG.getAppConfigMenuData()")
        when(sessionState){
            SessionState.Loading -> {
                emptySet()
            }
            is SessionState.Invalid -> {
                DestinationAppConfig.entries.filter { !it.usesAuth }.toSet()
            }
            is SessionState.Valid -> {
                DestinationAppConfig.entries.toSet()
            }
        }
    }
}
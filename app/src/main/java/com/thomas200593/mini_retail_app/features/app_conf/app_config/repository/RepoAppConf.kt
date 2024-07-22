package com.thomas200593.mini_retail_app.features.app_conf.app_config.repository

import com.thomas200593.mini_retail_app.core.data.local.datastore.DataStorePreferences
import com.thomas200593.mini_retail_app.core.data.local.session.SessionState
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatchers
import com.thomas200593.mini_retail_app.features.app_conf.app_config.entity.AppConfig
import com.thomas200593.mini_retail_app.features.app_conf.app_config.navigation.DestinationAppConfig
import com.thomas200593.mini_retail_app.features.initial.entity.FirstTimeStatus
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface RepoAppConf {
    val configCurrent: Flow<AppConfig.ConfigCurrent>
    val firstTimeStatus: Flow<FirstTimeStatus>
    suspend fun setFirstTimeStatus(firstTimeStatus: FirstTimeStatus)
    suspend fun getMenuData(sessionState: SessionState): Set<DestinationAppConfig>
}

internal class RepoAppConfImpl @Inject constructor(
    private val dataStore: DataStorePreferences,
    @Dispatcher(Dispatchers.Dispatchers.IO) private val ioDispatcher: CoroutineDispatcher
): RepoAppConf {
    override val configCurrent: Flow<AppConfig.ConfigCurrent> = dataStore.configCurrent
    override val firstTimeStatus: Flow<FirstTimeStatus> = dataStore.firstTimeStatus
    override suspend fun setFirstTimeStatus(firstTimeStatus: FirstTimeStatus) {
        dataStore.setFirstTimeStatus(firstTimeStatus)
    }
    override suspend fun getMenuData(sessionState: SessionState): Set<DestinationAppConfig> =
        withContext(ioDispatcher){
            when(sessionState){
                SessionState.Loading -> { emptySet() }
                is SessionState.Invalid -> {
                    DestinationAppConfig.entries.filterNot { it.usesAuth }.toSet()
                }
                is SessionState.Valid -> {
                    DestinationAppConfig.entries.toSet()
                }
            }
        }
}
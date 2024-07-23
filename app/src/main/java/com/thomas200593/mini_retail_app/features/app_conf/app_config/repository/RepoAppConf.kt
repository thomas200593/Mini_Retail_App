package com.thomas200593.mini_retail_app.features.app_conf.app_config.repository

import com.thomas200593.mini_retail_app.core.data.local.datastore.DataStorePreferences
import com.thomas200593.mini_retail_app.core.data.local.session.SessionState
import com.thomas200593.mini_retail_app.core.data.local.session.SessionState.Invalid
import com.thomas200593.mini_retail_app.core.data.local.session.SessionState.Loading
import com.thomas200593.mini_retail_app.core.data.local.session.SessionState.Valid
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatchers.Dispatchers.IO
import com.thomas200593.mini_retail_app.features.app_conf.app_config.entity.AppConfig.ConfigCurrent
import com.thomas200593.mini_retail_app.features.app_conf.app_config.navigation.DestAppConfig
import com.thomas200593.mini_retail_app.features.app_conf.app_config.navigation.DestAppConfig.entries
import com.thomas200593.mini_retail_app.features.initial.initial.entity.FirstTimeStatus
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface RepoAppConf {
    val configCurrent: Flow<ConfigCurrent>
    val firstTimeStatus: Flow<FirstTimeStatus>
    suspend fun setFirstTimeStatus(firstTimeStatus: FirstTimeStatus)
    suspend fun getMenuData(sessionState: SessionState): Set<DestAppConfig>
}

internal class RepoAppConfImpl @Inject constructor(
    private val dataStore: DataStorePreferences,
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher
): RepoAppConf {
    override val configCurrent: Flow<ConfigCurrent> = dataStore.configCurrent
    override val firstTimeStatus: Flow<FirstTimeStatus> = dataStore.firstTimeStatus
    override suspend fun setFirstTimeStatus(firstTimeStatus: FirstTimeStatus)
    { dataStore.setFirstTimeStatus(firstTimeStatus) }
    override suspend fun getMenuData(sessionState: SessionState): Set<DestAppConfig> =
        withContext(ioDispatcher){
            when(sessionState){
                Loading -> { emptySet() }
                is Invalid -> { entries.filterNot { it.usesAuth }.toSet() }
                is Valid -> { entries.toSet() }
            }
        }
}
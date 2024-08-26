package com.thomas200593.mini_retail_app.features.app_conf.app_config.repository

import com.thomas200593.mini_retail_app.core.data.local.datastore.DataStorePreferences
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatchers.Dispatchers.IO
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.di.Dispatcher
import com.thomas200593.mini_retail_app.features.app_conf.app_config.entity.AppConfig.ConfigCurrent
import com.thomas200593.mini_retail_app.features.app_conf.app_config.navigation.DestAppConfig
import com.thomas200593.mini_retail_app.features.initial.initial.entity.FirstTimeStatus
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

interface RepoAppConf {
    val configCurrent: Flow<ConfigCurrent>
    val firstTimeStatus: Flow<FirstTimeStatus>
    suspend fun setFirstTimeStatus(firstTimeStatus: FirstTimeStatus)
    fun getMenuData(): Flow<Set<DestAppConfig>>
}

internal class RepoAppConfImpl @Inject constructor(
    private val dataStore: DataStorePreferences,
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher
): RepoAppConf {
    override val configCurrent: Flow<ConfigCurrent> = dataStore.configCurrent.flowOn(ioDispatcher)
    override val firstTimeStatus: Flow<FirstTimeStatus> = dataStore.firstTimeStatus.flowOn(ioDispatcher)
    override suspend fun setFirstTimeStatus(firstTimeStatus: FirstTimeStatus) {
        dataStore.setFirstTimeStatus(firstTimeStatus)
    }
    override fun getMenuData() = flowOf(DestAppConfig.entries.toSet()).flowOn(ioDispatcher)
}
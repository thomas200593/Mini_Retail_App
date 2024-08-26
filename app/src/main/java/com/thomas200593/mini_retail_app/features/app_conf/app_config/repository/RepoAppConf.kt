package com.thomas200593.mini_retail_app.features.app_conf.app_config.repository

import com.thomas200593.mini_retail_app.core.data.local.datastore.DataStorePreferences
import com.thomas200593.mini_retail_app.features.app_conf.app_config.entity.AppConfig.ConfigCurrent
import com.thomas200593.mini_retail_app.features.app_conf.app_config.navigation.DestAppConfig
import com.thomas200593.mini_retail_app.features.initial.initial.entity.FirstTimeStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

interface RepoAppConf {
    val configCurrent: Flow<ConfigCurrent>
    val firstTimeStatus: Flow<FirstTimeStatus>
    suspend fun setFirstTimeStatus(firstTimeStatus: FirstTimeStatus)
    fun getMenuData(): Flow<Set<DestAppConfig>>
}

internal class RepoAppConfImpl @Inject constructor(
    private val dataStore: DataStorePreferences
): RepoAppConf {
    override val configCurrent: Flow<ConfigCurrent> = dataStore.configCurrent
    override val firstTimeStatus: Flow<FirstTimeStatus> = dataStore.firstTimeStatus
    override suspend fun setFirstTimeStatus(firstTimeStatus: FirstTimeStatus) {
        dataStore.setFirstTimeStatus(firstTimeStatus)
    }
    override fun getMenuData() = flowOf(DestAppConfig.entries.toSet())
}
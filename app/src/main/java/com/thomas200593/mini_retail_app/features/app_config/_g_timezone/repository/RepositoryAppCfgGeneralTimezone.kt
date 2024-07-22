package com.thomas200593.mini_retail_app.features.app_config._g_timezone.repository

import com.thomas200593.mini_retail_app.core.data.local.datastore.DataStorePreferences
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatchers
import com.thomas200593.mini_retail_app.core.design_system.util.HelperTimezone
import com.thomas200593.mini_retail_app.features.app_config._g_timezone.entity.Timezone
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface RepositoryAppCfgGeneralTimezone {
    suspend fun getTimezones(): List<Timezone>
    suspend fun setTimezone(timezone: Timezone)
}

internal class RepositoryImplAppCfgGeneralTimezone @Inject constructor(
    private val dataStore: DataStorePreferences,
    @Dispatcher(Dispatchers.Dispatchers.IO) private val ioDispatcher: CoroutineDispatcher
): RepositoryAppCfgGeneralTimezone {
    override suspend fun getTimezones(): List<Timezone> = withContext(ioDispatcher){
        HelperTimezone.getTimezoneList()
    }

    override suspend fun setTimezone(timezone: Timezone) {
        dataStore.setTimezone(timezone)
    }
}
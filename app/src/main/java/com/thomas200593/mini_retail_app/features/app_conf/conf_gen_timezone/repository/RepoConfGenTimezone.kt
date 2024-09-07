package com.thomas200593.mini_retail_app.features.app_conf.conf_gen_timezone.repository

import com.thomas200593.mini_retail_app.core.data.local.datastore.DataStorePreferences
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatchers.Dispatchers.IO
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.di.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.util.HlpDatetime.getTimezoneList
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_timezone.entity.Timezone
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

interface RepoConfGenTimezone {
    fun getTimezones(): Flow<List<Timezone>>
    suspend fun setTimezone(timezone: Timezone)
}

internal class RepoImplConfGenTimezone @Inject constructor(
    private val dataStore: DataStorePreferences,
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher
): RepoConfGenTimezone {
    override fun getTimezones(): Flow<List<Timezone>> =
        flow{ emit(getTimezoneList()) }.flowOn(ioDispatcher)

    override suspend fun setTimezone(timezone: Timezone) {
        dataStore.setTimezone(timezone)
    }
}
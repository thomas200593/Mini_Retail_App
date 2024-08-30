package com.thomas200593.mini_retail_app.features.app_conf.conf_gen_dynamic_color.repository

import com.thomas200593.mini_retail_app.core.data.local.datastore.DataStorePreferences
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatchers.Dispatchers.IO
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.di.Dispatcher
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_dynamic_color.entity.DynamicColor
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_dynamic_color.entity.DynamicColor.entries
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

interface RepoConfGenDynamicColor{
    fun getDynamicColors(): Flow<Set<DynamicColor>>
    suspend fun setDynamicColor(dynamicColor: DynamicColor)
}

internal class RepoImplConfGenDynamicColor @Inject constructor(
    private val dataStore: DataStorePreferences,
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher
): RepoConfGenDynamicColor{
    override fun getDynamicColors(): Flow<Set<DynamicColor>> =
        flow{ emit(entries.toSet()) }.flowOn(ioDispatcher)

    override suspend fun setDynamicColor(dynamicColor: DynamicColor) {
        dataStore.setDynamicColor(dynamicColor)
    }
}
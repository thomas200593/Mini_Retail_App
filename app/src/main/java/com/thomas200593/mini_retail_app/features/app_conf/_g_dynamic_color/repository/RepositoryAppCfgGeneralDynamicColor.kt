package com.thomas200593.mini_retail_app.features.app_conf._g_dynamic_color.repository

import com.thomas200593.mini_retail_app.core.data.local.datastore.DataStorePreferences
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatchers
import com.thomas200593.mini_retail_app.features.app_conf._g_dynamic_color.entity.DynamicColor
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface RepositoryAppCfgGeneralDynamicColor{
    suspend fun getDynamicColors(): Set<DynamicColor>
    suspend fun setDynamicColor(dynamicColor: DynamicColor)
}

internal class RepositoryImplAppCfgGeneralDynamicColor @Inject constructor(
    private val dataStore: DataStorePreferences,
    @Dispatcher(Dispatchers.Dispatchers.IO) private val ioDispatcher: CoroutineDispatcher
): RepositoryAppCfgGeneralDynamicColor{
    override suspend fun getDynamicColors(): Set<DynamicColor> = withContext(ioDispatcher){
        DynamicColor.entries.toSet()
    }

    override suspend fun setDynamicColor(dynamicColor: DynamicColor) {
        dataStore.setDynamicColor(dynamicColor)
    }
}
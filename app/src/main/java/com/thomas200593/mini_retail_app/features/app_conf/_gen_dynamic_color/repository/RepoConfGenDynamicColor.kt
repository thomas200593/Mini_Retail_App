package com.thomas200593.mini_retail_app.features.app_conf._gen_dynamic_color.repository

import com.thomas200593.mini_retail_app.core.data.local.datastore.DataStorePreferences
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatchers.Dispatchers.IO
import com.thomas200593.mini_retail_app.features.app_conf._gen_dynamic_color.entity.DynamicColor
import com.thomas200593.mini_retail_app.features.app_conf._gen_dynamic_color.entity.DynamicColor.entries
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface RepoConfGenDynamicColor{
    suspend fun getDynamicColors(): Set<DynamicColor>
    suspend fun setDynamicColor(dynamicColor: DynamicColor)
}

internal class RepoImplConfGenDynamicColor @Inject constructor(
    private val dataStore: DataStorePreferences,
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher
): RepoConfGenDynamicColor{
    override suspend fun getDynamicColors(): Set<DynamicColor> = withContext(ioDispatcher){ entries.toSet() }
    override suspend fun setDynamicColor(dynamicColor: DynamicColor) { dataStore.setDynamicColor(dynamicColor) }
}
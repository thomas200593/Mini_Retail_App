package com.thomas200593.mini_retail_app.features.app_conf._g_font_size.repository

import com.thomas200593.mini_retail_app.core.data.local.datastore.DataStorePreferences
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatchers
import com.thomas200593.mini_retail_app.features.app_conf._g_font_size.entity.FontSize
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface RepositoryAppCfgGeneralFontSize{
    suspend fun getFontSizes(): Set<FontSize>
    suspend fun setFontSize(fontSize: FontSize)
}

internal class RepositoryImplAppCfgGeneralFontSize @Inject constructor(
    private val dataStore: DataStorePreferences,
    @Dispatcher(Dispatchers.Dispatchers.IO) private val ioDispatcher: CoroutineDispatcher
): RepositoryAppCfgGeneralFontSize{
    override suspend fun getFontSizes(): Set<FontSize> = withContext(ioDispatcher){
        FontSize.entries.toSet()
    }

    override suspend fun setFontSize(fontSize: FontSize) {
        dataStore.setFontSize(fontSize)
    }
}
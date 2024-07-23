package com.thomas200593.mini_retail_app.features.app_conf.conf_gen_font_size.repository

import com.thomas200593.mini_retail_app.core.data.local.datastore.DataStorePreferences
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatchers.Dispatchers.IO
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_font_size.entity.FontSize
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_font_size.entity.FontSize.entries
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface RepoConfGenFontSize{
    suspend fun getFontSizes(): Set<FontSize>
    suspend fun setFontSize(fontSize: FontSize)
}

internal class RepoImplConfGenFontSize @Inject constructor(
    private val dataStore: DataStorePreferences,
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher
): RepoConfGenFontSize{
    override suspend fun getFontSizes(): Set<FontSize> = withContext(ioDispatcher){ entries.toSet() }
    override suspend fun setFontSize(fontSize: FontSize) { dataStore.setFontSize(fontSize) }
}
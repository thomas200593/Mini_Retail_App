package com.thomas200593.mini_retail_app.features.app_conf._gen_language.repository

import com.thomas200593.mini_retail_app.core.data.local.datastore.DataStorePreferences
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatchers.Dispatchers.IO
import com.thomas200593.mini_retail_app.features.app_conf._gen_language.entity.Language
import com.thomas200593.mini_retail_app.features.app_conf._gen_language.entity.Language.entries
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface RepoConfGenLanguage{
    suspend fun getLanguages(): Set<Language>
    suspend fun setLanguage(language: Language)
}

internal class RepoImplConfGenLanguage @Inject constructor(
    private val dataStore: DataStorePreferences,
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher
): RepoConfGenLanguage{
    override suspend fun getLanguages(): Set<Language> = withContext(ioDispatcher){ entries.toSet() }
    override suspend fun setLanguage(language: Language) { dataStore.setLanguage(language) }
}
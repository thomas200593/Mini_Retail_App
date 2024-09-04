package com.thomas200593.mini_retail_app.features.app_conf.conf_gen_language.repository

import com.thomas200593.mini_retail_app.core.data.local.datastore.DataStorePreferences
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatchers.Dispatchers.IO
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.di.Dispatcher
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_language.entity.Language
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_language.entity.Language.entries
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

interface RepoConfGenLanguage{
    fun getLanguages(): Flow<Set<Language>>
    suspend fun setLanguage(language: Language)
}

internal class RepoImplConfGenLanguage @Inject constructor(
    private val dataStore: DataStorePreferences,
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher
): RepoConfGenLanguage{
    override fun getLanguages(): Flow<Set<Language>> =
        flow{ emit(entries.toSet()) }.flowOn(ioDispatcher)

    override suspend fun setLanguage(language: Language) {
        dataStore.setLanguage(language)
    }
}
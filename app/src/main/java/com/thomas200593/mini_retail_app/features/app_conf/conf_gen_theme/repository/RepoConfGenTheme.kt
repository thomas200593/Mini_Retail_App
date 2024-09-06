package com.thomas200593.mini_retail_app.features.app_conf.conf_gen_theme.repository

import com.thomas200593.mini_retail_app.core.data.local.datastore.DataStorePreferences
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatchers.Dispatchers.IO
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.di.Dispatcher
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_theme.entity.Theme
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_theme.entity.Theme.entries
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

interface RepoConfGenTheme {
    fun getThemes(): Flow<Set<Theme>>
    suspend fun setTheme(theme: Theme)
}

internal class RepoImplConfGenTheme @Inject constructor(
    private val dataStore: DataStorePreferences,
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher
): RepoConfGenTheme {
    override fun getThemes(): Flow<Set<Theme>> =
        flowOf(entries.toSet()).flowOn(ioDispatcher)

    override suspend fun setTheme(theme: Theme) {
        dataStore.setTheme(theme)
    }
}
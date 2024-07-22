package com.thomas200593.mini_retail_app.features.app_config.app_cfg_general_theme.repository

import com.thomas200593.mini_retail_app.core.data.local.datastore.DataStorePreferences
import com.thomas200593.mini_retail_app.core.design_system.dispatchers.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.dispatchers.Dispatchers
import com.thomas200593.mini_retail_app.features.app_config.app_cfg_general_theme.entity.Theme
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface RepositoryAppCfgGeneralTheme {
    suspend fun getThemes(): Set<Theme>
    suspend fun setTheme(theme: Theme)
}

internal class RepositoryImplAppCfgGeneralTheme @Inject constructor(
    private val dataStore: DataStorePreferences,
    @Dispatcher(Dispatchers.Dispatchers.IO) private val ioDispatcher: CoroutineDispatcher
): RepositoryAppCfgGeneralTheme {
    override suspend fun getThemes(): Set<Theme> = withContext(ioDispatcher){
        Theme.entries.toSet()
    }

    override suspend fun setTheme(theme: Theme) {
        dataStore.setTheme(theme)
    }
}
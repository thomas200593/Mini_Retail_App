package com.thomas200593.mini_retail_app.features.app_config.cfg_general.repository

import com.thomas200593.mini_retail_app.core.data.local.session.SessionState
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatchers
import com.thomas200593.mini_retail_app.features.app_config.cfg_general.navigation.DestinationConfigGeneral
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface ConfigGeneralRepository {
    suspend fun getMenuData(sessionState: SessionState): Set<DestinationConfigGeneral>
}

internal class ConfigGeneralRepositoryImpl @Inject constructor(
    @Dispatcher(Dispatchers.Dispatchers.IO) private val ioDispatcher: CoroutineDispatcher
): ConfigGeneralRepository {
    override suspend fun getMenuData(
        sessionState: SessionState
    ): Set<DestinationConfigGeneral> = withContext(ioDispatcher) {
        when (sessionState) {
            SessionState.Loading -> { emptySet() }
            is SessionState.Invalid -> {
                DestinationConfigGeneral.entries.filterNot { it.usesAuth }.toSet()
            }
            is SessionState.Valid -> {
                DestinationConfigGeneral.entries.toSet()
            }
        }
    }
}
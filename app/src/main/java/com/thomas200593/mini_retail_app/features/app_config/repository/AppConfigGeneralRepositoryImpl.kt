package com.thomas200593.mini_retail_app.features.app_config.repository

import com.thomas200593.mini_retail_app.core.data.local.session.SessionState
import com.thomas200593.mini_retail_app.core.design_system.dispatchers.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.dispatchers.Dispatchers
import com.thomas200593.mini_retail_app.features.app_config.navigation.DestinationAppConfigGeneral
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

private val TAG = AppConfigGeneralRepositoryImpl::class.simpleName

internal class AppConfigGeneralRepositoryImpl @Inject constructor(
    @Dispatcher(Dispatchers.Dispatchers.IO) private val ioDispatcher: CoroutineDispatcher
): AppConfigGeneralRepository {
    override suspend fun getAppConfigGeneralMenuData(
        sessionState: SessionState
    ): Set<DestinationAppConfigGeneral> = withContext(ioDispatcher) {
        Timber.d("Called : fun $TAG.getAppConfigGeneralMenuData()")
        when (sessionState) {
            SessionState.Loading -> {
                emptySet()
            }

            is SessionState.Invalid -> {
                DestinationAppConfigGeneral.entries.filter { !it.usesAuth }.toSet()
            }

            is SessionState.Valid -> {
                DestinationAppConfigGeneral.entries.toSet()
            }
        }
    }
}
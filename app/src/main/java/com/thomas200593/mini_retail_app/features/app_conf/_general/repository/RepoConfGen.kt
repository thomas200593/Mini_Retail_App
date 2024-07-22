package com.thomas200593.mini_retail_app.features.app_conf._general.repository

import com.thomas200593.mini_retail_app.core.data.local.session.SessionState
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatchers.Dispatchers.IO
import com.thomas200593.mini_retail_app.features.app_conf._general.navigation.DestConfGen
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface RepoConfGen {
    suspend fun getMenuData(sessionState: SessionState): Set<DestConfGen>
}

internal class RepoImplConfGen @Inject constructor(
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher
): RepoConfGen {
    override suspend fun getMenuData(sessionState: SessionState): Set<DestConfGen> =
        withContext(ioDispatcher) {
            when (sessionState) {
                SessionState.Loading -> { emptySet() }
                is SessionState.Invalid -> { DestConfGen.entries.filterNot { it.usesAuth }.toSet() }
                is SessionState.Valid -> { DestConfGen.entries.toSet() }
            }
    }
}
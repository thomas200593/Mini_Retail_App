package com.thomas200593.mini_retail_app.features.app_conf.conf_gen.repository

import com.thomas200593.mini_retail_app.core.data.local.session.SessionState
import com.thomas200593.mini_retail_app.core.data.local.session.SessionState.*
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatchers.Dispatchers.IO
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen.navigation.DestConfGen
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen.navigation.DestConfGen.entries
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
                Loading -> { emptySet() }
                is Invalid -> { entries.filterNot { it.usesAuth }.toSet() }
                is Valid -> { entries.toSet() }
            }
    }
}
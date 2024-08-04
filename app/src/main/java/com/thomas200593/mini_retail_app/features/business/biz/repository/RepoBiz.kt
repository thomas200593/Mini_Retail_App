package com.thomas200593.mini_retail_app.features.business.biz.repository

import com.thomas200593.mini_retail_app.core.data.local.session.SessionState
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatchers.Dispatchers.IO
import com.thomas200593.mini_retail_app.features.business.biz.navigation.DestBiz
import com.thomas200593.mini_retail_app.features.business.biz.navigation.DestBiz.entries
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface RepoBiz {
    suspend fun getMenuData(sessionState: SessionState): Set<DestBiz>
}

internal class RepoBizImpl @Inject constructor(
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher
): RepoBiz {
    override suspend fun getMenuData(sessionState: SessionState): Set<DestBiz> =
        withContext(ioDispatcher){
            when(sessionState){
                SessionState.Loading -> emptySet()
                is SessionState.Invalid -> entries.filterNot { it.usesAuth }.toSet()
                is SessionState.Valid -> entries.toSet()
            }
        }
}
package com.thomas200593.mini_retail_app.features.business.biz_m_data.repository

import com.thomas200593.mini_retail_app.core.data.local.session.SessionState
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatchers
import com.thomas200593.mini_retail_app.features.business.biz_m_data.navigation.DestMData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface RepoMData {
    suspend fun getMenuData(sessionState: SessionState): Set<DestMData>
}

internal class RepoMDataImpl @Inject constructor(
    @Dispatcher(Dispatchers.Dispatchers.IO) private val ioDispatcher: CoroutineDispatcher
): RepoMData {
    override suspend fun getMenuData(sessionState: SessionState): Set<DestMData> =
        withContext(ioDispatcher){
            when(sessionState){
                SessionState.Loading -> { emptySet() }
                is SessionState.Invalid -> { DestMData.entries.filterNot { it.usesAuth }.toSet() }
                is SessionState.Valid -> { DestMData.entries.toSet() }
            }
        }
}
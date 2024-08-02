package com.thomas200593.mini_retail_app.features.business.biz_m_data.repository

import com.thomas200593.mini_retail_app.core.data.local.session.SessionState
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatchers
import com.thomas200593.mini_retail_app.features.business.biz_m_data.navigation.DestMasterData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface RepoMasterData {
    suspend fun getMenuData(sessionState: SessionState): Set<DestMasterData>
}

internal class RepoMasterDataImpl @Inject constructor(
    @Dispatcher(Dispatchers.Dispatchers.IO) private val ioDispatcher: CoroutineDispatcher
): RepoMasterData {
    override suspend fun getMenuData(sessionState: SessionState): Set<DestMasterData> =
        withContext(ioDispatcher){
            when(sessionState){
                SessionState.Loading -> { emptySet() }
                is SessionState.Invalid -> { DestMasterData.entries.filterNot { it.usesAuth }.toSet() }
                is SessionState.Valid -> { DestMasterData.entries.toSet() }
            }
        }
}
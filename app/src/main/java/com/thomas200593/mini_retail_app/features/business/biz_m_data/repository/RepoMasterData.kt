package com.thomas200593.mini_retail_app.features.business.biz_m_data.repository

import com.thomas200593.mini_retail_app.core.data.local.session.SessionState
import com.thomas200593.mini_retail_app.core.data.local.session.SessionState.Invalid
import com.thomas200593.mini_retail_app.core.data.local.session.SessionState.Loading
import com.thomas200593.mini_retail_app.core.data.local.session.SessionState.Valid
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.di.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatchers.Dispatchers.IO
import com.thomas200593.mini_retail_app.features.business.biz_m_data.navigation.DestMasterData
import com.thomas200593.mini_retail_app.features.business.biz_m_data.navigation.DestMasterData.entries
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface RepoMasterData {
    suspend fun getMenuData(sessionState: SessionState): Set<DestMasterData>
}

internal class RepoMasterDataImpl @Inject constructor(
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher
): RepoMasterData {
    override suspend fun getMenuData(sessionState: SessionState): Set<DestMasterData> =
        withContext(ioDispatcher){
            when(sessionState){
                Loading -> emptySet()
                is Invalid -> entries.filterNot { it.usesAuth }.toSet()
                is Valid -> entries.toSet()
            }
        }
}
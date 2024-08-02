package com.thomas200593.mini_retail_app.features.business.repository

import com.thomas200593.mini_retail_app.core.data.local.session.SessionState
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatchers
import com.thomas200593.mini_retail_app.features.business.navigation.DestinationMasterData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface MasterDataRepository {
    suspend fun getMenuData(sessionState: SessionState): Set<DestinationMasterData>
}

internal class MasterDataRepositoryImpl @Inject constructor(
    @Dispatcher(Dispatchers.Dispatchers.IO) private val ioDispatcher: CoroutineDispatcher
): MasterDataRepository {
    override suspend fun getMenuData(sessionState: SessionState): Set<DestinationMasterData> =
        withContext(ioDispatcher){
            when(sessionState){
                SessionState.Loading -> { emptySet() }
                is SessionState.Invalid -> { DestinationMasterData.entries.filterNot { it.usesAuth }.toSet() }
                is SessionState.Valid -> { DestinationMasterData.entries.toSet() }
            }
        }
}
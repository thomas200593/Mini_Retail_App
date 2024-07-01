package com.thomas200593.mini_retail_app.features.business.repository

import com.thomas200593.mini_retail_app.core.data.local.session.SessionState
import com.thomas200593.mini_retail_app.core.design_system.dispatchers.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.dispatchers.Dispatchers
import com.thomas200593.mini_retail_app.features.business.navigation.DestinationBusinessMasterData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class MasterDataRepositoryImpl @Inject constructor(
    @Dispatcher(Dispatchers.Dispatchers.IO) private val ioDispatcher: CoroutineDispatcher
): MasterDataRepository {
    override suspend fun getBusinessMasterDataMenuData(
        sessionState: SessionState
    ): Set<DestinationBusinessMasterData> = withContext(ioDispatcher){
        when(sessionState){
            SessionState.Loading -> {
                emptySet()
            }
            is SessionState.Invalid -> {
                DestinationBusinessMasterData.entries.filterNot { it.usesAuth }.toSet()
            }
            is SessionState.Valid -> {
                DestinationBusinessMasterData.entries.toSet()
            }
        }
    }
}
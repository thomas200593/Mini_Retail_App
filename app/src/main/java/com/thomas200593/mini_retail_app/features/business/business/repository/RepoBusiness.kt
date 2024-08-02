package com.thomas200593.mini_retail_app.features.business.business.repository

import com.thomas200593.mini_retail_app.core.data.local.session.SessionState
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatchers.Dispatchers.IO
import com.thomas200593.mini_retail_app.features.business.business.navigation.DestinationBusiness
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface RepoBusiness {
    suspend fun getMenuData(sessionState: SessionState): Set<DestinationBusiness>
}

internal class RepoBusinessImpl @Inject constructor(
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher
): RepoBusiness {
    override suspend fun getMenuData(sessionState: SessionState): Set<DestinationBusiness> =
        withContext(ioDispatcher){
            when(sessionState){
                SessionState.Loading -> { emptySet() }
                is SessionState.Invalid ->
                    { DestinationBusiness.entries.filterNot { it.usesAuth }.toSet() }
                is SessionState.Valid -> { DestinationBusiness.entries.toSet() }
            }
        }
}
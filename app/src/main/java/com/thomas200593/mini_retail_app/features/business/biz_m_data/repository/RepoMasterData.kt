package com.thomas200593.mini_retail_app.features.business.biz_m_data.repository

import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatchers.Dispatchers.IO
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.di.Dispatcher
import com.thomas200593.mini_retail_app.features.business.biz_m_data.navigation.DestMasterData
import com.thomas200593.mini_retail_app.features.business.biz_m_data.navigation.DestMasterData.entries
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

interface RepoMasterData {
    fun getMenuData(): Flow<Set<DestMasterData>>
}

internal class RepoMasterDataImpl @Inject constructor(
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher
): RepoMasterData {
    override fun getMenuData(): Flow<Set<DestMasterData>> = flowOf(entries.toSet()).flowOn(ioDispatcher)
}
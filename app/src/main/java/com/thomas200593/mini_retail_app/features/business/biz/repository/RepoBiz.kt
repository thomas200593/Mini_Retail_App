package com.thomas200593.mini_retail_app.features.business.biz.repository

import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatchers.Dispatchers.IO
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.di.Dispatcher
import com.thomas200593.mini_retail_app.features.business.biz.navigation.DestBiz
import com.thomas200593.mini_retail_app.features.business.biz.navigation.DestBiz.entries
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

interface RepoBiz {
    fun getMenuData(): Flow<Set<DestBiz>>
}

internal class RepoBizImpl @Inject constructor(
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher
): RepoBiz {
    override fun getMenuData(): Flow<Set<DestBiz>> = flowOf(entries.toSet()).flowOn(ioDispatcher)
}
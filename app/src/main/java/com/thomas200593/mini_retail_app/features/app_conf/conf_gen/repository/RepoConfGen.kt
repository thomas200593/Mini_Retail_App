package com.thomas200593.mini_retail_app.features.app_conf.conf_gen.repository

import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatchers.Dispatchers.IO
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.di.Dispatcher
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen.navigation.DestConfGen
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

interface RepoConfGen {
    fun getMenuData(): Flow<Set<DestConfGen>>
}

internal class RepoImplConfGen @Inject constructor(
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher
): RepoConfGen {
    override fun getMenuData() = flowOf(DestConfGen.entries.toSet()).flowOn(ioDispatcher)
}
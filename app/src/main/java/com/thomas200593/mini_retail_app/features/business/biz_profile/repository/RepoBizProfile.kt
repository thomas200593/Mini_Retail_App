package com.thomas200593.mini_retail_app.features.business.biz_profile.repository

import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.di.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatchers.Dispatchers.IO
import com.thomas200593.mini_retail_app.features.business.biz_profile.dao.DaoBizProfile
import com.thomas200593.mini_retail_app.features.business.biz_profile.entity.BizProfile
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

interface RepoBizProfile{
    fun getBizProfile(): Flow<BizProfile>
    suspend fun setBizProfile(bizProfile: BizProfile): Long
}

class RepoBizProfileImpl @Inject constructor(
    private val dao: DaoBizProfile,
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher
): RepoBizProfile {
    override fun getBizProfile(): Flow<BizProfile> =
        dao.getBizProfile().flowOn(ioDispatcher)
    override suspend fun setBizProfile(bizProfile: BizProfile): Long =
        dao.setInitBizProfile(bizProfile)
}
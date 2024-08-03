package com.thomas200593.mini_retail_app.features.business.biz_profile.repository

import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatchers.Dispatchers.IO
import com.thomas200593.mini_retail_app.features.business.biz_profile.dao.BusinessProfileDao
import com.thomas200593.mini_retail_app.features.business.biz_profile.entity.BusinessProfile
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

interface RepoBizProfile{
    fun getBusinessProfile(): Flow<BusinessProfile?>
    suspend fun setBusinessProfile(businessProfile: BusinessProfile): Long
}

class RepoBizProfileImpl @Inject constructor(
    private val dao: BusinessProfileDao,
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher
): RepoBizProfile {
    override fun getBusinessProfile(): Flow<BusinessProfile?> =
        dao.getBusinessProfile().flowOn(ioDispatcher)
    override suspend fun setBusinessProfile(businessProfile: BusinessProfile): Long =
        dao.setInitialBizProfile(businessProfile)
}
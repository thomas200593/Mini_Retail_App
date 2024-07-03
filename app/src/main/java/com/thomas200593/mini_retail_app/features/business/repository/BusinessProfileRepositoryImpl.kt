package com.thomas200593.mini_retail_app.features.business.repository

import com.thomas200593.mini_retail_app.core.design_system.dispatchers.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.dispatchers.Dispatchers
import com.thomas200593.mini_retail_app.features.business.dao.BusinessProfileDao
import com.thomas200593.mini_retail_app.features.business.entity.BusinessProfile
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class BusinessProfileRepositoryImpl @Inject constructor(
    private val dao: BusinessProfileDao,
    @Dispatcher(Dispatchers.Dispatchers.IO) private val ioDispatcher: CoroutineDispatcher
): BusinessProfileRepository {
    override fun getBusinessProfile(): Flow<BusinessProfile?> =
        dao.getBusinessProfile().flowOn(ioDispatcher)
}
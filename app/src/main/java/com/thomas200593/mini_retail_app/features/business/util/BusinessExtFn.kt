package com.thomas200593.mini_retail_app.features.business.util

import com.thomas200593.mini_retail_app.core.design_system.dispatchers.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.dispatchers.Dispatchers.Dispatchers.IO
import com.thomas200593.mini_retail_app.features.business.entity.BusinessProfile
import com.thomas200593.mini_retail_app.features.business.entity.dto.BusinessProfileSummary
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface BusinessExtFn{
    suspend fun bizProfileToBizProfileSummary(businessProfile: BusinessProfile): BusinessProfileSummary
}

class BusinessExtFnImpl @Inject constructor(
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher
): BusinessExtFn {
    override suspend fun bizProfileToBizProfileSummary(
        businessProfile: BusinessProfile
    ): BusinessProfileSummary = withContext(ioDispatcher){
        BusinessProfileSummary(
            businessProfile.seqId,
            businessProfile.genId,
            businessProfile.bizIdentity?.bizName,
            businessProfile.bizIdentity?.industries,
            businessProfile.auditTrail
        )
    }
}
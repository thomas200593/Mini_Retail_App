package com.thomas200593.mini_retail_app.features.business.util

import com.thomas200593.mini_retail_app.core.design_system.dispatchers.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.dispatchers.Dispatchers.Dispatchers.IO
import com.thomas200593.mini_retail_app.features.business.entity.business_profile.BizIdentity
import com.thomas200593.mini_retail_app.features.business.entity.business_profile.BusinessProfile
import com.thomas200593.mini_retail_app.features.business.entity.business_profile.dto.BusinessProfileSummary
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface BusinessExtFn{
    suspend fun bizProfileToBizProfileSummary(businessProfile: BusinessProfile): BusinessProfileSummary
    suspend fun bizProfileSummaryToBusinessProfile(businessProfileSummary: BusinessProfileSummary): BusinessProfile
}

class BusinessExtFnImpl @Inject constructor(
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher
): BusinessExtFn {
    override suspend fun bizProfileToBizProfileSummary(
        businessProfile: BusinessProfile
    ): BusinessProfileSummary = withContext(ioDispatcher){
        BusinessProfileSummary(
            seqId = businessProfile.seqId,
            genId = businessProfile.genId,
            auditTrail = businessProfile.auditTrail,
            bizName = businessProfile.bizIdentity?.bizName,
            bizIndustry = businessProfile.bizIdentity?.industries
        )
    }

    override suspend fun bizProfileSummaryToBusinessProfile(
        businessProfileSummary: BusinessProfileSummary
    ): BusinessProfile = withContext(ioDispatcher){
        BusinessProfile(
            seqId = businessProfileSummary.seqId,
            genId = businessProfileSummary.genId,
            auditTrail = businessProfileSummary.auditTrail,
            bizIdentity = BizIdentity(
                bizName = businessProfileSummary.bizName,
                industries = businessProfileSummary.bizIndustry
            )
        )
    }
}
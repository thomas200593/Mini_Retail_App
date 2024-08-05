package com.thomas200593.mini_retail_app.features.business.util

import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatchers.Dispatchers.IO
import com.thomas200593.mini_retail_app.features.business.biz_profile.entity.BizIdentity
import com.thomas200593.mini_retail_app.features.business.biz_profile.entity.BusinessProfile
import com.thomas200593.mini_retail_app.features.business.biz_profile.entity.dto.BizProfileSummary
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface ExtFnBusiness{
    suspend fun bizProfileToBizProfileSummary(businessProfile: BusinessProfile): BizProfileSummary
    suspend fun bizProfileSummaryToBusinessProfile(businessProfileSummary: BizProfileSummary): BusinessProfile
}

class BusinessExtFnImpl @Inject constructor(
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher
): ExtFnBusiness {
    override suspend fun bizProfileToBizProfileSummary(
        businessProfile: BusinessProfile
    ): BizProfileSummary = withContext(ioDispatcher){
        BizProfileSummary(
            seqId = businessProfile.seqId,
            genId = businessProfile.genId,
            auditTrail = businessProfile.auditTrail,
            bizName = businessProfile.bizIdentity?.bizName,
            bizIndustry = businessProfile.bizIdentity?.industries
        )
    }

    override suspend fun bizProfileSummaryToBusinessProfile(
        businessProfileSummary: BizProfileSummary
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
package com.thomas200593.mini_retail_app.features.business.biz_profile.util

import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatchers.Dispatchers.IO
import com.thomas200593.mini_retail_app.features.business.biz_profile.entity.BizIdentity
import com.thomas200593.mini_retail_app.features.business.biz_profile.entity.BizProfile
import com.thomas200593.mini_retail_app.features.business.biz_profile.entity.BizProfileShort
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface ExtFnBizProfile{
    suspend fun bizProfileToBizProfileShort(bizProfile: BizProfile): BizProfileShort
    suspend fun bizProfileShortToBizProfile(bizProfileShort: BizProfileShort): BizProfile
}

class ExtFnBizProfileImpl @Inject constructor(
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher
): ExtFnBizProfile {
    override suspend fun bizProfileToBizProfileShort(bizProfile: BizProfile): BizProfileShort =
        withContext(ioDispatcher){
            BizProfileShort(
                seqId = bizProfile.seqId,
                genId = bizProfile.genId,
                auditTrail = bizProfile.auditTrail,
                bizName = bizProfile.bizIdentity?.bizName,
                bizIndustry = bizProfile.bizIdentity?.industries
            )
        }
    override suspend fun bizProfileShortToBizProfile(bizProfileShort: BizProfileShort): BizProfile =
        withContext(ioDispatcher){
            BizProfile(
                seqId = bizProfileShort.seqId,
                genId = bizProfileShort.genId,
                auditTrail = bizProfileShort.auditTrail,
                bizIdentity = BizIdentity(
                    bizName = bizProfileShort.bizName,
                    industries = bizProfileShort.bizIndustry
                )
            )
        }
}
package com.thomas200593.mini_retail_app.features.business.repository

import com.thomas200593.mini_retail_app.core.design_system.dispatchers.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.dispatchers.Dispatchers
import com.thomas200593.mini_retail_app.features.business.dao.BusinessProfileDao
import com.thomas200593.mini_retail_app.features.business.entity.BizIdentity
import com.thomas200593.mini_retail_app.features.business.entity.BizName
import com.thomas200593.mini_retail_app.features.business.entity.BusinessProfile
import com.thomas200593.mini_retail_app.features.business.entity.dto.BusinessProfileSummary
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface BusinessProfileRepository{
    fun getBusinessProfile(): Flow<BusinessProfile?>
    suspend fun testGenerate()
    fun getBusinessProfileSummary(): Flow<BusinessProfileSummary?>
}

class BusinessProfileRepositoryImpl @Inject constructor(
    private val businessProfileDao: BusinessProfileDao,
    @Dispatcher(Dispatchers.Dispatchers.IO) private val ioDispatcher: CoroutineDispatcher
): BusinessProfileRepository{
    override fun getBusinessProfile(): Flow<BusinessProfile?> =
        businessProfileDao.getBusinessProfile()

    override suspend fun testGenerate() {
        val businessProfile = BusinessProfile(
            bizIdentity = BizIdentity(
                BizName(
                    "Test Legal",
                    "Test Common"
                ),
            )
        )
        businessProfileDao.testGenerate(businessProfile)
    }

    override fun getBusinessProfileSummary() = businessProfileDao.getBusinessProfile()
        .flowOn(ioDispatcher)
        .map { bp ->
            if(bp != null){
                val businessProfileSummary = BusinessProfileSummary(
                    seqId = bp.seqId,
                    genId = bp.genId,
                    bizName = bp.bizIdentity?.bizName,
                    bizIndustry = bp.bizIdentity?.industries,
                    auditTrail = bp.auditTrail
                )
                businessProfileSummary
            }else{
                null
            }
        }
}
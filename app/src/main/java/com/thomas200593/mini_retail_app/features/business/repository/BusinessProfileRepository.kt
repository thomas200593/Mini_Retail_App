package com.thomas200593.mini_retail_app.features.business.repository

import com.thomas200593.mini_retail_app.features.business.dao.BusinessProfileDao
import com.thomas200593.mini_retail_app.features.business.entity.BizIdentity
import com.thomas200593.mini_retail_app.features.business.entity.BizName
import com.thomas200593.mini_retail_app.features.business.entity.BusinessProfile
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface BusinessProfileRepository{
    fun getBusinessProfile(): Flow<BusinessProfile?>
    suspend fun testGenerate()
}

class BusinessProfileRepositoryImpl @Inject constructor(
    private val businessProfileDao: BusinessProfileDao
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
}
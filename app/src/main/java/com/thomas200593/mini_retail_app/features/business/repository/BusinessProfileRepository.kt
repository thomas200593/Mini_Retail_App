package com.thomas200593.mini_retail_app.features.business.repository

import com.thomas200593.mini_retail_app.features.business.dao.BusinessProfileDao
import com.thomas200593.mini_retail_app.features.business.entity.BusinessProfile
import com.thomas200593.mini_retail_app.features.business.entity.dto.BizIdName
import com.thomas200593.mini_retail_app.features.business.entity.dto.BizIdentity
import com.thomas200593.mini_retail_app.features.business.entity.dto.BizIndustry
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface BusinessProfileRepository{
    fun getBusinessProfile(): Flow<BusinessProfile?>
    suspend fun testGenerate(): Unit
}

class BusinessProfileRepositoryImpl @Inject constructor(
    private val businessProfileDao: BusinessProfileDao
): BusinessProfileRepository{
    override fun getBusinessProfile(): Flow<BusinessProfile?> =
        businessProfileDao.getBusinessProfile()

    override suspend fun testGenerate() {
        val businessProfile = BusinessProfile(
            bizIdentity = BizIdentity(
                BizIdName(
                    "Test Legal",
                    "Test Common"
                ),
            )
        )
        businessProfileDao.testGenerate(businessProfile)
    }
}
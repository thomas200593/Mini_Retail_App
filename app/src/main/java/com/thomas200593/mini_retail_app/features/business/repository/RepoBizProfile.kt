package com.thomas200593.mini_retail_app.features.business.repository

import com.thomas200593.mini_retail_app.features.business.entity.business_profile.BusinessProfile
import kotlinx.coroutines.flow.Flow

interface RepoBizProfile{
    fun getBusinessProfile(): Flow<BusinessProfile?>
    suspend fun setBusinessProfile(businessProfile: BusinessProfile): Long
}
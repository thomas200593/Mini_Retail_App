package com.thomas200593.mini_retail_app.features.business.repository

import com.thomas200593.mini_retail_app.features.business.entity.BusinessProfile
import kotlinx.coroutines.flow.Flow

interface BusinessProfileRepository{
    fun getBusinessProfile(): Flow<BusinessProfile?>
}
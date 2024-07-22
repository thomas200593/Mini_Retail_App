package com.thomas200593.mini_retail_app.features.initial.domain

import com.thomas200593.mini_retail_app.features.app_config.app_config.repository.AppConfigRepository
import com.thomas200593.mini_retail_app.features.business.entity.business_profile.dto.BusinessProfileSummary
import com.thomas200593.mini_retail_app.features.business.repository.BusinessProfileRepository
import com.thomas200593.mini_retail_app.features.business.util.BusinessExtFn
import com.thomas200593.mini_retail_app.features.initial.entity.FirstTimeStatus
import javax.inject.Inject

class SetDefaultInitialBizProfileUseCase @Inject constructor(
    private val appCfgRepository: AppConfigRepository,
    private val businessProfileRepository: BusinessProfileRepository,
    private val bizExtFn: BusinessExtFn
){
    suspend operator fun invoke(bizProfileSummary: BusinessProfileSummary): BusinessProfileSummary?{
        if(businessProfileRepository.setBusinessProfile(bizExtFn.bizProfileSummaryToBusinessProfile(bizProfileSummary)) > 0){
            appCfgRepository.setFirstTimeStatus(FirstTimeStatus.NO)
            return bizProfileSummary
        }
        return null
    }
}
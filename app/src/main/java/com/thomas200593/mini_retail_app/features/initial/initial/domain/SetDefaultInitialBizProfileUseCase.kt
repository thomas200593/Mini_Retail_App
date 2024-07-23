package com.thomas200593.mini_retail_app.features.initial.initial.domain

import com.thomas200593.mini_retail_app.features.app_conf.app_config.repository.RepoAppConf
import com.thomas200593.mini_retail_app.features.business.entity.business_profile.dto.BusinessProfileSummary
import com.thomas200593.mini_retail_app.features.business.repository.BusinessProfileRepository
import com.thomas200593.mini_retail_app.features.business.util.BusinessExtFn
import com.thomas200593.mini_retail_app.features.initial.initial.entity.FirstTimeStatus
import javax.inject.Inject

class SetDefaultInitialBizProfileUseCase @Inject constructor(
    private val appCfgRepository: RepoAppConf,
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
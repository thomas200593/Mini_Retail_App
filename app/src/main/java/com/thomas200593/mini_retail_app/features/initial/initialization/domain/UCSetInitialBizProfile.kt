package com.thomas200593.mini_retail_app.features.initial.initialization.domain

import com.thomas200593.mini_retail_app.features.app_conf.app_config.repository.RepoAppConf
import com.thomas200593.mini_retail_app.features.business.biz_profile.entity.dto.BizProfileSummary
import com.thomas200593.mini_retail_app.features.business.biz_profile.repository.RepoBizProfile
import com.thomas200593.mini_retail_app.features.business.util.ExtFnBusiness
import com.thomas200593.mini_retail_app.features.initial.initial.entity.FirstTimeStatus.NO
import javax.inject.Inject

class UCSetInitialBizProfile @Inject constructor(
    private val repoAppConf: RepoAppConf,
    private val repoBizProfile: RepoBizProfile,
    private val extFnBusiness: ExtFnBusiness
){
    suspend operator fun invoke(bizProfileSummary: BizProfileSummary): BizProfileSummary?{
        if(repoBizProfile.setBusinessProfile(extFnBusiness.bizProfileSummaryToBusinessProfile(bizProfileSummary)) > 0){
            repoAppConf.setFirstTimeStatus(NO)
            return bizProfileSummary
        }
        return null
    }
}
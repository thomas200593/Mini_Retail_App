package com.thomas200593.mini_retail_app.features.initial.initialization.domain

import com.thomas200593.mini_retail_app.features.app_conf.app_config.repository.RepoAppConf
import com.thomas200593.mini_retail_app.features.business.biz_profile.entity.BizProfileShort
import com.thomas200593.mini_retail_app.features.business.biz_profile.repository.RepoBizProfile
import com.thomas200593.mini_retail_app.features.business.biz_profile.util.ExtFnBizProfile
import com.thomas200593.mini_retail_app.features.initial.initial.entity.FirstTimeStatus.NO
import javax.inject.Inject

class UCSetInitialBizProfile @Inject constructor(
    private val repoAppConf: RepoAppConf,
    private val repoBizProfile: RepoBizProfile,
    private val extFnBizProfile: ExtFnBizProfile
){
    suspend operator fun invoke(bizProfileShort: BizProfileShort): BizProfileShort?{
        if(repoBizProfile.setBusinessProfile(extFnBizProfile.bizProfileShortToBizProfile(bizProfileShort)) > 0){
            repoAppConf.setFirstTimeStatus(NO)
            return bizProfileShort
        }
        return null
    }
}
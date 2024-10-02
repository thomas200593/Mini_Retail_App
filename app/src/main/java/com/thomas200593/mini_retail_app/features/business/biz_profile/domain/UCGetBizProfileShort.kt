package com.thomas200593.mini_retail_app.features.business.biz_profile.domain

import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatchers.Dispatchers.IO
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.di.Dispatcher
import com.thomas200593.mini_retail_app.features.app_conf.app_config.repository.RepoAppConf
import com.thomas200593.mini_retail_app.features.business.biz_profile.entity.toBizProfileShort
import com.thomas200593.mini_retail_app.features.business.biz_profile.repository.RepoBizProfile
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class UCGetBizProfileShort @Inject constructor(
    private val repoBizProfile: RepoBizProfile,
    private val repoAppConf: RepoAppConf,
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher
) {
    operator fun invoke() =
        combine(flow = repoBizProfile.getBizProfile(), flow2 = repoAppConf.configCurrent)
        { bizProfile, configCurrent -> Pair(bizProfile.toBizProfileShort(), configCurrent) }
            .flowOn(ioDispatcher)
}
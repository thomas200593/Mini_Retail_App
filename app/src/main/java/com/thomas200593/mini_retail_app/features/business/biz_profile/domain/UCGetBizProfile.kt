package com.thomas200593.mini_retail_app.features.business.biz_profile.domain

import com.thomas200593.mini_retail_app.core.data.local.session.SessionState
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatchers.Dispatchers.IO
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.di.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState.Error
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState.Loading
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState.Success
import com.thomas200593.mini_retail_app.features.app_conf.app_config.repository.RepoAppConf
import com.thomas200593.mini_retail_app.features.business.biz_profile.repository.RepoBizProfile
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class UCGetBizProfile @Inject constructor(
    private val repoBizProfile: RepoBizProfile,
    private val repoAppConf: RepoAppConf,
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher
) {
    operator fun invoke(sessionState: SessionState) = when(sessionState){
        SessionState.Loading -> flowOf(Loading)
        is SessionState.Invalid -> flowOf(Error(t = Throwable("SessionExpired")))
        is SessionState.Valid -> combine(repoBizProfile.getBizProfile(), repoAppConf.configCurrent)
        { bizProfile, configCurrent -> Success(data = Pair(bizProfile, configCurrent)) }
            .flowOn(ioDispatcher).catch { t -> Error(t) }
    }
}
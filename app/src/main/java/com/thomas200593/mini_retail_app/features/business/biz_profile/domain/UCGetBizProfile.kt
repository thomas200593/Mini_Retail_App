package com.thomas200593.mini_retail_app.features.business.biz_profile.domain

import com.thomas200593.mini_retail_app.core.data.local.session.SessionState
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatchers.Dispatchers.IO
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState.Empty
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState.Error
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState.Loading
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState.Success
import com.thomas200593.mini_retail_app.features.app_conf.app_config.repository.RepoAppConf
import com.thomas200593.mini_retail_app.features.business.biz_profile.entity.BizProfileDtl
import com.thomas200593.mini_retail_app.features.business.biz_profile.repository.RepoBizProfile
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class UCGetBizProfile @Inject constructor(
    private val repoBizProfile: RepoBizProfile,
    private val repoAppConf: RepoAppConf,
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(sessionState: SessionState) = when(sessionState){
        SessionState.Loading -> flow { emit(Loading) }
        is SessionState.Invalid -> flow { emit(Error(t = Throwable("SessionExpired"))) }
        is SessionState.Valid -> combine(
            repoBizProfile.getBizProfile(),
            repoAppConf.configCurrent
        ){ bizProfile, configCurrent ->
            if(bizProfile == null) Empty
            else Success(
                data = BizProfileDtl(
                    bizProfile = bizProfile,
                    configCurrent = configCurrent
                )
            )
        }.flowOn(ioDispatcher).catch { t -> emit(Error(t)) }
    }
}
package com.thomas200593.mini_retail_app.features.business.biz_profile.domain

import com.thomas200593.mini_retail_app.core.data.local.session.SessionState
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatchers.Dispatchers.IO
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState.Empty
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState.Error
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState.Loading
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState.Success
import com.thomas200593.mini_retail_app.features.app_conf.app_config.repository.RepoAppConf
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_timezone.repository.RepoConfGenTimezone
import com.thomas200593.mini_retail_app.features.business.biz_profile.entity.BizProfileDtl
import com.thomas200593.mini_retail_app.features.business.biz_profile.repository.RepoBizProfile
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class UCGetBizProfile @Inject constructor(
    private val repoBizProfile: RepoBizProfile,
    private val repoAppConf: RepoAppConf,
    private val repoConfGenTimezone: RepoConfGenTimezone,
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(sessionState: SessionState) = when(sessionState){
        SessionState.Loading -> Loading
        is SessionState.Invalid -> Error(t = Throwable("SessionExpired"))
        is SessionState.Valid -> {
            val result =
                combine(
                    repoBizProfile.getBizProfile(),
                    repoAppConf.configCurrent,
                    flow { emit(repoConfGenTimezone.getTimezones()) }
                ){ bizProfile, configCurrent, timezones ->
                    if(bizProfile == null) Empty
                    else Success(
                        data = BizProfileDtl(
                            bizProfile = bizProfile,
                            configCurrent = configCurrent,
                            timezones = timezones
                        )
                    )
                }
                .flowOn(ioDispatcher)
                .catch { t -> emit(Error(t)) }
                .onStart { emit(Loading) }.first()
            result
        }
    }
}
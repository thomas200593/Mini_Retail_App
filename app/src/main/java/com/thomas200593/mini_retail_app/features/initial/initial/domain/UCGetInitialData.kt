package com.thomas200593.mini_retail_app.features.initial.initial.domain

import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatchers.Dispatchers.IO
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState.Error
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState.Success
import com.thomas200593.mini_retail_app.features.app_conf.app_config.repository.RepoAppConf
import com.thomas200593.mini_retail_app.features.auth.repository.RepoAuth
import com.thomas200593.mini_retail_app.features.initial.initial.entity.Initial
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UCGetInitialData @Inject constructor(
    private val repoAuth: RepoAuth,
    private val repoAppConf: RepoAppConf,
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher
){
    operator fun invoke() = combine(
        flow = repoAuth.authSessionToken,
        flow2 = repoAppConf.configCurrent,
        flow3 = repoAppConf.firstTimeStatus
    ){ authSession, configCurrent, firstTimeStatus ->
        Success(
            data = Initial(
                firstTimeStatus = firstTimeStatus,
                configCurrent = configCurrent,
                session = repoAuth.mapAuthSessionTokenToUserData(authSession)
            )
        )
    }.flowOn(ioDispatcher).catch { throwable -> Error(throwable) }.map { it }
}
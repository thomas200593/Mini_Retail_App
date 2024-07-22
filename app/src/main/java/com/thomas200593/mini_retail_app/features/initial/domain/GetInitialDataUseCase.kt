package com.thomas200593.mini_retail_app.features.initial.domain

import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatchers
import com.thomas200593.mini_retail_app.core.design_system.util.RequestState
import com.thomas200593.mini_retail_app.features.app_config.app_config.repository.AppConfigRepository
import com.thomas200593.mini_retail_app.features.auth.repository.AuthRepository
import com.thomas200593.mini_retail_app.features.initial.entity.Initial
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetInitialDataUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val appCfgRepository: AppConfigRepository,
    @Dispatcher(Dispatchers.Dispatchers.IO) private val ioDispatcher: CoroutineDispatcher
){
    operator fun invoke(): Flow<RequestState.Success<Initial>> = combine(
        flow = authRepository.authSessionToken,
        flow2 = appCfgRepository.configCurrent,
        flow3 = appCfgRepository.firstTimeStatus
    ){ authSession, configCurrent, firstTimeStatus ->
        RequestState.Success(
            data = Initial(
                isFirstTime = firstTimeStatus,
                configCurrent = configCurrent,
                session = authRepository.mapAuthSessionTokenToUserData(authSession)
            )
        )
    }.flowOn(ioDispatcher).catch { throwable -> RequestState.Error(throwable) }.map { it }
}
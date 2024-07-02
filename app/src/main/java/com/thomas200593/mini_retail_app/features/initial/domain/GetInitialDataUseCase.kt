package com.thomas200593.mini_retail_app.features.initial.domain

import com.thomas200593.mini_retail_app.core.design_system.dispatchers.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.dispatchers.Dispatchers
import com.thomas200593.mini_retail_app.core.design_system.util.RequestState
import com.thomas200593.mini_retail_app.features.app_config.repository.AppConfigRepository
import com.thomas200593.mini_retail_app.features.auth.repository.AuthRepository
import com.thomas200593.mini_retail_app.features.initial.entity.Initial
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetInitialDataUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val appConfigRepository: AppConfigRepository,
    @Dispatcher(Dispatchers.Dispatchers.IO) private val ioDispatcher: CoroutineDispatcher
){
    operator fun invoke(): Flow<RequestState.Success<Initial>> = authRepository.authSessionToken
        .flowOn(ioDispatcher)
        .catch { throwable -> RequestState.Error(throwable) }
        .combine(appConfigRepository.configCurrent){ auth, config ->
            RequestState.Success(
                Initial(
                    userData = authRepository.mapAuthSessionTokenToUserData(auth),
                    onboardingPageStatus = config.onboardingStatus
                )
            )
        }
}
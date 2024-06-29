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
import kotlinx.coroutines.flow.onEach
import timber.log.Timber
import javax.inject.Inject

private val TAG = GetInitialDataUseCase::class.simpleName

class GetInitialDataUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val appConfigRepository: AppConfigRepository,
    @Dispatcher(Dispatchers.Dispatchers.IO) private val ioDispatcher: CoroutineDispatcher
){
    operator fun invoke(): Flow<RequestState.Success<Initial>> = authRepository.authSessionToken
        .flowOn(ioDispatcher)
        .onEach {
            Timber.d("fun $TAG.invoke() : $it")
        }
        .catch { throwable ->
            RequestState.Error(throwable)
        }
        .combine(appConfigRepository.configCurrentData){ auth, config ->
            RequestState.Success(
                Initial(
                    isSessionValid = authRepository.validateAuthSessionToken(auth),
                    onboardingPageStatus = config.onboardingPagesStatus
                )
            )
        }
}
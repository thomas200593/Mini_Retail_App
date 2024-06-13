package com.thomas200593.mini_retail_app.features.initial.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thomas200593.mini_retail_app.core.design_system.dispatchers.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.dispatchers.Dispatchers
import com.thomas200593.mini_retail_app.features.app_config.entity.OnboardingStatus
import com.thomas200593.mini_retail_app.features.app_config.repository.AppConfigRepository
import com.thomas200593.mini_retail_app.features.auth.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import timber.log.Timber
import javax.inject.Inject

private  const val TAG = "InitialViewModel"

@HiltViewModel
class InitialViewModel @Inject constructor(
    authRepository: AuthRepository,
    appConfigRepository: AppConfigRepository,
    @Dispatcher(Dispatchers.Dispatchers.IO) ioDispatchers: CoroutineDispatcher
): ViewModel(){
    val uiState = appConfigRepository.configCurrentData.flowOn(ioDispatchers)
        .combine(authRepository.authSessionToken){ currentConfig, authToken ->
            InitialUiState.Success(
                onboardingPagesStatus = currentConfig.onboardingPagesStatus,
                isSessionValid = authRepository.validateAuthSessionToken(authToken)
            )
        }
        .onEach {
            Timber.d("Called : %s.uiState: %s", TAG, it)
        }
        .stateIn(
            scope = viewModelScope,
            initialValue = InitialUiState.Loading,
            started = SharingStarted.Eagerly
        )
}

sealed interface InitialUiState{
    data object Loading: InitialUiState
    data class Success(
        val isSessionValid: Boolean,
        val onboardingPagesStatus: OnboardingStatus
    ): InitialUiState
}
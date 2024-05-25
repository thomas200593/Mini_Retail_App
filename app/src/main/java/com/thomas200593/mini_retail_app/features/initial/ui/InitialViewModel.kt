package com.thomas200593.mini_retail_app.features.initial.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thomas200593.mini_retail_app.core.design_system.dispatchers.AppDispatchers
import com.thomas200593.mini_retail_app.core.design_system.dispatchers.Dispatcher
import com.thomas200593.mini_retail_app.core.util.JWTHelper
import com.thomas200593.mini_retail_app.features.app_config.entity.CurrentAppConfig
import com.thomas200593.mini_retail_app.features.app_config.entity.Onboarding
import com.thomas200593.mini_retail_app.features.app_config.repository.AppConfigRepository
import com.thomas200593.mini_retail_app.features.auth.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.zip
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class InitialViewModel @Inject constructor(
    authRepository: AuthRepository,
    appConfigRepository: AppConfigRepository,
    @Dispatcher(AppDispatchers.IO) ioDispatchers: CoroutineDispatcher
): ViewModel(){
    val uiState = appConfigRepository.currentAppConfigData.flowOn(ioDispatchers)
        .combine(authRepository.authSessionToken){ currentConfig, authToken ->
            InitialUiState.Success(
                shouldShowOnboarding = currentConfig.showOnboardingPages,
                isSessionValid = JWTHelper.isJWTTokenValid(authToken.idToken.orEmpty())
            )
        }.stateIn(
            scope = viewModelScope,
            initialValue = InitialUiState.Loading,
            started = SharingStarted.Eagerly
        )
}

sealed interface InitialUiState{
    data object Loading: InitialUiState
    data class Success(
        val isSessionValid: Boolean,
        val shouldShowOnboarding: Onboarding
    ): InitialUiState
}
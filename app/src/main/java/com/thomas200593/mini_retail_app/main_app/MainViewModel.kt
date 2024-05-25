package com.thomas200593.mini_retail_app.main_app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thomas200593.mini_retail_app.features.app_config.entity.CurrentAppConfig
import com.thomas200593.mini_retail_app.features.app_config.repository.AppConfigRepository
import com.thomas200593.mini_retail_app.main_app.MainActivityUiState.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    appConfigRepository: AppConfigRepository
) : ViewModel(){
    val uiState: StateFlow<MainActivityUiState> = appConfigRepository.currentAppConfigData.map {
        Success(it)
    }.stateIn(
        scope = viewModelScope,
        initialValue = Loading,
        started = SharingStarted.Eagerly
    )
}

sealed interface MainActivityUiState {
    data object Loading: MainActivityUiState
    data class Success(val currentAppConfig: CurrentAppConfig): MainActivityUiState
}
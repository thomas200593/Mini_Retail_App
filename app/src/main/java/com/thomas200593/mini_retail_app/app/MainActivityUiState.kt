package com.thomas200593.mini_retail_app.app

import com.thomas200593.mini_retail_app.features.app_config.entity.CurrentAppConfig

sealed interface MainActivityUiState {
    data object Loading: MainActivityUiState
    data class Success(val currentAppConfig: CurrentAppConfig): MainActivityUiState
}
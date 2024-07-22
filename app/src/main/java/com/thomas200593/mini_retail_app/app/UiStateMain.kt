package com.thomas200593.mini_retail_app.app

import com.thomas200593.mini_retail_app.features.app_config.app_config.entity.AppConfig

sealed interface UiStateMain {
    data object Loading: UiStateMain
    data class Success(val configCurrent: AppConfig.ConfigCurrent): UiStateMain
}
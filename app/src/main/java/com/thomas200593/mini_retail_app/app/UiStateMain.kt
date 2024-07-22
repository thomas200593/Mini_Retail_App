package com.thomas200593.mini_retail_app.app

import com.thomas200593.mini_retail_app.features.app_conf.app_config.entity.AppConfig.ConfigCurrent

sealed interface UiStateMain {
    data object Loading: UiStateMain
    data class Success(val confCurrent: ConfigCurrent): UiStateMain
}
package com.thomas200593.mini_retail_app.features.app_config.repository

import com.thomas200593.mini_retail_app.features.app_config.entity.CurrentGeneralAppConfig
import kotlinx.coroutines.flow.Flow

interface AppConfigRepository {
    val currentGeneralAppConfigData: Flow<CurrentGeneralAppConfig>
}
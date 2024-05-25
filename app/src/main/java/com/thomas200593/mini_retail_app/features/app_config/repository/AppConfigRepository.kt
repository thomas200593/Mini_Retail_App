package com.thomas200593.mini_retail_app.features.app_config.repository

import com.thomas200593.mini_retail_app.features.app_config.entity.CurrentAppConfig
import kotlinx.coroutines.flow.Flow

interface AppConfigRepository {
    val currentAppConfigData: Flow<CurrentAppConfig>
}
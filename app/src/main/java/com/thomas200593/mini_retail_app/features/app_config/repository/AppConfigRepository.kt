package com.thomas200593.mini_retail_app.features.app_config.repository

import com.thomas200593.mini_retail_app.core.data.local.session.SessionState
import com.thomas200593.mini_retail_app.features.app_config.entity.ConfigCurrent
import com.thomas200593.mini_retail_app.features.app_config.navigation.DestinationAppConfig
import kotlinx.coroutines.flow.Flow

interface AppConfigRepository {
    val configCurrentData: Flow<ConfigCurrent>
    suspend fun getAppConfigMenuData(sessionState: SessionState): Set<DestinationAppConfig>
}
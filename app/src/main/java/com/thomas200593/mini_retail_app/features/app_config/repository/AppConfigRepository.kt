package com.thomas200593.mini_retail_app.features.app_config.repository

import com.thomas200593.mini_retail_app.core.data.local.session.SessionState
import com.thomas200593.mini_retail_app.features.app_config.entity.ConfigCurrent
import com.thomas200593.mini_retail_app.features.app_config.navigation.DestinationAppConfig
import com.thomas200593.mini_retail_app.features.initial.entity.FirstTimeStatus
import kotlinx.coroutines.flow.Flow

interface AppConfigRepository {
    val configCurrent: Flow<ConfigCurrent>
    val firstTimeStatus: Flow<FirstTimeStatus>
    suspend fun setFirstTimeStatus(firstTimeStatus: FirstTimeStatus)
    suspend fun getMenuData(sessionState: SessionState): Set<DestinationAppConfig>
}
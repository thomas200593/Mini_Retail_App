package com.thomas200593.mini_retail_app.features.app_config.repository

import com.thomas200593.mini_retail_app.features.app_config.navigation.ConfigGeneralDestination
import com.thomas200593.mini_retail_app.features.app_config.entity.ConfigCurrent
import kotlinx.coroutines.flow.Flow

interface AppConfigRepository {
    val configCurrentData: Flow<ConfigCurrent>
    suspend fun getAppConfigGeneralMenuData(): Set<ConfigGeneralDestination>
}
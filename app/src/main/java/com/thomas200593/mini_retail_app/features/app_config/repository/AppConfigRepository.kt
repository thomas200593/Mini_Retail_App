package com.thomas200593.mini_retail_app.features.app_config.repository

import com.thomas200593.mini_retail_app.features.app_config.entity.AppConfigGeneralMenu
import com.thomas200593.mini_retail_app.features.app_config.entity.CurrentAppConfigGeneral
import kotlinx.coroutines.flow.Flow

interface AppConfigRepository {
    val currentAppConfigGeneralData: Flow<CurrentAppConfigGeneral>
    suspend fun getAppConfigGeneralMenuData(): Set<AppConfigGeneralMenu>
}
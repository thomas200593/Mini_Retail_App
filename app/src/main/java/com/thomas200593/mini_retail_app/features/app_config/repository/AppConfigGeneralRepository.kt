package com.thomas200593.mini_retail_app.features.app_config.repository

import com.thomas200593.mini_retail_app.core.data.local.session.SessionState
import com.thomas200593.mini_retail_app.features.app_config.navigation.DestinationAppConfigGeneral

interface AppConfigGeneralRepository {
    suspend fun getAppConfigGeneralMenuData(sessionState: SessionState): Set<DestinationAppConfigGeneral>
}
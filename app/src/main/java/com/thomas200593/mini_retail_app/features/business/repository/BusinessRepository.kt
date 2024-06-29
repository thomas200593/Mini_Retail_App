package com.thomas200593.mini_retail_app.features.business.repository

import com.thomas200593.mini_retail_app.core.data.local.session.SessionState
import com.thomas200593.mini_retail_app.features.business.navigation.DestinationBusiness

interface BusinessRepository {
    suspend fun getBusinessMenuData(sessionState: SessionState): Set<DestinationBusiness>
}
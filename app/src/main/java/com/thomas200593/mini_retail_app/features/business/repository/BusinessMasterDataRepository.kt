package com.thomas200593.mini_retail_app.features.business.repository

import com.thomas200593.mini_retail_app.core.data.local.session.SessionState
import com.thomas200593.mini_retail_app.features.business.navigation.DestinationBusinessMasterData

interface BusinessMasterDataRepository {
    suspend fun getBusinessMasterDataMenuData(sessionState: SessionState): Set<DestinationBusinessMasterData>
}

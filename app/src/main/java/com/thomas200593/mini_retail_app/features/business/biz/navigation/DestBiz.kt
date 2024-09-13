package com.thomas200593.mini_retail_app.features.business.biz.navigation

import com.thomas200593.mini_retail_app.app.navigation.ScrGraphs
import com.thomas200593.mini_retail_app.app.navigation.ScrGraphs.BizProfile
import com.thomas200593.mini_retail_app.app.navigation.ScrGraphs.MasterData

enum class DestBiz (val scrGraphs: ScrGraphs) {
    BIZ_PROFILE(scrGraphs = BizProfile),
    MASTER_DATA(scrGraphs = MasterData)
}

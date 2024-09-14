package com.thomas200593.mini_retail_app.features.business.biz_m_data.navigation

import com.thomas200593.mini_retail_app.app.navigation.ScrGraphs
import com.thomas200593.mini_retail_app.app.navigation.ScrGraphs.Supplier

enum class DestMasterData(
    val scrGraphs: ScrGraphs
){
    SUPPLIER(scrGraphs = Supplier)
}
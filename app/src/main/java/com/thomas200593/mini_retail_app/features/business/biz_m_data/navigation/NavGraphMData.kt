package com.thomas200593.mini_retail_app.features.business.biz_m_data.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.thomas200593.mini_retail_app.app.navigation.NavGraph
import com.thomas200593.mini_retail_app.app.navigation.ScrGraphs
import com.thomas200593.mini_retail_app.features.business.biz_m_data.ui.ScrMData
import com.thomas200593.mini_retail_app.features.business.navigation.navGraphSupplier

fun NavGraphBuilder.navGraphMData() {
    navigation(
        route = NavGraph.G_MASTER_DATA,
        startDestination = ScrGraphs.MasterData.route
    ){
        composable(
            route = ScrGraphs.MasterData.route
        ){
            ScrMData()
        }

        /**
         * - Supplier
         */
        navGraphSupplier()
//        navGraphBusinessMasterDataCustomer()
    }
}

fun NavController.navToMData(
    navOptions: NavOptions?,
    destMData: DestMData? = null
){
    this.navigate(
        route = destMData?.route?: NavGraph.G_MASTER_DATA,
        navOptions
    )
}
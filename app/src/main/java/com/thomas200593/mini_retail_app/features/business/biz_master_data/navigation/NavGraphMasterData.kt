package com.thomas200593.mini_retail_app.features.business.biz_master_data.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.thomas200593.mini_retail_app.app.navigation.NavGraph
import com.thomas200593.mini_retail_app.app.navigation.ScrGraphs
import com.thomas200593.mini_retail_app.features.business.biz_master_data.ui.MasterDataScreen
import com.thomas200593.mini_retail_app.features.business.navigation.navGraphSupplier

fun NavGraphBuilder.navGraphMasterData() {
    navigation(
        route = NavGraph.G_MASTER_DATA,
        startDestination = ScrGraphs.MasterData.route
    ){
        composable(
            route = ScrGraphs.MasterData.route
        ){
            MasterDataScreen()
        }

        /**
         * - Supplier
         */
        navGraphSupplier()
//        navGraphBusinessMasterDataCustomer()
    }
}

fun NavController.navigateToMasterData(
    navOptions: NavOptions?,
    destinationMasterData: DestinationMasterData? = null
){
    this.navigate(
        route = destinationMasterData?.route?: NavGraph.G_MASTER_DATA,
        navOptions
    )
}
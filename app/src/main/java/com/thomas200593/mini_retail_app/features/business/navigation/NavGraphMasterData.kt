package com.thomas200593.mini_retail_app.features.business.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.thomas200593.mini_retail_app.app.navigation.NavigationGraphs
import com.thomas200593.mini_retail_app.app.navigation.ScreenGraphs
import com.thomas200593.mini_retail_app.features.business.ui.master_data.MasterDataScreen

fun NavGraphBuilder.navGraphMasterData() {
    navigation(
        route = NavigationGraphs.G_MASTER_DATA,
        startDestination = ScreenGraphs.MasterData.route
    ){
        composable(
            route = ScreenGraphs.MasterData.route
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
        route = destinationMasterData?.route?: NavigationGraphs.G_MASTER_DATA,
        navOptions
    )
}
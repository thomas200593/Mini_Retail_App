package com.thomas200593.mini_retail_app.features.business.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.thomas200593.mini_retail_app.app.navigation.NavigationGraphs.G_MASTER_DATA
import com.thomas200593.mini_retail_app.app.navigation.ScreenGraphs
import com.thomas200593.mini_retail_app.features.business.ui.master_data.BusinessMasterDataScreen

fun NavGraphBuilder.navGraphBusinessMasterData() {
    navigation(
        route = G_MASTER_DATA,
        startDestination = ScreenGraphs.MasterData.route
    ){
        composable(
            route = ScreenGraphs.MasterData.route
        ){
            BusinessMasterDataScreen()
        }

        /**
         * - Supplier
         */
        navGraphBusinessMasterDataSupplier()
//        navGraphBusinessMasterDataCustomer()
    }
}

fun NavController.navigateToBusinessMasterData(
    navOptions: NavOptions?,
    destinationBusinessMasterData: DestinationBusinessMasterData? = null
){
    this.navigate(
        route = destinationBusinessMasterData?.route?: G_MASTER_DATA,
        navOptions
    )
}
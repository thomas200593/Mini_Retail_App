package com.thomas200593.mini_retail_app.features.business.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.thomas200593.mini_retail_app.app.navigation.NavigationGraphs
import com.thomas200593.mini_retail_app.app.navigation.ScreenGraphs
import com.thomas200593.mini_retail_app.features.business.ui.BusinessScreen

fun NavGraphBuilder.navGraphBusiness(){
    navigation(
        route = NavigationGraphs.G_BUSINESS,
        startDestination = ScreenGraphs.Business.route
    ){
        composable(
            route = ScreenGraphs.Business.route
        ){
            BusinessScreen()
        }

        /**
         * - Master Data
         */
        navGraphMasterData()
//        navGraphBusinessConfiguration()
    }
}

fun NavController.navigateToBusiness(
    navOptions: NavOptions?,
    destinationBusiness: DestinationBusiness? = null
) {
    this.navigate(
        route = destinationBusiness?.route?: NavigationGraphs.G_BUSINESS,
        navOptions
    )
}
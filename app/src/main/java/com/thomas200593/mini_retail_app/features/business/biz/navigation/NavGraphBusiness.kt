package com.thomas200593.mini_retail_app.features.business.biz.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.thomas200593.mini_retail_app.app.navigation.NavGraph
import com.thomas200593.mini_retail_app.app.navigation.ScrGraphs
import com.thomas200593.mini_retail_app.features.business.biz.ui.ScrBusiness
import com.thomas200593.mini_retail_app.features.business.navigation.navGraphMasterData

fun NavGraphBuilder.navGraphBusiness(){
    navigation(
        route = NavGraph.G_BUSINESS,
        startDestination = ScrGraphs.Business.route
    ){
        composable(
            route = ScrGraphs.Business.route
        ){
            ScrBusiness()
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
        route = destinationBusiness?.route?: NavGraph.G_BUSINESS,
        navOptions
    )
}
package com.thomas200593.mini_retail_app.features.business.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.thomas200593.mini_retail_app.app.navigation.NavigationGraphs.G_BUSINESS
import com.thomas200593.mini_retail_app.app.navigation.ScreenGraphs
import com.thomas200593.mini_retail_app.features.business.ui.BusinessScreen
import timber.log.Timber

private val TAG_NAV_GRAPH_BUILDER = NavGraphBuilder::class.simpleName
private val TAG_NAV_CONTROLLER = NavController::class.simpleName

fun NavGraphBuilder.navGraphBusiness(){
    Timber.d("Called : fun $TAG_NAV_GRAPH_BUILDER.businessNavGraph()")
    navigation(
        route = G_BUSINESS,
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
        navGraphBusinessMasterData()
//        navGraphBusinessConfiguration()
    }
}

fun NavController.navigateToBusiness(
    navOptions: NavOptions?,
    destinationBusiness: DestinationBusiness? = null
) {
    Timber.d("Called : fun $TAG_NAV_CONTROLLER.navigateToBusiness()")
    this.navigate(
        route = destinationBusiness?.route?: G_BUSINESS,
        navOptions
    )
}
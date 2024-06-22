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

fun NavGraphBuilder.businessNavGraph(){
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
    }
}

fun NavController.navigateToBusiness(navOptions: NavOptions?) {
    Timber.d("Called : fun $TAG_NAV_CONTROLLER.navigateToBusiness()")
    this.navigate(
        route = G_BUSINESS,
        navOptions
    )
}
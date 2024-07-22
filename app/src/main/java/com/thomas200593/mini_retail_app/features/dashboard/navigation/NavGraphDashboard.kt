package com.thomas200593.mini_retail_app.features.dashboard.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.thomas200593.mini_retail_app.app.navigation.NavGraph.G_DASHBOARD
import com.thomas200593.mini_retail_app.app.navigation.ScrGraphs
import com.thomas200593.mini_retail_app.features.business.navigation.navGraphBusiness
import com.thomas200593.mini_retail_app.features.dashboard.ui.DashboardScreen
import com.thomas200593.mini_retail_app.features.reporting.navigation.navGraphReporting
import com.thomas200593.mini_retail_app.features.user_profile.navigation.navGraphUserProfile
import timber.log.Timber

private val TAG_NAV_GRAPH_BUILDER = NavGraphBuilder::class.simpleName
private val TAG_NAV_CONTROLLER = NavController::class.simpleName

fun NavGraphBuilder.navGraphDashboard() {
    Timber.d("Called : fun $TAG_NAV_GRAPH_BUILDER.dashboardNavGraph()")
    navigation(
        route = G_DASHBOARD,
        startDestination = ScrGraphs.Dashboard.route
    ){
        composable(
            route = ScrGraphs.Dashboard.route
        ){
            DashboardScreen()
        }

        //children graph beyond dashboard
        navGraphBusiness()
        navGraphReporting()
        navGraphUserProfile()
    }
}

fun NavController.navigateToDashboard(navOptions: NavOptions) {
    Timber.d("Called : fun $TAG_NAV_CONTROLLER.navigateToDashboard()")
    this.navigate(
        route = G_DASHBOARD,
        navOptions
    )
}
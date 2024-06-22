package com.thomas200593.mini_retail_app.features.dashboard.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.thomas200593.mini_retail_app.app.navigation.NavigationGraphs.G_DASHBOARD
import com.thomas200593.mini_retail_app.app.navigation.ScreenGraphs
import com.thomas200593.mini_retail_app.features.business.navigation.businessNavGraph
import com.thomas200593.mini_retail_app.features.dashboard.ui.DashboardScreen
import com.thomas200593.mini_retail_app.features.reporting.navigation.reportingNavGraph
import com.thomas200593.mini_retail_app.features.user_profile.navigation.userProfileNavGraph
import timber.log.Timber

private val TAG_NAV_GRAPH_BUILDER = NavGraphBuilder::class.simpleName
private val TAG_NAV_CONTROLLER = NavController::class.simpleName

fun NavGraphBuilder.dashboardNavGraph() {
    Timber.d("Called : fun $TAG_NAV_GRAPH_BUILDER.dashboardNavGraph()")
    navigation(
        route = G_DASHBOARD,
        startDestination = ScreenGraphs.Dashboard.route
    ){
        composable(
            route = ScreenGraphs.Dashboard.route
        ){
            DashboardScreen()
        }

        //children graph beyond dashboard
        businessNavGraph()
        reportingNavGraph()
        userProfileNavGraph()
    }
}

fun NavController.navigateToDashboard(navOptions: NavOptions) {
    Timber.d("Called : fun $TAG_NAV_CONTROLLER.navigateToDashboard()")
    this.navigate(
        route = G_DASHBOARD,
        navOptions
    )
}
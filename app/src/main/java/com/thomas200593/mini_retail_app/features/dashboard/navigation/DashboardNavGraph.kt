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
import com.thomas200593.mini_retail_app.features.report_analysis.navigation.reportAnalysisNavGraph
import com.thomas200593.mini_retail_app.features.user_profile.navigation.userProfileNavGraph

fun NavGraphBuilder.dashboardNavGraph(
    onSignOut: () -> Unit,
    onNavigateToAppConfig: () -> Unit
){
    navigation(
        route = G_DASHBOARD,
        startDestination = ScreenGraphs.Dashboard.route
    ){
        composable(
            route = ScreenGraphs.Dashboard.route
        ){
            DashboardScreen(
                onSignOut = onSignOut,
                onNavigateToAppConfig = onNavigateToAppConfig
            )
        }

        //children graph beyond dashboard
        businessNavGraph()
        reportAnalysisNavGraph()
        userProfileNavGraph()
    }
}

fun NavController.navigateToDashboard(navOptions: NavOptions) {
    this.navigate(
        route = G_DASHBOARD,
        navOptions
    )
}
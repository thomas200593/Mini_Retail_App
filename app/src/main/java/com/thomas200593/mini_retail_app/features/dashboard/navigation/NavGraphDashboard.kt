package com.thomas200593.mini_retail_app.features.dashboard.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.thomas200593.mini_retail_app.app.navigation.NavGraph.G_DASHBOARD
import com.thomas200593.mini_retail_app.app.navigation.ScrGraphs
import com.thomas200593.mini_retail_app.features.business.navigation.navGraphBusiness
import com.thomas200593.mini_retail_app.features.dashboard.ui.ScrDashboard
import com.thomas200593.mini_retail_app.features.reporting.navigation.navGraphReporting
import com.thomas200593.mini_retail_app.features.user_profile.navigation.navGraphUserProfile

fun NavGraphBuilder.navGraphDashboard() {
    navigation(
        route = G_DASHBOARD,
        startDestination = ScrGraphs.Dashboard.route
    ){
        composable(
            route = ScrGraphs.Dashboard.route
        ){ ScrDashboard() }

        //children graph beyond dashboard
        navGraphBusiness()
        navGraphReporting()
        navGraphUserProfile()
    }
}

fun NavController.navToDashboard(navOptions: NavOptions) {
    this.navigate(
        route = G_DASHBOARD,
        navOptions
    )
}
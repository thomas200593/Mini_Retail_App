package com.thomas200593.mini_retail_app.features.reporting.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.thomas200593.mini_retail_app.app.navigation.NavigationGraphs.G_REPORTING
import com.thomas200593.mini_retail_app.app.navigation.ScreenGraphs
import com.thomas200593.mini_retail_app.features.reporting.ui.ReportingScreen

fun NavGraphBuilder.navGraphReporting(){
    navigation(
        route = G_REPORTING,
        startDestination = ScreenGraphs.Reporting.route
    ){
        composable(
            route = ScreenGraphs.Reporting.route
        ){
            ReportingScreen()
        }
    }
}

fun NavController.navigateToReporting(navOptions: NavOptions?) {
    this.navigate(
        route = G_REPORTING,
        navOptions
    )
}
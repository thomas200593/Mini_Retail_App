package com.thomas200593.mini_retail_app.features.reporting.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.thomas200593.mini_retail_app.app.navigation.NavGraph.G_REPORTING
import com.thomas200593.mini_retail_app.app.navigation.ScrGraphs
import com.thomas200593.mini_retail_app.features.reporting.ui.ReportingScreen

fun NavGraphBuilder.navGraphReporting(){
    navigation(
        route = G_REPORTING,
        startDestination = ScrGraphs.Reporting.route
    ){
        composable(
            route = ScrGraphs.Reporting.route
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
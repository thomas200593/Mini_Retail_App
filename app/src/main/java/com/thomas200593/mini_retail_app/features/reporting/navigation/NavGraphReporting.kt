package com.thomas200593.mini_retail_app.features.reporting.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.thomas200593.mini_retail_app.app.navigation.NavGraph.G_REPORTING
import com.thomas200593.mini_retail_app.app.navigation.ScrGraphs.Reporting
import com.thomas200593.mini_retail_app.features.reporting.ui.ScrReporting

fun NavGraphBuilder.navGraphReporting(){
    navigation(
        route = G_REPORTING,
        startDestination = Reporting.route
    ){
        composable(
            route = Reporting.route
        ){ ScrReporting() }
    }
}

fun NavController.navToReporting(navOptions: NavOptions?) {
    this.navigate(
        route = G_REPORTING,
        navOptions
    )
}
package com.thomas200593.mini_retail_app.features.report_analysis.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.thomas200593.mini_retail_app.app.navigation.NavigationGraphs
import com.thomas200593.mini_retail_app.app.navigation.NavigationGraphs.G_REPORT_ANALYSIS
import com.thomas200593.mini_retail_app.app.navigation.ScreenGraphs
import com.thomas200593.mini_retail_app.features.report_analysis.ui.ReportAnalysisScreen

fun NavGraphBuilder.reportAnalysisNavGraph(){
    navigation(
        route = G_REPORT_ANALYSIS,
        startDestination = ScreenGraphs.ReportAnalysis.route
    ){
        composable(
            route = ScreenGraphs.ReportAnalysis.route
        ){
            ReportAnalysisScreen()
        }
    }
}

fun NavController.navigateToReportAnalysis(navOptions: NavOptions?) {
    this.navigate(
        route = G_REPORT_ANALYSIS,
        navOptions
    )
}
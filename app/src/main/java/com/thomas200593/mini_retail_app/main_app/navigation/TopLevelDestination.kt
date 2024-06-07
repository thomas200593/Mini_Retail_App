package com.thomas200593.mini_retail_app.main_app.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.ui.graphics.vector.ImageVector

enum class TopLevelDestination(
    val route: String,
//    val selectedIcon: ImageVector,
//    val unselectedIcon: ImageVector,
//    val iconTextId: Int,
//    val titleText: Int
){
    DASHBOARD(
        route = ScreenGraphs.Dashboard.route
//        selectedIcon = Icons.Rounded.
    ),
    BUSINESS(
        route = ScreenGraphs.Business.route
    ),
    REPORT_ANALYSIS(
        route = ScreenGraphs.ReportAnalysis.route
    ),
    USER_PROFILE(
        route = ScreenGraphs.UserProfile.route
    )
}
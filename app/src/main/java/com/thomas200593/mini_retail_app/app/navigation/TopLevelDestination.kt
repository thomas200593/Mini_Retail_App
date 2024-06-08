package com.thomas200593.mini_retail_app.app.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.thomas200593.mini_retail_app.R
import com.thomas200593.mini_retail_app.core.ui.common.Icons

enum class TopLevelDestination(
    val route: String,
    @DrawableRes val selectedIcon: Int,
    @DrawableRes val unselectedIcon: Int,
    @StringRes val iconTextId: Int
){
    DASHBOARD(
        route = ScreenGraphs.Dashboard.route,
        selectedIcon = Icons.TopLevelDestinations.dashboard,
        unselectedIcon = Icons.TopLevelDestinations.dashboard,
        iconTextId = R.string.str_dashboard,
    ),
    BUSINESS(
        route = ScreenGraphs.Business.route,
        selectedIcon = Icons.TopLevelDestinations.business,
        unselectedIcon = Icons.TopLevelDestinations.business,
        iconTextId = R.string.str_business,
    ),
    REPORT_ANALYSIS(
        route = ScreenGraphs.ReportAnalysis.route,
        selectedIcon = Icons.TopLevelDestinations.report_analysis,
        unselectedIcon = Icons.TopLevelDestinations.report_analysis,
        iconTextId = R.string.str_report_analysis,
    ),
    USER_PROFILE(
        route = ScreenGraphs.UserProfile.route,
        selectedIcon = Icons.TopLevelDestinations.user_profile,
        unselectedIcon = Icons.TopLevelDestinations.user_profile,
        iconTextId = R.string.str_user_profile,
    )
}
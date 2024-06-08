package com.thomas200593.mini_retail_app.app.navigation

sealed class ScreenGraphs(
    val route: String
) {
    data object Initial: ScreenGraphs(
        route = AppRoutes.APP_ROUTE_INITIAL
    )
    data object Onboarding: ScreenGraphs(
        route = AppRoutes.APP_ROUTE_ONBOARDING
    )
    data object Auth: ScreenGraphs(
        route = AppRoutes.APP_ROUTE_AUTH
    )
    data object AppConfig: ScreenGraphs(
        route = AppRoutes.APP_ROUTE_APP_CONFIG
    )
    data object Dashboard: ScreenGraphs(
        route = AppRoutes.APP_ROUTE_DASHBOARD
    )
    data object Business: ScreenGraphs(
        route = AppRoutes.APP_ROUTE_BUSINESS
    )
    data object ReportAnalysis: ScreenGraphs(
        route = AppRoutes.APP_ROUTE_REPORT_ANALYSIS
    )
    data object UserProfile: ScreenGraphs(
        route = AppRoutes.APP_ROUTE_USER_PROFILE
    )
}
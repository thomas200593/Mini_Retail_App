package com.thomas200593.mini_retail_app.main_app.navigation

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
}
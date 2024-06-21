package com.thomas200593.mini_retail_app.app.navigation

sealed class ScreenGraphs(
    val route: String
) {

    data object Initial: ScreenGraphs(
        route = Routes.APP_ROUTE_INITIAL
    )
    data object Onboarding: ScreenGraphs(
        route = Routes.APP_ROUTE_ONBOARDING
    )
    data object Auth: ScreenGraphs(
        route = Routes.APP_ROUTE_AUTH
    )
    data object AppConfig: ScreenGraphs(
        route = Routes.APP_ROUTE_APP_CONFIG
    )
    data object AppConfigGeneral : ScreenGraphs(
        route = Routes.APP_ROUTE_APP_CONFIG_GENERAL
    )
    data object AppConfigGeneralCountry : ScreenGraphs(
        route = Routes.APP_ROUTE_APP_CONFIG_GENERAL_COUNTRY
    )
    data object AppConfigGeneralLanguage : ScreenGraphs(
        route = Routes.APP_ROUTE_APP_CONFIG_GENERAL_LANGUAGE
    )
    data object AppConfigGeneralTheme: ScreenGraphs (
        route = Routes.APP_ROUTE_APP_CONFIG_GENERAL_THEME
    )
    data object AppConfigGeneralTimezone: ScreenGraphs(
        route = Routes.APP_ROUTE_APP_CONFIG_GENERAL_TIMEZONE
    )
    data object AppConfigGeneralFontSize: ScreenGraphs(
        route = Routes.APP_ROUTE_APP_CONFIG_GENERAL_FONT_SIZE
    )
    data object AppConfigGeneralDynamicColor: ScreenGraphs (
        route = Routes.APP_ROUTE_APP_CONFIG_GENERAL_DYNAMIC_COLOR
    )
    data object AppConfigGeneralCurrency: ScreenGraphs(
        route = Routes.APP_ROUTE_APP_CONFIG_GENERAL_CURRENCY
    )
    data object Dashboard: ScreenGraphs(
        route = Routes.APP_ROUTE_DASHBOARD
    )
    data object Business: ScreenGraphs(
        route = Routes.APP_ROUTE_BUSINESS
    )
    data object Reporting: ScreenGraphs(
        route = Routes.APP_ROUTE_REPORTING
    )
    data object UserProfile: ScreenGraphs(
        route = Routes.APP_ROUTE_USER_PROFILE
    )
}
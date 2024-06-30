package com.thomas200593.mini_retail_app.app.navigation

sealed class ScreenGraphs(
    val route: String
) {
    /**
     * Initial
     */
    data object Initial: ScreenGraphs(route = Routes.APP_ROUTE_INITIAL)

    /**
     * Onboarding
     */
    data object Onboarding: ScreenGraphs(route = Routes.APP_ROUTE_ONBOARDING)

    /**
     * Auth
     */
    data object Auth: ScreenGraphs(route = Routes.APP_ROUTE_AUTH)

    /**
     * App Config
     */
    data object AppConfig: ScreenGraphs(route = Routes.APP_ROUTE_APP_CONFIG)
    //App Config General
    data object AppConfigGeneral : ScreenGraphs(route = Routes.APP_ROUTE_APP_CONFIG_GENERAL)
    data object AppConfigGeneralCountry : ScreenGraphs(route = Routes.APP_ROUTE_APP_CONFIG_GENERAL_COUNTRY)
    data object AppConfigGeneralCurrency: ScreenGraphs(route = Routes.APP_ROUTE_APP_CONFIG_GENERAL_CURRENCY)
    data object AppConfigGeneralDynamicColor: ScreenGraphs (route = Routes.APP_ROUTE_APP_CONFIG_GENERAL_DYNAMIC_COLOR)
    data object AppConfigGeneralFontSize: ScreenGraphs(route = Routes.APP_ROUTE_APP_CONFIG_GENERAL_FONT_SIZE)
    data object AppConfigGeneralLanguage : ScreenGraphs(route = Routes.APP_ROUTE_APP_CONFIG_GENERAL_LANGUAGE)
    data object AppConfigGeneralTheme: ScreenGraphs (route = Routes.APP_ROUTE_APP_CONFIG_GENERAL_THEME)
    data object AppConfigGeneralTimezone: ScreenGraphs(route = Routes.APP_ROUTE_APP_CONFIG_GENERAL_TIMEZONE)
    //App Config Data
    data object AppConfigData: ScreenGraphs(route = Routes.APP_ROUTE_APP_CONFIG_DATA)
    data object AppConfigDataBackup: ScreenGraphs(route = Routes.APP_ROUTE_APP_CONFIG_DATA_BACKUP)

    /**
     * Dashboard
     */
    data object Dashboard: ScreenGraphs(route = Routes.APP_ROUTE_DASHBOARD)

    /**
     * Business
     */
    data object Business: ScreenGraphs(route = Routes.APP_ROUTE_BUSINESS)
    data object BusinessMasterData: ScreenGraphs(route = Routes.APP_ROUTE_BUSINESS_MASTER_DATA)
    data object BusinessMasterDataSupplier: ScreenGraphs(route = Routes.APP_ROUTE_BUSINESS_MASTER_DATA_SUPPLIER)
    data object BusinessMasterDataCustomer: ScreenGraphs(route = Routes.APP_ROUTE_BUSINESS_MASTER_DATA_CUSTOMER)
    /**
     * Reporting
     */
    data object Reporting: ScreenGraphs(route = Routes.APP_ROUTE_REPORTING)

    /**
     * User Profile
     */
    data object UserProfile: ScreenGraphs(route = Routes.APP_ROUTE_USER_PROFILE)
}
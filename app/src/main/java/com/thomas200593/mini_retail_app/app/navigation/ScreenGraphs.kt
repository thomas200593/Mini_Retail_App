package com.thomas200593.mini_retail_app.app.navigation

sealed class ScreenGraphs(
    val route: String
) {

    /**
     * Initial
     */
    data object Initial: ScreenGraphs(route = Routes.R_INITIAL)
    data object Initialization: ScreenGraphs(route = Routes.R_INITIALIZATION)

    /**
     * Onboarding
     */
    data object Onboarding: ScreenGraphs(route = Routes.R_ONBOARDING)

    /**
     * Auth
     */
    data object Auth: ScreenGraphs(route = Routes.R_AUTH)

    /**
     * App Config
     */
    data object AppConfig: ScreenGraphs(route = Routes.R_APP_CONFIG)
    //App Config General
    data object ConfigGeneral : ScreenGraphs(route = Routes.R_CONFIG_GENERAL)
    data object Country : ScreenGraphs(route = Routes.R_COUNTRY)
    data object Currency: ScreenGraphs(route = Routes.R_CURRENCY)
    data object DynamicColor: ScreenGraphs (route = Routes.R_DYNAMIC_COLOR)
    data object FontSize: ScreenGraphs(route = Routes.R_FONT_SIZE)
    data object Language : ScreenGraphs(route = Routes.R_LANGUAGE)
    data object Theme: ScreenGraphs (route = Routes.R_THEME)
    data object Timezone: ScreenGraphs(route = Routes.R_TIMEZONE)
    //App Config Data
    data object ConfigData: ScreenGraphs(route = Routes.R_CONFIG_DATA)
    data object DataBackup: ScreenGraphs(route = Routes.R_DATA_BACKUP)

    /**
     * Dashboard
     */
    data object Dashboard: ScreenGraphs(route = Routes.R_DASHBOARD)

    /**
     * Business
     */
    data object Business: ScreenGraphs(route = Routes.R_BUSINESS)
    data object MasterData: ScreenGraphs(route = Routes.R_MASTER_DATA)
    data object Supplier: ScreenGraphs(route = Routes.R_SUPPLIER)
    data object Customer: ScreenGraphs(route = Routes.R_CUSTOMER)

    /**
     * Reporting
     */
    data object Reporting: ScreenGraphs(route = Routes.R_REPORTING)

    /**
     * User Profile
     */
    data object UserProfile: ScreenGraphs(route = Routes.R_USER_PROFILE)
}
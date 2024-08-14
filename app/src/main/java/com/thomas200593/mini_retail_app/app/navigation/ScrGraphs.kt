package com.thomas200593.mini_retail_app.app.navigation

sealed class ScrGraphs(
    val route: String
) {

    /**
     * Initial
     */
    data object Initial: ScrGraphs(route = Routes.R_INITIAL)
    data object Initialization: ScrGraphs(route = Routes.R_INITIALIZATION)

    /**
     * Onboarding
     */
    data object Onboarding: ScrGraphs(route = Routes.R_ONBOARDING)

    /**
     * Auth
     */
    data object Auth: ScrGraphs(route = Routes.R_AUTH)

    /**
     * App Config
     */
    data object AppConfig: ScrGraphs(route = Routes.R_APP_CONFIG)
    //App Config General
    data object ConfigGeneral : ScrGraphs(route = Routes.R_CONFIG_GENERAL)
    data object Country : ScrGraphs(route = Routes.R_COUNTRY)
    data object Currency: ScrGraphs(route = Routes.R_CURRENCY)
    data object DynamicColor: ScrGraphs (route = Routes.R_DYNAMIC_COLOR)
    data object FontSize: ScrGraphs(route = Routes.R_FONT_SIZE)
    data object Language : ScrGraphs(route = Routes.R_LANGUAGE)
    data object Theme: ScrGraphs (route = Routes.R_THEME)
    data object Timezone: ScrGraphs(route = Routes.R_TIMEZONE)
    //App Config Data
    data object ConfigData: ScrGraphs(route = Routes.R_CONFIG_DATA)
    data object DataBackup: ScrGraphs(route = Routes.R_DATA_BACKUP)

    /**
     * Dashboard
     */
    data object Dashboard: ScrGraphs(route = Routes.R_DASHBOARD)

    /**
     * Business
     */
    data object Business: ScrGraphs(route = Routes.R_BUSINESS)
    data object MasterData: ScrGraphs(route = Routes.R_MASTER_DATA)
    data object Supplier: ScrGraphs(route = Routes.R_SUPPLIER)
    data object Customer: ScrGraphs(route = Routes.R_CUSTOMER)
    data object BizProfile: ScrGraphs(route = Routes.R_BIZ_PROFILE)
    data object BizProfileAddressesAddUpdate: ScrGraphs(route = Routes.R_BIZ_PROFILE_ADDRESSES_ADD_UPDATE)
    data object BizProfileContactsAddUpdate: ScrGraphs(route = Routes.R_BIZ_PROFILE_CONTACTS_ADD_UPDATE)
    data object BizProfileLinksAddUpdate: ScrGraphs(route = Routes.R_BIZ_PROFILE_LINKS_ADD_UPDATE)

    /**
     * Reporting
     */
    data object Reporting: ScrGraphs(route = Routes.R_REPORTING)

    /**
     * User Profile
     */
    data object UserProfile: ScrGraphs(route = Routes.R_USER_PROFILE)
}
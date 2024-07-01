package com.thomas200593.mini_retail_app.app.navigation

import com.thomas200593.mini_retail_app.app.navigation.Routes.R_APP_CONFIG
import com.thomas200593.mini_retail_app.app.navigation.Routes.R_AUTH
import com.thomas200593.mini_retail_app.app.navigation.Routes.R_BUSINESS
import com.thomas200593.mini_retail_app.app.navigation.Routes.R_CONFIG_DATA
import com.thomas200593.mini_retail_app.app.navigation.Routes.R_CONFIG_GENERAL
import com.thomas200593.mini_retail_app.app.navigation.Routes.R_COUNTRY
import com.thomas200593.mini_retail_app.app.navigation.Routes.R_CURRENCY
import com.thomas200593.mini_retail_app.app.navigation.Routes.R_CUSTOMER
import com.thomas200593.mini_retail_app.app.navigation.Routes.R_DASHBOARD
import com.thomas200593.mini_retail_app.app.navigation.Routes.R_DATA_BACKUP
import com.thomas200593.mini_retail_app.app.navigation.Routes.R_DYNAMIC_COLOR
import com.thomas200593.mini_retail_app.app.navigation.Routes.R_FONT_SIZE
import com.thomas200593.mini_retail_app.app.navigation.Routes.R_INITIAL
import com.thomas200593.mini_retail_app.app.navigation.Routes.R_LANGUAGE
import com.thomas200593.mini_retail_app.app.navigation.Routes.R_MASTER_DATA
import com.thomas200593.mini_retail_app.app.navigation.Routes.R_ONBOARDING
import com.thomas200593.mini_retail_app.app.navigation.Routes.R_REPORTING
import com.thomas200593.mini_retail_app.app.navigation.Routes.R_SUPPLIER
import com.thomas200593.mini_retail_app.app.navigation.Routes.R_THEME
import com.thomas200593.mini_retail_app.app.navigation.Routes.R_TIMEZONE
import com.thomas200593.mini_retail_app.app.navigation.Routes.R_USER_PROFILE

sealed class ScreenGraphs(
    val route: String
) {
    /**
     * Initial
     */
    data object Initial: ScreenGraphs(route = R_INITIAL)

    /**
     * Onboarding
     */
    data object Onboarding: ScreenGraphs(route = R_ONBOARDING)

    /**
     * Auth
     */
    data object Auth: ScreenGraphs(route = R_AUTH)

    /**
     * App Config
     */
    data object AppConfig: ScreenGraphs(route = R_APP_CONFIG)
    //App Config General
    data object ConfigGeneral : ScreenGraphs(route = R_CONFIG_GENERAL)
    data object Country : ScreenGraphs(route = R_COUNTRY)
    data object Currency: ScreenGraphs(route = R_CURRENCY)
    data object DynamicColor: ScreenGraphs (route = R_DYNAMIC_COLOR)
    data object FontSize: ScreenGraphs(route = R_FONT_SIZE)
    data object Language : ScreenGraphs(route = R_LANGUAGE)
    data object Theme: ScreenGraphs (route = R_THEME)
    data object Timezone: ScreenGraphs(route = R_TIMEZONE)
    //App Config Data
    data object ConfigData: ScreenGraphs(route = R_CONFIG_DATA)
    data object DataBackup: ScreenGraphs(route = R_DATA_BACKUP)

    /**
     * Dashboard
     */
    data object Dashboard: ScreenGraphs(route = R_DASHBOARD)

    /**
     * Business
     */
    data object Business: ScreenGraphs(route = R_BUSINESS)
    data object MasterData: ScreenGraphs(route = R_MASTER_DATA)
    data object Supplier: ScreenGraphs(route = R_SUPPLIER)
    data object Customer: ScreenGraphs(route = R_CUSTOMER)

    /**
     * Reporting
     */
    data object Reporting: ScreenGraphs(route = R_REPORTING)

    /**
     * User Profile
     */
    data object UserProfile: ScreenGraphs(route = R_USER_PROFILE)
}
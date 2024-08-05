package com.thomas200593.mini_retail_app.app.navigation

import com.thomas200593.mini_retail_app.app.navigation.Routes.R_APP_CONFIG
import com.thomas200593.mini_retail_app.app.navigation.Routes.R_AUTH
import com.thomas200593.mini_retail_app.app.navigation.Routes.R_BIZ_PROFILE
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
import com.thomas200593.mini_retail_app.app.navigation.Routes.R_INITIALIZATION
import com.thomas200593.mini_retail_app.app.navigation.Routes.R_LANGUAGE
import com.thomas200593.mini_retail_app.app.navigation.Routes.R_MASTER_DATA
import com.thomas200593.mini_retail_app.app.navigation.Routes.R_ONBOARDING
import com.thomas200593.mini_retail_app.app.navigation.Routes.R_REPORTING
import com.thomas200593.mini_retail_app.app.navigation.Routes.R_SUPPLIER
import com.thomas200593.mini_retail_app.app.navigation.Routes.R_THEME
import com.thomas200593.mini_retail_app.app.navigation.Routes.R_TIMEZONE
import com.thomas200593.mini_retail_app.app.navigation.Routes.R_USER_PROFILE

sealed class ScrGraphs(
    val route: String
) {
    /**
     * Initial
     */
    data object Initial: ScrGraphs(route = R_INITIAL)
    data object Initialization: ScrGraphs(route = R_INITIALIZATION)

    /**
     * Onboarding
     */
    data object Onboarding: ScrGraphs(route = R_ONBOARDING)

    /**
     * Auth
     */
    data object Auth: ScrGraphs(route = R_AUTH)

    /**
     * App Config
     */
    data object AppConfig: ScrGraphs(route = R_APP_CONFIG)
    //App Config General
    data object ConfigGeneral : ScrGraphs(route = R_CONFIG_GENERAL)
    data object Country : ScrGraphs(route = R_COUNTRY)
    data object Currency: ScrGraphs(route = R_CURRENCY)
    data object DynamicColor: ScrGraphs (route = R_DYNAMIC_COLOR)
    data object FontSize: ScrGraphs(route = R_FONT_SIZE)
    data object Language : ScrGraphs(route = R_LANGUAGE)
    data object Theme: ScrGraphs (route = R_THEME)
    data object Timezone: ScrGraphs(route = R_TIMEZONE)
    //App Config Data
    data object ConfigData: ScrGraphs(route = R_CONFIG_DATA)
    data object DataBackup: ScrGraphs(route = R_DATA_BACKUP)

    /**
     * Dashboard
     */
    data object Dashboard: ScrGraphs(route = R_DASHBOARD)

    /**
     * Business
     */
    data object Business: ScrGraphs(route = R_BUSINESS)
    data object MasterData: ScrGraphs(route = R_MASTER_DATA)
    data object Supplier: ScrGraphs(route = R_SUPPLIER)
    data object Customer: ScrGraphs(route = R_CUSTOMER)
    data object BizProfile: ScrGraphs(route = R_BIZ_PROFILE)

    /**
     * Reporting
     */
    data object Reporting: ScrGraphs(route = R_REPORTING)

    /**
     * User Profile
     */
    data object UserProfile: ScrGraphs(route = R_USER_PROFILE)
}
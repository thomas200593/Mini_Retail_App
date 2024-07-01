package com.thomas200593.mini_retail_app.app.navigation

import com.thomas200593.mini_retail_app.app.navigation.ScreenGraphs.AppConfig
import com.thomas200593.mini_retail_app.app.navigation.ScreenGraphs.Business
import com.thomas200593.mini_retail_app.app.navigation.ScreenGraphs.ConfigData
import com.thomas200593.mini_retail_app.app.navigation.ScreenGraphs.ConfigGeneral
import com.thomas200593.mini_retail_app.app.navigation.ScreenGraphs.Country
import com.thomas200593.mini_retail_app.app.navigation.ScreenGraphs.Currency
import com.thomas200593.mini_retail_app.app.navigation.ScreenGraphs.Customer
import com.thomas200593.mini_retail_app.app.navigation.ScreenGraphs.Dashboard
import com.thomas200593.mini_retail_app.app.navigation.ScreenGraphs.DynamicColor
import com.thomas200593.mini_retail_app.app.navigation.ScreenGraphs.FontSize
import com.thomas200593.mini_retail_app.app.navigation.ScreenGraphs.Language
import com.thomas200593.mini_retail_app.app.navigation.ScreenGraphs.MasterData
import com.thomas200593.mini_retail_app.app.navigation.ScreenGraphs.Reporting
import com.thomas200593.mini_retail_app.app.navigation.ScreenGraphs.Supplier
import com.thomas200593.mini_retail_app.app.navigation.ScreenGraphs.Theme
import com.thomas200593.mini_retail_app.app.navigation.ScreenGraphs.Timezone
import timber.log.Timber

private val TAG = DestinationWithTopAppBar::class.simpleName

object DestinationWithTopAppBar{
    fun destinationWithTopAppBar(): Set<String> {
        Timber.d("Called : fun $TAG.destinationWithTopAppBar()")
        return setOf(
            /**
             * App Config
             */
            AppConfig.route,
            //Config General
            ConfigGeneral.route,
            Theme.route,
            Country.route,
            DynamicColor.route,
            Language.route,
            Timezone.route,
            Currency.route,
            FontSize.route,
            //Config Data
            ConfigData.route,

            /**
             * Dashboard
             */
            Dashboard.route,

            /**
             * Business
             */
            Business.route,
            MasterData.route,
            Supplier.route,
            Customer.route,

            /**
             * Reporting
             */
            Reporting.route,
        )
    }
}
package com.thomas200593.mini_retail_app.app.navigation

import com.thomas200593.mini_retail_app.app.navigation.ScrGraphs.*

object DestWithTopAppBar{
    fun destWithTopAppBar(): Set<String> {
        return setOf(
            /**
             * App Config
             */
            AppConfig.route,
            //Config General
            ConfigGeneral.route,
            Country.route,
            Currency.route,
            DynamicColor.route,
            FontSize.route,
            Language.route,
            Theme.route,
            Timezone.route,
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
            //Master Data
            MasterData.route,
            Customer.route,
            Supplier.route,

            /**
             * Reporting
             */
            Reporting.route,
        )
    }
}
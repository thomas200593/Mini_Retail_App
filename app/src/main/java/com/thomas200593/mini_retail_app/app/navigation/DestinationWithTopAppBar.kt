package com.thomas200593.mini_retail_app.app.navigation

object DestinationWithTopAppBar{
    fun destinationWithTopAppBar(): Set<String> {
        return setOf(
            /**
             * App Config
             */
            ScreenGraphs.AppConfig.route,
            //Config General
            ScreenGraphs.ConfigGeneral.route,
            ScreenGraphs.Country.route,
            ScreenGraphs.Currency.route,
            ScreenGraphs.DynamicColor.route,
            ScreenGraphs.FontSize.route,
            ScreenGraphs.Language.route,
            ScreenGraphs.Theme.route,
            ScreenGraphs.Timezone.route,
            //Config Data
            ScreenGraphs.ConfigData.route,

            /**
             * Dashboard
             */
            ScreenGraphs.Dashboard.route,

            /**
             * Business
             */
            ScreenGraphs.Business.route,
            //Master Data
            ScreenGraphs.MasterData.route,
            ScreenGraphs.Customer.route,
            ScreenGraphs.Supplier.route,

            /**
             * Reporting
             */
            ScreenGraphs.Reporting.route,
        )
    }
}
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
            ScreenGraphs.Theme.route,
            ScreenGraphs.Country.route,
            ScreenGraphs.DynamicColor.route,
            ScreenGraphs.Language.route,
            ScreenGraphs.Timezone.route,
            ScreenGraphs.Currency.route,
            ScreenGraphs.FontSize.route,
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
            ScreenGraphs.MasterData.route,
            ScreenGraphs.Supplier.route,
            ScreenGraphs.Customer.route,

            /**
             * Reporting
             */
            ScreenGraphs.Reporting.route,
        )
    }
}
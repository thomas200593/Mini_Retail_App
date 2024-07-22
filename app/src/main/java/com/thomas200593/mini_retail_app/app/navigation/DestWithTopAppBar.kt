package com.thomas200593.mini_retail_app.app.navigation

object DestWithTopAppBar{
    fun destWithTopAppBar(): Set<String> {
        return setOf(
            /**
             * App Config
             */
            ScrGraphs.AppConfig.route,
            //Config General
            ScrGraphs.ConfigGeneral.route,
            ScrGraphs.Country.route,
            ScrGraphs.Currency.route,
            ScrGraphs.DynamicColor.route,
            ScrGraphs.FontSize.route,
            ScrGraphs.Language.route,
            ScrGraphs.Theme.route,
            ScrGraphs.Timezone.route,
            //Config Data
            ScrGraphs.ConfigData.route,

            /**
             * Dashboard
             */
            ScrGraphs.Dashboard.route,

            /**
             * Business
             */
            ScrGraphs.Business.route,
            //Master Data
            ScrGraphs.MasterData.route,
            ScrGraphs.Customer.route,
            ScrGraphs.Supplier.route,

            /**
             * Reporting
             */
            ScrGraphs.Reporting.route,
        )
    }
}
package com.thomas200593.mini_retail_app.app.navigation

object DestinationWithTopAppBar{
    fun destinationWithTopAppBar(): Set<String> {
        return setOf(
            /**
             * App Config
             */
            ScreenGraphs.AppConfig.route,
            ScreenGraphs.AppConfigGeneral.route,
            ScreenGraphs.AppConfigGeneralTheme.route,
            ScreenGraphs.AppConfigGeneralDynamicColor.route,
            ScreenGraphs.AppConfigGeneralLanguage.route,
            ScreenGraphs.AppConfigGeneralTimezone.route,
            ScreenGraphs.AppConfigGeneralCurrency.route,
            /**
             * Dashboard
             */
            ScreenGraphs.Dashboard.route,
            /**
             * Business
             */
            ScreenGraphs.Business.route,
            /**
             * Reporting
             */
            ScreenGraphs.Reporting.route,
        )
    }
}
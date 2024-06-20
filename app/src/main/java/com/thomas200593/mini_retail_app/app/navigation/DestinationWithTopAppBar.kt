package com.thomas200593.mini_retail_app.app.navigation

import timber.log.Timber

private const val TAG = "DestinationWithTopAppBar"

object DestinationWithTopAppBar{
    fun destinationWithTopAppBar(): Set<String> {
        Timber.d("Called %s.destinationWithTopAppBar()", TAG)
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
            ScreenGraphs.AppConfigGeneralFontSize.route,
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
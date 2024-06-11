package com.thomas200593.mini_retail_app.app.navigation

object NavigationWithTopAppBar{
    fun navigationWithTopAppBar(): Set<String> {
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
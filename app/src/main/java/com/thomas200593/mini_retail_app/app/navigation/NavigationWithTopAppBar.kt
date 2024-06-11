package com.thomas200593.mini_retail_app.app.navigation

object NavigationWithTopAppBar{
    fun navigationWithTopAppBar(): Set<String> {
        return setOf(
            ScreenGraphs.AppConfig.route,
            ScreenGraphs.AppConfigGeneral.route,
            ScreenGraphs.AppConfigGeneralTheme.route,
            ScreenGraphs.AppConfigGeneralDynamicColor.route,
            ScreenGraphs.AppConfigGeneralLanguage.route,
            ScreenGraphs.Dashboard.route,
            ScreenGraphs.Business.route,
            ScreenGraphs.Reporting.route,
        )
    }
}
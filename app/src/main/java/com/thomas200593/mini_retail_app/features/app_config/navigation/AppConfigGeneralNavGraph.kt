package com.thomas200593.mini_retail_app.features.app_config.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.thomas200593.mini_retail_app.app.navigation.NavigationGraphs
import com.thomas200593.mini_retail_app.app.navigation.ScreenGraphs
import com.thomas200593.mini_retail_app.features.app_config.ui.components.AppConfigGeneralScreen


fun NavGraphBuilder.appConfigGeneralNavGraph(){
    navigation(
        route = NavigationGraphs.G_APP_CONFIG_GENERAL,
        startDestination = ScreenGraphs.AppConfigGeneral.route
    ){
        composable(
            route = ScreenGraphs.AppConfigGeneral.route
        ){
            AppConfigGeneralScreen(
                onNavigateBack = {},
                onSelectedDestinationMenu = {}
            )
        }
    }
}

fun NavController.navigateToAppConfigGeneralMenu() {
    this.navigate(
        route = NavigationGraphs.G_APP_CONFIG_GENERAL
    ){
        launchSingleTop = true
        restoreState = true
    }
}
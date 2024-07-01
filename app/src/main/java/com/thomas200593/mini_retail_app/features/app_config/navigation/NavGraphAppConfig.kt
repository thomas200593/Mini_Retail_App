package com.thomas200593.mini_retail_app.features.app_config.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import androidx.navigation.navigation
import com.thomas200593.mini_retail_app.app.navigation.NavigationGraphs.G_APP_CONFIG
import com.thomas200593.mini_retail_app.app.navigation.ScreenGraphs.AppConfig
import com.thomas200593.mini_retail_app.features.app_config.ui.AppConfigScreen

fun NavGraphBuilder.navGraphAppConfig(){
    navigation(
        route = G_APP_CONFIG,
        startDestination = AppConfig.route
    ){
        composable(
            route = AppConfig.route
        ){
            AppConfigScreen()
        }
        navGraphConfigGeneral()
        navGraphConfigData()
    }
}

fun NavController.navigateToAppConfig(
    destinationAppConfig: DestinationAppConfig?
){
    val navOptions = navOptions {
        launchSingleTop = true
        restoreState = true
    }
    this.navigate(
        route = destinationAppConfig?.route?: G_APP_CONFIG,
        navOptions = navOptions
    )
}
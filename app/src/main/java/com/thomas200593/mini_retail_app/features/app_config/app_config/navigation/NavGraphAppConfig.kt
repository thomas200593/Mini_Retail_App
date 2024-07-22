package com.thomas200593.mini_retail_app.features.app_config.app_config.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import androidx.navigation.navigation
import com.thomas200593.mini_retail_app.app.navigation.NavigationGraphs
import com.thomas200593.mini_retail_app.app.navigation.ScreenGraphs
import com.thomas200593.mini_retail_app.features.app_config.app_config.ui.AppConfigScreen
import com.thomas200593.mini_retail_app.features.app_config._data.navigation.navGraphConfigData
import com.thomas200593.mini_retail_app.features.app_config._general.navigation.navGraphConfigGeneral

fun NavGraphBuilder.navGraphAppConfig(){
    navigation(
        route = NavigationGraphs.G_APP_CONFIG,
        startDestination = ScreenGraphs.AppConfig.route
    ){
        composable(
            route = ScreenGraphs.AppConfig.route
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
        route = destinationAppConfig?.route?: NavigationGraphs.G_APP_CONFIG,
        navOptions = navOptions
    )
}
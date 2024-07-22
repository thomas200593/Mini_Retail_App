package com.thomas200593.mini_retail_app.features.app_conf.app_config.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import androidx.navigation.navigation
import com.thomas200593.mini_retail_app.app.navigation.NavGraph
import com.thomas200593.mini_retail_app.app.navigation.ScrGraphs
import com.thomas200593.mini_retail_app.features.app_conf.app_config.ui.AppConfigScreen
import com.thomas200593.mini_retail_app.features.app_conf._data.navigation.navGraphConfData
import com.thomas200593.mini_retail_app.features.app_conf._general.navigation.navGraphConfigGeneral

fun NavGraphBuilder.navGraphAppConfig(){
    navigation(
        route = NavGraph.G_APP_CONFIG,
        startDestination = ScrGraphs.AppConfig.route
    ){
        composable(
            route = ScrGraphs.AppConfig.route
        ){
            AppConfigScreen()
        }
        navGraphConfigGeneral()
        navGraphConfData()
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
        route = destinationAppConfig?.route?: NavGraph.G_APP_CONFIG,
        navOptions = navOptions
    )
}
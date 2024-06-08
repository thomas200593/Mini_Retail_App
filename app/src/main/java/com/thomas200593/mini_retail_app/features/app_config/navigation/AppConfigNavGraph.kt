package com.thomas200593.mini_retail_app.features.app_config.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.thomas200593.mini_retail_app.features.app_config.ui.AppConfigScreen
import com.thomas200593.mini_retail_app.app.navigation.NavigationGraphs
import com.thomas200593.mini_retail_app.app.navigation.ScreenGraphs
import com.thomas200593.mini_retail_app.app.ui.AppState

fun NavGraphBuilder.appConfigNavGraph(
    appState: AppState
){
    navigation(
        route = NavigationGraphs.G_APP_CONFIG,
        startDestination = ScreenGraphs.Config.route
    ){
        composable(
            route = ScreenGraphs.Config.route
        ){
            AppConfigScreen(
                //onNavKeyUp = { appState.onNavKeyUp() }
            )
        }
    }
}

fun NavController.navigateToAppConfig(){
    this.navigate(
        route = NavigationGraphs.G_APP_CONFIG
    ){
        launchSingleTop = true
        restoreState = true
    }
}
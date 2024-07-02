package com.thomas200593.mini_retail_app.features.auth.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.thomas200593.mini_retail_app.app.navigation.NavigationGraphs
import com.thomas200593.mini_retail_app.app.navigation.ScreenGraphs
import com.thomas200593.mini_retail_app.features.app_config.navigation.navGraphAppConfig
import com.thomas200593.mini_retail_app.features.auth.ui.AuthScreen
import com.thomas200593.mini_retail_app.features.dashboard.navigation.navGraphDashboard

fun NavGraphBuilder.navGraphAuth() {
    navigation(
        route = NavigationGraphs.G_AUTH,
        startDestination = ScreenGraphs.Auth.route
    ){
        composable(
            route = ScreenGraphs.Auth.route
        ){
            AuthScreen()
        }
        navGraphAppConfig()
        navGraphDashboard()
    }
}

fun NavController.navigateToAuth(){
    this.navigate(
        route = NavigationGraphs.G_AUTH
    ){
        launchSingleTop = true
        restoreState = true
        popUpTo(NavigationGraphs.G_INITIAL){
            inclusive = true
        }
    }
}
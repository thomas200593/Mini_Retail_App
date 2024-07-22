package com.thomas200593.mini_retail_app.features.auth.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.thomas200593.mini_retail_app.app.navigation.NavGraph
import com.thomas200593.mini_retail_app.app.navigation.ScrGraphs
import com.thomas200593.mini_retail_app.features.app_conf.app_config.navigation.navGraphAppConfig
import com.thomas200593.mini_retail_app.features.auth.ui.AuthScreen
import com.thomas200593.mini_retail_app.features.dashboard.navigation.navGraphDashboard

fun NavGraphBuilder.navGraphAuth() {
    navigation(
        route = NavGraph.G_AUTH,
        startDestination = ScrGraphs.Auth.route
    ){
        composable(
            route = ScrGraphs.Auth.route
        ){
            AuthScreen()
        }
        navGraphAppConfig()
        navGraphDashboard()
    }
}

fun NavController.navigateToAuth(){
    this.navigate(
        route = NavGraph.G_AUTH
    ){
        launchSingleTop = true
        restoreState = true
        popUpTo(NavGraph.G_INITIAL){
            inclusive = true
        }
    }
}
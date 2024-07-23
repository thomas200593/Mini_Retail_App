package com.thomas200593.mini_retail_app.features.auth.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.thomas200593.mini_retail_app.app.navigation.NavGraph
import com.thomas200593.mini_retail_app.app.navigation.NavGraph.G_AUTH
import com.thomas200593.mini_retail_app.app.navigation.ScrGraphs.Auth
import com.thomas200593.mini_retail_app.features.app_conf.app_config.navigation.navGraphAppConfig
import com.thomas200593.mini_retail_app.features.auth.ui.AuthScreen
import com.thomas200593.mini_retail_app.features.dashboard.navigation.navGraphDashboard

fun NavGraphBuilder.navGraphAuth() {
    navigation(
        route = G_AUTH,
        startDestination = Auth.route
    ){
        composable(
            route = Auth.route
        ){ AuthScreen() }
        navGraphAppConfig()
        navGraphDashboard()
    }
}

fun NavController.navToAuth(){
    this.navigate(
        route = G_AUTH
    ){
        launchSingleTop = true; restoreState = true
        popUpTo(NavGraph.G_INITIAL){ inclusive = true }
    }
}
package com.thomas200593.mini_retail_app.features.auth.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.thomas200593.mini_retail_app.features.app_config.navigation.appConfigNavGraph
import com.thomas200593.mini_retail_app.features.app_config.navigation.navigateToAppConfig
import com.thomas200593.mini_retail_app.features.auth.ui.AuthScreen
import com.thomas200593.mini_retail_app.features.dashboard.navigation.dashboardNavGraph
import com.thomas200593.mini_retail_app.main_app.navigation.NavigationGraphs.G_AUTH
import com.thomas200593.mini_retail_app.main_app.navigation.NavigationGraphs.G_INITIAL
import com.thomas200593.mini_retail_app.main_app.navigation.ScreenGraphs
import com.thomas200593.mini_retail_app.main_app.ui.AppState

fun NavGraphBuilder.authNavGraph(
    onNavigateToInitial: () -> Unit,
    appState: AppState
){
    navigation(
        route = G_AUTH,
        startDestination = ScreenGraphs.Auth.route
    ){
        composable(
            route = ScreenGraphs.Auth.route
        ){
            AuthScreen(
                onNavigateToInitial = onNavigateToInitial,
                onNavigateToAppConfig = { appState.navController.navigateToAppConfig() }
            )
        }
        appConfigNavGraph(appState = appState)
        dashboardNavGraph()
    }
}

fun NavController.navigateToAuth(){
    this.navigate(
        route = G_AUTH
    ){
        launchSingleTop = true
        restoreState = true
        popUpTo(G_INITIAL){
            inclusive = true
        }
    }
}
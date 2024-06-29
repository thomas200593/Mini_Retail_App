package com.thomas200593.mini_retail_app.features.auth.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.thomas200593.mini_retail_app.features.app_config.navigation.navGraphAppConfig
import com.thomas200593.mini_retail_app.features.auth.ui.AuthScreen
import com.thomas200593.mini_retail_app.features.dashboard.navigation.navGraphDashboard
import com.thomas200593.mini_retail_app.app.navigation.NavigationGraphs.G_AUTH
import com.thomas200593.mini_retail_app.app.navigation.NavigationGraphs.G_INITIAL
import com.thomas200593.mini_retail_app.app.navigation.ScreenGraphs
import timber.log.Timber

private val TAG_NAV_GRAPH_BUILDER = NavGraphBuilder::class.simpleName
private val TAG_NAV_CONTROLLER = NavController::class.simpleName

fun NavGraphBuilder.navGraphAuth() {
    navigation(
        route = G_AUTH,
        startDestination = ScreenGraphs.Auth.route
    ){
        Timber.d("Called : fun $TAG_NAV_GRAPH_BUILDER.authNavGraph()")
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
    Timber.d("Called : fun $TAG_NAV_CONTROLLER.navigateToAuth()")
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
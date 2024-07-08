package com.thomas200593.mini_retail_app.features.initial.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.thomas200593.mini_retail_app.features.auth.navigation.navGraphAuth
import com.thomas200593.mini_retail_app.features.initial.ui.InitialScreen
import com.thomas200593.mini_retail_app.features.onboarding.navigation.navGraphOnboarding
import com.thomas200593.mini_retail_app.app.navigation.NavigationGraphs.G_INITIAL
import com.thomas200593.mini_retail_app.app.navigation.ScreenGraphs
import com.thomas200593.mini_retail_app.features.initial.ui.initialization.InitializationScreen
import timber.log.Timber

private val TAG_NAV_GRAPH_BUILDER = NavGraphBuilder::class.simpleName
private val TAG_NAV_CONTROLLER = NavController::class.simpleName

fun NavGraphBuilder.navGraphInitial() {
    Timber.d("Called : fun $TAG_NAV_GRAPH_BUILDER.initialNavGraph()")
    navigation(
        route = G_INITIAL,
        startDestination = ScreenGraphs.Initial.route
    ){
        composable(
            route = ScreenGraphs.Initial.route
        ){
            InitialScreen()
        }
        composable(
            route = ScreenGraphs.Initialization.route
        ){
            InitializationScreen()
        }
        navGraphOnboarding()
        navGraphAuth()
    }
}

fun NavController.navigateToInitial(){
    Timber.d("Called : fun $TAG_NAV_CONTROLLER.navigateToInitial()")
    this.navigate(
        route = G_INITIAL
    ){
        launchSingleTop = true
        restoreState = true
        popUpTo(G_INITIAL){
            inclusive = true
        }
    }
}

fun NavController.navigateToInitialization(){
    this.navigate(
        route = ScreenGraphs.Initialization.route
    ){
        launchSingleTop = true
        restoreState = true
        popUpTo(G_INITIAL){
            inclusive = true
        }
    }
}
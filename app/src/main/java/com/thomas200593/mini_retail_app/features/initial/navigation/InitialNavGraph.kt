package com.thomas200593.mini_retail_app.features.initial.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.thomas200593.mini_retail_app.features.auth.navigation.authNavGraph
import com.thomas200593.mini_retail_app.features.initial.ui.InitialScreen
import com.thomas200593.mini_retail_app.features.onboarding.navigation.onBoardingNavGraph
import com.thomas200593.mini_retail_app.app.navigation.NavigationGraphs.G_INITIAL
import com.thomas200593.mini_retail_app.app.navigation.ScreenGraphs

fun NavGraphBuilder.initialNavGraph() {
    navigation(
        route = G_INITIAL,
        startDestination = ScreenGraphs.Initial.route
    ){
        composable(
            route = ScreenGraphs.Initial.route
        ){
            InitialScreen()
        }
        onBoardingNavGraph()
        authNavGraph()
    }
}

fun NavController.navigateToInitial(){
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
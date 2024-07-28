package com.thomas200593.mini_retail_app.features.initial.initial.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.thomas200593.mini_retail_app.app.navigation.NavGraph.G_INITIAL
import com.thomas200593.mini_retail_app.app.navigation.ScrGraphs.Initial
import com.thomas200593.mini_retail_app.app.navigation.ScrGraphs.Initialization
import com.thomas200593.mini_retail_app.features.auth.navigation.navGraphAuth
import com.thomas200593.mini_retail_app.features.initial.initial.ui.ScrInitial
import com.thomas200593.mini_retail_app.features.initial.initialization.ui.ScrInitialization
import com.thomas200593.mini_retail_app.features.onboarding.navigation.navGraphOnboarding

fun NavGraphBuilder.navGraphInitial() {
    navigation(
        route = G_INITIAL,
        startDestination = Initial.route
    ){
        composable(
            route = Initial.route
        ){ ScrInitial() }
        composable(
            route = Initialization.route
        ){ ScrInitialization() }
        navGraphOnboarding()
        navGraphAuth()
    }
}

fun NavController.navToInitial(){
    this.navigate(
        route = G_INITIAL
    ){
        launchSingleTop = true; restoreState = true
        popUpTo(G_INITIAL){ inclusive = true }
    }
}
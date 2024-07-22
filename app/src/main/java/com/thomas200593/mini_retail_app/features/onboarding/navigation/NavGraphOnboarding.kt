package com.thomas200593.mini_retail_app.features.onboarding.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.thomas200593.mini_retail_app.features.onboarding.ui.OnboardingScreen
import com.thomas200593.mini_retail_app.app.navigation.NavGraph.G_INITIAL
import com.thomas200593.mini_retail_app.app.navigation.NavGraph.G_ONBOARDING
import com.thomas200593.mini_retail_app.app.navigation.ScrGraphs


fun NavGraphBuilder.navGraphOnboarding(){
    navigation(
        route = G_ONBOARDING,
        startDestination = ScrGraphs.Onboarding.route
    ){
        composable(
            route = ScrGraphs.Onboarding.route
        ){
            OnboardingScreen()
        }
    }
}

fun NavController.navigateToOnboarding(){
    this.navigate(
        route = G_ONBOARDING
    ){
        launchSingleTop = true
        restoreState = true
        popUpTo(G_INITIAL){
            inclusive
        }
    }
}
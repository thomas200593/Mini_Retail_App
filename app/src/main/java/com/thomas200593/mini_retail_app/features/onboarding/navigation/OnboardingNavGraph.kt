package com.thomas200593.mini_retail_app.features.onboarding.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.thomas200593.mini_retail_app.features.onboarding.ui.OnboardingScreen
import com.thomas200593.mini_retail_app.app.navigation.NavigationGraphs.G_INITIAL
import com.thomas200593.mini_retail_app.app.navigation.NavigationGraphs.G_ONBOARDING
import com.thomas200593.mini_retail_app.app.navigation.ScreenGraphs


fun NavGraphBuilder.onBoardingNavGraph(
    onOnboardingFinished: () -> Unit = {}
){
    navigation(
        route = G_ONBOARDING,
        startDestination = ScreenGraphs.Onboarding.route
    ){
        composable(
            route = ScreenGraphs.Onboarding.route
        ){
            OnboardingScreen(
                onOnboardingFinished = onOnboardingFinished
            )
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
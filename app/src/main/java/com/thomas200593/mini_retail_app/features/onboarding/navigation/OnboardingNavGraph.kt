package com.thomas200593.mini_retail_app.features.onboarding.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.thomas200593.mini_retail_app.features.onboarding.ui.OnboardingScreen
import com.thomas200593.mini_retail_app.main_app.navigation.NavigationGraphs
import com.thomas200593.mini_retail_app.main_app.navigation.ScreenGraphs


fun NavGraphBuilder.onBoardingNavGraph(){
    navigation(
        route = NavigationGraphs.G_ONBOARDING,
        startDestination = ScreenGraphs.Onboarding.route
    ){
        composable(
            route = ScreenGraphs.Onboarding.route
        ){
            OnboardingScreen()
        }
    }
}

fun NavController.navigateToOnboardingScreen(){
    this.navigate(
        route = NavigationGraphs.G_ONBOARDING,
        navOptions = NavOptions.Builder()
            .setLaunchSingleTop(true)
            .setRestoreState(true)
            .build()
    )
}
package com.thomas200593.mini_retail_app.features.initial.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.thomas200593.mini_retail_app.features.auth.navigation.authNavGraph
import com.thomas200593.mini_retail_app.features.initial.ui.InitialScreen
import com.thomas200593.mini_retail_app.features.onboarding.navigation.onBoardingNavGraph
import com.thomas200593.mini_retail_app.main_app.navigation.NavigationGraphs.G_INITIAL
import com.thomas200593.mini_retail_app.main_app.navigation.ScreenGraphs
import com.thomas200593.mini_retail_app.main_app.ui.AppState

fun NavGraphBuilder.initialNavGraph(
    appState: AppState,
    onNavigateToOnboarding: () -> Unit,
    onNavigateToAuth: () -> Unit,
    onNavigateToDashboard: () -> Unit
) {
    navigation(
        route = G_INITIAL,
        startDestination = ScreenGraphs.Initial.route
    ){
        composable(
            route = ScreenGraphs.Initial.route
        ){
            InitialScreen(
                onNavigateToOnboarding = onNavigateToOnboarding,
                onNavigateToAuthScreen = onNavigateToAuth,
                onNavigateToDashboard = onNavigateToDashboard
            )
        }
        onBoardingNavGraph(
            onOnboardingFinished = { appState.navController.navigateToInitial() }
        )
        authNavGraph(
            onNavigateToInitial = { appState.navController.navigateToInitial() },
            appState = appState
        )
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
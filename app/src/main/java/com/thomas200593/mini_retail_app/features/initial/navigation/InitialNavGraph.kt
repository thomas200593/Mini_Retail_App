package com.thomas200593.mini_retail_app.features.initial.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.thomas200593.mini_retail_app.features.auth.navigation.authNavGraph
import com.thomas200593.mini_retail_app.features.initial.ui.InitialScreen
import com.thomas200593.mini_retail_app.features.onboarding.navigation.navigateToOnboardingScreen
import com.thomas200593.mini_retail_app.features.onboarding.navigation.onBoardingNavGraph
import com.thomas200593.mini_retail_app.main_app.navigation.NavigationGraphs.G_INITIAL
import com.thomas200593.mini_retail_app.main_app.navigation.ScreenGraphs
import com.thomas200593.mini_retail_app.main_app.ui.AppState

fun NavGraphBuilder.initialNavGraph(
    appState: AppState,
    onNavigateToOnboardingScreen: () -> Unit,
    onNavigateToAuthScreen: () -> Unit,
) {
    navigation(
        route = G_INITIAL,
        startDestination = ScreenGraphs.Initial.route
    ){
        composable(
            route = ScreenGraphs.Initial.route
        ){
            //TODO Fix This
            InitialScreen(
                //onNavigateToDashboard
                onNavigateToOnboardingScreen = onNavigateToOnboardingScreen,
                onNavigateToAuthScreen = onNavigateToAuthScreen
            )
        }
        onBoardingNavGraph(
            onOnboardingFinished = { appState.navController.navigateToInitial() }
        )
        authNavGraph()
    }
}

fun NavController.navigateToInitial(){
    this.navigate(
        route = G_INITIAL
    ){
        popUpTo(G_INITIAL){
            inclusive = true
        }
    }
}
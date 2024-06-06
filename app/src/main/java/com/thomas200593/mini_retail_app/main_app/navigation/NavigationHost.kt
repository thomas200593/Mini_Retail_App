package com.thomas200593.mini_retail_app.main_app.navigation

import androidx.compose.material3.SnackbarDuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.thomas200593.mini_retail_app.features.auth.navigation.navigateToAuthScreen
import com.thomas200593.mini_retail_app.features.dashboard.navigation.navigateToDashboardScreen
import com.thomas200593.mini_retail_app.features.initial.navigation.initialNavGraph
import com.thomas200593.mini_retail_app.features.onboarding.navigation.navigateToOnboardingScreen
import com.thomas200593.mini_retail_app.main_app.navigation.NavigationGraphs.G_INITIAL
import com.thomas200593.mini_retail_app.main_app.navigation.NavigationGraphs.G_ROOT
import com.thomas200593.mini_retail_app.main_app.ui.AppState

@Composable
fun NavigationHost(
    appState: AppState,
    onShowSnackBar: suspend (String, String, SnackbarDuration?) -> Boolean,
    modifier: Modifier = Modifier
) {
    val navController = appState.navController
    NavHost(
        navController = navController,
        route = G_ROOT,
        startDestination = G_INITIAL,
    ){
        initialNavGraph(
            appState,
            onNavigateToOnboardingScreen = navController::navigateToOnboardingScreen,
            onNavigateToAuthScreen = navController::navigateToAuthScreen,
            onNavigateToDashboardScreen = {
                //TODO FIX THIS ROUTE
            }
        )
    }
}
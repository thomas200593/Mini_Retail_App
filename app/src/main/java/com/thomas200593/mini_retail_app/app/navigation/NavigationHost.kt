package com.thomas200593.mini_retail_app.app.navigation

import androidx.compose.material3.SnackbarDuration
import androidx.compose.runtime.Composable
import androidx.navigation.NavOptions
import androidx.navigation.compose.NavHost
import com.thomas200593.mini_retail_app.features.auth.navigation.navigateToAuth
import com.thomas200593.mini_retail_app.features.dashboard.navigation.navigateToDashboard
import com.thomas200593.mini_retail_app.features.initial.navigation.initialNavGraph
import com.thomas200593.mini_retail_app.features.onboarding.navigation.navigateToOnboarding
import com.thomas200593.mini_retail_app.app.navigation.NavigationGraphs.G_INITIAL
import com.thomas200593.mini_retail_app.app.navigation.NavigationGraphs.G_ROOT
import com.thomas200593.mini_retail_app.app.ui.AppState

object NavigationHost{
    @Composable
    fun NavigationHost(
        appState: AppState,
        onShowSnackBar: suspend (String, String, SnackbarDuration?) -> Boolean
    ) {
        val navController = appState.navController
        NavHost(
            navController = navController,
            route = G_ROOT,
            startDestination = G_INITIAL,
        ){
            initialNavGraph(
                appState,
                onNavigateToOnboarding = navController::navigateToOnboarding,
                onNavigateToAuth = navController::navigateToAuth,
                onNavigateToDashboard = {
                    navController
                        .navigateToDashboard(
                            navOptions = NavOptions.Builder()
                                .setPopUpTo(route = G_INITIAL, inclusive = true, saveState = true)
                                .setLaunchSingleTop(true)
                                .setRestoreState(true)
                                .build()
                        )
                }
            )
        }
    }
}
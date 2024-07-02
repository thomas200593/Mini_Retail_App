package com.thomas200593.mini_retail_app.app.navigation

import androidx.compose.material3.SnackbarDuration
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import com.thomas200593.mini_retail_app.app.ui.LocalAppState
import com.thomas200593.mini_retail_app.features.initial.navigation.navGraphInitial

object NavigationHost{
    @Composable
    fun NavigationHost(
        onShowSnackBar: suspend (String, String, SnackbarDuration?) -> Boolean
    ) {
        val appState = LocalAppState.current
        val navController = appState.navController
        NavHost(
            navController = navController,
            route = NavigationGraphs.G_ROOT,
            startDestination = NavigationGraphs.G_INITIAL,
        ){
            navGraphInitial()
        }
    }
}
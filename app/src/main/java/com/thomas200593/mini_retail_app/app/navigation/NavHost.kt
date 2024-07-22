package com.thomas200593.mini_retail_app.app.navigation

import androidx.compose.material3.SnackbarDuration
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import com.thomas200593.mini_retail_app.app.ui.LocalStateApp
import com.thomas200593.mini_retail_app.features.initial.navigation.navGraphInitial

object NavHost{
    @Composable
    fun NavigationHost(
        onShowSnackBar: suspend (String, String, SnackbarDuration?) -> Boolean
    ) {
        val appState = LocalStateApp.current
        val navController = appState.navController
        NavHost(
            navController = navController,
            route = NavGraph.G_ROOT,
            startDestination = NavGraph.G_INITIAL,
        ){ navGraphInitial() }
    }
}
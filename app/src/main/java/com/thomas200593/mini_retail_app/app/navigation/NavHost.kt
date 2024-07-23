package com.thomas200593.mini_retail_app.app.navigation

import androidx.compose.material3.SnackbarDuration
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import com.thomas200593.mini_retail_app.app.navigation.NavGraph.G_INITIAL
import com.thomas200593.mini_retail_app.app.navigation.NavGraph.G_ROOT
import com.thomas200593.mini_retail_app.app.ui.LocalStateApp
import com.thomas200593.mini_retail_app.features.initial.initial.navigation.navGraphInitial

object NavHost{
    @Composable
    fun NavigationHost(
        onShowSnackBar: suspend (String, String, SnackbarDuration?) -> Boolean
    ) {
        val appState = LocalStateApp.current
        val navController = appState.navController
        NavHost(
            navController = navController,
            route = G_ROOT,
            startDestination = G_INITIAL,
        ){ navGraphInitial() }
    }
}
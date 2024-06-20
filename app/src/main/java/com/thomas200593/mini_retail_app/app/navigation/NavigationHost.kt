package com.thomas200593.mini_retail_app.app.navigation

import androidx.compose.material3.SnackbarDuration
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import com.thomas200593.mini_retail_app.features.initial.navigation.initialNavGraph
import com.thomas200593.mini_retail_app.app.navigation.NavigationGraphs.G_INITIAL
import com.thomas200593.mini_retail_app.app.navigation.NavigationGraphs.G_ROOT
import com.thomas200593.mini_retail_app.app.ui.LocalAppState
import timber.log.Timber

private const val TAG = "NavigationHost"

object NavigationHost{
    @Composable
    fun NavigationHost(
        onShowSnackBar: suspend (String, String, SnackbarDuration?) -> Boolean
    ) {
        Timber.d("Called %s.NavigationHost()", TAG)
        val appState = LocalAppState.current
        val navController = appState.navController
        NavHost(
            navController = navController,
            route = G_ROOT,
            startDestination = G_INITIAL,
        ){
            initialNavGraph()
        }
    }
}
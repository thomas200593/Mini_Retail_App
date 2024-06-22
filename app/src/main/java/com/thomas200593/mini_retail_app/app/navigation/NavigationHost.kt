package com.thomas200593.mini_retail_app.app.navigation

import androidx.compose.material3.SnackbarDuration
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import com.thomas200593.mini_retail_app.features.initial.navigation.initialNavGraph
import com.thomas200593.mini_retail_app.app.navigation.NavigationGraphs.G_INITIAL
import com.thomas200593.mini_retail_app.app.navigation.NavigationGraphs.G_ROOT
import com.thomas200593.mini_retail_app.app.ui.LocalAppState
import timber.log.Timber

private val TAG = NavigationHost::class.simpleName

object NavigationHost{
    @Composable
    fun NavigationHost(
        onShowSnackBar: suspend (String, String, SnackbarDuration?) -> Boolean
    ) {
        Timber.d("Called : fun $TAG.NavigationHost()")
        val appState = LocalAppState.current
        val navController = appState.navController
        NavHost(
            navController = navController,
            route = G_ROOT,
            startDestination = G_INITIAL,
        ){
            Timber.d("Called : fun $TAG.NavHost()")
            initialNavGraph()
        }
    }
}
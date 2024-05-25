package com.thomas200593.mini_retail_app.main_app.ui

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.thomas200593.mini_retail_app.core.design_system.util.NetworkMonitor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

@Composable
fun rememberAppState(
    windowsSizeClass: WindowSizeClass,
    networkMonitor: NetworkMonitor,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    navController: NavHostController = rememberNavController()
): AppState = remember(
        windowsSizeClass,
        networkMonitor,
        coroutineScope,
        navController
    ){
        AppState(
            windowsSizeClass = windowsSizeClass,
            networkMonitor = networkMonitor,
            coroutineScope = coroutineScope,
            navController = navController,
        )
    }

@Stable
class AppState(
    val navController: NavHostController,
    coroutineScope: CoroutineScope,
    val windowsSizeClass: WindowSizeClass,
    networkMonitor: NetworkMonitor
) {
    val isNetworkOffline = networkMonitor.isNetworkOnline
        .map(Boolean::not)
        .stateIn(
            scope = coroutineScope,
            initialValue = false,
            started = SharingStarted.WhileSubscribed(1_000)
        )
}
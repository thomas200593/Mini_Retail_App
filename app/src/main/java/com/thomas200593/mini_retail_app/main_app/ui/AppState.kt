package com.thomas200593.mini_retail_app.main_app.ui

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.util.trace
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.thomas200593.mini_retail_app.core.design_system.util.NetworkMonitor
import com.thomas200593.mini_retail_app.features.business.navigation.navigateToBusiness
import com.thomas200593.mini_retail_app.features.dashboard.navigation.navigateToDashboard
import com.thomas200593.mini_retail_app.features.report_analysis.navigation.navigateToReportAnalysis
import com.thomas200593.mini_retail_app.features.user_profile.navigation.navigateToUserProfile
import com.thomas200593.mini_retail_app.main_app.navigation.NavigationGraphs
import com.thomas200593.mini_retail_app.main_app.navigation.TopLevelDestination
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
            navController = navController
        )
    }

@Stable
class AppState(
    val windowsSizeClass: WindowSizeClass,
    networkMonitor: NetworkMonitor,
    coroutineScope: CoroutineScope,
    val navController: NavHostController,
) {
    val isNetworkOffline = networkMonitor.isNetworkOnline
        .map(Boolean::not)
        .stateIn(
            scope = coroutineScope,
            initialValue = false,
            started = SharingStarted.WhileSubscribed(1_000)
        )

    val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState()
            .value?.destination

    val currentRoute
        get() = navController.currentDestination?.route

    val windowsSizeTypeClass: MutableState<WindowSizeClass>
        get() = mutableStateOf(windowsSizeClass)

    private val NavGraph.startDestination: NavDestination?
        get() = findNode(startDestinationId)

    private tailrec fun findStartDestination(graph: NavDestination): NavDestination {
        return if (graph is NavGraph) findStartDestination(graph.startDestination!!) else graph
    }

    val currentTopLevelDestination: TopLevelDestination?
        @Composable get() = when(currentDestination?.route){
            NavigationGraphs.G_DASHBOARD -> TopLevelDestination.DASHBOARD
            NavigationGraphs.G_BUSINESS -> TopLevelDestination.BUSINESS
            NavigationGraphs.G_REPORT_ANALYSIS -> TopLevelDestination.REPORT_ANALYSIS
            NavigationGraphs.G_USER_PROFILE -> TopLevelDestination.USER_PROFILE
            else -> null
        }

    val topLevelDestination: List<TopLevelDestination> = TopLevelDestination.entries

    fun navigateToTopLevelDestination(topLevelDestination: TopLevelDestination){
        trace("Navigation: ${topLevelDestination.name}"){
            val topLevelDestinationNavOptions = navOptions {
                popUpTo(navController.graph.findStartDestination().id){
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }

            when(topLevelDestination){
                TopLevelDestination.DASHBOARD -> navController
                    .navigateToDashboard(topLevelDestinationNavOptions)
                TopLevelDestination.BUSINESS -> navController
                    .navigateToBusiness(topLevelDestinationNavOptions)
                TopLevelDestination.REPORT_ANALYSIS -> navController
                    .navigateToReportAnalysis(topLevelDestinationNavOptions)
                TopLevelDestination.USER_PROFILE -> navController
                    .navigateToUserProfile(topLevelDestinationNavOptions)
            }
        }
    }
}
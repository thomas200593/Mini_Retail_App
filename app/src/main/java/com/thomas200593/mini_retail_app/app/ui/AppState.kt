package com.thomas200593.mini_retail_app.app.ui

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavDestination
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
import com.thomas200593.mini_retail_app.app.navigation.ScreenGraphs
import com.thomas200593.mini_retail_app.app.navigation.TopLevelDestination
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
            .currentBackStackEntryAsState().value?.destination

    val topLevelDestinations: List<TopLevelDestination> = TopLevelDestination.entries

    val shouldShowBottomBar: Boolean
        @Composable get() = currentDestination?.route in topLevelDestinations.map { it.route }

    val shouldShowTopBar: Boolean
        @Composable get() = currentDestination?.route in setOf(
            ScreenGraphs.AppConfig.route,
            ScreenGraphs.Dashboard.route,
            ScreenGraphs.Business.route,
            ScreenGraphs.ReportAnalysis.route,
            ScreenGraphs.UserProfile.route,
        )

    val currentTopLevelDestination: TopLevelDestination?
        @Composable get() = when(currentDestination?.route){
            ScreenGraphs.Dashboard.route -> TopLevelDestination.DASHBOARD
            ScreenGraphs.Business.route -> TopLevelDestination.BUSINESS
            ScreenGraphs.ReportAnalysis.route -> TopLevelDestination.REPORT_ANALYSIS
            ScreenGraphs.UserProfile.route -> TopLevelDestination.USER_PROFILE
            else -> null
        }

    fun navigateToTopLevelDestination(topLevelDestination: TopLevelDestination){
        val topLevelDestinationNavOptions = navOptions {
            popUpTo(navController.graph.findStartDestination().id){
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }

        when(topLevelDestination){
            TopLevelDestination.DASHBOARD -> {
                navController.navigateToDashboard(navOptions = topLevelDestinationNavOptions)
            }
            TopLevelDestination.BUSINESS -> {
                navController.navigateToBusiness(navOptions = topLevelDestinationNavOptions)
            }
            TopLevelDestination.REPORT_ANALYSIS -> {
                navController.navigateToReportAnalysis(navOptions = topLevelDestinationNavOptions)
            }
            TopLevelDestination.USER_PROFILE -> {
                navController.navigateToUserProfile(navOptions = topLevelDestinationNavOptions)
            }
        }
    }
}
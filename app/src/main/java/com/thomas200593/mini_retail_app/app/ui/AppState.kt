package com.thomas200593.mini_retail_app.app.ui

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
import com.thomas200593.mini_retail_app.app.navigation.DestinationTopLevel
import com.thomas200593.mini_retail_app.app.navigation.DestinationWithTopAppBar.destinationWithTopAppBar
import com.thomas200593.mini_retail_app.core.design_system.util.NetworkMonitor
import com.thomas200593.mini_retail_app.features.business.navigation.navigateToBusiness
import com.thomas200593.mini_retail_app.features.dashboard.navigation.navigateToDashboard
import com.thomas200593.mini_retail_app.features.reporting.navigation.navigateToReporting
import com.thomas200593.mini_retail_app.features.user_profile.navigation.navigateToUserProfile
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import timber.log.Timber

private const val TAG = "AppState"

@Composable
fun rememberAppState(
    networkMonitor: NetworkMonitor,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    navController: NavHostController = rememberNavController()
): AppState =
    remember(
        networkMonitor,
        coroutineScope,
        navController
    ) {
        Timber.d("Called %s.rememberAppState()", TAG)
        AppState(
            networkMonitor = networkMonitor,
            coroutineScope = coroutineScope,
            navController = navController
        )
    }

@Stable
class AppState(
    networkMonitor: NetworkMonitor,
    val coroutineScope: CoroutineScope,
    val navController: NavHostController,
) {
    val isNetworkOffline = networkMonitor.isNetworkOnline
        .map(Boolean::not)
        .stateIn(
            scope = coroutineScope,
            initialValue = false,
            started = SharingStarted.WhileSubscribed(1_000)
        )

    val destinationCurrent: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination

    val destinationTopLevels: List<DestinationTopLevel> = DestinationTopLevel.entries

    val shouldShowBottomBar: Boolean
        @Composable get() = destinationCurrent?.route in destinationTopLevels.map { it.route }

    val shouldShowTopBar: Boolean
        @Composable get() = destinationCurrent?.route in destinationWithTopAppBar()

    fun navigateToDestinationTopLevel(destinationTopLevel: DestinationTopLevel){
        Timber.d("Called %s.navigateToDestinationTopLevel()", TAG)
        val destinationTopLevelNavOptions = navOptions {
            popUpTo(navController.graph.findStartDestination().id){
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
        when(destinationTopLevel){
            DestinationTopLevel.DASHBOARD -> {
                navController.navigateToDashboard(navOptions = destinationTopLevelNavOptions)
            }
            DestinationTopLevel.BUSINESS -> {
                navController.navigateToBusiness(navOptions = destinationTopLevelNavOptions)
            }
            DestinationTopLevel.REPORTING -> {
                navController.navigateToReporting(navOptions = destinationTopLevelNavOptions)
            }
            DestinationTopLevel.USER_PROFILE -> {
                navController.navigateToUserProfile(navOptions = destinationTopLevelNavOptions)
            }
        }
    }

    fun onNavigateUp() {
        Timber.d("Called : %s.onNavigateUp()", TAG)
        navController.navigateUp()
    }
}
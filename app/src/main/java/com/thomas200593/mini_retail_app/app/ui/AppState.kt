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
import com.thomas200593.mini_retail_app.app.navigation.DestinationTopLevel.BUSINESS
import com.thomas200593.mini_retail_app.app.navigation.DestinationTopLevel.DASHBOARD
import com.thomas200593.mini_retail_app.app.navigation.DestinationTopLevel.REPORTING
import com.thomas200593.mini_retail_app.app.navigation.DestinationTopLevel.USER_PROFILE
import com.thomas200593.mini_retail_app.app.navigation.DestinationTopLevel.entries
import com.thomas200593.mini_retail_app.app.navigation.DestinationWithTopAppBar.destinationWithTopAppBar
import com.thomas200593.mini_retail_app.core.data.local.session.Session
import com.thomas200593.mini_retail_app.core.data.local.session.SessionState.Loading
import com.thomas200593.mini_retail_app.core.design_system.util.NetworkMonitor
import com.thomas200593.mini_retail_app.features.business.navigation.navigateToBusiness
import com.thomas200593.mini_retail_app.features.dashboard.navigation.navigateToDashboard
import com.thomas200593.mini_retail_app.features.reporting.navigation.navigateToReporting
import com.thomas200593.mini_retail_app.features.user_profile.navigation.navigateToUserProfile
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted.Companion.Eagerly
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import timber.log.Timber

private val TAG = AppState::class.simpleName

@Composable
fun rememberAppState(
    networkMonitor: NetworkMonitor,
    session: Session,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    navController: NavHostController = rememberNavController()
): AppState =
    remember(
        networkMonitor,
        session,
        coroutineScope,
        navController
    ) {
        Timber.d("Called : fun $TAG.rememberAppState()")
        AppState(
            networkMonitor = networkMonitor,
            session = session,
            coroutineScope = coroutineScope,
            navController = navController
        )
    }

@Stable
class AppState(
    networkMonitor: NetworkMonitor,
    session: Session,
    val coroutineScope: CoroutineScope,
    val navController: NavHostController,
) {
    val isNetworkOffline = networkMonitor.isNetworkOnline
        .map(Boolean::not)
        .stateIn(
            scope = coroutineScope,
            initialValue = false,
            started = WhileSubscribed(1_000)
        )

    val isSessionValid = session.currentUserSession
        .stateIn(
            scope = coroutineScope,
            initialValue = Loading,
            started = Eagerly
        )

    val destinationCurrent: NavDestination?
        @Composable get() = navController.currentBackStackEntryAsState().value?.destination

    val destinationTopLevels: List<DestinationTopLevel> = entries

    val shouldShowBottomBar: Boolean
        @Composable get() = destinationCurrent?.route in destinationTopLevels.map { it.route }

    val shouldShowTopBar: Boolean
        @Composable get() = destinationCurrent?.route in destinationWithTopAppBar()

    fun navigateToDestinationTopLevel(destinationTopLevel: DestinationTopLevel){
        Timber.d("Called : fun $TAG.navigateToDestinationTopLevel(destinationTopLevel=$destinationTopLevel)")
        val destinationTopLevelNavOptions = navOptions {
            popUpTo(navController.graph.findStartDestination().id){
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
        when(destinationTopLevel){
            DASHBOARD -> {
                navController.navigateToDashboard(navOptions = destinationTopLevelNavOptions)
            }
            BUSINESS -> {
                navController.navigateToBusiness(navOptions = destinationTopLevelNavOptions)
            }
            REPORTING -> {
                navController.navigateToReporting(navOptions = destinationTopLevelNavOptions)
            }
            USER_PROFILE -> {
                navController.navigateToUserProfile(navOptions = destinationTopLevelNavOptions)
            }
        }
    }

    fun onNavigateUp() {
        Timber.d("Called : fun $TAG.onNavigateUp()")
        navController.navigateUp()
    }
}
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
import com.thomas200593.mini_retail_app.app.navigation.DestTopLevel
import com.thomas200593.mini_retail_app.app.navigation.DestWithTopAppBar
import com.thomas200593.mini_retail_app.core.data.local.session.Session
import com.thomas200593.mini_retail_app.core.data.local.session.SessionState
import com.thomas200593.mini_retail_app.core.design_system.network_monitor.NetworkMonitor
import com.thomas200593.mini_retail_app.features.business.navigation.navigateToBusiness
import com.thomas200593.mini_retail_app.features.dashboard.navigation.navigateToDashboard
import com.thomas200593.mini_retail_app.features.reporting.navigation.navigateToReporting
import com.thomas200593.mini_retail_app.features.user_profile.navigation.navigateToUserProfile
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

@Composable
fun rememberStateApplication(
    networkMonitor: NetworkMonitor,
    session: Session,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    navController: NavHostController = rememberNavController()
): StateApp =
    remember(networkMonitor, session, coroutineScope, navController) {
        StateApp(
            networkMonitor = networkMonitor,
            session = session,
            coroutineScope = coroutineScope,
            navController = navController
        )
    }

@Stable
class StateApp(
    networkMonitor: NetworkMonitor,
    session: Session,
    val coroutineScope: CoroutineScope,
    val navController: NavHostController,
) {
    val isNetworkOffline = networkMonitor.isNetworkOnline.map(Boolean::not).stateIn(
        scope = coroutineScope,
        initialValue = false,
        started = SharingStarted.WhileSubscribed(1_000)
    )

    val isSessionValid = session.currentUserSession.stateIn(
        scope = coroutineScope,
        initialValue = SessionState.Loading,
        started = SharingStarted.Eagerly
    )

    val destCurrent: NavDestination?
        @Composable get() = navController.currentBackStackEntryAsState().value?.destination

    val destTopLevels: List<DestTopLevel> = DestTopLevel.entries

    val shouldShowBottomBar: Boolean
        @Composable get() = destCurrent?.route in destTopLevels.map { it.route }

    val shouldShowTopBar: Boolean
        @Composable get() = destCurrent?.route in DestWithTopAppBar.destWithTopAppBar()

    fun navToDestTopLevel(destTopLevel: DestTopLevel){
        val destTopLevelNavOptions = navOptions {
            popUpTo(id = navController.graph.findStartDestination().id){ saveState = true }
            launchSingleTop = true; restoreState = true
        }
        when(destTopLevel){
            DestTopLevel.DASHBOARD ->
                { navController.navigateToDashboard(navOptions = destTopLevelNavOptions) }
            DestTopLevel.BUSINESS ->
                { navController.navigateToBusiness(navOptions = destTopLevelNavOptions) }
            DestTopLevel.REPORTING ->
                { navController.navigateToReporting(navOptions = destTopLevelNavOptions) }
            DestTopLevel.USER_PROFILE ->
                { navController.navigateToUserProfile(navOptions = destTopLevelNavOptions) }
        }
    }

    fun onNavUp() { navController.navigateUp() }
}
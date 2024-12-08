package com.thomas200593.mini_retail_app.app.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.thomas200593.mini_retail_app.app.navigation.DestTopLevel
import com.thomas200593.mini_retail_app.app.navigation.DestTopLevel.BUSINESS
import com.thomas200593.mini_retail_app.app.navigation.DestTopLevel.DASHBOARD
import com.thomas200593.mini_retail_app.app.navigation.DestTopLevel.REPORTING
import com.thomas200593.mini_retail_app.app.navigation.DestTopLevel.USER_PROFILE
import com.thomas200593.mini_retail_app.app.navigation.DestTopLevel.entries
import com.thomas200593.mini_retail_app.app.navigation.ScrGraphs.Companion.getScreenRoutesUsesTopBar
import com.thomas200593.mini_retail_app.core.data.local.session.Session
import com.thomas200593.mini_retail_app.core.data.local.session.SessionState.Loading
import com.thomas200593.mini_retail_app.core.design_system.network_monitor.NetworkMonitor
import com.thomas200593.mini_retail_app.features.business.biz.navigation.navToBiz
import com.thomas200593.mini_retail_app.features.dashboard.navigation.navToDashboard
import com.thomas200593.mini_retail_app.features.reporting.navigation.navToReporting
import com.thomas200593.mini_retail_app.features.user_profile.navigation.navToUserProfile
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted.Companion.Eagerly
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
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
        started = WhileSubscribed(1_000)
    )

    val isSessionValid = session.currentUserSession.stateIn(
        scope = coroutineScope,
        initialValue = Loading,
        started = Eagerly
    )

    val destCurrent: NavDestination?
        @Composable get() =
            rememberUpdatedState(navController.currentBackStackEntryAsState().value?.destination)
                .value

    val destTopLevels: List<DestTopLevel> = entries

    val shouldShowBottomBar: Boolean
        @Composable get() = destCurrent?.route in destTopLevels.map { it.scrGraphs.route }

    val shouldShowTopBar: Boolean
        @Composable get() = destCurrent?.route in getScreenRoutesUsesTopBar()

    fun navToDestTopLevel(destTopLevel: DestTopLevel){
        val navOptions = navOptions {
            popUpTo(id = navController.graph.findStartDestination().id){ saveState = true }
            launchSingleTop = true; restoreState = true
        }
        when(destTopLevel){
            DASHBOARD -> { navController.navToDashboard(navOptions = navOptions) }
            BUSINESS -> { navController.navToBiz(navOptions = navOptions) }
            REPORTING -> { navController.navToReporting(navOptions = navOptions) }
            USER_PROFILE -> { navController.navToUserProfile(navOptions = navOptions) }
        }
    }

    fun onNavUp() { navController.navigateUp() }
}
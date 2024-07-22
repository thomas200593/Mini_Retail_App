package com.thomas200593.mini_retail_app.app.ui

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.thomas200593.mini_retail_app.R
import com.thomas200593.mini_retail_app.app.navigation.NavHost.NavigationHost
import com.thomas200593.mini_retail_app.core.ui.component.AppBar.TopAppBar
import com.thomas200593.mini_retail_app.core.ui.component.BottomBar.BottomBar

@Composable
fun ScrApp(
    stateApp: StateApp = LocalStateApp.current
){
    val snackBarHostState = remember { SnackbarHostState() }
    val isNetworkOffline by stateApp.isNetworkOffline.collectAsStateWithLifecycle()
    val networkNotConnectedMessage = stringResource(id = R.string.str_network_not_connected)

    LaunchedEffect(isNetworkOffline) {
        if(isNetworkOffline){
            snackBarHostState.showSnackbar(
                message = networkNotConnectedMessage,
                duration = SnackbarDuration.Indefinite
            )
        }
    }

    ScrApp(snackBarHostState = snackBarHostState)
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
internal fun ScrApp(
    modifier: Modifier = Modifier,
    snackBarHostState: SnackbarHostState,
    stateApp: StateApp = LocalStateApp.current
){
    Scaffold(
        modifier = modifier.semantics { testTagsAsResourceId = true },
        containerColor = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.onBackground,
        snackbarHost = { SnackbarHost(snackBarHostState) },
        topBar = {
            if(stateApp.shouldShowTopBar)
            { TopAppBar(navController = stateApp.navController) }
        },
        bottomBar = {
            if(stateApp.shouldShowBottomBar){
                BottomBar(
                    destinations = stateApp.destTopLevels,
                    onNavigateToDestination = stateApp::navToDestTopLevel,
                    currentDestination = stateApp.destCurrent,
                    modifier = modifier
                )
            }
        },
        content = { padding ->
            Surface(
                modifier = Modifier.fillMaxSize().padding(padding).consumeWindowInsets(padding)
                    .windowInsetsPadding(WindowInsets.safeDrawing.only(WindowInsetsSides.Horizontal)),
                content = {
                    NavigationHost(
                        onShowSnackBar = { message, action, duration ->
                            snackBarHostState.showSnackbar(
                                message = message,
                                actionLabel = action,
                                duration = duration ?: SnackbarDuration.Short
                            ) == SnackbarResult.ActionPerformed
                        }
                    )
                }
            )
        }
    )
}
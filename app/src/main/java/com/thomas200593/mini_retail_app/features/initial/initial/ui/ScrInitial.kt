package com.thomas200593.mini_retail_app.features.initial.initial.ui

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.navOptions
import com.thomas200593.mini_retail_app.R.string
import com.thomas200593.mini_retail_app.app.navigation.NavGraph.G_INITIAL
import com.thomas200593.mini_retail_app.app.ui.LocalStateApp
import com.thomas200593.mini_retail_app.app.ui.StateApp
import com.thomas200593.mini_retail_app.core.ui.component.CustomPanel.ErrorScreen
import com.thomas200593.mini_retail_app.core.ui.component.CustomPanel.LoadingScreen
import com.thomas200593.mini_retail_app.features.auth.entity.OAuth2UserMetadata.Google
import com.thomas200593.mini_retail_app.features.auth.entity.OAuthProvider.GOOGLE
import com.thomas200593.mini_retail_app.features.auth.entity.UserData
import com.thomas200593.mini_retail_app.features.auth.navigation.navToAuth
import com.thomas200593.mini_retail_app.features.dashboard.navigation.navToDashboard
import com.thomas200593.mini_retail_app.features.initial.initial.entity.FirstTimeStatus.NO
import com.thomas200593.mini_retail_app.features.initial.initial.entity.FirstTimeStatus.YES
import com.thomas200593.mini_retail_app.features.initial.initial.entity.Initial
import com.thomas200593.mini_retail_app.features.initial.initial.ui.VMInitial.UiEvents
import com.thomas200593.mini_retail_app.features.initial.initial.ui.VMInitial.UiState
import com.thomas200593.mini_retail_app.features.initial.initial.ui.VMInitial.UiStateInitial.Error
import com.thomas200593.mini_retail_app.features.initial.initial.ui.VMInitial.UiStateInitial.Loading
import com.thomas200593.mini_retail_app.features.initial.initial.ui.VMInitial.UiStateInitial.Success
import com.thomas200593.mini_retail_app.features.initial.initialization.navigation.navToInitialization
import com.thomas200593.mini_retail_app.features.onboarding.entity.OnboardingStatus.HIDE
import com.thomas200593.mini_retail_app.features.onboarding.entity.OnboardingStatus.SHOW
import com.thomas200593.mini_retail_app.features.onboarding.navigation.navToOnboarding
import kotlinx.coroutines.launch

@Composable
fun ScrInitial(
    vm: VMInitial = hiltViewModel(),
    stateApp: StateApp = LocalStateApp.current
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val uiState by vm.uiState.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) { vm.onEvent(UiEvents.OnOpenEvents) }
    ScrInitial(
        uiState = uiState,
        onNavToOnboarding = { coroutineScope.launch { stateApp.navController.navToOnboarding() } },
        onNavToInitialization = { coroutineScope.launch { stateApp.navController.navToInitialization() } },
        onNavToAuth = { coroutineScope.launch { stateApp.navController.navToAuth() } },
        onNavToDashboard = { welcomeMessage, userData ->
            val navOptions =
                navOptions{ popUpTo(route = G_INITIAL) { inclusive = true; saveState = true }
                restoreState = true; launchSingleTop = true }
            when (userData.authSessionToken?.authProvider) {
                GOOGLE ->
                    Toast.makeText(
                        context,
                        "$welcomeMessage! ${(userData.oAuth2UserMetadata as Google).name}.",
                        Toast.LENGTH_SHORT
                    ).show()
                else -> Unit
            }
            coroutineScope.launch { stateApp.navController.navToDashboard(navOptions) }
        }
    )
}

@Composable
private fun ScrInitial(
    uiState: UiState,
    onNavToOnboarding: () -> Unit,
    onNavToInitialization: () -> Unit,
    onNavToAuth: () -> Unit,
    onNavToDashboard: (String, UserData) -> Unit
) = when (uiState.initial) {
    Loading -> LoadingScreen()
    is Error -> ErrorScreen(
        title = stringResource(id = string.str_error),
        errorMessage = "${uiState.initial.t.message} : ${uiState.initial.t.cause}",
        showIcon = true
    )
    is Success -> ScreenContent(
        initial = uiState.initial.initial,
        onNavToOnboarding = onNavToOnboarding,
        onNavToInitialization = onNavToInitialization,
        onNavToAuth = onNavToAuth,
        onNavToDashboard = onNavToDashboard
    )
}

@Composable
private fun ScreenContent(
    initial: Initial,
    onNavToOnboarding: () -> Unit,
    onNavToInitialization: () -> Unit,
    onNavToAuth: () -> Unit,
    onNavToDashboard: (String, UserData) -> Unit
) = when (initial.firstTimeStatus) {
    YES -> when (initial.configCurrent.onboardingStatus) {
        SHOW -> onNavToOnboarding.invoke()
        HIDE -> onNavToInitialization.invoke()
    }
    NO -> when (initial.configCurrent.onboardingStatus) {
        SHOW -> onNavToOnboarding.invoke()
        HIDE -> when (initial.session == null) {
            true -> onNavToAuth.invoke()
            false -> onNavToDashboard.invoke(stringResource(string.str_welcome), initial.session)
        }
    }
}
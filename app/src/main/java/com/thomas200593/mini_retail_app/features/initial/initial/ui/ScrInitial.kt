package com.thomas200593.mini_retail_app.features.initial.initial.ui

import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavOptions.Builder
import com.thomas200593.mini_retail_app.R.string.str_error
import com.thomas200593.mini_retail_app.R.string.str_error_fetching_preferences
import com.thomas200593.mini_retail_app.app.navigation.NavGraph.G_INITIAL
import com.thomas200593.mini_retail_app.app.ui.LocalStateApp
import com.thomas200593.mini_retail_app.app.ui.StateApp
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState.Empty
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState.Error
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState.Idle
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState.Loading
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState.Success
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
import com.thomas200593.mini_retail_app.features.initial.initial.ui.VMInitial.UiEvents.OnOpen
import com.thomas200593.mini_retail_app.features.initial.initialization.navigation.navToInitialization
import com.thomas200593.mini_retail_app.features.onboarding.entity.OnboardingStatus.HIDE
import com.thomas200593.mini_retail_app.features.onboarding.entity.OnboardingStatus.SHOW
import com.thomas200593.mini_retail_app.features.onboarding.navigation.navToOnboarding
import kotlinx.coroutines.launch

@Composable
fun ScrInitial(
    vm: VMInitial = hiltViewModel(),
    stateApp: StateApp = LocalStateApp.current
){
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val uiState by vm.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) { vm.onEvent(OnOpen) }

    when(uiState.initData){
        Idle, Loading, Empty -> LoadingScreen()
        is Error -> ErrorScreen(
            title = stringResource(id = str_error),
            errorMessage = stringResource(id = str_error_fetching_preferences),
            showIcon = true
        )
        is Success -> ScreenContent(
            data = (uiState.initData as Success).data,
            onNavToOnboarding =
            { coroutineScope.launch { stateApp.navController.navToOnboarding() } },
            onNavToInitialization =
            { coroutineScope.launch { stateApp.navController.navToInitialization() } },
            onNavToAuth =
            { coroutineScope.launch { stateApp.navController.navToAuth() } },
            onNavToDashboard = {
                val navOpt = Builder().setPopUpTo(route = G_INITIAL, inclusive = true, saveState = true)
                    .setLaunchSingleTop(true).setRestoreState(true).build()
                when(it.authSessionToken?.authProvider){
                    GOOGLE -> Toast.makeText(context, "Welcome! ${(it.oAuth2UserMetadata as Google).name}", LENGTH_SHORT).show()
                    else -> Unit
                }
                coroutineScope.launch { stateApp.navController.navToDashboard(navOpt) }
            }
        )
    }
}

@Composable
private fun ScreenContent(
    data: Initial,
    onNavToOnboarding: () -> Unit,
    onNavToInitialization: () -> Unit,
    onNavToAuth: () -> Unit,
    onNavToDashboard: (UserData) -> Unit
) {
    when(data.firstTimeStatus){
        YES -> when(data.configCurrent.onboardingStatus){
            SHOW -> onNavToOnboarding.invoke()
            HIDE -> onNavToInitialization.invoke()
        }
        NO -> when(data.configCurrent.onboardingStatus){
            SHOW -> onNavToOnboarding.invoke()
            HIDE -> when(data.session == null){
                true -> onNavToAuth.invoke()
                false -> onNavToDashboard.invoke(data.session)
            }
        }
    }
}
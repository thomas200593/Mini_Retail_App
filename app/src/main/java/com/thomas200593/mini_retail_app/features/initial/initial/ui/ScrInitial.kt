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
import com.thomas200593.mini_retail_app.R
import com.thomas200593.mini_retail_app.app.navigation.NavGraph.G_INITIAL
import com.thomas200593.mini_retail_app.app.ui.LocalStateApp
import com.thomas200593.mini_retail_app.app.ui.StateApp
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState
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
import com.thomas200593.mini_retail_app.features.dashboard.navigation.navigateToDashboard
import com.thomas200593.mini_retail_app.features.initial.initial.entity.FirstTimeStatus.NO
import com.thomas200593.mini_retail_app.features.initial.initial.entity.FirstTimeStatus.YES
import com.thomas200593.mini_retail_app.features.initial.initial.entity.Initial
import com.thomas200593.mini_retail_app.features.initial.initialization.navigation.navToInitialization
import com.thomas200593.mini_retail_app.features.onboarding.entity.OnboardingStatus.HIDE
import com.thomas200593.mini_retail_app.features.onboarding.entity.OnboardingStatus.SHOW
import com.thomas200593.mini_retail_app.features.onboarding.navigation.navigateToOnboarding
import kotlinx.coroutines.launch

@Composable
fun ScrInitial(
    vm: VMInitial = hiltViewModel(),
    stateApp: StateApp = LocalStateApp.current
) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val uiState by vm.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) { vm.onOpen() }

    ScreenContent(
        uiState = uiState,
        onNavigateToOnboarding = {
            coroutineScope.launch { stateApp.navController.navigateToOnboarding() }
        },
        onNavigateToInitialization = {
            coroutineScope.launch { stateApp.navController.navToInitialization() }
        },
        onNavigateToDashboard = { userData ->
            val navOptions = Builder().setPopUpTo(route = G_INITIAL, inclusive = true, saveState = true)
                .setLaunchSingleTop(true).setRestoreState(true).build()
            when(userData.authSessionToken?.authProvider){
                GOOGLE -> {
                    Toast.makeText(
                        context,
                        "Welcome back! ${(userData.oAuth2UserMetadata as Google).name}",
                        LENGTH_SHORT
                    ).show()
                }
                null -> Unit
            }
            coroutineScope.launch {
                stateApp.navController.navigateToDashboard(navOptions)
            }
        },
        onNavigateToAuth = { coroutineScope.launch { stateApp.navController.navToAuth() } }
    )
}

@Composable
private fun ScreenContent(
    uiState: ResourceState<Initial>,
    onNavigateToOnboarding: () -> Unit,
    onNavigateToInitialization: () -> Unit,
    onNavigateToDashboard: (UserData) -> Unit,
    onNavigateToAuth: () -> Unit
) {
    when(uiState){
        Idle, Loading, Empty -> { LoadingScreen() }
        is Error -> {
            ErrorScreen(
                title = stringResource(id = R.string.str_error),
                errorMessage = stringResource(id = R.string.str_error_fetching_preferences),
                showIcon = true
            )
        }
        is Success -> {
            when(uiState.data.isFirstTime){
                YES -> {
                    when(uiState.data.configCurrent.onboardingStatus){
                        SHOW -> { onNavigateToOnboarding.invoke() }
                        HIDE -> { onNavigateToInitialization.invoke() }
                    }
                }
                NO -> {
                    when(uiState.data.configCurrent.onboardingStatus){
                        SHOW -> { onNavigateToOnboarding.invoke() }
                        HIDE -> {
                            when(uiState.data.session == null){
                                true -> { onNavigateToAuth.invoke() }
                                false -> { onNavigateToDashboard.invoke(uiState.data.session) }
                            }
                        }
                    }
                }
            }
        }
    }
}
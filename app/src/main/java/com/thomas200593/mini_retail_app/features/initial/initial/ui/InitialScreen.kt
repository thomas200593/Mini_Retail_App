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
import androidx.navigation.NavOptions
import com.thomas200593.mini_retail_app.R
import com.thomas200593.mini_retail_app.app.navigation.NavGraph
import com.thomas200593.mini_retail_app.app.ui.StateApp
import com.thomas200593.mini_retail_app.app.ui.LocalStateApp
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState
import com.thomas200593.mini_retail_app.core.ui.component.CustomPanel.ErrorScreen
import com.thomas200593.mini_retail_app.core.ui.component.CustomPanel.LoadingScreen
import com.thomas200593.mini_retail_app.features.onboarding.entity.OnboardingStatus
import com.thomas200593.mini_retail_app.features.auth.entity.OAuth2UserMetadata
import com.thomas200593.mini_retail_app.features.auth.entity.OAuthProvider
import com.thomas200593.mini_retail_app.features.auth.entity.UserData
import com.thomas200593.mini_retail_app.features.auth.navigation.navigateToAuth
import com.thomas200593.mini_retail_app.features.dashboard.navigation.navigateToDashboard
import com.thomas200593.mini_retail_app.features.initial.initial.entity.FirstTimeStatus
import com.thomas200593.mini_retail_app.features.initial.initial.entity.Initial
import com.thomas200593.mini_retail_app.features.initial.initial.navigation.navigateToInitialization
import com.thomas200593.mini_retail_app.features.onboarding.navigation.navigateToOnboarding
import kotlinx.coroutines.launch

@Composable
fun InitialScreen(
    viewModel: InitialViewModel = hiltViewModel(),
    stateApp: StateApp = LocalStateApp.current
) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) { viewModel.onOpen() }

    ScreenContent(
        uiState = uiState,
        onNavigateToOnboarding = { coroutineScope.launch { stateApp.navController.navigateToOnboarding() } },
        onNavigateToInitialization = { coroutineScope.launch { stateApp.navController.navigateToInitialization() } },
        onNavigateToDashboard = { userData ->
            val navOptions = NavOptions.Builder().setPopUpTo(route = NavGraph.G_INITIAL, inclusive = true, saveState = true).setLaunchSingleTop(true).setRestoreState(true).build()
            when(userData.authSessionToken?.authProvider){
                OAuthProvider.GOOGLE -> { Toast.makeText(context, "Welcome back! ${(userData.oAuth2UserMetadata as OAuth2UserMetadata.Google).name}", Toast.LENGTH_SHORT).show() }
                null -> Unit
            }
            coroutineScope.launch { stateApp.navController.navigateToDashboard(navOptions) }
        },
        onNavigateToAuth = { coroutineScope.launch { stateApp.navController.navigateToAuth() } }
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
        ResourceState.Idle, ResourceState.Loading, ResourceState.Empty -> { LoadingScreen() }
        is ResourceState.Error -> {
            ErrorScreen(
                title = stringResource(id = R.string.str_error),
                errorMessage = stringResource(id = R.string.str_error_fetching_preferences),
                showIcon = true
            )
        }
        is ResourceState.Success -> {
            when(uiState.data.isFirstTime){
                FirstTimeStatus.YES -> {
                    when(uiState.data.configCurrent.onboardingStatus){
                        OnboardingStatus.SHOW -> { onNavigateToOnboarding.invoke() }
                        OnboardingStatus.HIDE -> { onNavigateToInitialization.invoke() }
                    }
                }
                FirstTimeStatus.NO -> {
                    when(uiState.data.configCurrent.onboardingStatus){
                        OnboardingStatus.SHOW -> { onNavigateToOnboarding.invoke() }
                        OnboardingStatus.HIDE -> {
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
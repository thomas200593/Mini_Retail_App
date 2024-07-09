package com.thomas200593.mini_retail_app.features.initial.ui

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavOptions
import com.thomas200593.mini_retail_app.app.navigation.NavigationGraphs
import com.thomas200593.mini_retail_app.app.ui.AppState
import com.thomas200593.mini_retail_app.app.ui.LocalAppState
import com.thomas200593.mini_retail_app.core.design_system.util.RequestState
import com.thomas200593.mini_retail_app.core.ui.component.CommonMessagePanel.ErrorScreen
import com.thomas200593.mini_retail_app.core.ui.component.CommonMessagePanel.LoadingScreen
import com.thomas200593.mini_retail_app.features.app_config.entity.OnboardingStatus
import com.thomas200593.mini_retail_app.features.auth.entity.OAuth2UserMetadata
import com.thomas200593.mini_retail_app.features.auth.entity.OAuthProvider
import com.thomas200593.mini_retail_app.features.auth.entity.UserData
import com.thomas200593.mini_retail_app.features.auth.navigation.navigateToAuth
import com.thomas200593.mini_retail_app.features.dashboard.navigation.navigateToDashboard
import com.thomas200593.mini_retail_app.features.initial.entity.FirstTimeStatus
import com.thomas200593.mini_retail_app.features.initial.entity.Initial
import com.thomas200593.mini_retail_app.features.initial.navigation.navigateToInitialization
import com.thomas200593.mini_retail_app.features.onboarding.navigation.navigateToOnboarding
import kotlinx.coroutines.launch

@Composable
fun InitialScreen(
    viewModel: InitialViewModel = hiltViewModel(),
    appState: AppState = LocalAppState.current
) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val initialData by viewModel.initialData.collectAsStateWithLifecycle()
    val showLoadingScreen by viewModel.showLoadingScreen
    val showErrorScreen by viewModel.showErrorScreen
    LaunchedEffect(Unit) { viewModel.onOpen() }
    if(showLoadingScreen){ LoadingScreen() }
    if(showErrorScreen.first){
        ErrorScreen(
            showIcon = true,
            title = showErrorScreen.second?.message,
            errorMessage = showErrorScreen.second?.cause?.toString()
        )
    }
    ScreenContent(
        initialData = initialData,
        onLoadingScreen = { coroutineScope.launch { viewModel.setLoadingScreen(true) } },
        onErrorScreen = { coroutineScope.launch { viewModel.setErrorScreen(true, it) } },
        onNavigateToOnboarding = { coroutineScope.launch { appState.navController.navigateToOnboarding() } },
        onNavigateToInitialization = { coroutineScope.launch { appState.navController.navigateToInitialization() } },
        onNavigateToDashboard = { userData ->
            val navOptions = NavOptions.Builder().setPopUpTo(route = NavigationGraphs.G_INITIAL, inclusive = true, saveState = true).setLaunchSingleTop(true).setRestoreState(true).build()
            when(userData.authSessionToken?.authProvider){
                OAuthProvider.GOOGLE -> { Toast.makeText(context, "Welcome back! ${(userData.oAuth2UserMetadata as OAuth2UserMetadata.Google).name}", Toast.LENGTH_SHORT).show() }
                null -> Unit
            }
            coroutineScope.launch { appState.navController.navigateToDashboard(navOptions) }
        },
        onNavigateToAuth = { coroutineScope.launch { appState.navController.navigateToAuth() } }
    )
}

@Composable
private fun ScreenContent(
    initialData: RequestState<Initial>,
    onLoadingScreen: () -> Unit,
    onErrorScreen: (Throwable?) -> Unit,
    onNavigateToOnboarding: () -> Unit,
    onNavigateToInitialization: () -> Unit,
    onNavigateToDashboard: (UserData) -> Unit,
    onNavigateToAuth: () -> Unit
) {
    when(initialData){
        RequestState.Idle, RequestState.Loading, RequestState.Empty -> onLoadingScreen()
        is RequestState.Error -> { onErrorScreen(initialData.t) }
        is RequestState.Success -> {
            when(initialData.data.isFirstTime){
                FirstTimeStatus.YES -> {
                    when(initialData.data.configCurrent.onboardingStatus){
                        OnboardingStatus.SHOW -> { onNavigateToOnboarding.invoke() }
                        OnboardingStatus.HIDE -> { onNavigateToInitialization.invoke() }
                    }
                }
                FirstTimeStatus.NO -> {
                    when(initialData.data.configCurrent.onboardingStatus){
                        OnboardingStatus.SHOW -> { onNavigateToOnboarding.invoke() }
                        OnboardingStatus.HIDE -> {
                            when(initialData.data.session == null){
                                true -> { onNavigateToAuth.invoke() }
                                false -> { onNavigateToDashboard.invoke(initialData.data.session) }
                            }
                        }
                    }
                }
            }
        }
    }
}
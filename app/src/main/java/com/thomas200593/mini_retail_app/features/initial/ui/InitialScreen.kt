package com.thomas200593.mini_retail_app.features.initial.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavOptions
import com.thomas200593.mini_retail_app.app.navigation.NavigationGraphs.G_INITIAL
import com.thomas200593.mini_retail_app.app.ui.AppState
import com.thomas200593.mini_retail_app.app.ui.LocalAppState
import com.thomas200593.mini_retail_app.core.design_system.util.RequestState
import com.thomas200593.mini_retail_app.core.ui.component.CommonMessagePanel.ErrorScreen
import com.thomas200593.mini_retail_app.core.ui.component.CommonMessagePanel.LoadingScreen
import com.thomas200593.mini_retail_app.features.app_config.entity.OnboardingStatus
import com.thomas200593.mini_retail_app.features.auth.navigation.navigateToAuth
import com.thomas200593.mini_retail_app.features.dashboard.navigation.navigateToDashboard
import com.thomas200593.mini_retail_app.features.initial.entity.Initial
import com.thomas200593.mini_retail_app.features.onboarding.navigation.navigateToOnboarding
import timber.log.Timber

private const val TAG = "InitialScreen"

@Composable
fun InitialScreen(
    viewModel: InitialViewModel = hiltViewModel(),
    appState: AppState = LocalAppState.current
) {
    Timber.d("Called : fun $TAG()")

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var showLoadingPage by remember { mutableStateOf(false) }
    var showErrorPage by remember { mutableStateOf(false) }
    var throwable: Throwable? by remember { mutableStateOf(null) }

    ScreenContent(
        appState = appState,
        uiState = uiState,
        onLoading = {
            showLoadingPage = true
        },
        onError = { error ->
            showErrorPage = true
            throwable = error
        }
    )

    if(showErrorPage){
        ErrorScreen(
            showIcon = true,
            title = throwable?.message,
            errorMessage = throwable?.cause.toString()
        )
    }

    if(showLoadingPage){
        LoadingScreen()
    }
}

@Composable
private fun ScreenContent(
    appState: AppState,
    uiState: RequestState<Initial>,
    onLoading: () -> Unit,
    onError: (Throwable) -> Unit
) {
    when(uiState){
        RequestState.Idle, RequestState.Loading, RequestState.Empty -> onLoading()
        is RequestState.Error -> onError(uiState.t)
        is RequestState.Success -> {
            val data = uiState.data
            val isSessionValid = data.isSessionValid
            val shouldShowOnboarding = data.onboardingPageStatus
            when(isSessionValid){
                true -> {
                    when(shouldShowOnboarding){
                        OnboardingStatus.SHOW -> {
                            LaunchedEffect(key1 = uiState) {
                                appState.navController.navigateToOnboarding()
                            }
                        }
                        OnboardingStatus.HIDE -> {
                            LaunchedEffect(key1 = uiState) {
                                appState.navController.navigateToDashboard(
                                    navOptions = NavOptions.Builder()
                                        .setPopUpTo(route = G_INITIAL, inclusive = true, saveState = true)
                                        .setLaunchSingleTop(true)
                                        .setRestoreState(true)
                                        .build()
                                )
                            }
                        }
                    }
                }
                false -> {
                    when(shouldShowOnboarding){
                        OnboardingStatus.SHOW -> {
                            LaunchedEffect(key1 = uiState) {
                                appState.navController.navigateToOnboarding()
                            }
                        }
                        OnboardingStatus.HIDE -> {
                            LaunchedEffect(key1 = uiState) {
                                appState.navController.navigateToAuth()
                            }
                        }
                    }
                }
            }
        }
    }
}
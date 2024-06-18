package com.thomas200593.mini_retail_app.features.initial.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavOptions
import com.thomas200593.mini_retail_app.app.navigation.NavigationGraphs.G_INITIAL
import com.thomas200593.mini_retail_app.app.ui.LocalAppState
import com.thomas200593.mini_retail_app.core.ui.component.CommonMessagePanel.LoadingScreen
import com.thomas200593.mini_retail_app.features.app_config.entity.OnboardingStatus
import com.thomas200593.mini_retail_app.features.auth.navigation.navigateToAuth
import com.thomas200593.mini_retail_app.features.dashboard.navigation.navigateToDashboard
import com.thomas200593.mini_retail_app.features.initial.ui.InitialUiState.Success
import com.thomas200593.mini_retail_app.features.onboarding.navigation.navigateToOnboarding
import timber.log.Timber

private const val TAG = "InitialScreen"

@Composable
fun InitialScreen(
    viewModel: InitialViewModel = hiltViewModel()
) {
    Timber.d("Called: %s", TAG)

    val appState = LocalAppState.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    when(uiState){
        InitialUiState.Loading -> { ScreenContent() }
        is Success -> {
            val isSessionValid = (uiState as Success).isSessionValid
            val shouldShowOnboarding = (uiState as Success).onboardingPagesStatus
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

@Composable
private fun ScreenContent(
    modifier: Modifier = Modifier
) {
    LoadingScreen(modifier = modifier)
}
package com.thomas200593.mini_retail_app.features.initial.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.thomas200593.mini_retail_app.features.app_config.entity.Onboarding

@Composable
fun InitialScreen(
    viewModel: InitialViewModel = hiltViewModel(),
    onNavigateToOnboardingScreen: () -> Unit = {},
    onNavigateToSignInScreen: () -> Unit = {},
    onNavigateToDashboardScreen: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    when(uiState){
        InitialUiState.Loading -> {
            Text(text = "Loading")
        }
        is InitialUiState.Success -> {
            val isSessionValid = (uiState as InitialUiState.Success).isSessionValid
            val shouldShowOnboarding = (uiState as InitialUiState.Success).shouldShowOnboarding
            when(isSessionValid){
                true -> {
                    when(shouldShowOnboarding){
                        Onboarding.SHOW -> {
                            Text(text = "Valid Session, Go to Onboarding")
                        }
                        Onboarding.HIDE -> {
                            Text(text = "Valid Session, Go to Dashboard")
                        }
                    }
                }
                false -> {
                    when(shouldShowOnboarding){
                        Onboarding.SHOW -> {
                            Text(text = "Invalid Session, Go to Onboarding")
                        }
                        Onboarding.HIDE -> {
                            Text(text = "Invalid Session, Go to Login")
                        }
                    }
                }
            }
        }
    }
}
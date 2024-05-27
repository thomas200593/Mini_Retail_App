package com.thomas200593.mini_retail_app.features.initial.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.thomas200593.mini_retail_app.core.ui.component.DotsLoadingAnimation
import com.thomas200593.mini_retail_app.features.app_config.entity.Onboarding

@Composable
fun InitialScreen(
    viewModel: InitialViewModel = hiltViewModel(),
    onNavigateToOnboardingScreen: () -> Unit = {},
    onNavigateToAuthScreen: () -> Unit = {},
    onNavigateToDashboardScreen: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    when(uiState){
        InitialUiState.Loading -> {
            InitialLoadingScreenContent()
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
                            LaunchedEffect(key1 = uiState) {
                                //TODO single launch
                                onNavigateToOnboardingScreen()
                            }
                        }
                        Onboarding.HIDE -> {
                            LaunchedEffect(key1 = uiState) {
                                //TODO single launch
                                onNavigateToAuthScreen()
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun InitialLoadingScreenContent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        content = {
            DotsLoadingAnimation()
        }
    )
}
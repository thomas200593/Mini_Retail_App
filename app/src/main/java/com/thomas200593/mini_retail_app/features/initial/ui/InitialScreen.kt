package com.thomas200593.mini_retail_app.features.initial.ui

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun InitialScreen(
    viewModel: InitialViewModel = hiltViewModel(),
    onNavigateToOnboardingScreen: () -> Unit = {},
    onNavigateToSignInScreen: () -> Unit = {},
    onNavigateToDashboardScreen: () -> Unit = {}
) {

}
package com.thomas200593.mini_retail_app.features.app_config.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.thomas200593.mini_retail_app.core.design_system.util.RequestState

@Composable
fun AppConfigGeneralScreen(
    viewModel: AppConfigGeneralViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit,
    onSelectedDestinationMenu: () -> Unit
) {
    LaunchedEffect(Unit) {
        viewModel.onOpen()
    }
    val appConfigGeneralMenuUiState by viewModel.appConfigGeneralMenuUiState
}
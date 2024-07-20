package com.thomas200593.mini_retail_app.features.business.ui.master_data.supplier.add_update

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.thomas200593.mini_retail_app.app.ui.AppState
import com.thomas200593.mini_retail_app.app.ui.LocalAppState

@Composable
fun SupplierAddUpdateScreen(
    viewModel: SupplierAddUpdateViewModel = hiltViewModel(),
    appState: AppState = LocalAppState.current
) {
    val sessionState by appState.isSessionValid.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) { viewModel.onEvent(SupplierAddUpdateUiEvent.OnOpen(sessionState)) }
}
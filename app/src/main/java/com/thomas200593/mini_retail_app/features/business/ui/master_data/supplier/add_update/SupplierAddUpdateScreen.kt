package com.thomas200593.mini_retail_app.features.business.ui.master_data.supplier.add_update

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.thomas200593.mini_retail_app.app.ui.AppState
import com.thomas200593.mini_retail_app.app.ui.LocalAppState
import com.thomas200593.mini_retail_app.core.data.local.session.SessionState

@Composable
fun SupplierAddUpdateScreen(
    viewModel: SupplierAddUpdateViewModel = hiltViewModel(),
    appState: AppState = LocalAppState.current
) {
    val sessionState by appState.isSessionValid.collectAsStateWithLifecycle()
    when(sessionState){
        SessionState.Loading -> {
            viewModel.onEvent(SupplierAddUpdateUiEvent.OnSessionCheckLoading)
        }
        is SessionState.Invalid -> {
            viewModel.onEvent(SupplierAddUpdateUiEvent.OnSessionInvalid(sessionState as SessionState.Invalid))
        }
        is SessionState.Valid -> {
            viewModel.onEvent(SupplierAddUpdateUiEvent.OnSessionValid(sessionState as SessionState.Valid))
        }
    }

    TopAppBar()
    ScreenContent()
}

@Composable
private fun TopAppBar() {

}

@Composable
private fun ScreenContent() {

}
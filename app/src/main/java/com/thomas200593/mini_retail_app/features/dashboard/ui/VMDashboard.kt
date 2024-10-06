package com.thomas200593.mini_retail_app.features.dashboard.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thomas200593.mini_retail_app.core.data.local.session.SessionState
import com.thomas200593.mini_retail_app.features.dashboard.ui.VMDashboard.UiEvents.ButtonEvents.BtnScrDescEvents
import com.thomas200593.mini_retail_app.features.dashboard.ui.VMDashboard.UiEvents.OnOpenEvents
import com.thomas200593.mini_retail_app.features.dashboard.ui.VMDashboard.UiStateDashboard.Idle
import com.thomas200593.mini_retail_app.features.dashboard.ui.VMDashboard.UiStateDashboard.Loading
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VMDashboard @Inject constructor() : ViewModel() {
    sealed interface UiStateDashboard {
        data object Idle : UiStateDashboard
        data object Loading : UiStateDashboard
        data object Success: UiStateDashboard
    }
    data class UiState(
        val uiStateDashboard: UiStateDashboard = Idle,
        val dialogState: DialogState = DialogState()
    )
    data class DialogState(
        val dlgDenySession: MutableState<Boolean> = mutableStateOf(false),
        val dlgScrDesc: MutableState<Boolean> = mutableStateOf(false)
    )
    sealed interface UiEvents {
        data class OnOpenEvents(val sessionState: SessionState) : UiEvents
        sealed interface ButtonEvents : UiEvents {
            sealed interface BtnScrDescEvents : ButtonEvents {
                data object OnClick : BtnScrDescEvents
                data object OnDismiss : BtnScrDescEvents
            }
        }
    }

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    fun onEvent(events: UiEvents) {
        when(events) {
            is OnOpenEvents -> onOpenEvent(events.sessionState)
            is BtnScrDescEvents.OnClick ->
                { resetDialogState(); updateDialogState(dlgScrDesc = true) }
            is BtnScrDescEvents.OnDismiss -> resetDialogState()
        }
    }

    private fun updateDialogState(
        dlgDenySession: Boolean = false,
        dlgScrDesc: Boolean = false
    ) = _uiState.update { it.copy(
        dialogState = it.dialogState.copy(
            dlgDenySession = mutableStateOf(dlgDenySession),
            dlgScrDesc = mutableStateOf(dlgScrDesc)
        )
    ) }
    private fun resetDialogState() = _uiState.update { it.copy(dialogState = DialogState()) }
    private fun resetUiStateDashboard() = _uiState.update { it.copy(uiStateDashboard = Idle) }
    private fun resetDialogAndUiState() { resetDialogState(); resetUiStateDashboard() }
    private fun onOpenEvent(sessionState: SessionState) {
        resetUiStateDashboard(); resetDialogState()
        when(sessionState) {
            SessionState.Loading -> viewModelScope.launch {
                resetDialogState()
                _uiState.update { it.copy(uiStateDashboard = Loading) }
            }
            is SessionState.Invalid -> onDenyAccess()
            is SessionState.Valid -> viewModelScope.launch {
                resetDialogState()
                _uiState.update { it.copy(uiStateDashboard = Loading) }
                /*TODO*/
            }
        }
    }
    private fun onDenyAccess() {
        resetDialogState(); resetUiStateDashboard()
        _uiState.update {
            it.copy(
                dialogState = it.dialogState.copy(
                    dlgDenySession = mutableStateOf(true)
                )
            )
        }
    }
}

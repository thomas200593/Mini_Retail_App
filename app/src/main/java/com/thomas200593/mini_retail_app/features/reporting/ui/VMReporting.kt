package com.thomas200593.mini_retail_app.features.reporting.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thomas200593.mini_retail_app.core.data.local.session.SessionState
import com.thomas200593.mini_retail_app.features.reporting.ui.VMReporting.UiEvents.ButtonEvents.BtnScrDescEvents
import com.thomas200593.mini_retail_app.features.reporting.ui.VMReporting.UiEvents.OnOpenEvents
import com.thomas200593.mini_retail_app.features.reporting.ui.VMReporting.UiStateReporting.Idle
import com.thomas200593.mini_retail_app.features.reporting.ui.VMReporting.UiStateReporting.Loading
import com.thomas200593.mini_retail_app.features.reporting.ui.VMReporting.UiStateReporting.Success
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VMReporting @Inject constructor(): ViewModel() {
    sealed interface UiStateReporting {
        data object Idle : UiStateReporting
        data object Loading : UiStateReporting
        data class Success(val data: Boolean) : UiStateReporting
    }
    data class UiState(
        val uiStateReporting: UiStateReporting = Idle,
        val dialogState: DialogState = DialogState()
    )
    data class DialogState(
        val dlgSessionInvalid: MutableState<Boolean> = mutableStateOf(false),
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
        dlgSessionInvalid: Boolean = false,
        dlgScrDesc: Boolean = false
    ) = _uiState.update { it.copy(
        dialogState = it.dialogState.copy(
            dlgSessionInvalid = mutableStateOf(dlgSessionInvalid),
            dlgScrDesc = mutableStateOf(dlgScrDesc)
        )
    ) }
    private fun resetDialogState() = _uiState.update { it.copy(dialogState = DialogState()) }
    private fun resetUiStateReporting() = _uiState.update { it.copy(uiStateReporting = Idle) }
    private fun resetDialogAndUiState() { resetDialogState(); resetUiStateReporting() }
    private fun onOpenEvent(sessionState: SessionState) {
        resetDialogAndUiState()
        when(sessionState) {
            SessionState.Loading -> _uiState.update { it.copy(uiStateReporting = Loading) }
            is SessionState.Invalid -> onDenyAccess()
            is SessionState.Valid -> viewModelScope.launch {
                _uiState.update { it.copy(uiStateReporting = Loading) }
                _uiState.update { it.copy(uiStateReporting = Success(true)) }
            }
        }
    }
    private fun onDenyAccess() {
        resetDialogAndUiState()
        updateDialogState(dlgSessionInvalid = true)
    }
}
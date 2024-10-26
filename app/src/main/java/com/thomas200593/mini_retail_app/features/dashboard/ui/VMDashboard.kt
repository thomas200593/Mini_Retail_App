package com.thomas200593.mini_retail_app.features.dashboard.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thomas200593.mini_retail_app.core.data.local.session.SessionState
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatchers.Dispatchers.IO
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.di.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.util.HlpCountry
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_country.entity.Country
import com.thomas200593.mini_retail_app.features.dashboard.ui.VMDashboard.UiEvents.ButtonEvents.BtnScrDescEvents
import com.thomas200593.mini_retail_app.features.dashboard.ui.VMDashboard.UiEvents.OnOpenEvents
import com.thomas200593.mini_retail_app.features.dashboard.ui.VMDashboard.UiStateDashboard.Idle
import com.thomas200593.mini_retail_app.features.dashboard.ui.VMDashboard.UiStateDashboard.Loading
import com.thomas200593.mini_retail_app.features.dashboard.ui.VMDashboard.UiStateDashboard.Success
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VMDashboard @Inject constructor(
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {
    sealed interface UiStateDashboard {
        data object Idle : UiStateDashboard
        data object Loading : UiStateDashboard
        data class Success(val data: List<Country>): UiStateDashboard
    }
    data class UiState(
        val uiStateDashboard: UiStateDashboard = Idle,
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
    private fun resetUiStateDashboard() = _uiState.update { it.copy(uiStateDashboard = Idle) }
    private fun resetDialogAndUiState() { resetDialogState(); resetUiStateDashboard() }
    private fun onOpenEvent(sessionState: SessionState) {
        resetDialogAndUiState()
        when(sessionState) {
            SessionState.Loading -> _uiState.update { it.copy(uiStateDashboard = Loading) }
            is SessionState.Invalid -> onDenyAccess()
            is SessionState.Valid -> viewModelScope.launch {
                _uiState.update { it.copy(uiStateDashboard = Loading) }
                _uiState.update { it.copy(uiStateDashboard = Success(HlpCountry.getCountryList(ioDispatcher))) }
            }
        }
    }
    private fun onDenyAccess() {
        resetDialogAndUiState()
        updateDialogState(dlgSessionInvalid = true)
    }
}
package com.thomas200593.mini_retail_app.features.app_conf.conf_gen_timezone.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thomas200593.mini_retail_app.app.navigation.ScrGraphs
import com.thomas200593.mini_retail_app.core.data.local.session.SessionState
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatchers.Dispatchers.IO
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.di.Dispatcher
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_timezone.domain.UCGetConfTimezone
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_timezone.entity.ConfigTimezones
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_timezone.entity.Timezone
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_timezone.repository.RepoConfGenTimezone
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_timezone.ui.VMConfGenTimezone.UiEvents.ButtonEvents.BtnNavBackEvents
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_timezone.ui.VMConfGenTimezone.UiEvents.ButtonEvents.BtnScrDescEvents
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_timezone.ui.VMConfGenTimezone.UiEvents.ButtonEvents.BtnSetPrefTimezoneEvents
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_timezone.ui.VMConfGenTimezone.UiEvents.DialogEvents.DlgDenySetDataEvents
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_timezone.ui.VMConfGenTimezone.UiEvents.OnOpenEvents
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_timezone.ui.VMConfGenTimezone.UiStateConfigTimezone.Loading
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_timezone.ui.VMConfGenTimezone.UiStateConfigTimezone.Success
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VMConfGenTimezone @Inject constructor(
    private val repoConfGenTimezone: RepoConfGenTimezone,
    private val ucGetConfGenTimezone: UCGetConfTimezone,
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {
    sealed interface UiStateConfigTimezone {
        data object Loading: UiStateConfigTimezone
        data class Success(val configTimezones: ConfigTimezones) : UiStateConfigTimezone
    }
    data class UiState(
        val configTimezones: UiStateConfigTimezone = Loading,
        val dialogState: DialogState = DialogState()
    )
    data class DialogState(
        val dlgLoadingAuth: MutableState<Boolean> = mutableStateOf(false),
        val dlgLoadingGetData: MutableState<Boolean> = mutableStateOf(false),
        val dlgDenySetData: MutableState<Boolean> = mutableStateOf(false),
        val dlgScrDesc: MutableState<Boolean> = mutableStateOf(false)
    )
    sealed interface UiEvents {
        data class OnOpenEvents(
            val sessionState: SessionState,
            val currentScreen: ScrGraphs
        ) : UiEvents
        sealed interface ButtonEvents : UiEvents {
            sealed interface BtnNavBackEvents : ButtonEvents {
                data object OnClick : BtnNavBackEvents
            }
            sealed interface BtnScrDescEvents : ButtonEvents {
                data object OnClick : BtnScrDescEvents
                data object OnDismiss : BtnScrDescEvents
            }
            sealed interface BtnSetPrefTimezoneEvents : ButtonEvents {
                data class OnAllow(val timezone: Timezone) : BtnSetPrefTimezoneEvents
                data object OnDeny : BtnSetPrefTimezoneEvents
            }
        }
        sealed interface DialogEvents : UiEvents {
            sealed interface DlgDenySetDataEvents : DialogEvents {
                data object OnDismiss : DlgDenySetDataEvents
            }
        }
    }

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    fun onEvent(events: UiEvents) {
        when(events) {
            is OnOpenEvents -> onOpenEvent(events.sessionState, events.currentScreen)
            is BtnNavBackEvents.OnClick -> resetDialogAndUiState()
            is BtnScrDescEvents.OnClick ->
                { resetDialogState(); updateDialogState(dlgScrDesc = true) }
            is BtnScrDescEvents.OnDismiss -> resetDialogState()
            is BtnSetPrefTimezoneEvents.OnDeny -> onDenySet()
            is BtnSetPrefTimezoneEvents.OnAllow -> onAllowSet(events.timezone)
            is DlgDenySetDataEvents.OnDismiss -> resetDialogAndUiState()
        }
    }

    private fun updateDialogState(
        dlgLoadingAuth: Boolean = false,
        dlgLoadingGetData: Boolean = false,
        dlgDenySetData: Boolean = false,
        dlgScrDesc: Boolean = false
    ) = _uiState.update { it.copy(
        dialogState = it.dialogState.copy(
            dlgLoadingAuth = mutableStateOf(dlgLoadingAuth),
            dlgLoadingGetData = mutableStateOf(dlgLoadingGetData),
            dlgDenySetData = mutableStateOf(dlgDenySetData),
            dlgScrDesc = mutableStateOf(dlgScrDesc)
        )
    ) }
    private fun resetDialogState() = _uiState.update { it.copy(dialogState = DialogState()) }
    private fun resetUiStateConfigTimezone() = _uiState.update { it.copy(configTimezones = Loading) }
    private fun resetDialogAndUiState() { resetDialogState(); resetUiStateConfigTimezone() }
    private fun onOpenEvent(sessionState: SessionState, currentScreen: ScrGraphs) {
        resetUiStateConfigTimezone(); resetDialogState()
        when(sessionState) {
            SessionState.Loading -> updateDialogState(dlgLoadingAuth = true)
            is SessionState.Invalid -> if(currentScreen.usesAuth) onDenySet()
            else viewModelScope.launch {
                resetUiStateConfigTimezone(); updateDialogState(dlgLoadingGetData = true)
                ucGetConfGenTimezone.invoke().flowOn(ioDispatcher).collect{ data ->
                    _uiState.update {
                        it.copy(
                            configTimezones = Success(data),
                            dialogState = DialogState()
                        )
                    }
                }
            }
            is SessionState.Valid -> viewModelScope.launch {
                resetUiStateConfigTimezone(); updateDialogState(dlgLoadingGetData = true)
                ucGetConfGenTimezone.invoke().flowOn(ioDispatcher).collect{ data->
                    _uiState.update {
                        it.copy(
                            configTimezones = Success(data),
                            dialogState = DialogState()
                        )
                    }
                }
            }
        }
    }
    private fun onDenySet() { resetDialogState(); updateDialogState(dlgDenySetData = true) }
    private fun onAllowSet(timezone: Timezone) {
        resetDialogState()
        viewModelScope.launch { repoConfGenTimezone.setTimezone(timezone) }
    }
}
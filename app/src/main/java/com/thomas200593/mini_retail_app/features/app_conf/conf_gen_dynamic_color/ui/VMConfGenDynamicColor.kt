package com.thomas200593.mini_retail_app.features.app_conf.conf_gen_dynamic_color.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thomas200593.mini_retail_app.app.navigation.ScrGraphs
import com.thomas200593.mini_retail_app.core.data.local.session.SessionState
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatchers.Dispatchers.IO
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.di.Dispatcher
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_dynamic_color.domain.UCGetConfDynamicColor
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_dynamic_color.entity.ConfigDynamicColor
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_dynamic_color.entity.DynamicColor
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_dynamic_color.repository.RepoConfGenDynamicColor
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_dynamic_color.ui.VMConfGenDynamicColor.UiEvents.ButtonEvents.BtnNavBackEvents
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_dynamic_color.ui.VMConfGenDynamicColor.UiEvents.ButtonEvents.BtnScrDescEvents
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_dynamic_color.ui.VMConfGenDynamicColor.UiEvents.ButtonEvents.BtnSetPrefDynamicColorEvents
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_dynamic_color.ui.VMConfGenDynamicColor.UiEvents.DialogEvents.DlgDenySetDataEvents
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_dynamic_color.ui.VMConfGenDynamicColor.UiEvents.OnOpenEvents
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_dynamic_color.ui.VMConfGenDynamicColor.UiStateConfigDynamicColor.Loading
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_dynamic_color.ui.VMConfGenDynamicColor.UiStateConfigDynamicColor.Success
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VMConfGenDynamicColor @Inject constructor(
    private val ucGetConfGenDynamicColor: UCGetConfDynamicColor,
    private val repoConfGenDynamicColor: RepoConfGenDynamicColor,
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher
): ViewModel() {
    sealed interface UiStateConfigDynamicColor {
        data object Loading : UiStateConfigDynamicColor
        data class Success(val configDynamicColor: ConfigDynamicColor): UiStateConfigDynamicColor
    }
    data class UiState(
        val configDynamicColor: UiStateConfigDynamicColor = Loading,
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
            sealed interface BtnSetPrefDynamicColorEvents : ButtonEvents {
                data class OnAllow(val dynamicColor: DynamicColor) : BtnSetPrefDynamicColorEvents
                data object OnDeny : BtnSetPrefDynamicColorEvents
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
            is BtnSetPrefDynamicColorEvents.OnDeny -> onDenySet()
            is BtnSetPrefDynamicColorEvents.OnAllow -> onAllowSet(events.dynamicColor)
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
    private fun resetUiStateConfigDynamicColor() = _uiState.update { it.copy(configDynamicColor = Loading) }
    private fun resetDialogAndUiState() { resetDialogState(); resetUiStateConfigDynamicColor() }
    private fun onOpenEvent(sessionState: SessionState, currentScreen: ScrGraphs) {
        resetUiStateConfigDynamicColor(); resetDialogState()
        when(sessionState) {
            SessionState.Loading -> updateDialogState(dlgLoadingAuth = true)
            is SessionState.Invalid -> if(currentScreen.usesAuth) onDenySet()
            else viewModelScope.launch {
                resetUiStateConfigDynamicColor(); updateDialogState(dlgLoadingGetData = true)
                ucGetConfGenDynamicColor.invoke().flowOn(ioDispatcher).collect{ data ->
                    _uiState.update {
                        it.copy(
                            configDynamicColor = Success(data),
                            dialogState = DialogState()
                        )
                    }
                }
            }
            is SessionState.Valid -> viewModelScope.launch {
                resetUiStateConfigDynamicColor(); updateDialogState(dlgLoadingGetData = true)
                ucGetConfGenDynamicColor.invoke().flowOn(ioDispatcher).collect{ data ->
                    _uiState.update {
                        it.copy(
                            configDynamicColor = Success(data),
                            dialogState = DialogState()
                        )
                    }
                }
            }
        }
    }
    private fun onDenySet() { resetDialogState(); updateDialogState(dlgDenySetData = true) }
    private fun onAllowSet(dynamicColor: DynamicColor) {
        resetDialogState()
        viewModelScope.launch { repoConfGenDynamicColor.setDynamicColor(dynamicColor = dynamicColor) }
    }
}
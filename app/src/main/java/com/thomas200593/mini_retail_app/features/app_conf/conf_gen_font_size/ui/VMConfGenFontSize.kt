package com.thomas200593.mini_retail_app.features.app_conf.conf_gen_font_size.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thomas200593.mini_retail_app.app.navigation.ScrGraphs
import com.thomas200593.mini_retail_app.core.data.local.session.SessionState
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatchers.Dispatchers.IO
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.di.Dispatcher
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_font_size.domain.UCGetConfFontSize
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_font_size.entity.ConfigFontSizes
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_font_size.entity.FontSize
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_font_size.repository.RepoConfGenFontSize
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_font_size.ui.VMConfGenFontSize.UiEvents.ButtonEvents.BtnNavBackEvents
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_font_size.ui.VMConfGenFontSize.UiEvents.ButtonEvents.BtnScrDescEvents
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_font_size.ui.VMConfGenFontSize.UiEvents.ButtonEvents.BtnSetPrefFontSizeEvents
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_font_size.ui.VMConfGenFontSize.UiEvents.DialogEvents.DlgDenySetDataEvents
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_font_size.ui.VMConfGenFontSize.UiEvents.OnOpenEvents
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_font_size.ui.VMConfGenFontSize.UiStateConfigFontSize.Loading
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_font_size.ui.VMConfGenFontSize.UiStateConfigFontSize.Success
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VMConfGenFontSize @Inject constructor(
    private val repoConfGenFontSize: RepoConfGenFontSize,
    private val ucGetConfFontSize: UCGetConfFontSize,
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher
): ViewModel() {
    sealed interface UiStateConfigFontSize {
        data object Loading : UiStateConfigFontSize
        data class Success(val configFontSizes: ConfigFontSizes): UiStateConfigFontSize
    }

    data class UiState(
        val configFontSizes: UiStateConfigFontSize = Loading,
        val dialogState: DialogState = DialogState()
    )

    data class DialogState(
        val dlgLoadingAuth: MutableState<Boolean> = mutableStateOf(false),
        val dlgLoadingGetData: MutableState<Boolean> = mutableStateOf(false),
        val dlgDenySetData: MutableState<Boolean> = mutableStateOf(false),
        val dlgScrDesc: MutableState<Boolean> = mutableStateOf(false)
    )

    sealed class UiEvents {
        data class OnOpenEvents(
            val sessionState: SessionState,
            val currentScreen: ScrGraphs
        ) : UiEvents()
        sealed class ButtonEvents : UiEvents() {
            sealed class BtnNavBackEvents : ButtonEvents() {
                data object OnClick : BtnNavBackEvents()
            }
            sealed class BtnScrDescEvents : ButtonEvents() {
                data object OnClick : BtnScrDescEvents()
                data object OnDismiss : BtnScrDescEvents()
            }
            sealed class BtnSetPrefFontSizeEvents : ButtonEvents() {
                data class OnAllow(val fontSize: FontSize) : BtnSetPrefFontSizeEvents()
                data object OnDeny : BtnSetPrefFontSizeEvents()
            }
        }
        sealed class DialogEvents : UiEvents() {
            sealed class DlgDenySetDataEvents : DialogEvents() {
                data object OnDismiss : DlgDenySetDataEvents()
            }
        }
    }

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    fun onEvent(events: UiEvents) {
        when(events) {
            is OnOpenEvents -> onOpenEvent(events.sessionState, events.currentScreen)
            is BtnNavBackEvents.OnClick -> onNavBackEvent()
            is BtnScrDescEvents.OnClick -> onShowScrDescEvent()
            is BtnScrDescEvents.OnDismiss -> onHideScrDescEvent()
            is BtnSetPrefFontSizeEvents.OnDeny -> onDenySet()
            is BtnSetPrefFontSizeEvents.OnAllow -> onAllowSet(events.fontSize)
            is DlgDenySetDataEvents.OnDismiss -> onDismissDenySetDlg()
        }
    }

    private fun resetUiStateConfigFontSize() = _uiState.update { it.copy(configFontSizes = Loading) }
    private fun resetDialogState() = _uiState.update { it.copy(dialogState = DialogState()) }
    private fun updateDialogState(
        dlgLoadingAuth: Boolean = false,
        dlgLoadingGetData: Boolean = false,
        dlgDenySetData: Boolean = false,
        dlgScrDesc: Boolean = false
    ) = _uiState.update {
        it.copy(
            dialogState = it.dialogState.copy(
                dlgLoadingAuth = mutableStateOf(dlgLoadingAuth),
                dlgLoadingGetData = mutableStateOf(dlgLoadingGetData),
                dlgDenySetData = mutableStateOf(dlgDenySetData),
                dlgScrDesc = mutableStateOf(dlgScrDesc)
            )
        )
    }
    private fun onOpenEvent(sessionState: SessionState, currentScreen: ScrGraphs) {
        resetUiStateConfigFontSize()
        resetDialogState()
        when(sessionState) {
            SessionState.Loading -> {
                updateDialogState(dlgLoadingAuth = true)
            }
            is SessionState.Invalid -> {
                if(currentScreen.usesAuth) {
                    onDenySet()
                } else {
                    viewModelScope.launch {
                        resetUiStateConfigFontSize()
                        updateDialogState(dlgLoadingGetData = true)
                        ucGetConfFontSize.invoke().flowOn(ioDispatcher).collect{ data ->
                            _uiState.update {
                                it.copy(
                                    configFontSizes = Success(data),
                                    dialogState = DialogState()
                                )
                            }
                        }
                    }
                }
            }
            is SessionState.Valid -> viewModelScope.launch{
                resetUiStateConfigFontSize()
                updateDialogState(dlgLoadingGetData = true)
                ucGetConfFontSize.invoke().flowOn(ioDispatcher).collect{ data ->
                    _uiState.update {
                        it.copy(
                            configFontSizes = Success(data),
                            dialogState = DialogState()
                        )
                    }
                }
            }
        }
    }
    private fun onNavBackEvent() {
        resetDialogState()
        resetUiStateConfigFontSize()
    }
    private fun onShowScrDescEvent() {
        resetDialogState()
        updateDialogState(dlgScrDesc = true)
    }
    private fun onHideScrDescEvent() {
        resetDialogState()
    }
    private fun onDenySet() {
        resetDialogState()
        updateDialogState(dlgDenySetData = true)
    }
    private fun onAllowSet(fontSize: FontSize) {
        resetDialogState()
        viewModelScope.launch {
            repoConfGenFontSize.setFontSize(fontSize = fontSize)
        }
    }
    private fun onDismissDenySetDlg() {
        resetDialogState()
        resetUiStateConfigFontSize()
    }
}
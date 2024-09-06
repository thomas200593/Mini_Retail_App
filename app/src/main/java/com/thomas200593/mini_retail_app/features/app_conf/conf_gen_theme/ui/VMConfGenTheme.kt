package com.thomas200593.mini_retail_app.features.app_conf.conf_gen_theme.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thomas200593.mini_retail_app.app.navigation.ScrGraphs
import com.thomas200593.mini_retail_app.core.data.local.session.SessionState
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatchers.Dispatchers.IO
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.di.Dispatcher
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_theme.domain.UCGetConfGenTheme
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_theme.entity.ConfigThemes
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_theme.entity.Theme
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_theme.repository.RepoConfGenTheme
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_theme.ui.VMConfGenTheme.UiEvents.ButtonEvents.BtnNavBackEvents
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_theme.ui.VMConfGenTheme.UiEvents.ButtonEvents.BtnScrDescEvents
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_theme.ui.VMConfGenTheme.UiEvents.ButtonEvents.BtnSetPrefThemeEvents
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_theme.ui.VMConfGenTheme.UiEvents.DialogEvents.DlgDenySetDataEvents
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_theme.ui.VMConfGenTheme.UiEvents.OnOpenEvents
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_theme.ui.VMConfGenTheme.UiStateConfigTheme.Loading
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_theme.ui.VMConfGenTheme.UiStateConfigTheme.Success
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VMConfGenTheme @Inject constructor(
    private val repoConfGenTheme: RepoConfGenTheme,
    private val ucGetConfGenTheme: UCGetConfGenTheme,
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {
    sealed interface UiStateConfigTheme {
        data object Loading : UiStateConfigTheme
        data class Success(val configThemes: ConfigThemes) : UiStateConfigTheme
    }

    data class UiState(
        val configThemes: UiStateConfigTheme = Loading,
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
            sealed class BtnSetPrefThemeEvents : ButtonEvents() {
                data class OnAllow(val theme: Theme) : BtnSetPrefThemeEvents()
                data object OnDeny : BtnSetPrefThemeEvents()
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
            is BtnSetPrefThemeEvents.OnDeny -> onDenySet()
            is BtnSetPrefThemeEvents.OnAllow -> onAllowSet(events.theme)
            is DlgDenySetDataEvents.OnDismiss -> onDismissDenySetDlg()
        }
    }

    private fun resetUiStateConfigTheme() = _uiState.update { it.copy(configThemes = Loading) }
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
        resetUiStateConfigTheme()
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
                        resetUiStateConfigTheme()
                        updateDialogState(dlgLoadingGetData = true)
                        ucGetConfGenTheme.invoke().flowOn(ioDispatcher).collect{ data ->
                            _uiState.update {
                                it.copy(
                                    configThemes = Success(data),
                                    dialogState = DialogState()
                                )
                            }
                        }
                    }
                }
            }
            is SessionState.Valid -> viewModelScope.launch {
                resetUiStateConfigTheme()
                updateDialogState(dlgLoadingGetData = true)
                ucGetConfGenTheme.invoke().flowOn(ioDispatcher).collect{ data ->
                    _uiState.update {
                        it.copy(
                            configThemes = Success(data),
                            dialogState = DialogState()
                        )
                    }
                }
            }
        }
    }
    private fun onNavBackEvent() {
        resetDialogState()
        resetUiStateConfigTheme()
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
    private fun onAllowSet(theme: Theme) {
        resetDialogState()
        viewModelScope.launch { repoConfGenTheme.setTheme(theme) }
    }
    private fun onDismissDenySetDlg() {
        resetDialogState()
        resetUiStateConfigTheme()
    }
}
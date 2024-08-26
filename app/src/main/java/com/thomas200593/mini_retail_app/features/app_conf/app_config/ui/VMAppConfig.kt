package com.thomas200593.mini_retail_app.features.app_conf.app_config.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thomas200593.mini_retail_app.core.data.local.session.SessionState
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatchers.Dispatchers.IO
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.di.Dispatcher
import com.thomas200593.mini_retail_app.features.app_conf.app_config.navigation.DestAppConfig
import com.thomas200593.mini_retail_app.features.app_conf.app_config.repository.RepoAppConf
import com.thomas200593.mini_retail_app.features.app_conf.app_config.ui.VMAppConfig.UiEvents.ButtonEvents.BtnMenuSelectionEvents
import com.thomas200593.mini_retail_app.features.app_conf.app_config.ui.VMAppConfig.UiEvents.ButtonEvents.BtnScrDescEvents
import com.thomas200593.mini_retail_app.features.app_conf.app_config.ui.VMAppConfig.UiEvents.ButtonEvents.DialogEvents.DlgDenyAccessEvents
import com.thomas200593.mini_retail_app.features.app_conf.app_config.ui.VMAppConfig.UiEvents.OnOpenEvents
import com.thomas200593.mini_retail_app.features.app_conf.app_config.ui.VMAppConfig.UiStateDestAppConfig.Loading
import com.thomas200593.mini_retail_app.features.app_conf.app_config.ui.VMAppConfig.UiStateDestAppConfig.Success
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VMAppConfig @Inject constructor(
    private val repoAppConf: RepoAppConf,
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher
): ViewModel() {
    sealed interface UiStateDestAppConfig {
        data object Loading: UiStateDestAppConfig
        data class Success(val destAppConfig: Set<DestAppConfig>): UiStateDestAppConfig
    }
    data class UiState(
        val destAppConfig: UiStateDestAppConfig = Loading,
        val dialogState: DialogState = DialogState()
    )
    data class DialogState(
        val dlgLoadingAuth: MutableState<Boolean> = mutableStateOf(false),
        val dlgLoadingGetMenu: MutableState<Boolean> = mutableStateOf(false),
        val dlgDenyAccessMenu: MutableState<Boolean> = mutableStateOf(false),
        val dlgScrDesc: MutableState<Boolean> = mutableStateOf(false)
    )
    sealed class UiEvents {
        data class OnOpenEvents(val sessionState: SessionState): UiEvents()
        sealed class ButtonEvents: UiEvents() {
            sealed class BtnScrDescEvents: ButtonEvents() {
                data object OnClick: BtnScrDescEvents()
                data object OnDismiss: BtnScrDescEvents()
            }
            sealed class BtnMenuSelectionEvents: ButtonEvents() {
                data object OnAllow: BtnMenuSelectionEvents()
                data object OnDeny: BtnMenuSelectionEvents()
            }
            sealed class DialogEvents: UiEvents() {
                sealed class DlgDenyAccessEvents: DialogEvents() {
                    data class OnDismiss(val sessionState: SessionState): DlgDenyAccessEvents()
                }
            }
        }
    }

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    fun onEvent(events: UiEvents) {
        when(events) {
            is OnOpenEvents -> onOpenEvent(events.sessionState)
            is BtnScrDescEvents.OnClick -> btnScrDescOnClickEvent()
            is BtnScrDescEvents.OnDismiss -> btnScrDescOnDismissEvent()
            is DlgDenyAccessEvents.OnDismiss -> onOpenEvent(events.sessionState)
            is BtnMenuSelectionEvents.OnAllow -> onAllowAccessMenuEvent()
            is BtnMenuSelectionEvents.OnDeny -> updateDialogState(dlgDenyAccessMenu = true)
        }
    }

    private fun onOpenEvent(sessionState: SessionState) {
        resetDialogState()
        when(sessionState) {
            SessionState.Loading -> {
                resetUiStateDestAppConfig()
                updateDialogState(dlgLoadingAuth = true)
            }
            is SessionState.Invalid -> {
                updateDialogState(dlgLoadingAuth = false, dlgLoadingGetMenu = true)
                viewModelScope.launch(ioDispatcher) {
                    repoAppConf.getMenuData().collect{ menuData ->
                        _uiState.update {
                            it.copy(
                                destAppConfig = Success(
                                    destAppConfig = menuData
                                        .filter { menu ->
                                            !menu.scrGraphs.usesAuth
                                        }
                                        .toSet()
                                )
                            )
                        }
                    }
                }
                resetDialogState()
            }
            is SessionState.Valid -> {
                updateDialogState(dlgLoadingAuth = false, dlgLoadingGetMenu = true)
                viewModelScope.launch(ioDispatcher) {
                    repoAppConf.getMenuData().collect{ menuData ->
                        _uiState.update {
                            it.copy(
                                destAppConfig = Success(
                                    destAppConfig = menuData
                                )
                            )
                        }
                    }
                }
                resetDialogState()
            }
        }
    }
    private fun updateDialogState(
        dlgLoadingAuth: Boolean = false,
        dlgLoadingGetMenu: Boolean = false,
        dlgDenyAccessMenu: Boolean = false,
        dlgScrDesc: Boolean = false
    ) = _uiState.update {
        it.copy(
            dialogState = it.dialogState.copy(
                dlgLoadingAuth = mutableStateOf(dlgLoadingAuth),
                dlgLoadingGetMenu = mutableStateOf(dlgLoadingGetMenu),
                dlgDenyAccessMenu = mutableStateOf(dlgDenyAccessMenu),
                dlgScrDesc = mutableStateOf(dlgScrDesc)
            )
        )
    }
    private fun resetDialogState() = _uiState.update { it.copy(dialogState = DialogState()) }
    private fun resetUiStateDestAppConfig() = _uiState.update { it.copy(destAppConfig = Loading) }
    private fun btnScrDescOnClickEvent() = updateDialogState(dlgScrDesc = true)
    private fun btnScrDescOnDismissEvent() = updateDialogState(dlgScrDesc = false)
    private fun onAllowAccessMenuEvent() {
        resetDialogState()
        resetUiStateDestAppConfig()
    }
}
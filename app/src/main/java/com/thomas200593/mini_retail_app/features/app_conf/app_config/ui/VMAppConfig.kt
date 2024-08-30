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
import com.thomas200593.mini_retail_app.features.app_conf.app_config.ui.VMAppConfig.UiEvents.ButtonEvents.BtnNavBackEvents
import com.thomas200593.mini_retail_app.features.app_conf.app_config.ui.VMAppConfig.UiEvents.ButtonEvents.BtnScrDescEvents
import com.thomas200593.mini_retail_app.features.app_conf.app_config.ui.VMAppConfig.UiEvents.ButtonEvents.DialogEvents.DlgDenyAccessEvents
import com.thomas200593.mini_retail_app.features.app_conf.app_config.ui.VMAppConfig.UiEvents.OnOpenEvents
import com.thomas200593.mini_retail_app.features.app_conf.app_config.ui.VMAppConfig.UiStateDestAppConfig.Loading
import com.thomas200593.mini_retail_app.features.app_conf.app_config.ui.VMAppConfig.UiStateDestAppConfig.Success
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VMAppConfig @Inject constructor(
    private val repoAppConf: RepoAppConf,
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {
    sealed interface UiStateDestAppConfig {
        data object Loading : UiStateDestAppConfig
        data class Success(val destAppConfig: Set<DestAppConfig>) : UiStateDestAppConfig
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
        data class OnOpenEvents(val sessionState: SessionState) : UiEvents()
        sealed class ButtonEvents : UiEvents() {
            sealed class BtnNavBackEvents : ButtonEvents() {
                data object OnClick : BtnNavBackEvents()
            }
            sealed class BtnScrDescEvents : ButtonEvents() {
                data object OnClick : BtnScrDescEvents()
                data object OnDismiss : BtnScrDescEvents()
            }
            sealed class BtnMenuSelectionEvents : ButtonEvents() {
                data object OnAllow : BtnMenuSelectionEvents()
                data object OnDeny : BtnMenuSelectionEvents()
            }
            sealed class DialogEvents : UiEvents() {
                sealed class DlgDenyAccessEvents : DialogEvents() {
                    data object OnDismiss : DlgDenyAccessEvents()
                }
            }
        }
    }

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    fun onEvent(events: UiEvents) {
        when (events) {
            is OnOpenEvents -> onOpenEvent(events.sessionState)
            is BtnNavBackEvents.OnClick -> onNavBackEvent()
            is BtnScrDescEvents.OnClick ->
                { resetDialogState(); updateDialogState(dlgScrDesc = true) }
            is BtnScrDescEvents.OnDismiss -> resetDialogState()
            is BtnMenuSelectionEvents.OnDeny ->
                { resetDialogState(); updateDialogState(dlgDenyAccessMenu = true) }
            is BtnMenuSelectionEvents.OnAllow -> resetDialogState()
            is DlgDenyAccessEvents.OnDismiss -> onNavBackEvent()
        }
    }

    private fun resetUiStateDestAppConfig() = _uiState.update { it.copy(destAppConfig = Loading) }
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
    private fun onOpenEvent(sessionState: SessionState) {
        resetUiStateDestAppConfig()
        resetDialogState()
        when (sessionState) {
            SessionState.Loading -> updateDialogState(dlgLoadingAuth = true)
            is SessionState.Invalid -> viewModelScope.launch {
                resetDialogState()
                updateDialogState(dlgLoadingGetMenu = true)
                repoAppConf.getMenuData().flowOn(ioDispatcher).collect { menuData ->
                    _uiState.update {
                        it.copy(
                            destAppConfig = Success(
                                destAppConfig = menuData
                                    .filterNot { menu -> menu.scrGraphs.usesAuth }.toSet()
                            ),
                            dialogState = DialogState()
                        )
                    }
                }
            }
            is SessionState.Valid -> viewModelScope.launch {
                resetDialogState()
                updateDialogState(dlgLoadingGetMenu = true)
                repoAppConf.getMenuData().flowOn(ioDispatcher).collect { menuData ->
                    _uiState.update {
                        it.copy(
                            destAppConfig = Success(destAppConfig = menuData),
                            dialogState = DialogState()
                        )
                    }
                }
            }
        }
    }
    private fun onNavBackEvent()
    { resetDialogState(); resetUiStateDestAppConfig() }
}
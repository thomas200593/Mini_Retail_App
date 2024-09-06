package com.thomas200593.mini_retail_app.features.app_conf.conf_gen.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thomas200593.mini_retail_app.core.data.local.session.SessionState
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatchers.Dispatchers.IO
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.di.Dispatcher
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen.navigation.DestConfGen
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen.repository.RepoConfGen
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen.ui.VMConfGen.UiEvents.ButtonEvents.BtnMenuSelectionEvents
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen.ui.VMConfGen.UiEvents.ButtonEvents.BtnNavBackEvents
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen.ui.VMConfGen.UiEvents.ButtonEvents.BtnScrDescEvents
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen.ui.VMConfGen.UiEvents.ButtonEvents.DialogEvents.DlgDenyAccessEvents
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen.ui.VMConfGen.UiEvents.OnOpenEvents
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen.ui.VMConfGen.UiStateDestConfGen.Loading
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen.ui.VMConfGen.UiStateDestConfGen.Success
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VMConfGen @Inject constructor(
    private val repoConfGen: RepoConfGen,
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher
): ViewModel() {
    sealed interface UiStateDestConfGen {
        data object Loading: UiStateDestConfGen
        data class Success(val destConfGen: Set<DestConfGen>): UiStateDestConfGen
    }
    data class UiState(
        val destConfGen: UiStateDestConfGen = Loading,
        val dialogState: DialogState = DialogState()
    )
    data class DialogState(
        val dlgLoadingAuth: MutableState<Boolean> = mutableStateOf(false),
        val dlgLoadingGetMenu: MutableState<Boolean> = mutableStateOf(false),
        val dlgDenyAccessMenu: MutableState<Boolean> = mutableStateOf(false),
        val dlgScrDesc: MutableState<Boolean> = mutableStateOf(false)
    )
    sealed interface UiEvents {
        data class OnOpenEvents(val sessionState: SessionState): UiEvents
        sealed interface ButtonEvents: UiEvents {
            sealed interface BtnNavBackEvents: ButtonEvents {
                data object OnClick: BtnNavBackEvents
            }
            sealed interface BtnScrDescEvents: ButtonEvents {
                data object OnClick: BtnScrDescEvents
                data object OnDismiss: BtnScrDescEvents
            }
            sealed interface BtnMenuSelectionEvents: ButtonEvents {
                data object OnAllow: BtnMenuSelectionEvents
                data object OnDeny: BtnMenuSelectionEvents
            }
            sealed interface DialogEvents: UiEvents {
                sealed interface DlgDenyAccessEvents: DialogEvents {
                    data object OnDismiss: DlgDenyAccessEvents
                }
            }
        }
    }

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    fun onEvent(events: UiEvents) {
        when(events) {
            is OnOpenEvents -> onOpenEvent(events.sessionState)
            is BtnNavBackEvents.OnClick -> resetDialogAndUiState()
            is BtnScrDescEvents.OnClick ->
                { resetDialogState(); updateDialogState(dlgScrDesc = true) }
            is BtnScrDescEvents.OnDismiss -> resetDialogState()
            is BtnMenuSelectionEvents.OnDeny ->
                { resetDialogState(); updateDialogState(dlgDenyAccessMenu = true) }
            is BtnMenuSelectionEvents.OnAllow -> resetDialogState()
            is DlgDenyAccessEvents.OnDismiss -> resetDialogAndUiState()
        }
    }
    private fun updateDialogState(
        dlgLoadingAuth: Boolean = false,
        dlgLoadingGetMenu: Boolean = false,
        dlgDenyAccessMenu: Boolean = false,
        dlgScrDesc: Boolean = false
    ) = _uiState.update { it.copy(
        dialogState = it.dialogState.copy(
            dlgLoadingAuth = mutableStateOf(dlgLoadingAuth),
            dlgLoadingGetMenu = mutableStateOf(dlgLoadingGetMenu),
            dlgDenyAccessMenu = mutableStateOf(dlgDenyAccessMenu),
            dlgScrDesc = mutableStateOf(dlgScrDesc)
        )
    ) }
    private fun resetDialogState() = _uiState.update { it.copy(dialogState = DialogState()) }
    private fun resetUiStateDestConfGen() = _uiState.update { it.copy(destConfGen = Loading) }
    private fun resetDialogAndUiState() { resetDialogState(); resetUiStateDestConfGen() }
    private fun onOpenEvent(sessionState: SessionState) {
        resetUiStateDestConfGen(); resetDialogState()
        when(sessionState) {
            SessionState.Loading -> updateDialogState(dlgLoadingAuth = true)
            is SessionState.Invalid -> viewModelScope.launch {
                resetDialogState(); updateDialogState(dlgLoadingGetMenu = true)
                repoConfGen.getMenuData().flowOn(ioDispatcher).collect{ menuData ->
                    _uiState.update {
                        it.copy(
                            destConfGen = Success(
                                destConfGen = menuData.filterNot { menu ->
                                    menu.scrGraphs.usesAuth
                                }.toSet()
                            ),
                            dialogState = DialogState()
                        )
                    }
                }
            }
            is SessionState.Valid -> viewModelScope.launch {
                resetDialogState(); updateDialogState(dlgLoadingGetMenu = true)
                repoConfGen.getMenuData().flowOn(ioDispatcher).collect{ menuData ->
                    _uiState.update {
                        it.copy(
                            destConfGen = Success(destConfGen = menuData),
                            dialogState = DialogState()
                        )
                    }
                }
            }
        }
    }
}
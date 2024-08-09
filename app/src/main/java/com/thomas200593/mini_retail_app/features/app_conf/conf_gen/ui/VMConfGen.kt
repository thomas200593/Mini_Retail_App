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
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen.ui.VMConfGen.UiEvents.ButtonEvents.BtnMenuEvents
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen.ui.VMConfGen.UiEvents.ButtonEvents.BtnNavBackEvents
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen.ui.VMConfGen.UiEvents.OnOpenEvents
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen.ui.VMConfGen.UiStateDestConfGen.Loading
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen.ui.VMConfGen.UiStateDestConfGen.Success
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VMConfGen @Inject constructor(
    private val repoConfGen: RepoConfGen,
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher
): ViewModel() {
    sealed interface UiStateDestConfGen{
        data object Loading: UiStateDestConfGen
        data class Success(val destConfGen: Set<DestConfGen>): UiStateDestConfGen
    }
    data class UiState(
        val destConfGen: UiStateDestConfGen = Loading,
        val dialogState: DialogState = DialogState()
    )
    data class DialogState(
        val dlgVldAuthEnabled : MutableState<Boolean> = mutableStateOf(false),
        val dlgLoadMenuEnabled : MutableState<Boolean> = mutableStateOf(false),
        val dlgDenyAccessMenuEnabled : MutableState<Boolean> = mutableStateOf(false)
    )
    sealed class UiEvents {
        data class OnOpenEvents(val sessionState: SessionState) : UiEvents()
        sealed class ButtonEvents : UiEvents(){
            sealed class BtnNavBackEvents: ButtonEvents(){
                data object OnClick: BtnNavBackEvents()
            }
            sealed class BtnMenuEvents: ButtonEvents(){
                data object OnAllow: BtnMenuEvents()
                data object OnDeny: BtnMenuEvents()
            }
        }
    }

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    fun onEvent(events: UiEvents) {
        when(events){
            is OnOpenEvents -> onOpenEvent(events.sessionState)
            BtnNavBackEvents.OnClick -> onBtnNavBackClicked()
            BtnMenuEvents.OnAllow -> onAllowAccessMenu()
            BtnMenuEvents.OnDeny -> onDenyAccessMenu()
        }
    }
    private fun onOpenEvent(sessionState: SessionState) {
        when(sessionState){
            SessionState.Loading -> updateDialogState(
                dlgVldAuthEnabled = true,
                dlgLoadMenuEnabled = false,
                dlgDenyAccessMenuEnabled = false
            )
            is SessionState.Invalid, is SessionState.Valid -> viewModelScope.launch(ioDispatcher) {
                updateDialogState(
                    dlgVldAuthEnabled = false,
                    dlgLoadMenuEnabled = true,
                    dlgDenyAccessMenuEnabled = false
                )
                _uiState.update { it.copy(
                    destConfGen = Success(repoConfGen.getMenuData(sessionState)),
                    dialogState = DialogState()
                ) }
            }
        }
    }
    private fun onBtnNavBackClicked() = _uiState.update { it.copy(dialogState = DialogState()) }
    private fun onAllowAccessMenu() = _uiState.update { it.copy(dialogState = DialogState()) }
    private fun onDenyAccessMenu() = updateDialogState(
        dlgVldAuthEnabled = false,
        dlgLoadMenuEnabled = false,
        dlgDenyAccessMenuEnabled = true
    )

    private fun updateDialogState(
        dlgVldAuthEnabled: Boolean = false,
        dlgLoadMenuEnabled: Boolean = false,
        dlgDenyAccessMenuEnabled: Boolean = false
    ){
        _uiState.update { it.copy(
            dialogState = it.dialogState.copy(
                dlgVldAuthEnabled = mutableStateOf(dlgVldAuthEnabled),
                dlgLoadMenuEnabled = mutableStateOf(dlgLoadMenuEnabled),
                dlgDenyAccessMenuEnabled = mutableStateOf(dlgDenyAccessMenuEnabled)
            )
        ) }
    }
}
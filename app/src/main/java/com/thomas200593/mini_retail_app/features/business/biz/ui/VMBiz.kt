package com.thomas200593.mini_retail_app.features.business.biz.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.thomas200593.mini_retail_app.app.navigation.ScrGraphs
import com.thomas200593.mini_retail_app.core.data.local.session.SessionState
import com.thomas200593.mini_retail_app.features.business.biz.navigation.DestBiz
import com.thomas200593.mini_retail_app.features.business.biz.ui.VMBiz.UiStateDestBiz.Loading
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class VMBiz @Inject constructor() : ViewModel() {
    sealed interface UiStateDestBiz {
        data object Loading: UiStateDestBiz
        data class Success(val destBiz: Set<DestBiz>): UiStateDestBiz
    }
    data class UiState(
        val destBiz: UiStateDestBiz = Loading,
        val dialogState: DialogState = DialogState()
    )
    data class DialogState(
        val dlgLoadingAuth: MutableState<Boolean> = mutableStateOf(false),
        val dlgLoadingGetMenu: MutableState<Boolean> = mutableStateOf(false),
        val dlgDenyAccessMenu: MutableState<Boolean> = mutableStateOf(false),
        val dlgScrDesc: MutableState<Boolean> = mutableStateOf(false)
    )
    sealed interface UiEvents {
        data class OnOpenEvents(
            val sessionState: SessionState,
            val currentScreen: ScrGraphs
        ): UiEvents
        sealed interface ButtonEvents: UiEvents {
            sealed interface BtnScrDescEvents : ButtonEvents {
                data object OnClick: BtnScrDescEvents
                data object OnDismiss : BtnScrDescEvents
            }
            sealed interface BtnMenuSelectionEvents : ButtonEvents {
                data object OnAllow : BtnMenuSelectionEvents
                data object OnDeny : BtnMenuSelectionEvents
            }
        }
        sealed interface DialogEvents : UiEvents {
            sealed interface DlgDenyAccessEvents : DialogEvents {
                data object OnDismiss : DlgDenyAccessEvents
            }
        }
    }

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()
}

/*
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thomas200593.mini_retail_app.core.data.local.session.SessionState
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatchers.Dispatchers.IO
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.di.Dispatcher
import com.thomas200593.mini_retail_app.features.business.biz.navigation.DestBiz
import com.thomas200593.mini_retail_app.features.business.biz.repository.RepoBiz
import com.thomas200593.mini_retail_app.features.business.biz.ui.VMBiz.UiEvents.ButtonEvents.BtnMenuEvents
import com.thomas200593.mini_retail_app.features.business.biz.ui.VMBiz.UiEvents.OnOpenEvents
import com.thomas200593.mini_retail_app.features.business.biz.ui.VMBiz.UiStateDestBiz.Loading
import com.thomas200593.mini_retail_app.features.business.biz.ui.VMBiz.UiStateDestBiz.Success
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VMBiz @Inject constructor(
    private val repoBiz: RepoBiz,
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher
): ViewModel() {
    sealed interface UiStateDestBiz{
        data object Loading: UiStateDestBiz
        data class Success(val destBiz: Set<DestBiz>): UiStateDestBiz
    }
    data class UiState(
        val destBiz: UiStateDestBiz = Loading,
        val dialogState: DialogState = DialogState()
    )
    data class DialogState(
        val dlgVldAuthEnabled: MutableState<Boolean> = mutableStateOf(false),
        val dlgLoadMenuEnabled: MutableState<Boolean> = mutableStateOf(false),
        val dlgDenyAccessMenuEnabled: MutableState<Boolean> = mutableStateOf(false)
    )
    sealed class UiEvents {
        data class OnOpenEvents(val sessionState: SessionState) : UiEvents()
        sealed class ButtonEvents : UiEvents(){
            sealed class BtnMenuEvents: ButtonEvents(){
                data object OnAllow: BtnMenuEvents()
                data object OnDeny: BtnMenuEvents()
            }
        }
    }

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    fun onEvent(events: UiEvents) {
        when (events) {
            is OnOpenEvents -> onOpenEvent(events.sessionState)
            BtnMenuEvents.OnAllow -> onAllowAccessMenu()
            BtnMenuEvents.OnDeny -> onDenyAccessMenu()
        }
    }
    private fun onOpenEvent(sessionState: SessionState) {
        when(sessionState){
            SessionState.Loading -> {
                updateDialogState(
                    dlgVldAuthEnabled = true,
                    dlgLoadMenuEnabled = false,
                    dlgDenyAccessMenuEnabled = false
                )
                _uiState.update { it.copy(destBiz = Loading) }
            }
            is SessionState.Invalid, is SessionState.Valid -> viewModelScope.launch(ioDispatcher) {
                updateDialogState(
                    dlgVldAuthEnabled = false,
                    dlgLoadMenuEnabled = true,
                    dlgDenyAccessMenuEnabled = false
                )
                _uiState.update {
                    it.copy(destBiz = Success(repoBiz.getMenuData(sessionState)), dialogState = DialogState())
                }
            }
        }
    }
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
}*/

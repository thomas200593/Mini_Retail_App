package com.thomas200593.mini_retail_app.features.business.biz.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thomas200593.mini_retail_app.core.data.local.session.SessionState
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatchers.Dispatchers.IO
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.di.Dispatcher
import com.thomas200593.mini_retail_app.features.business.biz.navigation.DestBiz
import com.thomas200593.mini_retail_app.features.business.biz.repository.RepoBiz
import com.thomas200593.mini_retail_app.features.business.biz.ui.VMBiz.UiEvents.OnOpenEvents
import com.thomas200593.mini_retail_app.features.business.biz.ui.VMBiz.UiStateDestBiz.Loading
import com.thomas200593.mini_retail_app.features.business.biz.ui.VMBiz.UiStateDestBiz.Success
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VMBiz @Inject constructor(
    private val repoBiz: RepoBiz,
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher
): ViewModel() {
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
        val dlgLoadingGetData: MutableState<Boolean> = mutableStateOf(false),
        val dlgDenySession: MutableState<Boolean> = mutableStateOf(false),
        val dlgScrDesc: MutableState<Boolean> = mutableStateOf(false)
    )
    sealed interface UiEvents {
        data class OnOpenEvents(val sessionState: SessionState) : UiEvents
    }

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    fun onEvent(events: UiEvents) {
        when(events) {
            is OnOpenEvents -> onOpenEvent(events.sessionState)
        }
    }

    private fun updateDialogState(
        dlgLoadingAuth: Boolean = false,
        dlgLoadingGetData: Boolean = false,
        dlgDenySession: Boolean = false,
        dlgScrDesc: Boolean = false
    ) = _uiState.update { it.copy(
        dialogState = it.dialogState.copy(
            dlgLoadingAuth = mutableStateOf(dlgLoadingAuth),
            dlgLoadingGetData = mutableStateOf(dlgLoadingGetData),
            dlgDenySession = mutableStateOf(dlgDenySession),
            dlgScrDesc = mutableStateOf(dlgScrDesc)
        )
    ) }
    private fun resetDialogState() = _uiState.update { it.copy(dialogState = DialogState()) }
    private fun resetUiStateDestBiz() = _uiState.update { it.copy(destBiz = Loading) }
    private fun resetDialogAndUiState() { resetDialogState(); resetUiStateDestBiz() }
    private fun onOpenEvent(sessionState: SessionState) {
        resetDialogAndUiState()
        when(sessionState) {
            SessionState.Loading -> updateDialogState(dlgLoadingAuth = true)
            is SessionState.Invalid -> onDenyAccess()
            is SessionState.Valid -> loadMenuData()
        }
    }
    private fun onDenyAccess() { resetDialogAndUiState(); updateDialogState(dlgDenySession = true) }
    private fun loadMenuData() = viewModelScope.launch{
        updateDialogState(dlgLoadingGetData = true)
        _uiState.update { it.copy(destBiz = Loading) }
        repoBiz.getMenuData().flowOn(ioDispatcher).collectLatest { data ->
            _uiState.update {
                it.copy(
                    destBiz = Success(destBiz = data),
                    dialogState = DialogState()
                )
            }
        }
    }
}

/*
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thomas200593.mini_retail_app.app.navigation.ScrGraphs
import com.thomas200593.mini_retail_app.core.data.local.session.SessionState
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatchers.Dispatchers.IO
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.di.Dispatcher
import com.thomas200593.mini_retail_app.features.business.biz.navigation.DestBiz
import com.thomas200593.mini_retail_app.features.business.biz.repository.RepoBiz
import com.thomas200593.mini_retail_app.features.business.biz.ui.VMBiz.UiEvents.ButtonEvents.BtnMenuSelectionEvents
import com.thomas200593.mini_retail_app.features.business.biz.ui.VMBiz.UiEvents.ButtonEvents.BtnScrDescEvents
import com.thomas200593.mini_retail_app.features.business.biz.ui.VMBiz.UiEvents.DialogEvents.DlgDenyAccessEvents
import com.thomas200593.mini_retail_app.features.business.biz.ui.VMBiz.UiEvents.OnOpenEvents
import com.thomas200593.mini_retail_app.features.business.biz.ui.VMBiz.UiStateDestBiz.Loading
import com.thomas200593.mini_retail_app.features.business.biz.ui.VMBiz.UiStateDestBiz.Success
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VMBiz @Inject constructor(
    private val repoBiz: RepoBiz,
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {
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

    fun onEvent(events: UiEvents) {
        when(events) {
            is OnOpenEvents -> onOpenEvent(events.sessionState, events.currentScreen)
            is BtnScrDescEvents.OnClick ->
                { resetDialogState(); updateDialogState(dlgScrDesc = true) }
            is BtnScrDescEvents.OnDismiss -> resetDialogState()
            is BtnMenuSelectionEvents.OnAllow -> resetDialogState()
            is BtnMenuSelectionEvents.OnDeny -> onDenyAccess()
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
    private fun resetUiStateDestBiz() = _uiState.update { it.copy(destBiz = Loading) }
    private fun resetDialogAndUiState() { resetDialogState(); resetUiStateDestBiz() }
    private fun onOpenEvent(sessionState: SessionState, currentScreen: ScrGraphs) {
        resetUiStateDestBiz(); resetDialogState()
        when(sessionState) {
            SessionState.Loading -> updateDialogState(dlgLoadingAuth = true)
            is SessionState.Invalid -> if(currentScreen.usesAuth) onDenyAccess()
            else loadMenuData()
            is SessionState.Valid -> loadMenuData()
        }
    }
    private fun onDenyAccess() { resetDialogState(); updateDialogState(dlgDenyAccessMenu = true) }
    private fun loadMenuData() = viewModelScope.launch {
        resetDialogState(); updateDialogState(dlgLoadingGetMenu = true)
        repoBiz.getMenuData().flowOn(ioDispatcher).collectLatest { menuData ->
            _uiState.update {
                it.copy(
                    destBiz = Success(destBiz = menuData),
                    dialogState = DialogState()
                )
            }
        }
    }
}*/

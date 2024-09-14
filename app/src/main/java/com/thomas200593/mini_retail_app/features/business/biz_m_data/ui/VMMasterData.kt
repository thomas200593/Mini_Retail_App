package com.thomas200593.mini_retail_app.features.business.biz_m_data.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thomas200593.mini_retail_app.app.navigation.ScrGraphs
import com.thomas200593.mini_retail_app.core.data.local.session.SessionState
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatchers.Dispatchers.IO
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.di.Dispatcher
import com.thomas200593.mini_retail_app.features.business.biz_m_data.navigation.DestMasterData
import com.thomas200593.mini_retail_app.features.business.biz_m_data.repository.RepoMasterData
import com.thomas200593.mini_retail_app.features.business.biz_m_data.ui.VMMasterData.UiEvents.ButtonEvents.BtnMenuSelectionEvents
import com.thomas200593.mini_retail_app.features.business.biz_m_data.ui.VMMasterData.UiEvents.ButtonEvents.BtnNavBackEvents
import com.thomas200593.mini_retail_app.features.business.biz_m_data.ui.VMMasterData.UiEvents.ButtonEvents.BtnScrDescEvents
import com.thomas200593.mini_retail_app.features.business.biz_m_data.ui.VMMasterData.UiEvents.DialogEvents.DlgDenyAccessEvents
import com.thomas200593.mini_retail_app.features.business.biz_m_data.ui.VMMasterData.UiEvents.OnOpenEvents
import com.thomas200593.mini_retail_app.features.business.biz_m_data.ui.VMMasterData.UiStateDestMasterData.Loading
import com.thomas200593.mini_retail_app.features.business.biz_m_data.ui.VMMasterData.UiStateDestMasterData.Success
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
class VMMasterData @Inject constructor(
    private val repoMasterData: RepoMasterData,
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {
    sealed interface UiStateDestMasterData {
        data object Loading: UiStateDestMasterData
        data class Success(val destMasterData: Set<DestMasterData>): UiStateDestMasterData
    }
    data class UiState(
        val destMasterData: UiStateDestMasterData = Loading,
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
            sealed interface BtnNavBackEvents : ButtonEvents {
                data object OnClick : BtnNavBackEvents
            }
            sealed interface BtnScrDescEvents : ButtonEvents {
                data object OnClick : BtnScrDescEvents
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
            is BtnNavBackEvents.OnClick -> resetDialogAndUiState()
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
    private fun resetUiStateDestMasterData() = _uiState.update { it.copy(destMasterData = Loading) }
    private fun resetDialogAndUiState() { resetDialogState(); resetUiStateDestMasterData() }
    private fun onOpenEvent(sessionState: SessionState, currentScreen: ScrGraphs) {
        resetUiStateDestMasterData(); resetDialogState()
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
        repoMasterData.getMenuData().flowOn(ioDispatcher).collectLatest { menuData ->
            _uiState.update {
                it.copy(
                    destMasterData = Success(destMasterData = menuData),
                    dialogState = DialogState()
                )
            }
        }
    }
}
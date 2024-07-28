package com.thomas200593.mini_retail_app.features.app_conf.app_config.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thomas200593.mini_retail_app.core.data.local.session.SessionState
import com.thomas200593.mini_retail_app.core.data.local.session.SessionState.Invalid
import com.thomas200593.mini_retail_app.core.data.local.session.SessionState.Loading
import com.thomas200593.mini_retail_app.core.data.local.session.SessionState.Valid
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatchers.Dispatchers.IO
import com.thomas200593.mini_retail_app.core.design_system.util.HlpStateFlow.update
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState.Idle
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState.Success
import com.thomas200593.mini_retail_app.features.app_conf.app_config.navigation.DestAppConfig
import com.thomas200593.mini_retail_app.features.app_conf.app_config.repository.RepoAppConf
import com.thomas200593.mini_retail_app.features.app_conf.app_config.ui.VMAppConfig.UiEvents.MenuBtnEvents
import com.thomas200593.mini_retail_app.features.app_conf.app_config.ui.VMAppConfig.UiEvents.ScreenEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VMAppConfig @Inject constructor(
    private val repoAppConf: RepoAppConf,
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher
): ViewModel(){
    data class UiState(
        val sessionState: SessionState = Loading,
        val destAppConfig: ResourceState<Set<DestAppConfig>> = Idle,
        val dialogState: DialogState = DialogState()
    )
    data class DialogState(
        val dlgVldAuthUiEnabled : MutableState<Boolean> = mutableStateOf(true),
        val dlgGetMenuUiEnabled : MutableState<Boolean> = mutableStateOf(false),
        val dlgDenyAccessUiEnabled : MutableState<Boolean> = mutableStateOf(false)
    )
    sealed class UiEvents {
        sealed class ScreenEvents : UiEvents(){
            data class OnOpen(val sessionState: SessionState) : ScreenEvents()
            data object OnNavigateUp: ScreenEvents()
        }
        sealed class MenuBtnEvents: UiEvents(){
            data object OnAllow: MenuBtnEvents()
            data object OnDeny: MenuBtnEvents()
        }
    }

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    fun onEvent(events: UiEvents) = viewModelScope.launch(ioDispatcher) {
        when(events){
            is ScreenEvents.OnOpen -> handleOnOpen(events.sessionState)
            ScreenEvents.OnNavigateUp -> handleNavigateUp()
            MenuBtnEvents.OnAllow -> handleOnAllow()
            MenuBtnEvents.OnDeny -> handleOnDeny()
        }
    }
    private fun handleOnOpen(sessionState: SessionState) = viewModelScope.launch(ioDispatcher){
        when(sessionState){
            Loading ->
                updateDialogState(loadingAuth = true, loadingGetMenu = false, denyAccess = false)
            is Invalid, is Valid -> getMenuData(sessionState)
        }
    }
    private fun handleNavigateUp() = viewModelScope.launch(ioDispatcher) {
        updateDialogState(loadingAuth = false, loadingGetMenu = true, denyAccess = false)
    }
    private fun handleOnAllow() = viewModelScope.launch(ioDispatcher) {
        updateDialogState(loadingAuth = false, loadingGetMenu = false, denyAccess = false)
    }
    private fun handleOnDeny() = viewModelScope.launch(ioDispatcher) {
        updateDialogState(loadingAuth = false, loadingGetMenu = false, denyAccess = true)
    }
    private fun getMenuData(sessionState: SessionState) = viewModelScope.launch(ioDispatcher) {
        updateDialogState(loadingAuth = false, loadingGetMenu = true, denyAccess = false)
        val menuData = repoAppConf.getMenuData(sessionState)
        _uiState.update { it.copy(destAppConfig = Success(menuData)) }
        updateDialogState(loadingAuth = false, loadingGetMenu = false, denyAccess = false)
    }
    private fun updateDialogState(
        loadingAuth: Boolean = true,
        loadingGetMenu: Boolean = false,
        denyAccess: Boolean = false
    ) = viewModelScope.launch(ioDispatcher) {
        _uiState.update {
            it.copy(
                dialogState = it.dialogState.copy(
                    dlgVldAuthUiEnabled = mutableStateOf(loadingAuth),
                    dlgGetMenuUiEnabled = mutableStateOf(loadingGetMenu),
                    dlgDenyAccessUiEnabled = mutableStateOf(denyAccess)
                )
            )
        }
    }
}
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
import com.thomas200593.mini_retail_app.features.app_conf.app_config.ui.VMAppConfig.UiEvents.MenuEvents
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
    val menuData: ResourceState<Set<DestAppConfig>> = Idle,
    val dialogState: DialogState = DialogState()
    )
    data class DialogState(
        val uiEnableLoadAuthDlg : MutableState<Boolean> = mutableStateOf(true),
        val uiEnableLoadGetMenuDlg : MutableState<Boolean> = mutableStateOf(false),
        val uiEnableDenyAcsMenuDlg : MutableState<Boolean> = mutableStateOf(false)
    )
    sealed class UiEvents {
        sealed class ScreenEvents : UiEvents(){
            data class OnOpen(val sessionState: SessionState) : ScreenEvents()
            data object OnNavigateUp: ScreenEvents()
        }
        sealed class MenuEvents : UiEvents(){
            data class OnClick(val destAppConfig: DestAppConfig, val sessionState: SessionState) : MenuEvents()
        }
    }

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    fun onEvent(events: UiEvents){
        when(events){
            is ScreenEvents.OnOpen -> when(events.sessionState){
                Loading -> updateDialogState(loadingAuth = true, loadingGetMenu = false)
                is Invalid -> getMenuData(events.sessionState)
                is Valid -> getMenuData(events.sessionState)
            }
            ScreenEvents.OnNavigateUp -> updateDialogState(loadingAuth = false, loadingGetMenu = true)
            is MenuEvents.OnClick -> when(events.sessionState){
                Loading -> updateDialogState(loadingAuth = true, loadingGetMenu = false)
                is Invalid ->
                    if(events.destAppConfig.usesAuth){ updateDialogState(denyAccess = true) }
                    else{ updateDialogState(loadingAuth = false, loadingGetMenu = false, denyAccess = false) }
                is Valid -> updateDialogState(loadingAuth = false, loadingGetMenu = false, denyAccess = false)
            }
        }
    }
    private fun getMenuData(sessionState: SessionState) = viewModelScope.launch(ioDispatcher) {
        updateDialogState(loadingAuth = false, loadingGetMenu = true, denyAccess = false)
        _uiState.update { it.copy(menuData = Success(repoAppConf.getMenuData(sessionState = sessionState))) }
        updateDialogState(loadingAuth = false, loadingGetMenu = false, denyAccess = false)
    }
    private fun updateDialogState(
        loadingAuth: Boolean = true,
        loadingGetMenu: Boolean = false,
        denyAccess: Boolean = false
    ){
        _uiState.update {
            it.copy(
                dialogState = it.dialogState.copy(
                    uiEnableLoadAuthDlg = mutableStateOf(loadingAuth),
                    uiEnableLoadGetMenuDlg = mutableStateOf(loadingGetMenu),
                    uiEnableDenyAcsMenuDlg = mutableStateOf(denyAccess)
                )
            )
        }
    }
}
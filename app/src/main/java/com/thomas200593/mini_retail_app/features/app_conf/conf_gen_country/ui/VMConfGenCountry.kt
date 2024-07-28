package com.thomas200593.mini_retail_app.features.app_conf.conf_gen_country.ui

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
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState.Error
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState.Idle
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen.ui.VMConfGen.UiEvents
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_country.domain.UCGetConfCountry
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_country.entity.ConfigCountry
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_country.repository.RepoConfGenCountry
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_country.ui.VMConfGenCountry.UiEvents.MenuBtnEvents
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_country.ui.VMConfGenCountry.UiEvents.ScreenEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VMConfGenCountry @Inject constructor(
    private val repoConfGenCountry: RepoConfGenCountry,
    private val ucGetConfCountry: UCGetConfCountry,
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher
) : ViewModel(){
    data class UiState(
        val sessionState: SessionState = Loading,
        val configCountry: ResourceState<ConfigCountry> = Idle,
        val dialogState: DialogState = DialogState()
    )
    data class DialogState(
        val uiEnableLoadAuthDlg : MutableState<Boolean> = mutableStateOf(true),
        val uiEnableLoadGetMenuDlg : MutableState<Boolean> = mutableStateOf(false),
        val uiEnableDenyAcsDlg : MutableState<Boolean> = mutableStateOf(false)
    )
    sealed class UiEvents {
        sealed class ScreenEvents : UiEvents(){
            data class OnOpen(val sessionState: SessionState): ScreenEvents()
            data object OnNavigateUp: ScreenEvents()
        }
        sealed class MenuBtnEvents: UiEvents(){
            data object OnAllow: MenuBtnEvents()
            data object OnDeny: MenuBtnEvents()
        }
    }

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    fun onEvent(events: UiEvents) = viewModelScope.launch(ioDispatcher){
        when(events){
            is ScreenEvents.OnOpen -> handleOnOpen(events.sessionState)
            ScreenEvents.OnNavigateUp -> {}
            MenuBtnEvents.OnAllow -> {}
            MenuBtnEvents.OnDeny -> denyAccess()
        }
    }
    private fun handleOnOpen(sessionState: SessionState) = viewModelScope.launch(ioDispatcher) {
        when(sessionState){
            Loading -> validateSession()
            is Invalid -> denyAccess()
            is Valid -> getMenuData()
        }
    }
    private fun validateSession() = viewModelScope.launch(ioDispatcher) {
        updateDialogState(dlgVldAuth = true, dlgGetMenuData = false, dlgDenyAccess = false)
    }
    private fun getMenuData() = viewModelScope.launch(ioDispatcher) {
        updateDialogState(dlgVldAuth = false, dlgGetMenuData = true, dlgDenyAccess = false)
        ucGetConfCountry.invoke().flowOn(ioDispatcher)
            .catch { t -> _uiState.update { prev -> prev.copy(configCountry = Error(t)) } }
            .collect { data -> _uiState.update { prev -> prev.copy(configCountry = data) } }
        updateDialogState(dlgVldAuth = false, dlgGetMenuData = false, dlgDenyAccess = false)
    }
    private fun denyAccess() = viewModelScope.launch(ioDispatcher) {
        updateDialogState(dlgVldAuth = false, dlgGetMenuData = false, dlgDenyAccess = true)
    }
    private fun updateDialogState(
        dlgVldAuth: Boolean = true,
        dlgGetMenuData: Boolean = false,
        dlgDenyAccess: Boolean = false
    ) = viewModelScope.launch(ioDispatcher) {
        _uiState.update {
            it.copy(
                dialogState = it.dialogState.copy(
                    uiEnableLoadAuthDlg = mutableStateOf(dlgVldAuth),
                    uiEnableLoadGetMenuDlg = mutableStateOf(dlgGetMenuData),
                    uiEnableDenyAcsDlg = mutableStateOf(dlgDenyAccess)
                )
            )
        }
    }
}
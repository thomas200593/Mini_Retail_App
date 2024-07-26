package com.thomas200593.mini_retail_app.features.app_conf.conf_gen.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.thomas200593.mini_retail_app.core.data.local.session.SessionState
import com.thomas200593.mini_retail_app.core.data.local.session.SessionState.Invalid
import com.thomas200593.mini_retail_app.core.data.local.session.SessionState.Loading
import com.thomas200593.mini_retail_app.core.data.local.session.SessionState.Valid
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatchers.Dispatchers.IO
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState.Idle
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen.navigation.DestConfGen
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen.repository.RepoConfGen
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen.ui.VMConfGen.UiEvents.MenuBtnEvents
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen.ui.VMConfGen.UiEvents.ScreenEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class VMConfGen @Inject constructor(
    private val repoConfGen: RepoConfGen,
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher
): ViewModel() {
    data class UiState(
        val sessionState: SessionState = Loading,
        val menuData: ResourceState<Set<DestConfGen>> = Idle,
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
        sealed class MenuBtnEvents: UiEvents(){
            data object OnAllow: MenuBtnEvents()
            data object OnDeny: MenuBtnEvents()
        }
    }

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    fun onEvent(events: UiEvents){
        when(events){
            is ScreenEvents.OnOpen -> {/*TODO*/}
            ScreenEvents.OnNavigateUp -> {/*TODO*/}
            MenuBtnEvents.OnAllow -> {/*TODO*/}
            MenuBtnEvents.OnDeny -> {/*TODO*/}
        }
    }
    private fun handleOnOpen(sessionState: SessionState) {
        when(sessionState){
            Loading -> {/*TODO*/}
            is Invalid, is Valid -> {/*TODO*/}
        }
    }
}
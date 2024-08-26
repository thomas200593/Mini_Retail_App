package com.thomas200593.mini_retail_app.features.app_conf.conf_gen.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.thomas200593.mini_retail_app.core.data.local.session.SessionState
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatchers.Dispatchers.IO
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.di.Dispatcher
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen.navigation.DestConfGen
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen.repository.RepoConfGen
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen.ui.VMConfGen.UiEvents.ButtonEvents.BtnMenuSelectionEvents
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen.ui.VMConfGen.UiEvents.ButtonEvents.BtnScrDescEvents
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen.ui.VMConfGen.UiEvents.ButtonEvents.DialogEvents.DlgDenyAccessEvents
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen.ui.VMConfGen.UiEvents.OnOpenEvents
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen.ui.VMConfGen.UiStateDestConfGen.Loading
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
    sealed class UiEvents {
        data class OnOpenEvents(val sessionState: SessionState): UiEvents()
        sealed class ButtonEvents: UiEvents() {
            sealed class BtnScrDescEvents: ButtonEvents() {
                data object OnClick: BtnScrDescEvents()
                data object OnDismiss: BtnScrDescEvents()
            }
            sealed class BtnMenuSelectionEvents: ButtonEvents() {
                data object OnAllow: BtnMenuSelectionEvents()
                data object OnDeny: BtnMenuSelectionEvents()
            }
            sealed class DialogEvents: UiEvents() {
                sealed class DlgDenyAccessEvents: DialogEvents() {
                    data class OnDismiss(val sessionState: SessionState): DlgDenyAccessEvents()
                }
            }
        }
    }

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    fun onEvent(events: UiEvents) {
        when(events) {
            is OnOpenEvents -> {/*TODO*/}
            is BtnScrDescEvents.OnClick -> {/*TODO*/}
            is BtnScrDescEvents.OnDismiss -> {/*TODO*/}
            is DlgDenyAccessEvents.OnDismiss -> {/*TODO*/}
            is BtnMenuSelectionEvents.OnAllow -> {/*TODO*/}
            is BtnMenuSelectionEvents.OnDeny -> {/*TODO*/}
        }
    }
}
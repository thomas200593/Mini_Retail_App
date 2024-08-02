package com.thomas200593.mini_retail_app.features.app_conf.conf_gen_dynamic_color.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatchers.Dispatchers.IO
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState.Error
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState.Idle
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_dynamic_color.domain.UCGetConfDynamicColor
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_dynamic_color.entity.ConfigDynamicColor
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_dynamic_color.entity.DynamicColor
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_dynamic_color.repository.RepoConfGenDynamicColor
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_dynamic_color.ui.VMConfGenDynamicColor.UiEvents.BtnSelectDynamicColorEvents
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_dynamic_color.ui.VMConfGenDynamicColor.UiEvents.ButtonEvents.BtnNavBackEvents
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_dynamic_color.ui.VMConfGenDynamicColor.UiEvents.OnOpenEvents
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
class VMConfGenDynamicColor @Inject constructor(
    private val repoConfGenDynamicColor: RepoConfGenDynamicColor,
    private val ucGetConfDynamicColor: UCGetConfDynamicColor,
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher
): ViewModel() {
    data class UiState(
        val configDynamicColor: ResourceState<ConfigDynamicColor> = Idle,
        val dialogState: DialogState = DialogState()
    )
    data class DialogState(
        val dlgLoadDataEnabled : MutableState<Boolean> = mutableStateOf(false),
        val dlgLoadDataErrorEnabled : MutableState<Boolean> = mutableStateOf(false)
    )
    sealed class UiEvents {
        data object OnOpenEvents: UiEvents()
        sealed class ButtonEvents : UiEvents(){
            sealed class BtnNavBackEvents: ButtonEvents(){
                data object OnClick: BtnNavBackEvents()
            }
        }
        sealed class BtnSelectDynamicColorEvents: ButtonEvents(){
            data class OnClick(val dynamicColor: DynamicColor): BtnSelectDynamicColorEvents()
        }
    }

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    fun onEvent(events: UiEvents){
        when(events){
            OnOpenEvents -> onOpenEvent()
            BtnNavBackEvents.OnClick -> onBtnNavBackClicked()
            is BtnSelectDynamicColorEvents.OnClick -> onSaveSelectedDynamicColor(events.dynamicColor)
        }
    }
    private fun onOpenEvent() = viewModelScope.launch(ioDispatcher) {
        updateDialogState(
            dlgLoadDataEnabled = true,
            dlgLoadDataError = false
        )
        ucGetConfDynamicColor.invoke().flowOn(ioDispatcher)
            .catch { e ->
                _uiState.update { it.copy(configDynamicColor = Error(e)) }
                updateDialogState(
                    dlgLoadDataEnabled = false,
                    dlgLoadDataError = true
                )
            }
            .collect{ data ->
                _uiState.update { it.copy(configDynamicColor = data, dialogState = DialogState()) }
            }
    }
    private fun onBtnNavBackClicked() {
        _uiState.update { it.copy(dialogState = DialogState()) }
    }
    private fun onSaveSelectedDynamicColor(dynamicColor: DynamicColor) =
        viewModelScope.launch(ioDispatcher) { repoConfGenDynamicColor.setDynamicColor(dynamicColor) }
    private fun updateDialogState(
        dlgLoadDataEnabled: Boolean = false,
        dlgLoadDataError: Boolean = false
    ) {
        _uiState.update {
            it.copy(
                dialogState = it.dialogState.copy(
                    dlgLoadDataEnabled = mutableStateOf(dlgLoadDataEnabled),
                    dlgLoadDataErrorEnabled = mutableStateOf(dlgLoadDataError),
                )
            )
        }
    }
}
package com.thomas200593.mini_retail_app.features.app_conf.conf_gen_font_size.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.di.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatchers.Dispatchers.IO
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState.Error
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState.Idle
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_font_size.domain.UCGetConfFontSize
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_font_size.entity.ConfigFontSizes
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_font_size.entity.FontSize
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_font_size.repository.RepoConfGenFontSize
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_font_size.ui.VMConfGenFontSize.UiEvents.BtnSelectFontSizeEvents
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_font_size.ui.VMConfGenFontSize.UiEvents.ButtonEvents.BtnNavBackEvents
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_font_size.ui.VMConfGenFontSize.UiEvents.OnOpenEvents
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
class VMConfGenFontSize @Inject constructor(
    private val repoConfGenFontSize: RepoConfGenFontSize,
    private val ucGetConfFontSize: UCGetConfFontSize,
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher
): ViewModel() {
    data class UiState(
        val configFontSizes: ResourceState<ConfigFontSizes> = Idle,
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
        sealed class BtnSelectFontSizeEvents: ButtonEvents(){
            data class OnClick(val fontSize: FontSize): BtnSelectFontSizeEvents()
        }
    }

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    fun onEvent(events: UiEvents){
        when(events){
            OnOpenEvents -> onOpenEvent()
            BtnNavBackEvents.OnClick -> onBtnNavBackClicked()
            is BtnSelectFontSizeEvents.OnClick -> onSaveSelectedFontSize(events.fontSize)
        }
    }
    private fun onOpenEvent() = viewModelScope.launch(ioDispatcher) {
        updateDialogState(
            dlgLoadDataEnabled = true,
            dlgLoadDataError = false
        )
        ucGetConfFontSize.invoke().flowOn(ioDispatcher)
            .catch { e ->
                _uiState.update { it.copy(configFontSizes = Error(e)) }
                updateDialogState(
                    dlgLoadDataEnabled = false,
                    dlgLoadDataError = true
                )
            }
            .collect{ data ->
                _uiState.update { it.copy(configFontSizes = data, dialogState = DialogState()) }
            }
    }
    private fun onBtnNavBackClicked() {
        _uiState.update { it.copy(dialogState = DialogState()) }
    }
    private fun onSaveSelectedFontSize(fontSize: FontSize) =
        viewModelScope.launch(ioDispatcher) { repoConfGenFontSize.setFontSize(fontSize) }
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

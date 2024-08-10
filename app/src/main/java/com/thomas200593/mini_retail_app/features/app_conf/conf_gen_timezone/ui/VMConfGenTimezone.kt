package com.thomas200593.mini_retail_app.features.app_conf.conf_gen_timezone.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatchers.Dispatchers.IO
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.di.Dispatcher
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_timezone.domain.UCGetConfTimezone
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_timezone.entity.ConfigTimezones
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_timezone.entity.Timezone
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_timezone.repository.RepoConfGenTimezone
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_timezone.ui.VMConfGenTimezone.UiEvents.BtnSelectTimezoneEvents
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_timezone.ui.VMConfGenTimezone.UiEvents.ButtonEvents.BtnNavBackEvents
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_timezone.ui.VMConfGenTimezone.UiEvents.OnOpenEvents
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_timezone.ui.VMConfGenTimezone.UiStateConfigTimezone.Error
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_timezone.ui.VMConfGenTimezone.UiStateConfigTimezone.Loading
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_timezone.ui.VMConfGenTimezone.UiStateConfigTimezone.Success
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
class VMConfGenTimezone @Inject constructor(
    private val repoConfGenTimezone: RepoConfGenTimezone,
    private val ucGetConfTimezone: UCGetConfTimezone,
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher
): ViewModel() {
    sealed interface UiStateConfigTimezone{
        data object Loading: UiStateConfigTimezone
        data class Success(val configTimezone: ConfigTimezones): UiStateConfigTimezone
        data class Error(val t: Throwable): UiStateConfigTimezone
    }
    data class UiState(
        val configTimezone: UiStateConfigTimezone = Loading,
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
        sealed class BtnSelectTimezoneEvents: ButtonEvents(){
            data class OnClick(val timezone: Timezone): BtnSelectTimezoneEvents()
        }
    }

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    fun onEvent(events: UiEvents){
        when(events){
            OnOpenEvents -> onOpenEvent()
            BtnNavBackEvents.OnClick -> onBtnNavBackClicked()
            is BtnSelectTimezoneEvents.OnClick -> onSaveSelectedTimezone(events.timezone)
        }
    }
    private fun onOpenEvent() = viewModelScope.launch(ioDispatcher) {
        updateDialogState(
            dlgLoadDataEnabled = true,
            dlgLoadDataError = false
        )
        ucGetConfTimezone.invoke().flowOn(ioDispatcher)
            .catch { e -> _uiState.update { it.copy(configTimezone = Error(e)) }
                updateDialogState(
                    dlgLoadDataEnabled = false,
                    dlgLoadDataError = true
                )
            }
            .collect{ data ->
                _uiState.update { it.copy(configTimezone = Success(data.data), dialogState = DialogState()) }
            }
    }
    private fun onBtnNavBackClicked() =
        _uiState.update { it.copy(dialogState = DialogState()) }
    private fun onSaveSelectedTimezone(timezone: Timezone) =
        viewModelScope.launch(ioDispatcher){ repoConfGenTimezone.setTimezone(timezone) }
    private fun updateDialogState(
        dlgLoadDataEnabled: Boolean = false,
        dlgLoadDataError: Boolean = false
    ) {
        _uiState.update { it.copy(
            dialogState = it.dialogState.copy(
                dlgLoadDataEnabled = mutableStateOf(dlgLoadDataEnabled),
                dlgLoadDataErrorEnabled = mutableStateOf(dlgLoadDataError)
            )
        ) }
    }
}
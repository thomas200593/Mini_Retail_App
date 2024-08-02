package com.thomas200593.mini_retail_app.features.app_conf.conf_gen_currency.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatchers.Dispatchers.IO
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState.Error
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState.Idle
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_currency.domain.UCGetConfCurrency
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_currency.entity.ConfigCurrency
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_currency.entity.Currency
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_currency.repository.RepoConfGenCurrency
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_currency.ui.VMConfGenCurrency.UiEvents.BtnSelectCurrencyEvents
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_currency.ui.VMConfGenCurrency.UiEvents.ButtonEvents.BtnNavBackEvents
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_currency.ui.VMConfGenCurrency.UiEvents.OnOpenEvents
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
class VMConfGenCurrency @Inject constructor(
    private val repoConfGenCurrency: RepoConfGenCurrency,
    private val ucGetConfCurrency: UCGetConfCurrency,
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher
): ViewModel() {
    data class UiState(
        val configCurrency: ResourceState<ConfigCurrency> = Idle,
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
        sealed class BtnSelectCurrencyEvents: ButtonEvents(){
            data class OnClick(val currency: Currency): BtnSelectCurrencyEvents()
        }
    }

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    fun onEvent(events: UiEvents){
        when(events){
            OnOpenEvents -> onOpenEvent()
            BtnNavBackEvents.OnClick -> onBtnNavBackClicked()
            is BtnSelectCurrencyEvents.OnClick -> onSaveSelectedCurrency(events.currency)
        }
    }
    private fun onOpenEvent() = viewModelScope.launch(ioDispatcher) {
        updateDialogState(
            dlgLoadDataEnabled = true,
            dlgLoadDataError = false
        )
        ucGetConfCurrency.invoke().flowOn(ioDispatcher)
            .catch { e ->
                _uiState.update { it.copy(configCurrency = Error(e)) }
                updateDialogState(
                    dlgLoadDataEnabled = false,
                    dlgLoadDataError = true
                )
            }
            .collect{ data ->
                _uiState.update { it.copy(configCurrency = data, dialogState = DialogState()) }
            }
    }
    private fun onBtnNavBackClicked() {
        _uiState.update { it.copy(dialogState = DialogState()) }
    }
    private fun onSaveSelectedCurrency(currency: Currency) =
        viewModelScope.launch(ioDispatcher) { repoConfGenCurrency.setCurrency(currency) }
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
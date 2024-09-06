package com.thomas200593.mini_retail_app.features.app_conf.conf_gen_currency.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thomas200593.mini_retail_app.app.navigation.ScrGraphs
import com.thomas200593.mini_retail_app.core.data.local.session.SessionState
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatchers.Dispatchers.IO
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.di.Dispatcher
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_currency.domain.UCGetConfCurrency
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_currency.entity.ConfigCurrency
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_currency.entity.Currency
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_currency.repository.RepoConfGenCurrency
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_currency.ui.VMConfGenCurrency.UiEvents.ButtonEvents.BtnNavBackEvents
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_currency.ui.VMConfGenCurrency.UiEvents.ButtonEvents.BtnScrDescEvents
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_currency.ui.VMConfGenCurrency.UiEvents.ButtonEvents.BtnSetPrefCurrencyEvents
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_currency.ui.VMConfGenCurrency.UiEvents.DialogEvents.DlgDenySetDataEvents
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_currency.ui.VMConfGenCurrency.UiEvents.OnOpenEvents
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_currency.ui.VMConfGenCurrency.UiStateConfigCurrency.Loading
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_currency.ui.VMConfGenCurrency.UiStateConfigCurrency.Success
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VMConfGenCurrency @Inject constructor(
    private val ucGetConfCurrency: UCGetConfCurrency,
    private val repoConfGenCurrency: RepoConfGenCurrency,
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher
): ViewModel() {
    sealed interface UiStateConfigCurrency {
        data object Loading : UiStateConfigCurrency
        data class Success(val configCurrency: ConfigCurrency): UiStateConfigCurrency
    }
    data class UiState(
        val configCurrency: UiStateConfigCurrency = Loading,
        val dialogState: DialogState = DialogState()
    )
    data class DialogState(
        val dlgLoadingAuth: MutableState<Boolean> = mutableStateOf(false),
        val dlgLoadingGetData: MutableState<Boolean> = mutableStateOf(false),
        val dlgDenySetData: MutableState<Boolean> = mutableStateOf(false),
        val dlgScrDesc: MutableState<Boolean> = mutableStateOf(false)
    )
    sealed interface UiEvents {
        data class OnOpenEvents(
            val sessionState: SessionState,
            val currentScreen: ScrGraphs
        ) : UiEvents
        sealed interface ButtonEvents : UiEvents {
            sealed interface BtnNavBackEvents : ButtonEvents {
                data object OnClick : BtnNavBackEvents
            }
            sealed interface BtnScrDescEvents : ButtonEvents {
                data object OnClick : BtnScrDescEvents
                data object OnDismiss : BtnScrDescEvents
            }
            sealed interface BtnSetPrefCurrencyEvents : ButtonEvents {
                data class OnAllow(val currency: Currency) : BtnSetPrefCurrencyEvents
                data object OnDeny : BtnSetPrefCurrencyEvents
            }
        }
        sealed interface DialogEvents : UiEvents {
            sealed interface DlgDenySetDataEvents : DialogEvents {
                data object OnDismiss : DlgDenySetDataEvents
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
            is BtnSetPrefCurrencyEvents.OnDeny -> onDenySet()
            is BtnSetPrefCurrencyEvents.OnAllow -> onAllowSet(events.currency)
            is DlgDenySetDataEvents.OnDismiss -> resetDialogAndUiState()
        }
    }

    private fun updateDialogState(
        dlgLoadingAuth: Boolean = false,
        dlgLoadingGetData: Boolean = false,
        dlgDenySetData: Boolean = false,
        dlgScrDesc: Boolean = false
    ) = _uiState.update { it.copy(
        dialogState = it.dialogState.copy(
            dlgLoadingAuth = mutableStateOf(dlgLoadingAuth),
            dlgLoadingGetData = mutableStateOf(dlgLoadingGetData),
            dlgDenySetData = mutableStateOf(dlgDenySetData),
            dlgScrDesc = mutableStateOf(dlgScrDesc)
        )
    ) }
    private fun resetDialogState() = _uiState.update { it.copy(dialogState = DialogState()) }
    private fun resetUiStateConfigCurrency() = _uiState.update { it.copy(configCurrency = Loading) }
    private fun resetDialogAndUiState() { resetDialogState(); resetUiStateConfigCurrency() }
    private fun onOpenEvent(sessionState: SessionState, currentScreen: ScrGraphs) {
        resetUiStateConfigCurrency(); resetDialogState()
        when(sessionState) {
            SessionState.Loading -> updateDialogState(dlgLoadingAuth = true)
            is SessionState.Invalid -> if(currentScreen.usesAuth) onDenySet()
            else viewModelScope.launch {
                resetUiStateConfigCurrency(); updateDialogState(dlgLoadingGetData = true)
                ucGetConfCurrency.invoke().flowOn(ioDispatcher).collect{ data ->
                    _uiState.update {
                        it.copy(
                            configCurrency = Success(data),
                            dialogState = DialogState()
                        )
                    }
                }
            }
            is SessionState.Valid -> viewModelScope.launch {
                resetUiStateConfigCurrency(); updateDialogState(dlgLoadingGetData = true)
                ucGetConfCurrency.invoke().flowOn(ioDispatcher).collect{ data ->
                    _uiState.update {
                        it.copy(
                            configCurrency = Success(data),
                            dialogState = DialogState()
                        )
                    }
                }
            }
        }
    }
    private fun onDenySet() { resetDialogState(); updateDialogState(dlgDenySetData = true) }
    private fun onAllowSet(currency: Currency) {
        resetDialogState()
        viewModelScope.launch { repoConfGenCurrency.setCurrency(currency = currency) }
    }
}
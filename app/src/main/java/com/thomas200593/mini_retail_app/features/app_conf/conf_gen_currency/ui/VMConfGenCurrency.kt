package com.thomas200593.mini_retail_app.features.app_conf.conf_gen_currency.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.thomas200593.mini_retail_app.app.navigation.ScrGraphs
import com.thomas200593.mini_retail_app.core.data.local.session.SessionState
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_currency.entity.ConfigCurrency
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_currency.ui.VMConfGenCurrency.UiEvents.OnOpenEvents
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_currency.ui.VMConfGenCurrency.UiStateConfigCurrency.Loading
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class VMConfGenCurrency @Inject constructor(

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

    sealed class UiEvents {
        data class OnOpenEvents(
            val sessionState: SessionState,
            val currentScreen: ScrGraphs
        ) : UiEvents()
    }

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    fun onEvent(events: UiEvents) {
        when(events) {
            is OnOpenEvents -> {/*TODO*/}
        }
    }
}

/*
@HiltViewModel
class VMConfGenCurrency @Inject constructor(
    private val repoConfGenCurrency: RepoConfGenCurrency,
    private val ucGetConfCurrency: UCGetConfCurrency,
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher
): ViewModel() {
    sealed interface UiStateConfigCurrency{
        data object Loading: UiStateConfigCurrency
        data class Success(val configCurrency: ConfigCurrency): UiStateConfigCurrency
        data class Error(val t: Throwable): UiStateConfigCurrency
    }
    data class UiState(
        val configCurrency: UiStateConfigCurrency = Loading,
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
            .catch { e -> _uiState.update { it.copy(configCurrency = Error(e)) }
                updateDialogState(
                    dlgLoadDataEnabled = false,
                    dlgLoadDataError = true
                )
            }
            .collect{ result ->
                _uiState.update { it.copy(configCurrency = Success(result.data), dialogState = DialogState()) }
            }
    }
    private fun onBtnNavBackClicked() = _uiState.update { it.copy(dialogState = DialogState()) }
    private fun onSaveSelectedCurrency(currency: Currency) =
        viewModelScope.launch(ioDispatcher) { repoConfGenCurrency.setCurrency(currency) }
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
}*/

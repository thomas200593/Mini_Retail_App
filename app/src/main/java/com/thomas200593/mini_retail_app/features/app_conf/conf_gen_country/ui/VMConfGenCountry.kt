package com.thomas200593.mini_retail_app.features.app_conf.conf_gen_country.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thomas200593.mini_retail_app.app.navigation.ScrGraphs
import com.thomas200593.mini_retail_app.core.data.local.session.SessionState
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatchers.Dispatchers.IO
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.di.Dispatcher
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_country.domain.UCGetConfCountry
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_country.entity.ConfigCountry
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_country.entity.Country
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_country.repository.RepoConfGenCountry
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_country.ui.VMConfGenCountry.UiEvents.ButtonEvents.BtnNavBackEvents
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_country.ui.VMConfGenCountry.UiEvents.ButtonEvents.BtnScrDescEvents
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_country.ui.VMConfGenCountry.UiEvents.ButtonEvents.BtnSetPrefCountryEvents
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_country.ui.VMConfGenCountry.UiEvents.DialogEvents.DlgDenySetDataEvents
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_country.ui.VMConfGenCountry.UiEvents.OnOpenEvents
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_country.ui.VMConfGenCountry.UiStateConfigCountry.Loading
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_country.ui.VMConfGenCountry.UiStateConfigCountry.Success
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VMConfGenCountry @Inject constructor(
    private val ucGetConfCountry: UCGetConfCountry,
    private val repoConfGenCountry: RepoConfGenCountry,
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {
    sealed interface UiStateConfigCountry {
        data object Loading : UiStateConfigCountry
        data class Success(val configCountry: ConfigCountry) : UiStateConfigCountry
    }
    data class UiState(
        val configCountry: UiStateConfigCountry = Loading,
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
            sealed interface BtnSetPrefCountryEvents : ButtonEvents {
                data class OnAllow(val country: Country) : BtnSetPrefCountryEvents
                data object OnDeny : BtnSetPrefCountryEvents
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
        when (events) {
            is OnOpenEvents -> onOpenEvent(events.sessionState, events.currentScreen)
            is BtnNavBackEvents.OnClick -> resetDialogAndUiState()
            is BtnScrDescEvents.OnClick ->
                { resetDialogState(); updateDialogState(dlgScrDesc = true) }
            is BtnScrDescEvents.OnDismiss -> resetDialogState()
            is BtnSetPrefCountryEvents.OnDeny -> onDenySet()
            is BtnSetPrefCountryEvents.OnAllow -> onAllowSet(events.country)
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
    private fun resetUiStateConfigCountry() = _uiState.update { it.copy(configCountry = Loading) }
    private fun resetDialogAndUiState() { resetDialogState(); resetUiStateConfigCountry() }
    private fun onOpenEvent(sessionState: SessionState, currentScreen: ScrGraphs) {
        resetUiStateConfigCountry(); resetDialogState()
        when (sessionState) {
            SessionState.Loading -> updateDialogState(dlgLoadingAuth = true)
            is SessionState.Invalid -> if (currentScreen.usesAuth) onDenySet()
            else viewModelScope.launch {
                resetUiStateConfigCountry(); updateDialogState(dlgLoadingGetData = true)
                ucGetConfCountry.invoke().flowOn(ioDispatcher).collect { data ->
                    _uiState.update {
                        it.copy(
                            configCountry = Success(data),
                            dialogState = DialogState()
                        )
                    }
                }
            }
            is SessionState.Valid -> viewModelScope.launch {
                resetUiStateConfigCountry(); updateDialogState(dlgLoadingGetData = true)
                ucGetConfCountry.invoke().flowOn(ioDispatcher).collect { data ->
                    _uiState.update {
                        it.copy(
                            configCountry = Success(data),
                            dialogState = DialogState()
                        )
                    }
                }
            }
        }
    }
    private fun onDenySet() { resetDialogState(); updateDialogState(dlgDenySetData = true) }
    private fun onAllowSet(country: Country) {
        resetDialogState()
        viewModelScope.launch { repoConfGenCountry.setCountry(country = country) }
    }
}
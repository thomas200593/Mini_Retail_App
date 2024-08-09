package com.thomas200593.mini_retail_app.features.app_conf.conf_gen_country.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thomas200593.mini_retail_app.core.data.local.session.SessionState
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.di.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatchers.Dispatchers.IO
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState.Error
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState.Idle
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_country.domain.UCGetConfCountry
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_country.entity.ConfigCountry
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_country.entity.Country
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_country.repository.RepoConfGenCountry
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_country.ui.VMConfGenCountry.UiEvents.ButtonEvents.BtnNavBackEvents
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_country.ui.VMConfGenCountry.UiEvents.ButtonEvents.BtnSelectCountryEvents
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_country.ui.VMConfGenCountry.UiEvents.OnOpenEvents
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
        val configCountry: ResourceState<ConfigCountry> = Idle,
        val dialogState: DialogState = DialogState()
    )
    data class DialogState(
        val dlgVldAuthEnabled : MutableState<Boolean> = mutableStateOf(false),
        val dlgLoadDataEnabled : MutableState<Boolean> = mutableStateOf(false),
        val dlgLoadDataErrorEnabled : MutableState<Boolean> = mutableStateOf(false),
        val dlgDenyAccessEnabled: MutableState<Boolean> = mutableStateOf(false),
        val dlgDenySaveDataEnabled : MutableState<Boolean> = mutableStateOf(false)
    )
    sealed class UiEvents {
        data class OnOpenEvents(val sessionState: SessionState): UiEvents()
        sealed class ButtonEvents : UiEvents(){
            sealed class BtnNavBackEvents: ButtonEvents(){
                data object OnClick: BtnNavBackEvents()
            }
            sealed class BtnSelectCountryEvents: ButtonEvents(){
                data class OnClick(val sessionState: SessionState, val country: Country): BtnSelectCountryEvents()
            }
        }
    }

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    fun onEvent(events: UiEvents) {
        when(events){
            is OnOpenEvents -> onOpenEvent(events.sessionState)
            BtnNavBackEvents.OnClick -> onBtnNavBackClicked()
            is BtnSelectCountryEvents.OnClick ->
                onSaveSelectedCountry(events.sessionState, events.country)
        }
    }
    private fun onOpenEvent(sessionState: SessionState) {
        when(sessionState){
            SessionState.Loading -> {
                updateDialogState(
                    dlgVldAuthEnabled = true,
                    dlgLoadDataEnabled = false,
                    dlgLoadDataError = false,
                    dlgDenyAccessEnabled = false,
                    dlgDenySaveDataEnabled = false
                )
            }
            is SessionState.Invalid -> {
                updateDialogState(
                    dlgVldAuthEnabled = false,
                    dlgLoadDataEnabled = false,
                    dlgLoadDataError = false,
                    dlgDenyAccessEnabled = true,
                    dlgDenySaveDataEnabled = false
                )
            }
            is SessionState.Valid -> viewModelScope.launch(ioDispatcher) {
                updateDialogState(
                    dlgVldAuthEnabled = false,
                    dlgLoadDataEnabled = true,
                    dlgLoadDataError = false,
                    dlgDenyAccessEnabled = false,
                    dlgDenySaveDataEnabled = false
                )
                ucGetConfCountry.invoke().flowOn(ioDispatcher)
                    .catch { e ->
                        _uiState.update { it.copy(configCountry = Error(e)) }
                        updateDialogState(
                            dlgVldAuthEnabled = false,
                            dlgLoadDataEnabled = false,
                            dlgLoadDataError = true,
                            dlgDenyAccessEnabled = false,
                            dlgDenySaveDataEnabled = false
                        )
                    }
                    .collect{ data ->
                        _uiState.update { it.copy(configCountry = data, dialogState = DialogState()) }
                    }
            }
        }
    }
    private fun onBtnNavBackClicked() {
        _uiState.update { it.copy(dialogState = DialogState()) }
    }
    private fun onSaveSelectedCountry(sessionState: SessionState, country: Country) {
        when(sessionState){
            SessionState.Loading -> Unit
            is SessionState.Invalid -> {
                updateDialogState(
                    dlgVldAuthEnabled = false,
                    dlgLoadDataEnabled = false,
                    dlgLoadDataError = false,
                    dlgDenyAccessEnabled = false,
                    dlgDenySaveDataEnabled = true
                )
            }
            is SessionState.Valid ->
                viewModelScope.launch(ioDispatcher) { repoConfGenCountry.setCountry(country) }
        }
    }
    private fun updateDialogState(
        dlgVldAuthEnabled: Boolean = true,
        dlgLoadDataEnabled: Boolean = false,
        dlgLoadDataError: Boolean = false,
        dlgDenyAccessEnabled: Boolean = false,
        dlgDenySaveDataEnabled: Boolean = false
    ) {
        _uiState.update {
            it.copy(
                dialogState = it.dialogState.copy(
                    dlgVldAuthEnabled = mutableStateOf(dlgVldAuthEnabled),
                    dlgLoadDataEnabled = mutableStateOf(dlgLoadDataEnabled),
                    dlgLoadDataErrorEnabled = mutableStateOf(dlgLoadDataError),
                    dlgDenyAccessEnabled = mutableStateOf(dlgDenyAccessEnabled),
                    dlgDenySaveDataEnabled = mutableStateOf(dlgDenySaveDataEnabled)
                )
            )
        }
    }
}
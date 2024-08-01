package com.thomas200593.mini_retail_app.features.app_conf.conf_gen_country.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thomas200593.mini_retail_app.core.data.local.session.SessionState
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatchers.Dispatchers.IO
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState.Idle
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_country.domain.UCGetConfCountry
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_country.entity.ConfigCountry
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_country.repository.RepoConfGenCountry
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_country.ui.VMConfGenCountry.UiEvents.ButtonEvents.BtnNavBackEvents
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_country.ui.VMConfGenCountry.UiEvents.ButtonEvents.BtnSelectCountryEvents
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_country.ui.VMConfGenCountry.UiEvents.OnOpenEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
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
        val dlgDenySaveDataEnabled : MutableState<Boolean> = mutableStateOf(false)
    )
    sealed class UiEvents {
        data class OnOpenEvents(val sessionState: SessionState): UiEvents()
        sealed class ButtonEvents : UiEvents(){
            sealed class BtnNavBackEvents: ButtonEvents(){
                data object OnClick: BtnNavBackEvents()
            }
            sealed class BtnSelectCountryEvents: ButtonEvents(){
                data class OnClick(val sessionState: SessionState): BtnSelectCountryEvents()
            }
        }
    }

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    fun onEvent(events: UiEvents) = viewModelScope.launch(ioDispatcher){
        when(events){
            is OnOpenEvents -> TODO()
            BtnNavBackEvents.OnClick -> TODO()
            is BtnSelectCountryEvents.OnClick -> TODO()
        }
    }
    private fun updateDialogState(
        dlgVldAuthEnabled: Boolean = true,
        dlgLoadDataEnabled: Boolean = false,
        dlgDenySaveDataEnabled: Boolean = false
    ) {
        _uiState.update {
            it.copy(

            )
        }
    }
}
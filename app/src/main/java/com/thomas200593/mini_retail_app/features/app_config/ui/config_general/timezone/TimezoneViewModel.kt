package com.thomas200593.mini_retail_app.features.app_config.ui.config_general.timezone

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thomas200593.mini_retail_app.core.design_system.dispatchers.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.dispatchers.Dispatchers
import com.thomas200593.mini_retail_app.core.design_system.util.RequestState
import com.thomas200593.mini_retail_app.features.app_config.entity.Timezone
import com.thomas200593.mini_retail_app.features.app_config.repository.AppConfigRepository
import com.thomas200593.mini_retail_app.features.app_config.repository.ConfigGeneralRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TimezoneViewModel @Inject constructor(
    appCfgRepository: AppConfigRepository,
    private val cfgGeneralRepository: ConfigGeneralRepository,
    @Dispatcher(Dispatchers.Dispatchers.IO) private val ioDispatcher: CoroutineDispatcher
): ViewModel() {
    private val _timezones: MutableState<RequestState<List<Timezone>>> = mutableStateOf(RequestState.Idle)
    val timezones = _timezones
    val configCurrent = appCfgRepository.configCurrent.flowOn(ioDispatcher)
        .catch { RequestState.Error(it) }
        .map { RequestState.Success(it) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = RequestState.Loading
        )

    fun onOpen() = viewModelScope.launch(ioDispatcher) {
        getTimezones()
    }

    private fun getTimezones() = viewModelScope.launch(ioDispatcher) {
        _timezones.value = RequestState.Loading
        _timezones.value = try{ RequestState.Success(cfgGeneralRepository.getTimezones()) }
        catch (e: Throwable){ RequestState.Error(e) }
    }

    fun setTimezone(timezone: Timezone) = viewModelScope.launch {
        cfgGeneralRepository.setTimezone(timezone)
    }
}

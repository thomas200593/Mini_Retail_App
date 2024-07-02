package com.thomas200593.mini_retail_app.features.app_config.ui.config_general.dynamic_color

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thomas200593.mini_retail_app.core.design_system.dispatchers.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.dispatchers.Dispatchers
import com.thomas200593.mini_retail_app.core.design_system.util.RequestState
import com.thomas200593.mini_retail_app.features.app_config.entity.DynamicColor
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
class DynamicColorViewModel @Inject constructor(
    appCfgRepository: AppConfigRepository,
    private val cfgGeneralRepository: ConfigGeneralRepository,
    @Dispatcher(Dispatchers.Dispatchers.IO) private val ioDispatcher: CoroutineDispatcher
): ViewModel() {
    private val _dynamicColors: MutableState<RequestState<Set<DynamicColor>>> = mutableStateOf(RequestState.Idle)
    val dynamicColors = _dynamicColors
    val configCurrent = appCfgRepository.configCurrent.flowOn(ioDispatcher)
        .catch { RequestState.Error(it) }
        .map { RequestState.Success(it) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = RequestState.Loading
        )

    fun onOpen() = viewModelScope.launch(ioDispatcher) {
        getDynamicColors()
    }

    private fun getDynamicColors() = viewModelScope.launch(ioDispatcher){
        _dynamicColors.value = RequestState.Loading
        _dynamicColors.value = try { RequestState.Success(cfgGeneralRepository.getDynamicColors()) }
        catch (e: Throwable){ RequestState.Error(e) }
    }

    fun setDynamicColor(dynamicColor: DynamicColor) = viewModelScope.launch {
        cfgGeneralRepository.setDynamicColor(dynamicColor)
    }
}

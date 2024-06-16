package com.thomas200593.mini_retail_app.features.app_config.ui.components.general_config.dynamic_color

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thomas200593.mini_retail_app.core.design_system.dispatchers.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.dispatchers.Dispatchers
import com.thomas200593.mini_retail_app.core.design_system.dispatchers.Dispatchers.Dispatchers.*
import com.thomas200593.mini_retail_app.core.design_system.util.RequestState
import com.thomas200593.mini_retail_app.core.ui.common.Icons
import com.thomas200593.mini_retail_app.features.app_config.entity.DynamicColor
import com.thomas200593.mini_retail_app.features.app_config.repository.AppConfigRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AppConfigGeneralDynamicColorViewModel @Inject constructor(
    private val appConfigRepository: AppConfigRepository,
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher
): ViewModel() {
    private val _dynamicColorPreferences: MutableState<RequestState<Set<DynamicColor>>> =
        mutableStateOf(RequestState.Idle)
    val dynamicColorPreferences = _dynamicColorPreferences
    val configCurrentUiState = appConfigRepository.configCurrentData.flowOn(ioDispatcher)
        .onEach { Timber.d("Config Current State: $it") }
        .catch { RequestState.Error(it) }
        .map { RequestState.Success(it) }
        .stateIn(
            scope = viewModelScope,
            SharingStarted.Eagerly,
            initialValue = RequestState.Loading
        )

    fun onOpen() = viewModelScope.launch(ioDispatcher) {
        getDynamicColorPreferences()
    }

    private fun getDynamicColorPreferences() = viewModelScope.launch(ioDispatcher){
        _dynamicColorPreferences.value = RequestState.Loading
        _dynamicColorPreferences.value = try {
            RequestState.Success(appConfigRepository.getDynamicMenuPreferences())
        }catch (e: Throwable){
            RequestState.Error(e)
        }
    }

    fun saveSelectedDynamicColor(dynamicColor: DynamicColor) = viewModelScope.launch {
        appConfigRepository.setDynamicColorPreferences(dynamicColor)
    }
}

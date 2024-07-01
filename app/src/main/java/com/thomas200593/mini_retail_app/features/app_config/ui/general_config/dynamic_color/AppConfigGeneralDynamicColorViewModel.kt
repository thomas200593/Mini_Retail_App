package com.thomas200593.mini_retail_app.features.app_config.ui.general_config.dynamic_color

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thomas200593.mini_retail_app.core.design_system.dispatchers.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.dispatchers.Dispatchers.Dispatchers.IO
import com.thomas200593.mini_retail_app.core.design_system.util.RequestState
import com.thomas200593.mini_retail_app.features.app_config.entity.DynamicColor
import com.thomas200593.mini_retail_app.features.app_config.repository.ConfigGeneralRepository
import com.thomas200593.mini_retail_app.features.app_config.repository.AppConfigRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

private val TAG = AppConfigGeneralDynamicColorViewModel::class.simpleName

@HiltViewModel
class AppConfigGeneralDynamicColorViewModel @Inject constructor(
    appConfigRepository: AppConfigRepository,
    private val configGeneralRepository: ConfigGeneralRepository,
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher
): ViewModel() {
    private val _dynamicColorPreferences: MutableState<RequestState<Set<DynamicColor>>> =
        mutableStateOf(RequestState.Idle)
    val dynamicColorPreferences = _dynamicColorPreferences
    val configCurrentUiState = appConfigRepository.configCurrentData.flowOn(ioDispatcher)
        .catch { RequestState.Error(it) }
        .map { RequestState.Success(it) }
        .stateIn(
            scope = viewModelScope,
            SharingStarted.Eagerly,
            initialValue = RequestState.Loading
        )

    fun onOpen() = viewModelScope.launch(ioDispatcher) {
        Timber.d("Called : fun $TAG.onOpen()")
        getDynamicColorPreferences()
    }

    private fun getDynamicColorPreferences() = viewModelScope.launch(ioDispatcher){
        Timber.d("Called : fun $TAG.getDynamicColorPreferences()")
        _dynamicColorPreferences.value = RequestState.Loading
        _dynamicColorPreferences.value = try {
            RequestState.Success(configGeneralRepository.getDynamicMenuPreferences())
        }catch (e: Throwable){
            RequestState.Error(e)
        }
    }

    fun saveSelectedDynamicColor(dynamicColor: DynamicColor) = viewModelScope.launch {
        Timber.d("Called : fun $TAG.saveSelectedDynamicColor()")
        configGeneralRepository.setDynamicColorPreferences(dynamicColor)
    }
}

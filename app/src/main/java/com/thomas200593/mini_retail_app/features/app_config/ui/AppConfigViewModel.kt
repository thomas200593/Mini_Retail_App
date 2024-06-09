package com.thomas200593.mini_retail_app.features.app_config.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thomas200593.mini_retail_app.core.design_system.dispatchers.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.dispatchers.Dispatchers
import com.thomas200593.mini_retail_app.core.design_system.util.RequestState
import com.thomas200593.mini_retail_app.core.design_system.util.RequestState.Loading
import com.thomas200593.mini_retail_app.features.app_config.entity.CurrentGeneralAppConfig
import com.thomas200593.mini_retail_app.features.app_config.entity.GeneralAppConfigMenu
import com.thomas200593.mini_retail_app.features.app_config.entity.getGeneralAppConfigMenu
import com.thomas200593.mini_retail_app.features.app_config.repository.AppConfigRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AppConfigViewModel @Inject constructor(
    private val appConfigRepository: AppConfigRepository,
    @Dispatcher(Dispatchers.Dispatchers.IO) private val ioDispatcher: CoroutineDispatcher
): ViewModel(){

    private val _currentGeneralSettingUiState:MutableState<RequestState<CurrentGeneralAppConfig>> =
        mutableStateOf(RequestState.Idle)
    val currentGeneralConfigUiState = _currentGeneralSettingUiState

    private val _generalSettingMenuUiState: MutableState<RequestState<Set<GeneralAppConfigMenu>>> =
        mutableStateOf(RequestState.Idle)
    val generalSettingMenuUiState = _generalSettingMenuUiState

    suspend fun onOpen() = viewModelScope.launch(ioDispatcher) {
        loadGeneralSettings()
        loadGeneralSettingsMenu()
    }

    private suspend fun loadGeneralSettingsMenu() = viewModelScope.launch {
        _generalSettingMenuUiState.value = Loading
        viewModelScope.launch {
            _generalSettingMenuUiState.value = RequestState.Success(getGeneralAppConfigMenu())
        }
    }

    private suspend fun loadGeneralSettings() = viewModelScope.launch {
        _currentGeneralSettingUiState.value = Loading
        appConfigRepository.currentGeneralAppConfigData
            .flowOn(ioDispatcher)
            .onEach {
                Timber.d("CurrentGeneralAppConfig: $it")
            }
            .catch {
                _currentGeneralSettingUiState.value = RequestState.Error(it)
            }
            .collect {
                _currentGeneralSettingUiState.value = RequestState.Success(it)
            }
    }
}

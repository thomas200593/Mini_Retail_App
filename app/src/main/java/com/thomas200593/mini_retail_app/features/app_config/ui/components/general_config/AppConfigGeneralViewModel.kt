package com.thomas200593.mini_retail_app.features.app_config.ui.components.general_config

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thomas200593.mini_retail_app.core.design_system.dispatchers.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.dispatchers.Dispatchers
import com.thomas200593.mini_retail_app.core.design_system.util.RequestState
import com.thomas200593.mini_retail_app.features.app_config.navigation.ConfigGeneralDestination
import com.thomas200593.mini_retail_app.features.app_config.repository.AppConfigRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppConfigGeneralViewModel @Inject constructor(
    private val appConfigRepository: AppConfigRepository,
    @Dispatcher(Dispatchers.Dispatchers.IO) private val ioDispatcher: CoroutineDispatcher
): ViewModel() {
    private val _configGeneralDestinationUiState: MutableState<RequestState<Set<ConfigGeneralDestination>>> =
        mutableStateOf(RequestState.Idle)
    val appConfigGeneralMenuUiState = _configGeneralDestinationUiState

    fun onOpen() = viewModelScope.launch(ioDispatcher) {
        getAppConfigGeneralMenuData()
    }

    private suspend fun getAppConfigGeneralMenuData() {
        _configGeneralDestinationUiState.value = RequestState
            .Success(appConfigRepository.getAppConfigGeneralMenuData())
    }
}

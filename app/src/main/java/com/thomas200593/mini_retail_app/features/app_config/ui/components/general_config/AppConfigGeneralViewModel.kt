package com.thomas200593.mini_retail_app.features.app_config.ui.components.general_config

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thomas200593.mini_retail_app.core.design_system.dispatchers.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.dispatchers.Dispatchers
import com.thomas200593.mini_retail_app.core.design_system.util.RequestState
import com.thomas200593.mini_retail_app.features.app_config.entity.AppConfigGeneralMenu
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
    private val _appConfigGeneralMenuUiState: MutableState<RequestState<Set<AppConfigGeneralMenu>>> =
        mutableStateOf(RequestState.Idle)
    val appConfigGeneralMenuUiState = _appConfigGeneralMenuUiState

    fun onOpen() = viewModelScope.launch(ioDispatcher) {
        getAppConfigGeneralMenuData()
    }

    private suspend fun getAppConfigGeneralMenuData() {
        _appConfigGeneralMenuUiState.value = RequestState
            .Success(appConfigRepository.getAppConfigGeneralMenuData())
    }
}

package com.thomas200593.mini_retail_app.features.app_config.ui.config_general

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thomas200593.mini_retail_app.core.data.local.session.SessionState
import com.thomas200593.mini_retail_app.core.design_system.dispatchers.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.dispatchers.Dispatchers
import com.thomas200593.mini_retail_app.core.design_system.util.RequestState
import com.thomas200593.mini_retail_app.features.app_config.navigation.DestinationConfigGeneral
import com.thomas200593.mini_retail_app.features.app_config.repository.ConfigGeneralRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConfigGeneralViewModel @Inject constructor(
    private val cfgGeneralRepository: ConfigGeneralRepository,
    @Dispatcher(Dispatchers.Dispatchers.IO) private val ioDispatcher: CoroutineDispatcher
): ViewModel() {
    private val _menuData: MutableState<RequestState<Set<DestinationConfigGeneral>>> = mutableStateOf(RequestState.Idle)
    val menuData = _menuData

    fun onOpen(sessionState: SessionState) = viewModelScope.launch(ioDispatcher) {
        getMenuData(sessionState)
    }

    private suspend fun getMenuData(sessionState: SessionState) = viewModelScope.launch(ioDispatcher) {
        _menuData.value = RequestState.Loading
        _menuData.value = try{ RequestState.Success(cfgGeneralRepository.getMenuData(sessionState)) }
        catch (e: Throwable){ RequestState.Error(e) }
    }
}
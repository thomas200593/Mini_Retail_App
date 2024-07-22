package com.thomas200593.mini_retail_app.features.app_config.app_cfg.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thomas200593.mini_retail_app.core.data.local.session.SessionState
import com.thomas200593.mini_retail_app.core.design_system.dispatchers.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.dispatchers.Dispatchers
import com.thomas200593.mini_retail_app.core.design_system.util.RequestState
import com.thomas200593.mini_retail_app.features.app_config.app_cfg.navigation.DestinationAppConfig
import com.thomas200593.mini_retail_app.features.app_config.app_cfg.repository.AppConfigRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppConfigViewModel @Inject constructor(
    private val appCfgRepository: AppConfigRepository,
    @Dispatcher(Dispatchers.Dispatchers.IO) private val ioDispatcher: CoroutineDispatcher
): ViewModel(){
    private val _menuData: MutableState<RequestState<Set<DestinationAppConfig>>> = mutableStateOf(RequestState.Idle)
    val menuData = _menuData

    fun onOpen(sessionState: SessionState) = viewModelScope.launch(ioDispatcher) {
        getMenuData(sessionState)
    }

    private suspend fun getMenuData(sessionState: SessionState) = viewModelScope.launch(ioDispatcher){
        _menuData.value = RequestState.Loading
        _menuData.value = try { RequestState.Success(appCfgRepository.getMenuData(sessionState)) }
        catch (e: Throwable){ RequestState.Error(e) }
    }
}
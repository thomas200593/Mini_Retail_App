package com.thomas200593.mini_retail_app.features.app_config.ui.components.general_config

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thomas200593.mini_retail_app.core.design_system.dispatchers.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.dispatchers.Dispatchers
import com.thomas200593.mini_retail_app.core.design_system.util.RequestState
import com.thomas200593.mini_retail_app.features.app_config.navigation.AppConfigGeneralDestination
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
    private val _generalMenuPreferences: MutableState<RequestState<Set<AppConfigGeneralDestination>>> = mutableStateOf(RequestState.Idle)
    val generalMenuPreferences = _generalMenuPreferences

    fun onOpen() = viewModelScope.launch(ioDispatcher) {
        getGeneralMenuPreferences()
    }

    private suspend fun getGeneralMenuPreferences() = viewModelScope.launch(ioDispatcher) {
        _generalMenuPreferences.value = RequestState.Loading
        _generalMenuPreferences.value = try{
            RequestState.Success(appConfigRepository.getAppConfigGeneralMenuData(usesAuth = null))
        }catch (e: Throwable){
            RequestState.Error(e)
        }
    }

}

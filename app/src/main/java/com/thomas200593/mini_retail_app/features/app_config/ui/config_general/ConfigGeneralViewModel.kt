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
import timber.log.Timber
import javax.inject.Inject

private val TAG = ConfigGeneralViewModel::class.simpleName

@HiltViewModel
class ConfigGeneralViewModel @Inject constructor(
    private val configGeneralRepository: ConfigGeneralRepository,
    @Dispatcher(Dispatchers.Dispatchers.IO) private val ioDispatcher: CoroutineDispatcher
): ViewModel() {
    private val _appConfigGeneralMenuPreferences: MutableState<RequestState<Set<DestinationConfigGeneral>>> = mutableStateOf(RequestState.Idle)
    val appConfigGeneralMenuPreferences = _appConfigGeneralMenuPreferences

    fun onOpen(sessionState: SessionState) = viewModelScope.launch(ioDispatcher) {
        Timber.d("Called : fun $TAG.onOpen()")
        getAppConfigGeneralMenuPreferences(sessionState)
    }

    private suspend fun getAppConfigGeneralMenuPreferences(sessionState: SessionState) = viewModelScope.launch(ioDispatcher) {
        Timber.d("Called : fun $TAG.getAppConfigGeneralMenuPreferences()")
        _appConfigGeneralMenuPreferences.value = RequestState.Loading
        _appConfigGeneralMenuPreferences.value = try{
            RequestState.Success(configGeneralRepository.getMenuData(sessionState))
        }catch (e: Throwable){
            RequestState.Error(e)
        }
    }
}
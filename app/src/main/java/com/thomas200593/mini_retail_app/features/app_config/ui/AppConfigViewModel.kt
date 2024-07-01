package com.thomas200593.mini_retail_app.features.app_config.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thomas200593.mini_retail_app.core.data.local.session.SessionState
import com.thomas200593.mini_retail_app.core.design_system.dispatchers.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.dispatchers.Dispatchers
import com.thomas200593.mini_retail_app.core.design_system.util.RequestState
import com.thomas200593.mini_retail_app.features.app_config.navigation.DestinationAppConfig
import com.thomas200593.mini_retail_app.features.app_config.repository.AppConfigRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

private val TAG = AppConfigViewModel::class.simpleName

@HiltViewModel
class AppConfigViewModel @Inject constructor(
    private val appConfigRepository: AppConfigRepository,
    @Dispatcher(Dispatchers.Dispatchers.IO) private val ioDispatcher: CoroutineDispatcher
): ViewModel(){
    private val _appConfigMenuPreferences: MutableState<RequestState<Set<DestinationAppConfig>>> = mutableStateOf(RequestState.Idle)
    val appConfigMenuPreferences = _appConfigMenuPreferences

    fun onOpen(sessionState: SessionState) = viewModelScope.launch(ioDispatcher) {
        Timber.d("Called : fun $TAG.onOpen()")
        getAppConfigMenuPreferences(sessionState)
    }

    private suspend fun getAppConfigMenuPreferences(sessionState: SessionState) = viewModelScope.launch(ioDispatcher){
        Timber.d("Called : fun $TAG.getAppConfigMenuPreferences()")
        _appConfigMenuPreferences.value = RequestState.Loading
        _appConfigMenuPreferences.value = try {
            RequestState.Success(appConfigRepository.getMenuData(sessionState))
        }catch (e: Throwable){
            RequestState.Error(e)
        }
    }
}
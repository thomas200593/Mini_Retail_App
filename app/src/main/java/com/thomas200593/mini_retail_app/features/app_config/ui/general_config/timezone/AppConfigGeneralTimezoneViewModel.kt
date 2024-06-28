package com.thomas200593.mini_retail_app.features.app_config.ui.general_config.timezone

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thomas200593.mini_retail_app.core.design_system.dispatchers.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.dispatchers.Dispatchers
import com.thomas200593.mini_retail_app.core.design_system.util.RequestState
import com.thomas200593.mini_retail_app.features.app_config.entity.Timezone
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

private val TAG = AppConfigGeneralTimezoneViewModel::class.simpleName

@HiltViewModel
class AppConfigGeneralTimezoneViewModel @Inject constructor(
    private val appConfigRepository: AppConfigRepository,
    @Dispatcher(Dispatchers.Dispatchers.IO) private val ioDispatcher: CoroutineDispatcher
): ViewModel() {
    private val _timezonePreferences: MutableState<RequestState<List<Timezone>>> = mutableStateOf(RequestState.Idle)
    val timezonePreferences = _timezonePreferences
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
        getTimezonePreferences()
    }

    private fun getTimezonePreferences() = viewModelScope.launch(ioDispatcher) {
        Timber.d("Called : fun $TAG.getTimezonePreferences()")
        _timezonePreferences.value = RequestState.Loading
        _timezonePreferences.value = try{
            RequestState.Success(appConfigRepository.getTimezonePreferences())
        }catch (e: Throwable){
            RequestState.Error(e)
        }
    }

    fun saveSelectedTimezone(timezone: Timezone) = viewModelScope.launch {
        Timber.d("Called : fun $TAG.saveSelectedTimezone()")
        appConfigRepository.setTimezonePreferences(timezone)
    }
}

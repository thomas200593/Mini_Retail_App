package com.thomas200593.mini_retail_app.features.app_config.ui.components.general_config.country

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thomas200593.mini_retail_app.core.design_system.dispatchers.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.dispatchers.Dispatchers
import com.thomas200593.mini_retail_app.core.design_system.util.RequestState
import com.thomas200593.mini_retail_app.features.app_config.entity.Country
import com.thomas200593.mini_retail_app.features.app_config.repository.AppConfigRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AppConfigGeneralCountryViewModel @Inject constructor(
    private val appConfigRepository: AppConfigRepository,
    @Dispatcher(Dispatchers.Dispatchers.IO) private val ioDispatcher: CoroutineDispatcher
) : ViewModel(){
    //TODO CHANGE THIS TO COUNTRY
    private val _countryPreferences: MutableState<RequestState<List<Country>>> = mutableStateOf(RequestState.Idle)
    val countryPreferences = _countryPreferences
    val configCurrentUiState = appConfigRepository.configCurrentData.flowOn(ioDispatcher)
        .onEach { Timber.d("Config Current State: $it") }
        .catch { RequestState.Error(it) }
        .map { RequestState.Success(it) }
        .stateIn(
            scope = viewModelScope,
            SharingStarted.Eagerly,
            initialValue = RequestState.Loading
        )

    fun onOpen() = viewModelScope.launch(ioDispatcher){
        getCountryPreferences()
    }

    private fun getCountryPreferences() = viewModelScope.launch(ioDispatcher){
        _countryPreferences.value = RequestState.Loading
        _countryPreferences.value = try {
            RequestState.Success(appConfigRepository.getCountryPreferences())
        }catch (e: Throwable){
            RequestState.Error(e)
        }
    }

    fun saveSelectedCountry(country: Country) = viewModelScope.launch{
        appConfigRepository.setCountryPreferences(country)
    }
}
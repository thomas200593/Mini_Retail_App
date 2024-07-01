package com.thomas200593.mini_retail_app.features.app_config.ui.config_general.country

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thomas200593.mini_retail_app.core.design_system.dispatchers.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.dispatchers.Dispatchers.Dispatchers.IO
import com.thomas200593.mini_retail_app.core.design_system.util.RequestState
import com.thomas200593.mini_retail_app.core.design_system.util.RequestState.Error
import com.thomas200593.mini_retail_app.core.design_system.util.RequestState.Loading
import com.thomas200593.mini_retail_app.core.design_system.util.RequestState.Success
import com.thomas200593.mini_retail_app.features.app_config.entity.Country
import com.thomas200593.mini_retail_app.features.app_config.repository.AppConfigRepository
import com.thomas200593.mini_retail_app.features.app_config.repository.ConfigGeneralRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.SharingStarted.Companion.Eagerly
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

private val TAG = CountryViewModel::class.simpleName

@HiltViewModel
class CountryViewModel @Inject constructor(
    appConfigRepository: AppConfigRepository,
    private val configGeneralRepository: ConfigGeneralRepository,
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher
) : ViewModel(){
    private val _countryPreferences: MutableState<RequestState<List<Country>>> = mutableStateOf(RequestState.Idle)
    val countryPreferences = _countryPreferences
    val configCurrentUiState = appConfigRepository.configCurrent.flowOn(ioDispatcher)
        .catch { Error(it) }
        .map { Success(it) }
        .stateIn(
            scope = viewModelScope,
            started = Eagerly,
            initialValue = Loading
        )

    fun onOpen() = viewModelScope.launch(ioDispatcher){
        Timber.d("Called : fun $TAG.onOpen()")
        getCountryPreferences()
    }

    private fun getCountryPreferences() = viewModelScope.launch(ioDispatcher){
        Timber.d("Called : fun $TAG.getCountryPreferences()")
        _countryPreferences.value = Loading
        _countryPreferences.value = try { Success(configGeneralRepository.getCountryPreferences()) }
        catch (e: Throwable){ Error(e) }
    }

    fun saveSelectedCountry(country: Country) = viewModelScope.launch{
        Timber.d("Called : fun $TAG.saveSelectedCountry()")
        configGeneralRepository.setCountryPreferences(country)
    }
}
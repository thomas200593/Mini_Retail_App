package com.thomas200593.mini_retail_app.features.app_config.ui.config_general.country

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thomas200593.mini_retail_app.core.design_system.dispatchers.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.dispatchers.Dispatchers
import com.thomas200593.mini_retail_app.core.design_system.util.RequestState
import com.thomas200593.mini_retail_app.core.design_system.util.RequestState.Error
import com.thomas200593.mini_retail_app.core.design_system.util.RequestState.Idle
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
import javax.inject.Inject

@HiltViewModel
class CountryViewModel @Inject constructor(
    appCfgRepository: AppConfigRepository,
    private val cfgGeneralRepository: ConfigGeneralRepository,
    @Dispatcher(Dispatchers.Dispatchers.IO) private val ioDispatcher: CoroutineDispatcher
) : ViewModel(){
    private val _countries: MutableState<RequestState<List<Country>>> = mutableStateOf(Idle)
    val countries = _countries
    val configCurrent = appCfgRepository.configCurrent.flowOn(ioDispatcher)
        .catch { Error(it) }
        .map { Success(it) }
        .stateIn(
            scope = viewModelScope,
            started = Eagerly,
            initialValue = Loading
        )

    fun onOpen() = viewModelScope.launch(ioDispatcher){
        getCountries()
    }

    private fun getCountries() = viewModelScope.launch(ioDispatcher){
        _countries.value = Loading
        _countries.value = try { Success(cfgGeneralRepository.getCountries()) }
        catch (e: Throwable){ Error(e) }
    }

    fun setCountry(country: Country) = viewModelScope.launch{
        cfgGeneralRepository.setCountry(country)
    }
}
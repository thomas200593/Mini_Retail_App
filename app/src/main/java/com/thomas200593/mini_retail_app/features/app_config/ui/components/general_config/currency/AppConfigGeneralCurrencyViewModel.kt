package com.thomas200593.mini_retail_app.features.app_config.ui.components.general_config.currency

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thomas200593.mini_retail_app.core.design_system.dispatchers.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.dispatchers.Dispatchers
import com.thomas200593.mini_retail_app.core.design_system.util.RequestState
import com.thomas200593.mini_retail_app.features.app_config.entity.Currency
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
class AppConfigGeneralCurrencyViewModel @Inject constructor(
    private val appConfigRepository: AppConfigRepository,
    @Dispatcher(Dispatchers.Dispatchers.IO) private val ioDispatcher: CoroutineDispatcher
): ViewModel() {
    private val _currencyPreferences: MutableState<RequestState<List<Currency>>> = mutableStateOf(RequestState.Idle)
    val currencyPreferences = _currencyPreferences
    val configCurrentUiState = appConfigRepository.configCurrentData.flowOn(ioDispatcher)
        .onEach { Timber.d("Config Current State: $it") }
        .catch { RequestState.Error(it) }
        .map { RequestState.Success(it) }
        .stateIn(
            scope = viewModelScope,
            SharingStarted.Eagerly,
            initialValue = RequestState.Loading
        )

    fun onOpen() = viewModelScope.launch(ioDispatcher) {
        getCurrencyPreferences()
    }

    private fun getCurrencyPreferences() = viewModelScope.launch(ioDispatcher) {
        _currencyPreferences.value = RequestState.Loading
        _currencyPreferences.value = try{
            RequestState.Success(appConfigRepository.getCurrencyPreferences())
        }catch (e: Throwable){
            RequestState.Error(e)
        }
    }

    fun saveSelectedCurrency(currency: Currency) = viewModelScope.launch {
        appConfigRepository.setCurrencyPreferences(currency)
    }
}
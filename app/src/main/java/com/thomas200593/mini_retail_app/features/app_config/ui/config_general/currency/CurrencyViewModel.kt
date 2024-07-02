package com.thomas200593.mini_retail_app.features.app_config.ui.config_general.currency

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thomas200593.mini_retail_app.core.design_system.dispatchers.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.dispatchers.Dispatchers
import com.thomas200593.mini_retail_app.core.design_system.util.RequestState
import com.thomas200593.mini_retail_app.features.app_config.entity.Currency
import com.thomas200593.mini_retail_app.features.app_config.repository.AppConfigRepository
import com.thomas200593.mini_retail_app.features.app_config.repository.ConfigGeneralRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrencyViewModel @Inject constructor(
    appCfgRepository: AppConfigRepository,
    private val cfgGeneralRepository: ConfigGeneralRepository,
    @Dispatcher(Dispatchers.Dispatchers.IO) private val ioDispatcher: CoroutineDispatcher
): ViewModel() {
    private val _currencies: MutableState<RequestState<List<Currency>>> = mutableStateOf(RequestState.Idle)
    val currencies = _currencies
    val configCurrent = appCfgRepository.configCurrent.flowOn(ioDispatcher)
        .catch { RequestState.Error(it) }
        .map { RequestState.Success(it) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = RequestState.Loading
        )

    fun onOpen() = viewModelScope.launch(ioDispatcher) {
        getCurrencies()
    }

    private fun getCurrencies() = viewModelScope.launch(ioDispatcher) {
        _currencies.value = RequestState.Loading
        _currencies.value = try{ RequestState.Success(cfgGeneralRepository.getCurrencies()) }
        catch (e: Throwable){ RequestState.Error(e) }
    }

    fun setCurrency(currency: Currency) = viewModelScope.launch {
        cfgGeneralRepository.setCurrency(currency)
    }
}
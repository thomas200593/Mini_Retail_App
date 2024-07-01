package com.thomas200593.mini_retail_app.features.app_config.ui.config_general.currency

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thomas200593.mini_retail_app.core.design_system.dispatchers.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.dispatchers.Dispatchers.Dispatchers.IO
import com.thomas200593.mini_retail_app.core.design_system.util.RequestState
import com.thomas200593.mini_retail_app.core.design_system.util.RequestState.Error
import com.thomas200593.mini_retail_app.core.design_system.util.RequestState.Idle
import com.thomas200593.mini_retail_app.core.design_system.util.RequestState.Loading
import com.thomas200593.mini_retail_app.core.design_system.util.RequestState.Success
import com.thomas200593.mini_retail_app.features.app_config.entity.Currency
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
class CurrencyViewModel @Inject constructor(
    repository1: AppConfigRepository,
    private val repository2: ConfigGeneralRepository,
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher
): ViewModel() {
    private val _currencies: MutableState<RequestState<List<Currency>>> = mutableStateOf(Idle)
    val currencies = _currencies
    val configCurrent = repository1.configCurrent.flowOn(ioDispatcher)
        .catch { Error(it) }
        .map { Success(it) }
        .stateIn(
            scope = viewModelScope,
            started = Eagerly,
            initialValue = Loading
        )

    fun onOpen() = viewModelScope.launch(ioDispatcher) {
        getCurrencies()
    }

    private fun getCurrencies() = viewModelScope.launch(ioDispatcher) {
        _currencies.value = Loading
        _currencies.value = try{ Success(repository2.getCurrencies()) }
        catch (e: Throwable){ Error(e) }
    }

    fun setCurrency(currency: Currency) = viewModelScope.launch {
        repository2.setCurrency(currency)
    }
}
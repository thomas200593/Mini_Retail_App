package com.thomas200593.mini_retail_app.features.app_config.app_cfg_general_currency.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thomas200593.mini_retail_app.core.design_system.dispatchers.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.dispatchers.Dispatchers
import com.thomas200593.mini_retail_app.core.design_system.util.RequestState
import com.thomas200593.mini_retail_app.features.app_config.app_cfg_general_currency.domain.UseCaseGetCurrencyConfig
import com.thomas200593.mini_retail_app.features.app_config.app_cfg_general_currency.entity.Currency
import com.thomas200593.mini_retail_app.features.app_config.app_cfg_general_currency.repository.RepositoryAppCfgGeneralCurrency
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrencyViewModel @Inject constructor(
    private val repositoryAppCfgGeneralCurrency: RepositoryAppCfgGeneralCurrency,
    useCaseGetCurrencyConfig: UseCaseGetCurrencyConfig,
    @Dispatcher(Dispatchers.Dispatchers.IO) private val ioDispatcher: CoroutineDispatcher
): ViewModel() {

    val configData = useCaseGetCurrencyConfig.invoke().flowOn(ioDispatcher)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = RequestState.Loading
        )

    fun setCurrency(currency: Currency) = viewModelScope.launch {
        repositoryAppCfgGeneralCurrency.setCurrency(currency)
    }
}
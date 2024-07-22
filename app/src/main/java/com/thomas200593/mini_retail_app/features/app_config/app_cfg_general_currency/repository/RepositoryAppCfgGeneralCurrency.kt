package com.thomas200593.mini_retail_app.features.app_config.app_cfg_general_currency.repository

import com.thomas200593.mini_retail_app.core.data.local.datastore.DataStorePreferences
import com.thomas200593.mini_retail_app.core.design_system.dispatchers.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.dispatchers.Dispatchers
import com.thomas200593.mini_retail_app.core.design_system.util.CurrencyHelper
import com.thomas200593.mini_retail_app.features.app_config.app_cfg_general_currency.entity.Currency
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface RepositoryAppCfgGeneralCurrency {
    suspend fun getCurrencies(): List<Currency>
    suspend fun setCurrency(currency: Currency)
}

internal class RepositoryImplAppCfgGeneralCurrency @Inject constructor(
    private val dataStore: DataStorePreferences,
    @Dispatcher(Dispatchers.Dispatchers.IO) private val ioDispatcher: CoroutineDispatcher
): RepositoryAppCfgGeneralCurrency{
    override suspend fun getCurrencies(): List<Currency> = withContext(ioDispatcher){
        CurrencyHelper.getCurrencyList()
    }

    override suspend fun setCurrency(currency: Currency) {
        dataStore.setCurrency(currency)
    }
}
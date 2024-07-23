package com.thomas200593.mini_retail_app.features.app_conf.conf_gen_currency.repository

import com.thomas200593.mini_retail_app.core.data.local.datastore.DataStorePreferences
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatchers.Dispatchers.IO
import com.thomas200593.mini_retail_app.core.design_system.util.HlpCurrency.getCurrencyList
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_currency.entity.Currency
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface RepoConfGenCurrency {
    suspend fun getCurrencies(): List<Currency>
    suspend fun setCurrency(currency: Currency)
}

class RepoImplConfGenCurrency @Inject constructor(
    private val dataStore: DataStorePreferences,
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher
): RepoConfGenCurrency{
    override suspend fun getCurrencies(): List<Currency> = withContext(ioDispatcher){ getCurrencyList() }
    override suspend fun setCurrency(currency: Currency) { dataStore.setCurrency(currency) }
}
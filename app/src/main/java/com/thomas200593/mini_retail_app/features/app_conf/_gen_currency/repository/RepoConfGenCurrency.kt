package com.thomas200593.mini_retail_app.features.app_conf._gen_currency.repository

import com.thomas200593.mini_retail_app.core.data.local.datastore.DataStorePreferences
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatchers
import com.thomas200593.mini_retail_app.core.design_system.util.HlpCurrency
import com.thomas200593.mini_retail_app.features.app_conf._gen_currency.entity.Currency
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface RepoConfGenCurrency {
    suspend fun getCurrencies(): List<Currency>
    suspend fun setCurrency(currency: Currency)
}

class RepoImplConfGenCurrency @Inject constructor(
    private val dataStore: DataStorePreferences,
    @Dispatcher(Dispatchers.Dispatchers.IO) private val ioDispatcher: CoroutineDispatcher
): RepoConfGenCurrency{
    override suspend fun getCurrencies(): List<Currency> =
        withContext(ioDispatcher){ HlpCurrency.getCurrencyList() }

    override suspend fun setCurrency(currency: Currency)
    { dataStore.setCurrency(currency) }
}
package com.thomas200593.mini_retail_app.features.app_conf.conf_gen_currency.repository

import com.thomas200593.mini_retail_app.core.data.local.datastore.DataStorePreferences
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatchers.Dispatchers.IO
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.di.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.util.HlpCurrency.getCurrencyList
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_currency.entity.Currency
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

interface RepoConfGenCurrency {
    fun getCurrencies(): Flow<List<Currency>>
    suspend fun setCurrency(currency: Currency)
}

class RepoImplConfGenCurrency @Inject constructor(
    private val dataStore: DataStorePreferences,
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher
): RepoConfGenCurrency{
    override fun getCurrencies(): Flow<List<Currency>> =
        flow{ emit(getCurrencyList()) }.flowOn(ioDispatcher)

    override suspend fun setCurrency(currency: Currency) {
        dataStore.setCurrency(currency)
    }
}
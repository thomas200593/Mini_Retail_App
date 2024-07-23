package com.thomas200593.mini_retail_app.features.app_conf.conf_gen_country.repository

import com.thomas200593.mini_retail_app.core.data.local.datastore.DataStorePreferences
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatchers.Dispatchers.IO
import com.thomas200593.mini_retail_app.core.design_system.util.HlpCountry.getCountryList
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_country.entity.Country
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface RepoConfGenCountry {
    suspend fun getCountries(): List<Country>
    suspend fun setCountry(country: Country)
}

internal class RepoImplConfGenCountry @Inject constructor(
    private val dataStore: DataStorePreferences,
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher
): RepoConfGenCountry{
    override suspend fun getCountries(): List<Country> = withContext(ioDispatcher){ getCountryList() }
    override suspend fun setCountry(country: Country) { dataStore.setCountry(country) }
}
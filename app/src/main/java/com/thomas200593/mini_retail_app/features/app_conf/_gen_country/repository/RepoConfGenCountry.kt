package com.thomas200593.mini_retail_app.features.app_conf._gen_country.repository

import com.thomas200593.mini_retail_app.core.data.local.datastore.DataStorePreferences
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatchers
import com.thomas200593.mini_retail_app.core.design_system.util.HlpCountry
import com.thomas200593.mini_retail_app.features.app_conf._gen_country.entity.Country
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface RepoConfGenCountry {
    suspend fun getCountries(): List<Country>
    suspend fun setCountry(country: Country)
}

internal class RepoImplConfGenCountry @Inject constructor(
    private val dataStore: DataStorePreferences,
    @Dispatcher(Dispatchers.Dispatchers.IO) private val ioDispatcher: CoroutineDispatcher
): RepoConfGenCountry{
    override suspend fun getCountries(): List<Country> = withContext(ioDispatcher){
        HlpCountry.getCountryList()
    }
    override suspend fun setCountry(country: Country) {
        dataStore.setCountry(country)
    }
}
package com.thomas200593.mini_retail_app.features.app_config.app_cfg_general_country.repository

import com.thomas200593.mini_retail_app.core.data.local.datastore.DataStorePreferences
import com.thomas200593.mini_retail_app.core.design_system.dispatchers.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.dispatchers.Dispatchers
import com.thomas200593.mini_retail_app.core.design_system.util.CountryHelper
import com.thomas200593.mini_retail_app.features.app_config.app_cfg_general_country.entity.Country
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface RepositoryAppCfgGeneralCountry {
    suspend fun getCountries(): List<Country>
    suspend fun setCountry(country: Country)
}

internal class RepositoryImplAppCfgGeneralCountry @Inject constructor(
    private val dataStore: DataStorePreferences,
    @Dispatcher(Dispatchers.Dispatchers.IO) private val ioDispatcher: CoroutineDispatcher
): RepositoryAppCfgGeneralCountry{
    override suspend fun getCountries(): List<Country> = withContext(ioDispatcher){
        CountryHelper.getCountryList()
    }
    override suspend fun setCountry(country: Country) {
        dataStore.setCountry(country)
    }
}
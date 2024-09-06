package com.thomas200593.mini_retail_app.features.app_conf.conf_gen_country.repository

import com.thomas200593.mini_retail_app.core.data.local.datastore.DataStorePreferences
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatchers.Dispatchers.IO
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.di.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.util.HlpCountry.getCountryList
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_country.entity.Country
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

interface RepoConfGenCountry {
    fun getCountries(): Flow<List<Country>>
    suspend fun setCountry(country: Country)
}

internal class RepoImplConfGenCountry @Inject constructor(
    private val dataStore: DataStorePreferences,
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher
) : RepoConfGenCountry {
    override fun getCountries(): Flow<List<Country>> = flow { emit(getCountryList()) }
        .flowOn(ioDispatcher)
    override suspend fun setCountry(country: Country) { dataStore.setCountry(country) }
}
package com.thomas200593.mini_retail_app.core.design_system.util.countries

import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatchers.Dispatchers.IO
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_country.entity.Country
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.util.Locale
import java.util.Locale.US
import java.util.Locale.getISOCountries
import javax.inject.Inject

interface HlpCountries{
    suspend fun getCountries(): List<Country>
}

class HlpCountriesImpl @Inject constructor(
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher
): HlpCountries{
    override suspend fun getCountries() = withContext(ioDispatcher){
        val isoCountries = getISOCountries()
        val countries = HashSet<Country>()
        isoCountries.forEach {
            countries.add(
                Country(
                    isoCode = it,
                    iso03Country = Locale(String(), it).isO3Country,
                    displayName = Locale(String(), it).displayName
                )
            )
        }
        countries.toList().sortedBy { it.displayName }
    }
    companion object {
        private val COUNTRY_US = Locale(String(), US.country).country
        val COUNTRY_DEFAULT = Country(
            isoCode = COUNTRY_US,
            iso03Country = Locale(String(), COUNTRY_US).isO3Country,
            displayName = Locale(String(), COUNTRY_US).displayName
        )
    }
}
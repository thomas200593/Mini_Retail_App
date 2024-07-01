package com.thomas200593.mini_retail_app.core.util

import com.thomas200593.mini_retail_app.features.app_config.entity.Country
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.util.Locale
import java.util.Locale.getISOCountries

private val TAG = CountryHelper::class.simpleName

object CountryHelper {
    private val COUNTRY_US = Locale("", Locale.US.country).country
    val COUNTRY_DEFAULT = Country(
        isoCode = COUNTRY_US,
        iso03Country = Locale("", COUNTRY_US).isO3Country,
        displayName = Locale("", COUNTRY_US).displayName
    )
    suspend fun getCountryList() = withContext(Dispatchers.IO){
        Timber.d("Called : $TAG.getCountryList()")
        val isoCountries = getISOCountries()
        val countries = HashSet<Country>()
        for(isoCountry in isoCountries){
            val displayName = Locale("", isoCountry).displayName
            val isO03Country = Locale("", isoCountry).isO3Country
            countries.add(
                Country(
                    isoCode = isoCountry,
                    iso03Country = isO03Country,
                    displayName = displayName,
                )
            )
        }
        countries.toList().sortedBy { it.displayName }
    }
}
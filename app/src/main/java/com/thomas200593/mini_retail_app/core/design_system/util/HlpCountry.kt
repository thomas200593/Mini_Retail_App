package com.thomas200593.mini_retail_app.core.design_system.util

import com.thomas200593.mini_retail_app.features.app_conf._gen_country.entity.Country
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Locale
import java.util.Locale.US
import java.util.Locale.getISOCountries

object HlpCountry {
    private val COUNTRY_US = Locale(String(), US.country).country
    val COUNTRY_DEFAULT = Country(
        isoCode = COUNTRY_US,
        iso03Country = Locale(String(), COUNTRY_US).isO3Country,
        displayName = Locale(String(), COUNTRY_US).displayName
    )
    suspend fun getCountryList() = withContext(Dispatchers.IO) {
        val isoCountries = getISOCountries()
        val countries = HashSet<Country>()
        for (isoCountry in isoCountries) {
            countries.add(
                Country(
                    isoCode = isoCountry,
                    iso03Country = Locale(String(), isoCountry).isO3Country,
                    displayName = Locale(String(), isoCountry).displayName,
                )
            )
        }
        countries.toList().sortedBy { it.displayName }
    }
}
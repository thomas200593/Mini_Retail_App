package com.thomas200593.mini_retail_app.core.design_system.util

import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_country.entity.Country
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.util.Locale
import java.util.Locale.US

object HlpCountry {
    private val COUNTRY_US = Locale(String(), US.country).country
    val COUNTRY_DEFAULT = Country(
        isoCode = COUNTRY_US,
        iso03Country = Locale(String(), COUNTRY_US).isO3Country,
        displayName = Locale(String(), COUNTRY_US).displayName,
        flag = HlpCountryFlags.getCountryFlagByCountryCode(COUNTRY_US)
    )
    suspend fun getCountryList(dispatcher: CoroutineDispatcher) = withContext(dispatcher) {
        Locale.getISOCountries().map {
            val locale = Locale(String(), it)
            Country(
                isoCode = it,
                iso03Country = locale.isO3Country,
                displayName = locale.displayName,
                flag = HlpCountryFlags.getCountryFlagByCountryCode(it)
            )
        }.sortedBy { it.displayName }
    }
}
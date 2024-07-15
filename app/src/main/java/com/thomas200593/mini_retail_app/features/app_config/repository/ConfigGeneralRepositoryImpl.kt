package com.thomas200593.mini_retail_app.features.app_config.repository

import com.thomas200593.mini_retail_app.core.data.local.datastore.DataStorePreferences
import com.thomas200593.mini_retail_app.core.data.local.session.SessionState
import com.thomas200593.mini_retail_app.core.design_system.dispatchers.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.dispatchers.Dispatchers
import com.thomas200593.mini_retail_app.core.design_system.util.CountryHelper
import com.thomas200593.mini_retail_app.core.design_system.util.CurrencyHelper
import com.thomas200593.mini_retail_app.core.design_system.util.TimezoneHelper
import com.thomas200593.mini_retail_app.features.app_config.entity.Country
import com.thomas200593.mini_retail_app.features.app_config.entity.Currency
import com.thomas200593.mini_retail_app.features.app_config.entity.DynamicColor
import com.thomas200593.mini_retail_app.features.app_config.entity.FontSize
import com.thomas200593.mini_retail_app.features.app_config.entity.Language
import com.thomas200593.mini_retail_app.features.app_config.entity.Theme
import com.thomas200593.mini_retail_app.features.app_config.entity.Timezone
import com.thomas200593.mini_retail_app.features.app_config.navigation.DestinationConfigGeneral
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class ConfigGeneralRepositoryImpl @Inject constructor(
    private val dataStore: DataStorePreferences,
    @Dispatcher(Dispatchers.Dispatchers.IO) private val ioDispatcher: CoroutineDispatcher
): ConfigGeneralRepository {
    override suspend fun getMenuData(
        sessionState: SessionState
    ): Set<DestinationConfigGeneral> = withContext(ioDispatcher) {
        when (sessionState) {
            SessionState.Loading -> { emptySet() }
            is SessionState.Invalid -> {
                DestinationConfigGeneral.entries.filterNot { it.usesAuth }.toSet()
            }
            is SessionState.Valid -> {
               DestinationConfigGeneral.entries.toSet()
            }
        }
    }

    override suspend fun getThemes(): Set<Theme> = withContext(ioDispatcher){
        Theme.entries.toSet()
    }

    override suspend fun setTheme(theme: Theme) {
        dataStore.setTheme(theme)
    }

    override suspend fun getDynamicColors(): Set<DynamicColor> = withContext(ioDispatcher){
        DynamicColor.entries.toSet()
    }

    override suspend fun setDynamicColor(dynamicColor: DynamicColor) {
        dataStore.setDynamicColor(dynamicColor)
    }

    override suspend fun getLanguages(): Set<Language> = withContext(ioDispatcher){
        Language.entries.toSet()
    }

    override suspend fun setLanguage(language: Language) {
        dataStore.setLanguage(language)
    }

    override suspend fun getTimezones(): List<Timezone> = withContext(ioDispatcher){
        TimezoneHelper.getTimezoneList()
    }

    override suspend fun setTimezone(timezone: Timezone) {
        dataStore.setTimezone(timezone)
    }

    override suspend fun getCurrencies(): List<Currency> = withContext(ioDispatcher){
        CurrencyHelper.getCurrencyList()
    }

    override suspend fun setCurrency(currency: Currency) {
        dataStore.setCurrency(currency)
    }

    override suspend fun getFontSizes(): Set<FontSize> = withContext(ioDispatcher){
        FontSize.entries.toSet()
    }

    override suspend fun setFontSize(fontSize: FontSize) {
        dataStore.setFontSize(fontSize)
    }

    override suspend fun getCountries(): List<Country> = withContext(ioDispatcher){
        CountryHelper.getCountryList()
    }

    override suspend fun setCountry(country: Country) {
        dataStore.setCountry(country)
    }
}
package com.thomas200593.mini_retail_app.features.app_config.repository

import com.thomas200593.mini_retail_app.core.data.local.datastore.DataStorePreferences
import com.thomas200593.mini_retail_app.core.design_system.dispatchers.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.dispatchers.Dispatchers.Dispatchers
import com.thomas200593.mini_retail_app.core.util.CountryHelper
import com.thomas200593.mini_retail_app.core.util.CurrencyHelper
import com.thomas200593.mini_retail_app.core.util.TimezoneHelper
import com.thomas200593.mini_retail_app.features.app_config.navigation.ConfigGeneralDestination
import com.thomas200593.mini_retail_app.features.app_config.entity.ConfigCurrent
import com.thomas200593.mini_retail_app.features.app_config.entity.Country
import com.thomas200593.mini_retail_app.features.app_config.entity.Currency
import com.thomas200593.mini_retail_app.features.app_config.entity.DynamicColor
import com.thomas200593.mini_retail_app.features.app_config.entity.FontSize
import com.thomas200593.mini_retail_app.features.app_config.entity.Language
import com.thomas200593.mini_retail_app.features.app_config.entity.Theme
import com.thomas200593.mini_retail_app.features.app_config.entity.Timezone
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class AppConfigRepositoryImpl @Inject constructor(
    private val appDataStore: DataStorePreferences,
    @Dispatcher(Dispatchers.IO) private val ioDispatcher: CoroutineDispatcher
):AppConfigRepository {
    override val configCurrentData: Flow<ConfigCurrent> =
        appDataStore.configCurrentData

    override suspend fun getAppConfigGeneralMenuData(
        usesAuth: Boolean?
    ): Set<ConfigGeneralDestination> = withContext(ioDispatcher){
        ConfigGeneralDestination.entries.toSet()
    }

    override suspend fun getThemePreferences(): Set<Theme> = withContext(ioDispatcher){
        Theme.entries.toSet()
    }

    override suspend fun setThemePreferences(theme: Theme) {
        appDataStore.setThemePreferences(theme)
    }

    override suspend fun getDynamicMenuPreferences(): Set<DynamicColor> = withContext(ioDispatcher){
        DynamicColor.entries.toSet()
    }

    override suspend fun setDynamicColorPreferences(dynamicColor: DynamicColor) {
        appDataStore.setDynamicColorPreferences(dynamicColor)
    }

    override suspend fun getLanguagePreferences(): Set<Language> = withContext(ioDispatcher){
        Language.entries.toSet()
    }

    override suspend fun setLanguagePreferences(language: Language) {
        appDataStore.setLanguagePreferences(language)
    }

    override suspend fun getTimezonePreferences(): List<Timezone> = withContext(ioDispatcher){
        TimezoneHelper.getTimezones()
    }

    override suspend fun setTimezonePreferences(timezone: Timezone) {
        appDataStore.setTimezonePreferences(timezone)
    }

    override suspend fun getCurrencyPreferences(): List<Currency> = withContext(ioDispatcher){
        CurrencyHelper.getCurrencies()
    }

    override suspend fun setCurrencyPreferences(currency: Currency) {
        appDataStore.setCurrencyPreferences(currency)
    }

    override suspend fun getFontSizePreferences(): Set<FontSize> = withContext(ioDispatcher){
        FontSize.entries.toSet()
    }

    override suspend fun setFontSizePreferences(fontSize: FontSize) {
        appDataStore.setFontSizePreferences(fontSize)
    }

    override suspend fun getCountryPreferences(): List<Country> = withContext(ioDispatcher){
        CountryHelper.getCountryList()
    }

    override suspend fun setCountryPreferences(country: Country) {
        appDataStore.setCountryPreferences(country)
    }
}
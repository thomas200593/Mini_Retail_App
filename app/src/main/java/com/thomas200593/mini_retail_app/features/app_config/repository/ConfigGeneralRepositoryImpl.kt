package com.thomas200593.mini_retail_app.features.app_config.repository

import com.thomas200593.mini_retail_app.core.data.local.datastore.DataStorePreferences
import com.thomas200593.mini_retail_app.core.data.local.session.SessionState
import com.thomas200593.mini_retail_app.core.data.local.session.SessionState.Invalid
import com.thomas200593.mini_retail_app.core.data.local.session.SessionState.Loading
import com.thomas200593.mini_retail_app.core.data.local.session.SessionState.Valid
import com.thomas200593.mini_retail_app.core.design_system.dispatchers.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.dispatchers.Dispatchers.Dispatchers.IO
import com.thomas200593.mini_retail_app.core.util.CountryHelper.getCountryList
import com.thomas200593.mini_retail_app.features.app_config.entity.Country
import com.thomas200593.mini_retail_app.features.app_config.entity.Currency
import com.thomas200593.mini_retail_app.features.app_config.entity.DynamicColor
import com.thomas200593.mini_retail_app.features.app_config.entity.FontSize
import com.thomas200593.mini_retail_app.features.app_config.entity.Language
import com.thomas200593.mini_retail_app.features.app_config.entity.Theme
import com.thomas200593.mini_retail_app.features.app_config.entity.Timezone
import com.thomas200593.mini_retail_app.features.app_config.navigation.DestinationConfigGeneral
import com.thomas200593.mini_retail_app.features.app_config.navigation.DestinationConfigGeneral.entries
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

private val TAG = ConfigGeneralRepositoryImpl::class.simpleName

internal class ConfigGeneralRepositoryImpl @Inject constructor(
    private val dataStore: DataStorePreferences,
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher
): ConfigGeneralRepository {
    override suspend fun getMenuData(
        sessionState: SessionState
    ): Set<DestinationConfigGeneral> = withContext(ioDispatcher) {
        Timber.d("Called : fun $TAG.getAppConfigGeneralMenuData()")
        when (sessionState) {
            Loading -> { emptySet() }
            is Invalid -> { entries.filterNot { it.usesAuth }.toSet() }
            is Valid -> { entries.toSet() }
        }
    }

    override suspend fun getThemes(): Set<Theme> = withContext(ioDispatcher){
        Timber.d("Called : fun $TAG.getThemePreferences()")
        Theme.entries.toSet()
    }

    override suspend fun setTheme(theme: Theme) {
        Timber.d("Called : fun $TAG.setThemePreferences()")
        dataStore.setTheme(theme)
    }

    override suspend fun getDynamicColors(): Set<DynamicColor> = withContext(ioDispatcher){
        Timber.d("Called : fun $TAG.getDynamicMenuPreferences()")
        DynamicColor.entries.toSet()
    }

    override suspend fun setDynamicColor(dynamicColor: DynamicColor) {
        Timber.d("Called : fun $TAG.setDynamicColorPreferences()")
        dataStore.setDynamicColor(dynamicColor)
    }

    override suspend fun getLanguages(): Set<Language> = withContext(ioDispatcher){
        Timber.d("Called : fun $TAG.getLanguagePreferences()")
        Language.entries.toSet()
    }

    override suspend fun setLanguage(language: Language) {
        Timber.d("Called : fun $TAG.setLanguagePreferences()")
        dataStore.setLanguage(language)
    }

    override suspend fun getTimezones(): List<Timezone> = withContext(ioDispatcher){
        Timber.d("Called : fun $TAG.getTimezonePreferences()")
        getTimezones()
    }

    override suspend fun setTimezone(timezone: Timezone) {
        Timber.d("Called : fun $TAG.setTimezonePreferences()")
        dataStore.setTimezone(timezone)
    }

    override suspend fun getCurrencies(): List<Currency> = withContext(ioDispatcher){
        Timber.d("Called : fun $TAG.getCurrencyPreferences()")
        getCurrencies()
    }

    override suspend fun setCurrency(currency: Currency) {
        Timber.d("Called : fun $TAG.setCurrencyPreferences()")
        dataStore.setCurrency(currency)
    }

    override suspend fun getFontSizes(): Set<FontSize> = withContext(ioDispatcher){
        Timber.d("Called : fun $TAG.getFontSizePreferences()")
        FontSize.entries.toSet()
    }

    override suspend fun setFontSize(fontSize: FontSize) {
        Timber.d("Called : fun $TAG.setFontSizePreferences()")
        dataStore.setFontSize(fontSize)
    }

    override suspend fun getCountries(): List<Country> = withContext(ioDispatcher){
        Timber.d("Called : fun $TAG.getCountryPreferences()")
        getCountryList()
    }

    override suspend fun setCountry(country: Country) {
        Timber.d("Called : fun $TAG.setCountryPreferences()")
        dataStore.setCountry(country)
    }
}
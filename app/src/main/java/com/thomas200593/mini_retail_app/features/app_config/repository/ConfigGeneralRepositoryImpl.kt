package com.thomas200593.mini_retail_app.features.app_config.repository

import com.thomas200593.mini_retail_app.core.data.local.datastore.DataStorePreferences
import com.thomas200593.mini_retail_app.core.data.local.session.SessionState
import com.thomas200593.mini_retail_app.core.data.local.session.SessionState.Invalid
import com.thomas200593.mini_retail_app.core.data.local.session.SessionState.Loading
import com.thomas200593.mini_retail_app.core.data.local.session.SessionState.Valid
import com.thomas200593.mini_retail_app.core.design_system.dispatchers.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.dispatchers.Dispatchers
import com.thomas200593.mini_retail_app.core.util.CountryHelper.getCountryList
import com.thomas200593.mini_retail_app.core.util.CurrencyHelper.getCurrencies
import com.thomas200593.mini_retail_app.core.util.TimezoneHelper.getTimezones
import com.thomas200593.mini_retail_app.features.app_config.entity.Country
import com.thomas200593.mini_retail_app.features.app_config.entity.Currency
import com.thomas200593.mini_retail_app.features.app_config.entity.DynamicColor
import com.thomas200593.mini_retail_app.features.app_config.entity.FontSize
import com.thomas200593.mini_retail_app.features.app_config.entity.Language
import com.thomas200593.mini_retail_app.features.app_config.entity.Theme
import com.thomas200593.mini_retail_app.features.app_config.entity.Timezone
import com.thomas200593.mini_retail_app.features.app_config.navigation.DestinationAppConfigGeneral
import com.thomas200593.mini_retail_app.features.app_config.navigation.DestinationAppConfigGeneral.entries
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

private val TAG = ConfigGeneralRepositoryImpl::class.simpleName

internal class ConfigGeneralRepositoryImpl @Inject constructor(
    private val appDataStore: DataStorePreferences,
    @Dispatcher(Dispatchers.Dispatchers.IO) private val ioDispatcher: CoroutineDispatcher
): ConfigGeneralRepository {
    override suspend fun getAppConfigGeneralMenuData(
        sessionState: SessionState
    ): Set<DestinationAppConfigGeneral> = withContext(ioDispatcher) {
        Timber.d("Called : fun $TAG.getAppConfigGeneralMenuData()")
        when (sessionState) {
            Loading -> { emptySet() }
            is Invalid -> { entries.filterNot { it.usesAuth }.toSet() }
            is Valid -> { entries.toSet() }
        }
    }

    override suspend fun getThemePreferences(): Set<Theme> = withContext(ioDispatcher){
        Timber.d("Called : fun $TAG.getThemePreferences()")
        Theme.entries.toSet()
    }

    override suspend fun setThemePreferences(theme: Theme) {
        Timber.d("Called : fun $TAG.setThemePreferences()")
        appDataStore.setThemePreferences(theme)
    }

    override suspend fun getDynamicMenuPreferences(): Set<DynamicColor> = withContext(ioDispatcher){
        Timber.d("Called : fun $TAG.getDynamicMenuPreferences()")
        DynamicColor.entries.toSet()
    }

    override suspend fun setDynamicColorPreferences(dynamicColor: DynamicColor) {
        Timber.d("Called : fun $TAG.setDynamicColorPreferences()")
        appDataStore.setDynamicColorPreferences(dynamicColor)
    }

    override suspend fun getLanguagePreferences(): Set<Language> = withContext(ioDispatcher){
        Timber.d("Called : fun $TAG.getLanguagePreferences()")
        Language.entries.toSet()
    }

    override suspend fun setLanguagePreferences(language: Language) {
        Timber.d("Called : fun $TAG.setLanguagePreferences()")
        appDataStore.setLanguagePreferences(language)
    }

    override suspend fun getTimezonePreferences(): List<Timezone> = withContext(ioDispatcher){
        Timber.d("Called : fun $TAG.getTimezonePreferences()")
        getTimezones()
    }

    override suspend fun setTimezonePreferences(timezone: Timezone) {
        Timber.d("Called : fun $TAG.setTimezonePreferences()")
        appDataStore.setTimezonePreferences(timezone)
    }

    override suspend fun getCurrencyPreferences(): List<Currency> = withContext(ioDispatcher){
        Timber.d("Called : fun $TAG.getCurrencyPreferences()")
        getCurrencies()
    }

    override suspend fun setCurrencyPreferences(currency: Currency) {
        Timber.d("Called : fun $TAG.setCurrencyPreferences()")
        appDataStore.setCurrencyPreferences(currency)
    }

    override suspend fun getFontSizePreferences(): Set<FontSize> = withContext(ioDispatcher){
        Timber.d("Called : fun $TAG.getFontSizePreferences()")
        FontSize.entries.toSet()
    }

    override suspend fun setFontSizePreferences(fontSize: FontSize) {
        Timber.d("Called : fun $TAG.setFontSizePreferences()")
        appDataStore.setFontSizePreferences(fontSize)
    }

    override suspend fun getCountryPreferences(): List<Country> = withContext(ioDispatcher){
        Timber.d("Called : fun $TAG.getCountryPreferences()")
        getCountryList()
    }

    override suspend fun setCountryPreferences(country: Country) {
        Timber.d("Called : fun $TAG.setCountryPreferences()")
        appDataStore.setCountryPreferences(country)
    }
}
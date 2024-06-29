package com.thomas200593.mini_retail_app.features.app_config.repository

import com.thomas200593.mini_retail_app.core.data.local.datastore.DataStorePreferences
import com.thomas200593.mini_retail_app.core.data.local.session.SessionState
import com.thomas200593.mini_retail_app.core.design_system.dispatchers.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.dispatchers.Dispatchers.Dispatchers
import com.thomas200593.mini_retail_app.core.util.CountryHelper
import com.thomas200593.mini_retail_app.core.util.CurrencyHelper
import com.thomas200593.mini_retail_app.core.util.TimezoneHelper
import com.thomas200593.mini_retail_app.features.app_config.navigation.DestinationAppConfigGeneral
import com.thomas200593.mini_retail_app.features.app_config.entity.ConfigCurrent
import com.thomas200593.mini_retail_app.features.app_config.entity.Country
import com.thomas200593.mini_retail_app.features.app_config.entity.Currency
import com.thomas200593.mini_retail_app.features.app_config.entity.DynamicColor
import com.thomas200593.mini_retail_app.features.app_config.entity.FontSize
import com.thomas200593.mini_retail_app.features.app_config.entity.Language
import com.thomas200593.mini_retail_app.features.app_config.entity.Theme
import com.thomas200593.mini_retail_app.features.app_config.entity.Timezone
import com.thomas200593.mini_retail_app.features.app_config.navigation.DestinationAppConfig
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

private val TAG = AppConfigRepositoryImpl::class.simpleName

internal class AppConfigRepositoryImpl @Inject constructor(
    private val appDataStore: DataStorePreferences,
    @Dispatcher(Dispatchers.IO) private val ioDispatcher: CoroutineDispatcher
):AppConfigRepository {
    override val configCurrentData: Flow<ConfigCurrent> =
        appDataStore.configCurrentData

    override suspend fun getAppConfigMenuData(
        sessionState: SessionState
    ): Set<DestinationAppConfig> = withContext(ioDispatcher){
        Timber.d("Called : fun $TAG.getAppConfigMenuData()")
        when(sessionState){
            SessionState.Loading -> {
                emptySet()
            }
            is SessionState.Invalid -> {
                DestinationAppConfig.entries.filter { !it.usesAuth }.toSet()
            }
            is SessionState.Valid -> {
                DestinationAppConfig.entries.toSet()
            }
        }
    }

    override suspend fun getAppConfigGeneralMenuData(
        sessionState: SessionState
    ): Set<DestinationAppConfigGeneral> = withContext(ioDispatcher){
        Timber.d("Called : fun $TAG.getAppConfigGeneralMenuData()")
        when(sessionState){
            SessionState.Loading -> {
                emptySet()
            }
            is SessionState.Invalid -> {
                DestinationAppConfigGeneral.entries.filter { !it.usesAuth }.toSet()
            }
            is SessionState.Valid -> {
                DestinationAppConfigGeneral.entries.toSet()
            }
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
        TimezoneHelper.getTimezones()
    }

    override suspend fun setTimezonePreferences(timezone: Timezone) {
        Timber.d("Called : fun $TAG.setTimezonePreferences()")
        appDataStore.setTimezonePreferences(timezone)
    }

    override suspend fun getCurrencyPreferences(): List<Currency> = withContext(ioDispatcher){
        Timber.d("Called : fun $TAG.getCurrencyPreferences()")
        CurrencyHelper.getCurrencies()
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
        CountryHelper.getCountryList()
    }

    override suspend fun setCountryPreferences(country: Country) {
        Timber.d("Called : fun $TAG.setCountryPreferences()")
        appDataStore.setCountryPreferences(country)
    }
}
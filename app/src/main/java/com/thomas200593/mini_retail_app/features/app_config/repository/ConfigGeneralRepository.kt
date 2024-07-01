package com.thomas200593.mini_retail_app.features.app_config.repository

import com.thomas200593.mini_retail_app.core.data.local.session.SessionState
import com.thomas200593.mini_retail_app.features.app_config.entity.Country
import com.thomas200593.mini_retail_app.features.app_config.entity.Currency
import com.thomas200593.mini_retail_app.features.app_config.entity.DynamicColor
import com.thomas200593.mini_retail_app.features.app_config.entity.FontSize
import com.thomas200593.mini_retail_app.features.app_config.entity.Language
import com.thomas200593.mini_retail_app.features.app_config.entity.Theme
import com.thomas200593.mini_retail_app.features.app_config.entity.Timezone
import com.thomas200593.mini_retail_app.features.app_config.navigation.DestinationConfigGeneral

interface ConfigGeneralRepository {
    suspend fun getMenuData(sessionState: SessionState): Set<DestinationConfigGeneral>
    suspend fun getThemePreferences(): Set<Theme>
    suspend fun setThemePreferences(theme: Theme)
    suspend fun getDynamicMenuPreferences(): Set<DynamicColor>
    suspend fun setDynamicColorPreferences(dynamicColor: DynamicColor)
    suspend fun getLanguagePreferences(): Set<Language>
    suspend fun setLanguagePreferences(language: Language)
    suspend fun getTimezonePreferences(): List<Timezone>
    suspend fun setTimezonePreferences(timezone: Timezone)
    suspend fun getCurrencyPreferences(): List<Currency>
    suspend fun setCurrencyPreferences(currency: Currency)
    suspend fun getFontSizePreferences(): Set<FontSize>
    suspend fun setFontSizePreferences(fontSize: FontSize)
    suspend fun getCountryPreferences(): List<Country>
    suspend fun setCountryPreferences(country: Country)
}
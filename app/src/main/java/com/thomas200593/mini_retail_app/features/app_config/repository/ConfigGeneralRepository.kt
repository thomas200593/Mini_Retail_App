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

    suspend fun getThemes(): Set<Theme>
    suspend fun setTheme(theme: Theme)

    suspend fun getDynamicColors(): Set<DynamicColor>
    suspend fun setDynamicColor(dynamicColor: DynamicColor)

    suspend fun getLanguages(): Set<Language>
    suspend fun setLanguage(language: Language)

    suspend fun getTimezones(): List<Timezone>
    suspend fun setTimezone(timezone: Timezone)

    suspend fun getCurrencies(): List<Currency>
    suspend fun setCurrency(currency: Currency)

    suspend fun getFontSizes(): Set<FontSize>
    suspend fun setFontSize(fontSize: FontSize)

    suspend fun getCountries(): List<Country>
    suspend fun setCountry(country: Country)
}
package com.thomas200593.mini_retail_app.core.data.local.datastore

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object AppDataStorePreferencesKeys {
    val dsAppShouldShowOnboardingPages = stringPreferencesKey(
        name = "DS_APP_CONFIG_SHOULD_SHOW_ONBOARDING_PAGES"
    )
    val dsAppConfigTheme = stringPreferencesKey(
        name = "DS_APP_CONFIG_THEME"
    )
    val dsAppConfigDynamicColor = stringPreferencesKey(
        name = "DS_APP_CONFIG_DYNAMIC_COLOR"
    )
    val dsAppConfigFontSize = stringPreferencesKey(
        name = "DS_APP_CONFIG_FONT_SIZE"
    )
    val dsAppConfigLanguage = stringPreferencesKey(
        name = "DS_APP_CONFIG_LANGUAGE"
    )
}
package com.thomas200593.mini_retail_app.core.data.local.datastore

import androidx.datastore.preferences.core.stringPreferencesKey

object DataStoreKeys {
    object AppConfigKeys {
        val dsKeyFirstTimeStatus = stringPreferencesKey(name = "DS_KEY_FIRST_TIME_STATUS")
        val dsKeyOnboardingStatus = stringPreferencesKey(name = "DS_KEY_ONBOARDING_STATUS")
        val dsKeyTheme = stringPreferencesKey(name = "DS_KEY_THEME")
        val dsKeyDynamicColor = stringPreferencesKey(name = "DS_KEY_DYNAMIC_COLOR")
        val dsKeyFontSize = stringPreferencesKey(name = "DS_KEY_FONT_SIZE")
        val dsKeyLanguage = stringPreferencesKey(name = "DS_KEY_LANGUAGE")
        val dsKeyTimezone = stringPreferencesKey(name = "DS_KEY_TIMEZONE")
        val dsKeyCurrency = stringPreferencesKey(name = "DS_KEY_CURRENCY")
        val dsKeyCountry = stringPreferencesKey(name = "DS_KEY_COUNTRY")
    }
    object AuthKeys {
        val dsKeyAuthProvider = stringPreferencesKey(name = "DS_KEY_AUTH_PROVIDER")
        val dsKeyAuthSessionToken = stringPreferencesKey(name = "DS_KEY_AUTH_SESSION_TOKEN")
    }
}
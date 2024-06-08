package com.thomas200593.mini_retail_app.core.data.local.datastore

import androidx.datastore.preferences.core.stringPreferencesKey

object DataStorePreferencesKeys {
    object AppConfigKeys {
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

    object AuthKeys {
        val dsAuthProvider = stringPreferencesKey(
            name = "DS_AUTH_PROVIDER"
        )
        val dsAuthSessionToken = stringPreferencesKey(
            name = "DS_AUTH_SESSION_TOKEN"
        )
    }
}
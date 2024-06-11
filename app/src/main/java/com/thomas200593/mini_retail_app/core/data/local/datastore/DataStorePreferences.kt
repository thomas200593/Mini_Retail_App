package com.thomas200593.mini_retail_app.core.data.local.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.thomas200593.mini_retail_app.core.data.local.datastore.DataStoreKeys.AppConfigKeys.dsAppConfigCurrency
import com.thomas200593.mini_retail_app.core.data.local.datastore.DataStoreKeys.AppConfigKeys.dsAppConfigDynamicColor
import com.thomas200593.mini_retail_app.core.data.local.datastore.DataStoreKeys.AppConfigKeys.dsAppConfigFontSize
import com.thomas200593.mini_retail_app.core.data.local.datastore.DataStoreKeys.AppConfigKeys.dsAppConfigLanguage
import com.thomas200593.mini_retail_app.core.data.local.datastore.DataStoreKeys.AppConfigKeys.dsAppConfigTheme
import com.thomas200593.mini_retail_app.core.data.local.datastore.DataStoreKeys.AppConfigKeys.dsAppConfigTimezone
import com.thomas200593.mini_retail_app.core.data.local.datastore.DataStoreKeys.AppConfigKeys.dsAppShouldShowOnboardingPages
import com.thomas200593.mini_retail_app.core.data.local.datastore.DataStoreKeys.AuthKeys.dsAuthProvider
import com.thomas200593.mini_retail_app.core.data.local.datastore.DataStoreKeys.AuthKeys.dsAuthSessionToken
import com.thomas200593.mini_retail_app.core.design_system.dispatchers.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.dispatchers.Dispatchers
import com.thomas200593.mini_retail_app.core.util.CurrencyHelper
import com.thomas200593.mini_retail_app.core.util.TimezoneHelper
import com.thomas200593.mini_retail_app.features.app_config.entity.ConfigCurrent
import com.thomas200593.mini_retail_app.features.app_config.entity.Currency
import com.thomas200593.mini_retail_app.features.app_config.entity.DynamicColor
import com.thomas200593.mini_retail_app.features.app_config.entity.DynamicColor.DISABLED
import com.thomas200593.mini_retail_app.features.app_config.entity.Font
import com.thomas200593.mini_retail_app.features.app_config.entity.Font.MEDIUM
import com.thomas200593.mini_retail_app.features.app_config.entity.Language
import com.thomas200593.mini_retail_app.features.app_config.entity.Language.EN
import com.thomas200593.mini_retail_app.features.app_config.entity.Onboarding
import com.thomas200593.mini_retail_app.features.app_config.entity.Onboarding.HIDE
import com.thomas200593.mini_retail_app.features.app_config.entity.Onboarding.SHOW
import com.thomas200593.mini_retail_app.features.app_config.entity.Theme
import com.thomas200593.mini_retail_app.features.app_config.entity.Theme.SYSTEM
import com.thomas200593.mini_retail_app.features.app_config.entity.Timezone
import com.thomas200593.mini_retail_app.features.auth.entity.AuthSessionToken
import com.thomas200593.mini_retail_app.features.auth.entity.OAuthProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DataStorePreferences @Inject constructor(
    private val datastore: DataStore<Preferences>,
    @Dispatcher(Dispatchers.Dispatchers.IO) private val ioDispatcher: CoroutineDispatcher
){
    val configCurrentData = datastore.data
        .map { data ->
            ConfigCurrent(
                showOnboardingPages = data[dsAppShouldShowOnboardingPages] ?.let { showOnboardingPages ->
                    Onboarding.valueOf(showOnboardingPages)
                } ?: SHOW,
                currentTheme = data[dsAppConfigTheme] ?.let { themeString ->
                    Theme.valueOf(themeString)
                } ?: SYSTEM,
                currentDynamicColor = data[dsAppConfigDynamicColor] ?.let { dynamicColor ->
                    DynamicColor.valueOf(dynamicColor)
                } ?: DISABLED,
                currentFontSize = data[dsAppConfigFontSize] ?.let { fontSize ->
                    Font.valueOf(fontSize)
                } ?: MEDIUM,
                currentLanguage = data[dsAppConfigLanguage] ?.let { languageString ->
                    Language.valueOf(languageString)
                } ?: EN,
                currentTimezone = data[dsAppConfigTimezone] ?.let { timezoneOffset ->
                    Timezone(timezoneOffset)
                } ?: TimezoneHelper.TIMEZONE_DEFAULT,
                currentCurrency = data[dsAppConfigCurrency] ?.let { currencyCode ->
                    Currency(currencyCode)
                } ?: CurrencyHelper.CURRENCY_DEFAULT
            )
        }

    suspend fun hideOnboarding() = withContext(ioDispatcher){
        datastore.edit {
            it[dsAppShouldShowOnboardingPages] = HIDE.name
        }
    }

    val authSessionToken = datastore.data
        .flowOn(ioDispatcher)
        .map { data ->
            AuthSessionToken(
                authProvider = data[dsAuthProvider] ?.let { oAuthProvider ->
                    OAuthProvider.valueOf(oAuthProvider)
                } ?: OAuthProvider.GOOGLE,
                idToken = data[dsAuthSessionToken]
            )
        }

    suspend fun clearAuthSessionToken() = withContext((ioDispatcher)){
        datastore.edit {
            it.remove(dsAuthSessionToken)
        }
    }

    suspend fun saveAuthSessionToken(authSessionToken: AuthSessionToken) = withContext(ioDispatcher){
        datastore.edit {
            it[dsAuthSessionToken] = authSessionToken.idToken!!
            it[dsAuthProvider] = authSessionToken.authProvider?.name!!
        }
    }

    suspend fun setThemePreferences(theme: Theme) = withContext(ioDispatcher){
        datastore.edit {
            it[dsAppConfigTheme] = theme.name
        }
    }

    suspend fun setDynamicColorPreferences(dynamicColor: DynamicColor) = withContext(ioDispatcher){
        datastore.edit {
            it[dsAppConfigDynamicColor] = dynamicColor.name
        }
    }

    suspend fun setLanguagePreferences(language: Language) = withContext(ioDispatcher){
        datastore.edit {
            it[dsAppConfigLanguage] = language.name
        }
    }

    suspend fun setTimezonePreferences(timezone: Timezone) = withContext(ioDispatcher){
        datastore.edit {
            it[dsAppConfigTimezone] = timezone.timezoneOffset
        }
    }
}
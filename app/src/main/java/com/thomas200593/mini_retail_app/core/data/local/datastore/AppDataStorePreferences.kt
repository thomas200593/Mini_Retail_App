package com.thomas200593.mini_retail_app.core.data.local.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.thomas200593.mini_retail_app.core.data.local.datastore.AppConfigDataStorePreferencesKeys.dsAppConfigDynamicColor
import com.thomas200593.mini_retail_app.core.data.local.datastore.AppConfigDataStorePreferencesKeys.dsAppConfigFontSize
import com.thomas200593.mini_retail_app.core.data.local.datastore.AppConfigDataStorePreferencesKeys.dsAppConfigLanguage
import com.thomas200593.mini_retail_app.core.data.local.datastore.AppConfigDataStorePreferencesKeys.dsAppConfigTheme
import com.thomas200593.mini_retail_app.core.data.local.datastore.AppConfigDataStorePreferencesKeys.dsAppShouldShowOnboardingPages
import com.thomas200593.mini_retail_app.core.data.local.datastore.AuthDataStorePreferencesKeys.dsAuthProvider
import com.thomas200593.mini_retail_app.core.data.local.datastore.AuthDataStorePreferencesKeys.dsAuthSessionToken
import com.thomas200593.mini_retail_app.core.design_system.dispatchers.AppDispatchers.IO
import com.thomas200593.mini_retail_app.core.design_system.dispatchers.Dispatcher
import com.thomas200593.mini_retail_app.features.app_config.entity.CurrentAppConfig
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
import com.thomas200593.mini_retail_app.features.auth.entity.AuthSessionToken
import com.thomas200593.mini_retail_app.features.auth.entity.OAuthProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AppDataStorePreferences @Inject constructor(
    private val datastore: DataStore<Preferences>,
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher
){
    val currentAppConfigData = datastore.data
        .map { data ->
            CurrentAppConfig(
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
}
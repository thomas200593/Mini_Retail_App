package com.thomas200593.mini_retail_app.core.data.local.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.thomas200593.mini_retail_app.core.data.local.datastore.AppDataStorePreferencesKeys.dsAppConfigDynamicColor
import com.thomas200593.mini_retail_app.core.data.local.datastore.AppDataStorePreferencesKeys.dsAppConfigFontSize
import com.thomas200593.mini_retail_app.core.data.local.datastore.AppDataStorePreferencesKeys.dsAppConfigLanguage
import com.thomas200593.mini_retail_app.core.data.local.datastore.AppDataStorePreferencesKeys.dsAppConfigTheme
import com.thomas200593.mini_retail_app.core.data.local.datastore.AppDataStorePreferencesKeys.dsAppShouldShowOnboardingPages
import com.thomas200593.mini_retail_app.core.design_system.dispatchers.AppDispatchers
import com.thomas200593.mini_retail_app.core.design_system.dispatchers.Dispatcher
import com.thomas200593.mini_retail_app.features.app_config.entity.CurrentAppConfig
import com.thomas200593.mini_retail_app.features.app_config.entity.DynamicColor
import com.thomas200593.mini_retail_app.features.app_config.entity.DynamicColor.DISABLED
import com.thomas200593.mini_retail_app.features.app_config.entity.Font
import com.thomas200593.mini_retail_app.features.app_config.entity.Font.MEDIUM
import com.thomas200593.mini_retail_app.features.app_config.entity.Language
import com.thomas200593.mini_retail_app.features.app_config.entity.Language.EN
import com.thomas200593.mini_retail_app.features.app_config.entity.Onboarding
import com.thomas200593.mini_retail_app.features.app_config.entity.Onboarding.SHOW
import com.thomas200593.mini_retail_app.features.app_config.entity.Theme
import com.thomas200593.mini_retail_app.features.app_config.entity.Theme.SYSTEM
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AppDataStorePreferences @Inject constructor(
    datastore: DataStore<Preferences>,
    @Dispatcher(AppDispatchers.IO) private val ioDispatcher: CoroutineDispatcher
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

    val readUserSession = datastore.data
        .map { data ->
            true
            //TODO correct this
        }

//    suspend fun setAppTheme(appTheme: AppTheme) = withContext(ioDispatcher){
//        datastore.edit {data ->
//            data[AppDataStorePreferencesKeys.dsAppConfigTheme] = appTheme.name
//        }
//    }
}
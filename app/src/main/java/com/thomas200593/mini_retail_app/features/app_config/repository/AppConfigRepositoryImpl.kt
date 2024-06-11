package com.thomas200593.mini_retail_app.features.app_config.repository

import com.thomas200593.mini_retail_app.core.data.local.datastore.DataStorePreferences
import com.thomas200593.mini_retail_app.core.design_system.dispatchers.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.dispatchers.Dispatchers.Dispatchers
import com.thomas200593.mini_retail_app.core.util.TimezoneHelper
import com.thomas200593.mini_retail_app.features.app_config.navigation.ConfigGeneralDestination
import com.thomas200593.mini_retail_app.features.app_config.entity.ConfigCurrent
import com.thomas200593.mini_retail_app.features.app_config.entity.DynamicColor
import com.thomas200593.mini_retail_app.features.app_config.entity.Language
import com.thomas200593.mini_retail_app.features.app_config.entity.Theme
import com.thomas200593.mini_retail_app.features.app_config.entity.Timezone
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class AppConfigRepositoryImpl @Inject constructor(
    private val appDataStore: DataStorePreferences,
    @Dispatcher(Dispatchers.IO) private val ioDispatcher: CoroutineDispatcher
):AppConfigRepository {
    override val configCurrentData: Flow<ConfigCurrent> =
        appDataStore.configCurrentData

    override suspend fun getAppConfigGeneralMenuData(): Set<ConfigGeneralDestination> = withContext(ioDispatcher){
        ConfigGeneralDestination.entries.toSet()
    }

    override suspend fun getThemePreferences(): Set<Theme> = withContext(ioDispatcher){
        Theme.entries.toSet()
    }

    override suspend fun setThemePreferences(theme: Theme) {
        appDataStore.setThemePreferences(theme)
    }

    override suspend fun getDynamicMenuPreferences(): Set<DynamicColor> = withContext(ioDispatcher){
        DynamicColor.entries.toSet()
    }

    override suspend fun setDynamicColorPreferences(dynamicColor: DynamicColor) {
        appDataStore.setDynamicColorPreferences(dynamicColor)
    }

    override suspend fun getLanguagePreferences(): Set<Language> = withContext(ioDispatcher){
        Language.entries.toSet()
    }

    override suspend fun setLanguagePreferences(language: Language) {
        appDataStore.setLanguagePreferences(language)
    }

    override suspend fun getTimezonePreferences(): List<Timezone> = withContext(ioDispatcher){
        TimezoneHelper.getTimezones()
    }

    override suspend fun setTimezonePreferences(timezone: Timezone) {
        appDataStore.setTimezonePreferences(timezone)
    }
}
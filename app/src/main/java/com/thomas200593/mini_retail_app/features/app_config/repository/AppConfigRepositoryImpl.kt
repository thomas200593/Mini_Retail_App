package com.thomas200593.mini_retail_app.features.app_config.repository

import com.thomas200593.mini_retail_app.core.data.local.datastore.DataStorePreferences
import com.thomas200593.mini_retail_app.core.data.local.session.SessionState
import com.thomas200593.mini_retail_app.core.data.local.session.SessionState.Invalid
import com.thomas200593.mini_retail_app.core.data.local.session.SessionState.Loading
import com.thomas200593.mini_retail_app.core.data.local.session.SessionState.Valid
import com.thomas200593.mini_retail_app.core.design_system.dispatchers.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.dispatchers.Dispatchers.Dispatchers.IO
import com.thomas200593.mini_retail_app.features.app_config.entity.ConfigCurrent
import com.thomas200593.mini_retail_app.features.app_config.navigation.DestinationAppConfig
import com.thomas200593.mini_retail_app.features.app_config.navigation.DestinationAppConfig.entries
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class AppConfigRepositoryImpl @Inject constructor(
    dataStore: DataStorePreferences,
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher
):AppConfigRepository {

    override val configCurrent: Flow<ConfigCurrent> = dataStore.configCurrent

    override suspend fun getMenuData(sessionState: SessionState): Set<DestinationAppConfig> =
        withContext(ioDispatcher){
            when(sessionState){
                Loading -> { emptySet() }
                is Invalid -> { entries.filterNot { it.usesAuth }.toSet() }
                is Valid -> { entries.toSet() }
            }
        }
}
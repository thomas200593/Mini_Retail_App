package com.thomas200593.mini_retail_app.features.auth.repository

import com.thomas200593.mini_retail_app.core.data.local.datastore.AppDataStorePreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    appDataStore: AppDataStorePreferences,
): AuthRepository {
    override val isSessionValid: Flow<Boolean> =
        appDataStore.readUserSession
}
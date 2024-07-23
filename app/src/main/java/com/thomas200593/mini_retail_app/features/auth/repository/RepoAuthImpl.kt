package com.thomas200593.mini_retail_app.features.auth.repository

import com.thomas200593.mini_retail_app.core.data.local.datastore.DataStorePreferences
import com.thomas200593.mini_retail_app.core.design_system.util.HlpJwt.GoogleOAuth2.mapToUserData
import com.thomas200593.mini_retail_app.core.design_system.util.HlpJwt.GoogleOAuth2.validateToken
import com.thomas200593.mini_retail_app.features.auth.entity.AuthSessionToken
import com.thomas200593.mini_retail_app.features.auth.entity.OAuthProvider.GOOGLE
import com.thomas200593.mini_retail_app.features.auth.entity.UserData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RepoAuthImpl @Inject constructor(
    private val dataStore: DataStorePreferences
): RepoAuth {
    override val authSessionToken: Flow<AuthSessionToken> = dataStore.authSessionToken

    override suspend fun validateAuthSessionToken(authSessionToken: AuthSessionToken): Boolean {
        return when(authSessionToken.authProvider?.name){
            GOOGLE.name -> { validateToken(authSessionToken) }
            else -> { false }
        }
    }

    override suspend fun clearAuthSessionToken() { dataStore.clearAuthSessionToken() }

    override suspend fun saveAuthSessionToken(authSessionToken: AuthSessionToken)
    { dataStore.saveAuthSessionToken(authSessionToken) }

    override suspend fun mapAuthSessionTokenToUserData(authSessionToken: AuthSessionToken): UserData? {
        return when(authSessionToken.authProvider){
            GOOGLE -> { mapToUserData(authSessionToken) }
            else -> { null }
        }
    }
}
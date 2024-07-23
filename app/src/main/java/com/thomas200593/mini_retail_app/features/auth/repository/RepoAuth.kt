package com.thomas200593.mini_retail_app.features.auth.repository

import com.thomas200593.mini_retail_app.core.data.local.datastore.DataStorePreferences
import com.thomas200593.mini_retail_app.core.design_system.util.HlpJwt
import com.thomas200593.mini_retail_app.features.auth.entity.AuthSessionToken
import com.thomas200593.mini_retail_app.features.auth.entity.OAuthProvider
import com.thomas200593.mini_retail_app.features.auth.entity.UserData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface RepoAuth {
    val authSessionToken: Flow<AuthSessionToken>
    suspend fun validateAuthSessionToken(authSessionToken: AuthSessionToken): Boolean
    suspend fun clearAuthSessionToken()
    suspend fun saveAuthSessionToken(authSessionToken: AuthSessionToken)
    suspend fun mapAuthSessionTokenToUserData(authSessionToken: AuthSessionToken): UserData?
}

class RepoAuthImpl @Inject constructor(
    private val dataStore: DataStorePreferences
): RepoAuth {
    override val authSessionToken: Flow<AuthSessionToken> = dataStore.authSessionToken

    override suspend fun validateAuthSessionToken(authSessionToken: AuthSessionToken): Boolean {
        return when(authSessionToken.authProvider?.name){
            OAuthProvider.GOOGLE.name -> {
                HlpJwt.GoogleOAuth2.validateToken(authSessionToken)
            }
            else -> { false }
        }
    }

    override suspend fun clearAuthSessionToken() { dataStore.clearAuthSessionToken() }

    override suspend fun saveAuthSessionToken(authSessionToken: AuthSessionToken)
    { dataStore.saveAuthSessionToken(authSessionToken) }

    override suspend fun mapAuthSessionTokenToUserData(authSessionToken: AuthSessionToken): UserData? {
        return when(authSessionToken.authProvider){
            OAuthProvider.GOOGLE -> {
                HlpJwt.GoogleOAuth2.mapToUserData(authSessionToken)
            }
            else -> { null }
        }
    }
}
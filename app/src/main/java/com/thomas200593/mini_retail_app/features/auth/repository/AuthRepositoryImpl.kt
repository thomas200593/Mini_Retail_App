package com.thomas200593.mini_retail_app.features.auth.repository

import com.thomas200593.mini_retail_app.core.data.local.datastore.AppDataStorePreferences
import com.thomas200593.mini_retail_app.core.util.JWTHelper
import com.thomas200593.mini_retail_app.core.util.JWTHelper.GoogleOAuth2.validateToken
import com.thomas200593.mini_retail_app.features.auth.entity.AuthSessionToken
import com.thomas200593.mini_retail_app.features.auth.entity.OAuthProvider.GOOGLE
import com.thomas200593.mini_retail_app.features.auth.entity.UserData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val appDataStore: AppDataStorePreferences
): AuthRepository {
    override val authSessionToken: Flow<AuthSessionToken> =
        appDataStore.authSessionToken

    override suspend fun validateAuthSessionToken(authSessionToken: AuthSessionToken): Boolean =
        when(authSessionToken.authProvider?.name){
            GOOGLE.name -> {
                validateToken(authSessionToken)
            }
            else -> {
                false
            }
        }

    override suspend fun clearAuthSessionToken() {
        appDataStore.clearAuthSessionToken()
    }

    override suspend fun saveAuthSessionToken(authSessionToken: AuthSessionToken){
        appDataStore.saveAuthSessionToken(authSessionToken)
    }

    override suspend fun mapAuthSessionTokenToUserData(authSessionToken: AuthSessionToken): UserData? =
        when(authSessionToken.authProvider){
            GOOGLE -> {
                val userData = JWTHelper.GoogleOAuth2.mapToUserData(authSessionToken)
                userData
            }
            else -> {
                null
            }
        }
}
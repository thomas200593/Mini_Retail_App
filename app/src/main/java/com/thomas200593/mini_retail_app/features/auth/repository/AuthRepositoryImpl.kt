package com.thomas200593.mini_retail_app.features.auth.repository

import com.thomas200593.mini_retail_app.core.data.local.datastore.DataStorePreferences
import com.thomas200593.mini_retail_app.core.util.JWTHelper
import com.thomas200593.mini_retail_app.core.util.JWTHelper.GoogleOAuth2.validateToken
import com.thomas200593.mini_retail_app.features.auth.entity.AuthSessionToken
import com.thomas200593.mini_retail_app.features.auth.entity.OAuthProvider.GOOGLE
import com.thomas200593.mini_retail_app.features.auth.entity.UserData
import kotlinx.coroutines.flow.Flow
import timber.log.Timber
import javax.inject.Inject

private val TAG = AuthRepositoryImpl::class.simpleName

class AuthRepositoryImpl @Inject constructor(
    private val appDataStore: DataStorePreferences
): AuthRepository {
    override val authSessionToken: Flow<AuthSessionToken> =
        appDataStore.authSessionToken

    override suspend fun validateAuthSessionToken(authSessionToken: AuthSessionToken): Boolean {
        Timber.d("Called : fun $TAG.validateAuthSessionToken()")
        return when(authSessionToken.authProvider?.name){
            GOOGLE.name -> {
                validateToken(authSessionToken)
            }
            else -> {
                false
            }
        }
    }

    override suspend fun clearAuthSessionToken() {
        Timber.d("Called : fun $TAG.clearAuthSessionToken()")
        appDataStore.clearAuthSessionToken()
    }

    override suspend fun saveAuthSessionToken(authSessionToken: AuthSessionToken){
        Timber.d("Called : fun $TAG.saveAuthSessionToken()")
        appDataStore.saveAuthSessionToken(authSessionToken)
    }

    override suspend fun mapAuthSessionTokenToUserData(authSessionToken: AuthSessionToken): UserData? {
        Timber.d("Called : fun $TAG.mapAuthSessionTokenToUserData()")
        return when(authSessionToken.authProvider){
            GOOGLE -> {
                val userData = JWTHelper.GoogleOAuth2.mapToUserData(authSessionToken)
                userData
            }
            else -> {
                null
            }
        }
    }
}
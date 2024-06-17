package com.thomas200593.mini_retail_app.features.auth.repository

import com.thomas200593.mini_retail_app.core.data.local.datastore.DataStorePreferences
import com.thomas200593.mini_retail_app.core.design_system.util.RequestState
import com.thomas200593.mini_retail_app.core.util.JWTHelper
import com.thomas200593.mini_retail_app.core.util.JWTHelper.GoogleOAuth2.validateToken
import com.thomas200593.mini_retail_app.features.auth.entity.AuthSessionToken
import com.thomas200593.mini_retail_app.features.auth.entity.OAuthProvider.GOOGLE
import com.thomas200593.mini_retail_app.features.auth.entity.UserData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val appDataStore: DataStorePreferences
): AuthRepository {
    override val authSessionToken: Flow<AuthSessionToken> =
        appDataStore.authSessionToken

    override val currentUserSession: Flow<RequestState<UserData?>> =
        authSessionToken
            .catch {
                RequestState.Error(it)
            }
            .map { authToken ->
                if (validateAuthSessionToken(authToken)) {
                    val userData = mapAuthSessionTokenToUserData(authToken)
                    RequestState.Success(userData)
                } else {
                    RequestState.Success(null)
                }
            }

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
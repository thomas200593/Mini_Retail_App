package com.thomas200593.mini_retail_app.features.auth.repository

import com.thomas200593.mini_retail_app.core.design_system.util.RequestState
import com.thomas200593.mini_retail_app.features.auth.entity.AuthSessionToken
import com.thomas200593.mini_retail_app.features.auth.entity.UserData
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    val authSessionToken: Flow<AuthSessionToken>
    val currentUserSession: Flow<RequestState<UserData?>>
    suspend fun validateAuthSessionToken(authSessionToken: AuthSessionToken): Boolean
    suspend fun clearAuthSessionToken()
    suspend fun saveAuthSessionToken(authSessionToken: AuthSessionToken)
    suspend fun mapAuthSessionTokenToUserData(authSessionToken: AuthSessionToken): UserData?
}
package com.thomas200593.mini_retail_app.features.auth.repository

import com.thomas200593.mini_retail_app.features.auth.entity.AuthSessionToken
import com.thomas200593.mini_retail_app.features.auth.entity.UserData
import kotlinx.coroutines.flow.Flow

interface RepoAuth {
    val authSessionToken: Flow<AuthSessionToken>
    suspend fun validateAuthSessionToken(authSessionToken: AuthSessionToken): Boolean
    suspend fun clearAuthSessionToken()
    suspend fun saveAuthSessionToken(authSessionToken: AuthSessionToken)
    suspend fun mapAuthSessionTokenToUserData(authSessionToken: AuthSessionToken): UserData?
}
package com.thomas200593.mini_retail_app.features.auth.repository

import com.thomas200593.mini_retail_app.features.auth.entity.AuthSessionToken
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    val authState : Flow<Boolean>
    val authSessionToken: Flow<AuthSessionToken>
    suspend fun validateAuthSessionToken(authSessionToken: AuthSessionToken): Boolean
}
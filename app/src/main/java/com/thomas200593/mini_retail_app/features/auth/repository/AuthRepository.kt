package com.thomas200593.mini_retail_app.features.auth.repository

import android.content.Context
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialResponse
import com.thomas200593.mini_retail_app.core.design_system.util.Response
import com.thomas200593.mini_retail_app.features.auth.entity.AuthSessionToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

typealias AuthCredMgrSIWGResponse = Response<GetCredentialResponse>
interface AuthRepository {
    val authState : Flow<Boolean>
    val authSessionToken: Flow<AuthSessionToken>
    suspend fun validateAuthSessionToken(authSessionToken: AuthSessionToken): Boolean
    suspend fun saveAuthState(authState: Boolean)
    suspend fun beginSignInWithGoogle(context: Context, coroutineScope: CoroutineScope): Result<AuthSessionToken>
}
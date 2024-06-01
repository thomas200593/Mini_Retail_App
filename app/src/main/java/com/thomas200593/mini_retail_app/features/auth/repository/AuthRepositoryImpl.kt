package com.thomas200593.mini_retail_app.features.auth.repository

import androidx.core.content.ContentProviderCompat
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.thomas200593.mini_retail_app.BuildConfig
import com.thomas200593.mini_retail_app.core.data.local.datastore.AppDataStorePreferences
import com.thomas200593.mini_retail_app.core.util.JWTHelper
import com.thomas200593.mini_retail_app.features.auth.entity.AuthSessionToken
import kotlinx.coroutines.flow.Flow
import java.security.MessageDigest
import java.util.UUID
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val appDataStore: AppDataStorePreferences
): AuthRepository {

    override val authState: Flow<Boolean> =
        appDataStore.authState

    override val authSessionToken: Flow<AuthSessionToken> =
        appDataStore.authSessionToken

    override suspend fun validateAuthSessionToken(authSessionToken: AuthSessionToken) =
        JWTHelper.validateJWTToken(authSessionToken.idToken.orEmpty())

    override suspend fun saveAuthState(authState: Boolean){
        appDataStore.saveAuthState(authState)
    }


}
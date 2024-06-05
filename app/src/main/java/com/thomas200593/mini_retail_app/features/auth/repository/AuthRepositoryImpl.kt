package com.thomas200593.mini_retail_app.features.auth.repository

import android.content.Context
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.thomas200593.mini_retail_app.BuildConfig
import com.thomas200593.mini_retail_app.core.data.local.datastore.AppDataStorePreferences
import com.thomas200593.mini_retail_app.core.design_system.util.RequestState
import com.thomas200593.mini_retail_app.core.util.JWTHelper
import com.thomas200593.mini_retail_app.features.auth.entity.AuthSessionToken
import kotlinx.coroutines.flow.Flow
import timber.log.Timber
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

    suspend fun authenticateWithGoogle(context: Context): RequestState<String> {
        try {
            val credentialManager = CredentialManager.create(context = context)
            val googleIdOpt = GetGoogleIdOption.Builder()
                .setFilterByAuthorizedAccounts(false)
                .setAutoSelectEnabled(false)
                .setServerClientId(BuildConfig.GOOGLE_AUTH_WEB_ID)
                .build()
            val request = GetCredentialRequest.Builder()
                .addCredentialOption(googleIdOpt)
                .build()
            val result = credentialManager.getCredential(request = request, context = context)
            val credential = result.credential
            val googleIdCredential = GoogleIdTokenCredential.createFrom(credential.data)
            val idToken = googleIdCredential.idToken
            return RequestState.Success(idToken)
        }catch (e: Exception){
            Timber.e("authenticateWithGoogle() : Exception -> %s",e)
            return RequestState.Error(e)
        }
    }
}
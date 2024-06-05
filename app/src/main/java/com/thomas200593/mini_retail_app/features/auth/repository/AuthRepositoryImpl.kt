package com.thomas200593.mini_retail_app.features.auth.repository

import android.content.Context
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.thomas200593.mini_retail_app.BuildConfig
import com.thomas200593.mini_retail_app.core.data.local.datastore.AppDataStorePreferences
import com.thomas200593.mini_retail_app.core.design_system.util.Response
import com.thomas200593.mini_retail_app.core.util.JWTHelper
import com.thomas200593.mini_retail_app.features.auth.entity.AuthSessionToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
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

    override suspend fun saveAuthState(authState: Boolean){
        appDataStore.saveAuthState(authState)
    }

    override suspend fun beginSignInWithGoogle(context: Context, coroutineScope: CoroutineScope): Result<AuthSessionToken> {
        val credentialManager = CredentialManager.create(context)
        return withContext(coroutineScope.coroutineContext){
            try {
                val googleIdOpt = GetGoogleIdOption.Builder()
                    .setFilterByAuthorizedAccounts(false)
                    .setServerClientId(BuildConfig.GOOGLE_AUTH_WEB_ID).build()
                val request = GetCredentialRequest.Builder()
                    .addCredentialOption(googleIdOpt).build()
                val result = credentialManager.getCredential(request = request, context = context)
                val credential = result.credential
                val googleIdCredential = GoogleIdTokenCredential.createFrom(credential.data)
                val googleIdToken = googleIdCredential.idToken
                Result.success(
                    AuthSessionToken(
                        authProvider = "GOOGLE_OAUTH2_TOKEN",
                        idToken = googleIdToken
                    )
                )
            } catch (e: Exception) {
                Timber.e(e)
                Result.failure(e)
            }
        }
    }

    override suspend fun authenticateWithGoogle(context: Context): Response<String> {
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
            Timber.d("Data: %s", googleIdCredential.data)
            Timber.d("ID Token: %s", idToken)
            Timber.d("ID: %s", googleIdCredential.id)
            Timber.d("Type: %s", googleIdCredential.type)
            Timber.d("Given Name: %s", googleIdCredential.givenName)
            Timber.d("Family Name: %s", googleIdCredential.familyName)
            Timber.d("Display Name: %s", googleIdCredential.displayName)
            Timber.d("Phone Number: %s", googleIdCredential.phoneNumber)
            Timber.d("Profile Picture URI: %s", googleIdCredential.profilePictureUri)
            return Response.Success(idToken)
        }catch (e: Exception){
            Timber.e("authenticateWithGoogle() : Exception -> %s",e)
            return Response.Error(e)
        }
    }
}
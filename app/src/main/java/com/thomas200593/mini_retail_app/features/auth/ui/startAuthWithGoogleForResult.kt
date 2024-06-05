package com.thomas200593.mini_retail_app.features.auth.ui

import android.app.Activity
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialCancellationException
import androidx.credentials.exceptions.GetCredentialException
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.thomas200593.mini_retail_app.BuildConfig
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

suspend fun startAuthWithGoogleForResult(
    activityContext: Activity,
    coroutineScope: CoroutineScope,
    onResultReceived: (GoogleIdTokenCredential) -> Unit,
    onError: (Throwable) -> Unit,
    onDialogDismissed: (Throwable) -> Unit
){
    val credentialManager = CredentialManager.create(activityContext)
    val googleIdOptions = GetGoogleIdOption.Builder()
        .setFilterByAuthorizedAccounts(false)
        //.setNonce("") //TODO Generate Nonce to prevent Replay Attack
        .setAutoSelectEnabled(false)
        .setServerClientId(BuildConfig.GOOGLE_AUTH_WEB_ID)
        .build()
    val request = GetCredentialRequest.Builder()
        .addCredentialOption(googleIdOptions)
        .build()
    coroutineScope.launch {
        try{
            val result = credentialManager.getCredential(context = activityContext, request = request)
            val credential = result.credential
            val googleIdCredential = GoogleIdTokenCredential.createFrom(credential.data)
            onResultReceived(googleIdCredential)
        }catch (e: GetCredentialException){
            onError(e)
        }catch (e: GoogleIdTokenParsingException){
            onError(e)
        }catch (e: GetCredentialCancellationException){
            onDialogDismissed(e)
        }
    }
}
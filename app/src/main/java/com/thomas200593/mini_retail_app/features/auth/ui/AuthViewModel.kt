package com.thomas200593.mini_retail_app.features.auth.ui

import android.app.Activity
import androidx.activity.result.IntentSenderRequest
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.thomas200593.mini_retail_app.BuildConfig
import com.thomas200593.mini_retail_app.core.design_system.dispatchers.AppDispatchers
import com.thomas200593.mini_retail_app.core.design_system.dispatchers.Dispatcher
import com.thomas200593.mini_retail_app.features.auth.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject
import kotlin.jvm.internal.Intrinsics.Kotlin

@HiltViewModel
class AuthViewModel @Inject constructor(
    @Dispatcher(AppDispatchers.IO) val ioDispatcher: CoroutineDispatcher,
    private val authRepository: AuthRepository
): ViewModel() {
    val authState = authRepository
        .authState
        .flowOn(ioDispatcher)
        .onEach {
            Timber.d("authState (${System.currentTimeMillis()}): ", it)
        }
        .stateIn(
            initialValue = false,
            scope = viewModelScope,
            started = SharingStarted.Eagerly
        )

    fun saveAuthState(authState: Boolean) = viewModelScope.launch {
        authRepository.saveAuthState(authState)
    }

    fun handleSignIn(
        activity: Activity,
        launchActivityResult: (IntentSenderRequest) -> Unit,
        accountNotFound: (Exception) -> Unit
    ) {
        authCredMgrSIWG()
    }

    private fun authCredMgrSIWG() {
        val googleIdOption: GetGoogleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false)
            .setServerClientId(BuildConfig.GOOGLE_AUTH_WEB_ID)
            .build()
    }
}
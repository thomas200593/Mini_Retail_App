package com.thomas200593.mini_retail_app.features.auth.ui

import androidx.credentials.CredentialManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thomas200593.mini_retail_app.core.design_system.dispatchers.AppDispatchers
import com.thomas200593.mini_retail_app.core.design_system.dispatchers.Dispatcher
import com.thomas200593.mini_retail_app.features.auth.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    @Dispatcher(AppDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    private val authRepository: AuthRepository
): ViewModel() {

    private val _idToken = MutableStateFlow("")
    val token: StateFlow<String> = _idToken

    fun updateToken(googleIdToken: String) {
        _idToken.value = googleIdToken
    }

    val authState = authRepository.authState.flowOn(ioDispatcher)
        .onEach { Timber.d("authState (${System.currentTimeMillis()}): ", it) }
        .stateIn(
            initialValue = false,
            scope = viewModelScope,
            started = SharingStarted.Eagerly
        )
}
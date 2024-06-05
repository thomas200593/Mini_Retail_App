package com.thomas200593.mini_retail_app.features.auth.ui

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.thomas200593.mini_retail_app.core.design_system.dispatchers.AppDispatchers
import com.thomas200593.mini_retail_app.core.design_system.dispatchers.Dispatcher
import com.thomas200593.mini_retail_app.features.auth.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    @Dispatcher(AppDispatchers.IO) private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _authState = MutableStateFlow(false)
    val authState = _authState

    private val _idToken = MutableStateFlow("")
    val idToken = _idToken

    fun updateAuthState(authState: Boolean) {
        _authState.value = authState
    }

    fun verifyTokenBackend(it: GoogleIdTokenCredential) = viewModelScope.launch{
        _idToken.value = it.idToken
    }

    fun dismissToken(it: Throwable) = viewModelScope.launch{
        _idToken.value = it.toString()
    }

    fun errorToken(it: Throwable) = viewModelScope.launch{
        _idToken.value = it.toString()
    }
}
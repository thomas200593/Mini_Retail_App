package com.thomas200593.mini_retail_app.features.auth.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thomas200593.mini_retail_app.core.design_system.dispatchers.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.dispatchers.Dispatchers
import com.thomas200593.mini_retail_app.core.design_system.util.RequestState
import com.thomas200593.mini_retail_app.core.design_system.util.RequestState.Idle
import com.thomas200593.mini_retail_app.core.design_system.util.RequestState.Loading
import com.thomas200593.mini_retail_app.features.auth.entity.AuthSessionToken
import com.thomas200593.mini_retail_app.features.auth.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

private val TAG = AuthViewModel::class.simpleName

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    @Dispatcher(Dispatchers.Dispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    @Dispatcher(Dispatchers.Dispatchers.Default) private val defaultDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _authSessionTokenState: MutableState<RequestState<AuthSessionToken>> = mutableStateOf(Idle)
    val authSessionTokenState = _authSessionTokenState

    private val _stateSIWGButton = MutableStateFlow(false)
    val stateSIWGButton = _stateSIWGButton

    fun onOpen() = viewModelScope.launch(ioDispatcher) {
        Timber.d("Called : fun $TAG.onOpen()")
        clearAuthSessionToken()
    }

    fun verifyAndSaveAuthSession(authSessionToken: AuthSessionToken) = viewModelScope.launch(ioDispatcher) {
        Timber.d("Called : fun $TAG.verifyAndSaveAuthSession()")
        updateAuthSIWGButtonState(true)
        _authSessionTokenState.value = Loading
        if(authRepository.validateAuthSessionToken(authSessionToken)){
            authRepository.saveAuthSessionToken(authSessionToken)
            withContext(defaultDispatcher) {
                _authSessionTokenState.value = RequestState.Success(authSessionToken)
            }
        }else{
            authRepository.clearAuthSessionToken()
        }
    }

    fun updateAuthSIWGButtonState(authState: Boolean) = viewModelScope.launch(ioDispatcher) {
        Timber.d("Called : fun $TAG.updateAuthSIWGButtonState()")
        _stateSIWGButton.value = authState
    }

    fun clearAuthSessionToken() = viewModelScope.launch(ioDispatcher) {
        Timber.d("Called : fun $TAG.clearAuthSessionToken()")
        updateAuthSIWGButtonState(false)
        authRepository.clearAuthSessionToken()
    }

    suspend fun mapAuthSessionTokenToUserData(authSessionToken: AuthSessionToken) = withContext(ioDispatcher){
        Timber.d("Called : fun $TAG.mapAuthSessionTokenToUserData()")
        authRepository.mapAuthSessionTokenToUserData(authSessionToken)
    }
}
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
import com.thomas200593.mini_retail_app.features.auth.domain.ValidateAuthSessionUseCase
import com.thomas200593.mini_retail_app.features.auth.entity.AuthSessionToken
import com.thomas200593.mini_retail_app.features.auth.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: AuthRepository,
    private val authUseCase: ValidateAuthSessionUseCase,
    @Dispatcher(Dispatchers.Dispatchers.IO) private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _authSessionTokenState: MutableState<RequestState<AuthSessionToken>> = mutableStateOf(Idle)
    val authSessionTokenState = _authSessionTokenState

    private val _stateSIWGButton = MutableStateFlow(false)
    val stateSIWGButton = _stateSIWGButton

    fun onOpen() = viewModelScope.launch(ioDispatcher) {
        clearAuthSessionToken()
    }

    fun verifyAndSaveAuthSession(authSessionToken: AuthSessionToken) = viewModelScope.launch(ioDispatcher) {
        updateAuthSIWGButtonState(true)
        _authSessionTokenState.value = Loading
        if(authUseCase.invoke(authSessionToken)){
            _authSessionTokenState.value = RequestState.Success(authSessionToken)
        }else{
            clearAuthSessionToken()
        }
    }

    fun updateAuthSIWGButtonState(authState: Boolean) = viewModelScope.launch(ioDispatcher) {
        _stateSIWGButton.value = authState
    }

    fun clearAuthSessionToken() = viewModelScope.launch(ioDispatcher) {
        updateAuthSIWGButtonState(false)
        repository.clearAuthSessionToken()
    }
}
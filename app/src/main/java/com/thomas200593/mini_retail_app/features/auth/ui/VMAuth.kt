package com.thomas200593.mini_retail_app.features.auth.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatchers.Dispatchers.IO
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState.Idle
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState.Loading
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState.Success
import com.thomas200593.mini_retail_app.features.auth.domain.UCValidateAuthSession
import com.thomas200593.mini_retail_app.features.auth.entity.AuthSessionToken
import com.thomas200593.mini_retail_app.features.auth.repository.RepoAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VMAuth @Inject constructor(
    private val repoAuth: RepoAuth,
    private val ucValidateAuthSession: UCValidateAuthSession,
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _authSessionTokenState: MutableState<ResourceState<AuthSessionToken>> = mutableStateOf(Idle)
    val authSessionTokenState = _authSessionTokenState

    private val _stateSIWGButton = MutableStateFlow(false)
    val stateSIWGButton = _stateSIWGButton

    fun onOpen() = viewModelScope.launch(ioDispatcher) { clearAuthSessionToken() }

    fun verifyAndSaveAuthSession(authSessionToken: AuthSessionToken) = viewModelScope.launch(ioDispatcher) {
        updateAuthSIWGButtonState(true)
        _authSessionTokenState.value = Loading
        if(ucValidateAuthSession.invoke(authSessionToken)){
            _authSessionTokenState.value = Success(authSessionToken)
        }else{
            clearAuthSessionToken()
        }
    }

    fun updateAuthSIWGButtonState(authState: Boolean) =
        viewModelScope.launch(ioDispatcher) { _stateSIWGButton.value = authState }

    fun clearAuthSessionToken() = viewModelScope
        .launch(ioDispatcher) { updateAuthSIWGButtonState(false); repoAuth.clearAuthSessionToken() }
}
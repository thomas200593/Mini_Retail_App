package com.thomas200593.mini_retail_app.features.auth.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thomas200593.mini_retail_app.core.design_system.dispatchers.AppDispatchers
import com.thomas200593.mini_retail_app.core.design_system.dispatchers.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.util.Response
import com.thomas200593.mini_retail_app.core.util.JWTHelper
import com.thomas200593.mini_retail_app.features.auth.entity.AuthSessionToken
import com.thomas200593.mini_retail_app.features.auth.repository.AuthRepository
import com.thomas200593.mini_retail_app.features.auth.ui.AuthResultUiState.Idle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    @Dispatcher(AppDispatchers.IO) private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {
    val authState = authRepository.authState.flowOn(ioDispatcher)
        .stateIn(
            scope = viewModelScope,
            initialValue = false,
            started = SharingStarted.Eagerly
        )

    private val _authResult: MutableStateFlow<AuthResultUiState> = MutableStateFlow(Idle)
    val authResult = _authResult

    fun saveAuthState(authState: Boolean) = viewModelScope.launch {
        authRepository.saveAuthState(authState)
    }

    fun authenticateWithGoogle(context: Context) {
        viewModelScope.launch{
            _authResult.value = AuthResultUiState.Loading
            val authSessionToken = AuthSessionToken(
                authProvider = "GOOGLE_OAUTH2_TOKEN",
                idToken = (authRepository.authenticateWithGoogle(context) as Response.Success).data.orEmpty()
            )
            _authResult.value = AuthResultUiState.AuthResultReceived(authSessionToken)
            val isValidToken = withContext(ioDispatcher){
                _authResult.value = AuthResultUiState.ValidatingAuthToken
                JWTHelper.validateJWTToken(authSessionToken.idToken.orEmpty())
            }
            _authResult.value = if(isValidToken){
                AuthResultUiState.AuthTokenValid(authSessionToken)
            }else{
                AuthResultUiState.Error(e = Exception())
            }
        }
    }

//    private val _idToken = MutableStateFlow(AuthSessionToken())
//    val idToken = _idToken.asStateFlow()
//    fun authSignInWithGoogle(context: Context, coroutineScope: CoroutineScope) =
//        viewModelScope.launch {
//
//            val authSessionToken = authRepository.beginSignInWithGoogle(context, coroutineScope)
//                .getOrDefault(AuthSessionToken())
//            val isValidToken = withContext(ioDispatcher){
//                JWTHelper.validateJWTToken(authSessionToken.idToken.orEmpty())
//            }
//
//            _idToken.value = if (isValidToken){
//                AuthSessionToken(
//                    "VALID_GOOGLE_OAUTH2_TOKEN",
//                    authSessionToken.idToken
//                )
//            }else{
//                AuthSessionToken(
//                    "INVALID_GOOGLE_OAUTH2_TOKEN",
//                    null
//                )
//            }
//        }
}

sealed interface AuthResultUiState{
    data object Idle: AuthResultUiState
    data object Loading: AuthResultUiState
    data class AuthResultReceived(val authSessionToken: AuthSessionToken): AuthResultUiState
    data object ValidatingAuthToken: AuthResultUiState
    data class AuthTokenValid(val authSessionToken: AuthSessionToken): AuthResultUiState
    data class Error(val e: Throwable): AuthResultUiState
}
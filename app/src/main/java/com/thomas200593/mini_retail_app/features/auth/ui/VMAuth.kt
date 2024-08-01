package com.thomas200593.mini_retail_app.features.auth.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatchers.Dispatchers.IO
import com.thomas200593.mini_retail_app.core.design_system.util.HlpStateFlow.update
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState.Error
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState.Idle
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState.Loading
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState.Success
import com.thomas200593.mini_retail_app.features.auth.domain.UCValidateAuthSession
import com.thomas200593.mini_retail_app.features.auth.entity.AuthSessionToken
import com.thomas200593.mini_retail_app.features.auth.repository.RepoAuth
import com.thomas200593.mini_retail_app.features.auth.ui.VMAuth.UiEvents.BtnAuthWithGoogle
import com.thomas200593.mini_retail_app.features.auth.ui.VMAuth.UiEvents.ScreenEvents.OnOpen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VMAuth @Inject constructor(
    private val repoAuth: RepoAuth,
    private val ucValidateAuthSession: UCValidateAuthSession,
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher
): ViewModel() {
    data class UiState(
        val dialogState: DialogState = DialogState(),
        val authVldState: ResourceState<AuthSessionToken> = Idle,
        val authBtnGoogleState: AuthBtnGoogleState = AuthBtnGoogleState()
    )
    data class AuthBtnGoogleState(
        val uiInProgress: Boolean = false
    )
    data class DialogState(
        val uiEnableLoadingDialog: MutableState<Boolean> = mutableStateOf(false),
        val uiEnableSuccessDialog: MutableState<Boolean> = mutableStateOf(false),
        val uiEnableErrorDialog: MutableState<Boolean> = mutableStateOf(false)
    )
    sealed class UiEvents{
        sealed class ScreenEvents: UiEvents() {
            data object OnOpen: ScreenEvents()
        }
        sealed class BtnAuthWithGoogle: UiEvents() {
            data object OnClick: BtnAuthWithGoogle()
            data class OnValidationAuthSession(val authSessionToken: AuthSessionToken): BtnAuthWithGoogle()
        }
    }

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    fun onEvent(events: UiEvents) {
        when(events){
            OnOpen -> initialization()
            BtnAuthWithGoogle.OnClick -> handleGoogleButtonClick()
            is BtnAuthWithGoogle.OnValidationAuthSession -> handleAuthVldResult(events.authSessionToken)
        }
    }
    private fun handleAuthVldResult(authSessionToken: AuthSessionToken) =
        viewModelScope.launch(ioDispatcher) {
            updateDialogState(loading = true, success = false, error = false)
            _uiState.update { it.copy(authVldState = Loading) }
            try{
                if(ucValidateAuthSession.invoke(authSessionToken)) {
                    updateDialogState(loading = false, success = true, error = false)
                    _uiState.update { it.copy(authVldState = Success(authSessionToken)) }
                }
                else{
                    updateDialogState(loading = false, success = false, error = true)
                    _uiState.update {
                        it.copy(
                            authVldState = Error(Throwable("Cannot authenticate with Google")),
                            authBtnGoogleState = AuthBtnGoogleState()
                        )
                    }
                }
            } catch (e: Throwable) {
                updateDialogState(loading = false, error = true, success = false)
                _uiState.update { it.copy(authVldState = Error(e)) }
            }
        }
    private fun initialization() = viewModelScope.launch(ioDispatcher) {
        _uiState.update {
            it.copy(
                authBtnGoogleState = AuthBtnGoogleState(),
                authVldState = Idle, dialogState = DialogState()
            )
        }
        repoAuth.clearAuthSessionToken()
    }
    private fun handleGoogleButtonClick() {
        _uiState.update {
            it.copy(authBtnGoogleState = it.authBtnGoogleState.copy(uiInProgress = true))
        }
    }
    private fun updateDialogState(
        loading: Boolean = false,
        success: Boolean = false,
        error: Boolean = false
    ) {
        _uiState.update {
            it.copy(
                dialogState = it.dialogState.copy(
                    uiEnableLoadingDialog = mutableStateOf(loading),
                    uiEnableSuccessDialog = mutableStateOf(success),
                    uiEnableErrorDialog = mutableStateOf(error)
                )
            )
        }
    }
}
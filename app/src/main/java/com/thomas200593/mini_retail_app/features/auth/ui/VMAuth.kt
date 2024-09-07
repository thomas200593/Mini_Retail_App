package com.thomas200593.mini_retail_app.features.auth.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatchers.Dispatchers.IO
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.di.Dispatcher
import com.thomas200593.mini_retail_app.features.auth.domain.UCValidateAuthSessionAndSave
import com.thomas200593.mini_retail_app.features.auth.entity.AuthSessionToken
import com.thomas200593.mini_retail_app.features.auth.repository.RepoAuth
import com.thomas200593.mini_retail_app.features.auth.ui.VMAuth.AuthValidationResult.Idle
import com.thomas200593.mini_retail_app.features.auth.ui.VMAuth.UiEvents.ButtonEvents.BtnAppConfigEvents
import com.thomas200593.mini_retail_app.features.auth.ui.VMAuth.UiEvents.ButtonEvents.BtnAuthGoogleEvents
import com.thomas200593.mini_retail_app.features.auth.ui.VMAuth.UiEvents.ButtonEvents.BtnTncEvents
import com.thomas200593.mini_retail_app.features.auth.ui.VMAuth.UiEvents.DialogEvents.DlgAuthFailedEvent
import com.thomas200593.mini_retail_app.features.auth.ui.VMAuth.UiEvents.DialogEvents.DlgAuthSuccessEvent
import com.thomas200593.mini_retail_app.features.auth.ui.VMAuth.UiEvents.OnOpenEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VMAuth @Inject constructor(
    private val repoAuth: RepoAuth,
    private val ucValidateAuthSessionAndSave: UCValidateAuthSessionAndSave,
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher
): ViewModel() {
    data class UiState(
        val dialogState: DialogState = DialogState(),
        val btnGoogleUiState: BtnGoogleUiState = BtnGoogleUiState(),
        val authValidationResult: AuthValidationResult = Idle
    )
    sealed interface AuthValidationResult {
        data object Idle: AuthValidationResult
        data object Loading: AuthValidationResult
        data class Success(val authSessionToken: AuthSessionToken): AuthValidationResult
        data class Error(val throwable: Throwable): AuthValidationResult
    }
    data class DialogState(
        val dlgAuthLoading: MutableState<Boolean> = mutableStateOf(false),
        val dlgAuthSuccess: MutableState<Boolean> = mutableStateOf(false),
        val dlgAuthError: MutableState<Boolean> = mutableStateOf(false)
    )
    data class BtnGoogleUiState(
        val loading: Boolean = false
    )
    sealed interface UiEvents {
        data object OnOpenEvents: UiEvents
        sealed interface ButtonEvents: UiEvents {
            sealed interface BtnAppConfigEvents: ButtonEvents {
                data object OnClick: BtnAppConfigEvents
            }
            sealed interface BtnAuthGoogleEvents: ButtonEvents {
                data object OnClick: BtnAuthGoogleEvents
                data class OnResultReceived(val authSessionToken: AuthSessionToken): BtnAuthGoogleEvents
                data class OnResultError(val throwable: Throwable): BtnAuthGoogleEvents
                data class OnDismissed(val throwable: Throwable): BtnAuthGoogleEvents
            }
            sealed interface BtnTncEvents: ButtonEvents {
                data object OnClick: BtnTncEvents
            }
        }
        sealed interface DialogEvents: UiEvents {
            sealed interface DlgAuthSuccessEvent: DialogEvents {
                data object OnConfirm: DlgAuthSuccessEvent
            }
            sealed interface DlgAuthFailedEvent: DialogEvents {
                data object OnDismissed: DlgAuthFailedEvent
            }
        }
    }

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    fun onEvent(events: UiEvents) {
        when(events) {
            is OnOpenEvents -> onOpenEvent()
            is BtnAppConfigEvents.OnClick -> onOpenEvent()
            is BtnAuthGoogleEvents.OnClick -> btnAuthGoogleOnClickEvent()
            is BtnAuthGoogleEvents.OnResultReceived ->
                btnAuthGoogleOnResultReceivedEvent(events.authSessionToken)
            is BtnAuthGoogleEvents.OnResultError -> btnAuthGoogleOnResultErrorEvent(events.throwable)
            is BtnAuthGoogleEvents.OnDismissed -> btnAuthGoogleOnResultDismissed(events.throwable)
            is BtnTncEvents.OnClick -> onOpenEvent()
            is DlgAuthSuccessEvent.OnConfirm -> btnAuthGoogleOnConfirmEvent()
            is DlgAuthFailedEvent.OnDismissed -> onOpenEvent()
        }
    }

    private fun updateDialogState(
        dlgAuthLoading: Boolean = false,
        dlgAuthSuccess: Boolean = false,
        dlgAuthError: Boolean = false
    ) = _uiState.update { it.copy(
        dialogState = it.dialogState.copy(
            dlgAuthLoading = mutableStateOf(dlgAuthLoading),
            dlgAuthSuccess = mutableStateOf(dlgAuthSuccess),
            dlgAuthError = mutableStateOf(dlgAuthError)
        )
    ) }
    private fun resetDialogState() = _uiState.update { it.copy(dialogState = DialogState()) }
    private fun resetBtnGoogleUiState() = _uiState.update { it.copy(btnGoogleUiState = BtnGoogleUiState()) }
    private fun resetAuthResultState() = _uiState.update { it.copy(authValidationResult = Idle) }
    private fun onOpenEvent() = viewModelScope.launch(ioDispatcher) {
        repoAuth.clearAuthSessionToken()
            .also { resetBtnGoogleUiState(); resetDialogState(); resetAuthResultState() }
    }
    private fun btnAuthGoogleOnClickEvent() = _uiState.update {
        it.copy(
            btnGoogleUiState = it.btnGoogleUiState.copy(
                loading = true
            )
        )
    }
    private fun btnAuthGoogleOnResultReceivedEvent(authSessionToken: AuthSessionToken) {
        updateDialogState(dlgAuthLoading = true)
        _uiState.update {
            it.copy(
                authValidationResult = AuthValidationResult.Loading
            )
        }
        viewModelScope.launch(ioDispatcher) {
            if(ucValidateAuthSessionAndSave.invoke(authSessionToken)) {
                _uiState.update {
                    it.copy(
                        authValidationResult = AuthValidationResult.Success(
                            authSessionToken = authSessionToken
                        )
                    )
                }
                resetDialogState(); resetBtnGoogleUiState(); updateDialogState(dlgAuthSuccess = true)
            }
            else {
                _uiState.update {
                    it.copy(
                        authValidationResult = AuthValidationResult.Error(
                            throwable = Throwable(
                                message = "Client cannot authenticate with Google!"
                            )
                        )
                    )
                }
                updateDialogState(dlgAuthError = true)
            }
        }
    }
    private fun btnAuthGoogleOnConfirmEvent()
        { resetDialogState(); resetBtnGoogleUiState(); resetAuthResultState() }
    private fun btnAuthGoogleOnResultErrorEvent(throwable: Throwable) {
        resetDialogState(); resetBtnGoogleUiState(); resetAuthResultState()
        _uiState.update {
            it.copy(
                authValidationResult = AuthValidationResult.Error(
                    throwable = throwable
                )
            )
        }
        updateDialogState(dlgAuthError = true)
    }
    private fun btnAuthGoogleOnResultDismissed(throwable: Throwable) {
        _uiState.update {
            it.copy(
                authValidationResult = AuthValidationResult.Error(
                    throwable = throwable
                )
            )
        }
        updateDialogState(dlgAuthError = true)
    }
}
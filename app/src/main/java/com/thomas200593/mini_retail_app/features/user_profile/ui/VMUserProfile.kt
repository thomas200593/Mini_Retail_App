package com.thomas200593.mini_retail_app.features.user_profile.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thomas200593.mini_retail_app.core.data.local.session.SessionState
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatchers.Dispatchers.IO
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.di.Dispatcher
import com.thomas200593.mini_retail_app.features.app_conf.app_config.entity.AppConfig.ConfigCurrent
import com.thomas200593.mini_retail_app.features.auth.entity.UserData
import com.thomas200593.mini_retail_app.features.business.biz_profile.domain.UCGetBizProfileShort
import com.thomas200593.mini_retail_app.features.business.biz_profile.entity.BizProfileShort
import com.thomas200593.mini_retail_app.features.user_profile.ui.VMUserProfile.UiEvents.ButtonEvents.BtnAppConfigEvents
import com.thomas200593.mini_retail_app.features.user_profile.ui.VMUserProfile.UiEvents.ButtonEvents.BtnSignOutEvents
import com.thomas200593.mini_retail_app.features.user_profile.ui.VMUserProfile.UiEvents.DialogEvents.DlgDenyAccessEvents
import com.thomas200593.mini_retail_app.features.user_profile.ui.VMUserProfile.UiEvents.OnOpenEvents
import com.thomas200593.mini_retail_app.features.user_profile.ui.VMUserProfile.UiStateUserProfile.Idle
import com.thomas200593.mini_retail_app.features.user_profile.ui.VMUserProfile.UiStateUserProfile.Loading
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VMUserProfile @Inject constructor(
    private val ucGetBizProfileShort: UCGetBizProfileShort,
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher
): ViewModel() {
    sealed interface UiStateUserProfile {
        data object Idle : UiStateUserProfile
        data object Loading : UiStateUserProfile
        data class Success(val data: Triple<UserData, BizProfileShort, ConfigCurrent>) : UiStateUserProfile
    }
    data class UiState(
        val userProfileData: UiStateUserProfile = Idle,
        val dialogState: DialogState = DialogState()
    )
    data class DialogState(
        val dlgDenySession: MutableState<Boolean> = mutableStateOf(false)
    )
    sealed interface UiEvents {
        data class OnOpenEvents(val sessionState: SessionState) : UiEvents
        sealed interface ButtonEvents : UiEvents {
            sealed interface BtnAppConfigEvents : ButtonEvents {
                data object OnClick : BtnAppConfigEvents
            }
            sealed interface BtnSignOutEvents : ButtonEvents {
                data object OnClick: BtnSignOutEvents
            }
        }
        sealed interface DialogEvents : UiEvents {
            sealed interface DlgDenyAccessEvents : DialogEvents {
                data object OnDismiss :DlgDenyAccessEvents
            }
        }
    }

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    fun onEvent(events: UiEvents) {
        when(events) {
            is OnOpenEvents -> onOpenEvent(events.sessionState)
            is BtnAppConfigEvents.OnClick -> resetDialogState()
            is BtnSignOutEvents.OnClick -> resetDialogAndUiState()
            is DlgDenyAccessEvents.OnDismiss -> resetDialogAndUiState()
        }
    }

    private fun resetDialogState() = _uiState.update { it.copy(dialogState = DialogState()) }
    private fun resetUiStateUserProfile() = _uiState.update { it.copy(userProfileData = Idle) }
    private fun resetDialogAndUiState() { resetDialogState(); resetUiStateUserProfile() }

    private fun onOpenEvent(sessionState: SessionState) {
        when(sessionState) {
            SessionState.Loading -> viewModelScope.launch {
                resetDialogState()
                _uiState.update { it.copy(userProfileData = Loading) }
            }
            is SessionState.Invalid -> onDenyAccess()
            is SessionState.Valid -> viewModelScope.launch{
                resetDialogState()
                _uiState.update { it.copy(userProfileData = Loading) }
                ucGetBizProfileShort.invoke().flowOn(ioDispatcher).collectLatest { data ->
                    _uiState.update {
                        it.copy(
                            userProfileData = UiStateUserProfile.Success(
                                data = Triple(
                                    sessionState.userData,
                                    data.first,
                                    data.second
                                )
                            ),
                            dialogState = DialogState()
                        )
                    }
                }
            }
        }
    }

    private fun onDenyAccess() {
        resetDialogState(); resetUiStateUserProfile()
        _uiState.update {
            it.copy(
                dialogState = it.dialogState.copy(
                    dlgDenySession = mutableStateOf(true)
                )
            )
        }
    }
}

/*
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thomas200593.mini_retail_app.core.data.local.session.SessionState
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.di.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatchers.Dispatchers.IO
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState.Error
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState.Idle
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState.Loading
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState.Success
import com.thomas200593.mini_retail_app.features.auth.entity.UserData
import com.thomas200593.mini_retail_app.features.auth.repository.RepoAuth
import com.thomas200593.mini_retail_app.features.business.biz_profile.domain.UCGetBizProfileShort
import com.thomas200593.mini_retail_app.features.business.biz_profile.entity.BizProfileShort
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class VMUserProfile @Inject constructor(
    private val repoAuth: RepoAuth,
    private val ucGetBizProfileShort: UCGetBizProfileShort,
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher,
): ViewModel(){

    private val _currentSessionUserData: MutableState<ResourceState<UserData>> = mutableStateOf(Idle)
    val currentSessionUserData = _currentSessionUserData

    private val _businessProfileSummary: MutableState<ResourceState<BizProfileShort>> = mutableStateOf(
        Idle
    )
    val businessProfileSummary = _businessProfileSummary

    fun onOpen(validSession: SessionState.Valid) = viewModelScope.launch(ioDispatcher){
        getCurrentSessionUserData(validSession)
        getBusinessProfileSummary()
    }

    private fun getCurrentSessionUserData(validSession: SessionState.Valid) = viewModelScope.launch(ioDispatcher){
        _currentSessionUserData.value = Loading
        _currentSessionUserData.value = try{
            Success(validSession.userData)
        }catch (e: Throwable){
            Error(e)
        }
    }

    private fun getBusinessProfileSummary() = viewModelScope.launch(ioDispatcher){
        _businessProfileSummary.value = Loading
        ucGetBizProfileShort.invoke().collect{ bps ->
            _businessProfileSummary.value = bps
        }
    }

    fun handleSignOut() = viewModelScope.launch(ioDispatcher){
        _currentSessionUserData.value = Loading
        repoAuth.clearAuthSessionToken()
    }
}*/

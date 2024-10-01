package com.thomas200593.mini_retail_app.features.user_profile.ui

import androidx.lifecycle.ViewModel
import com.thomas200593.mini_retail_app.app.navigation.ScrGraphs
import com.thomas200593.mini_retail_app.core.data.local.session.SessionState
import com.thomas200593.mini_retail_app.features.user_profile.ui.VMUserProfile.UiEvents.OnOpenEvents
import com.thomas200593.mini_retail_app.features.user_profile.ui.VMUserProfile.UiStateUserProfile.Loading
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class VMUserProfile @Inject constructor(

): ViewModel() {
    sealed interface UiStateUserProfile {
        data object Loading : UiStateUserProfile
    }
    data class UiState(
        val currentUserSession: UiStateUserProfile = Loading
    )
    sealed interface UiEvents {
        data class OnOpenEvents(
            val sessionState: SessionState,
            val currentScreen: ScrGraphs
        ) : UiEvents
    }

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    fun onEvent(events: UiEvents) {
        when(events) {
            is OnOpenEvents -> onOpenEvent(events.sessionState, events.currentScreen)
        }
    }

    private fun onOpenEvent(sessionState: SessionState, currentScreen: ScrGraphs) {

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

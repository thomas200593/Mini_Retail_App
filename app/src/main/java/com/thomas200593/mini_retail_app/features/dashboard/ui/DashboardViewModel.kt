package com.thomas200593.mini_retail_app.features.dashboard.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thomas200593.mini_retail_app.core.data.local.session.SessionState
import com.thomas200593.mini_retail_app.features.auth.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val authRepository: AuthRepository
): ViewModel() {

    private val _sessionState : MutableState<SessionState> = mutableStateOf(SessionState.Loading)
    val sessionState = _sessionState

    fun handleSignOut() = viewModelScope.launch {
        authRepository.clearAuthSessionToken()
    }

    fun updateSessionState(state: SessionState) {
        _sessionState.value = state
    }
}

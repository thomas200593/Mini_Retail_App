package com.thomas200593.mini_retail_app.features.user_profile.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thomas200593.mini_retail_app.core.data.local.session.Session
import com.thomas200593.mini_retail_app.core.data.local.session.SessionState
import com.thomas200593.mini_retail_app.core.design_system.dispatchers.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.dispatchers.Dispatchers
import com.thomas200593.mini_retail_app.core.design_system.util.RequestState
import com.thomas200593.mini_retail_app.features.auth.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserProfileViewModel @Inject constructor(
    session: Session,
    private val authRepository: AuthRepository,
    @Dispatcher(Dispatchers.Dispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
): ViewModel(){
    val sessionState = session.currentUserSession.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = SessionState.Loading
    )

    fun handleSignOut() = viewModelScope.launch(ioDispatcher){
        authRepository.clearAuthSessionToken()
    }
}

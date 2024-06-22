package com.thomas200593.mini_retail_app.features.user_profile.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.thomas200593.mini_retail_app.core.design_system.dispatchers.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.dispatchers.Dispatchers
import com.thomas200593.mini_retail_app.features.auth.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

private val TAG = UserProfileViewModel::class.simpleName

@HiltViewModel
class UserProfileViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    @Dispatcher(Dispatchers.Dispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
): ViewModel(){

    fun onOpen() = viewModelScope.launch(ioDispatcher){
        Timber.d("Called : fun $TAG.onOpen()")
    }

    fun handleSignOut() = viewModelScope.launch(ioDispatcher){
        Timber.d("Called : fun $TAG.handleSignOut()")
        authRepository.clearAuthSessionToken()
    }
}

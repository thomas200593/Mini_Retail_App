package com.thomas200593.mini_retail_app.features.user_profile.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thomas200593.mini_retail_app.core.data.local.session.SessionState
import com.thomas200593.mini_retail_app.core.design_system.dispatchers.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.dispatchers.Dispatchers
import com.thomas200593.mini_retail_app.core.design_system.util.RequestState
import com.thomas200593.mini_retail_app.features.auth.entity.UserData
import com.thomas200593.mini_retail_app.features.auth.repository.AuthRepository
import com.thomas200593.mini_retail_app.features.business.entity.BusinessProfile
import com.thomas200593.mini_retail_app.features.business.entity.dto.BusinessProfileSummary
import com.thomas200593.mini_retail_app.features.business.repository.BusinessProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

private val TAG = UserProfileViewModel::class.simpleName

@HiltViewModel
class UserProfileViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val businessProfileRepository: BusinessProfileRepository,
    @Dispatcher(Dispatchers.Dispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
): ViewModel(){

    private val _currentSessionUserData: MutableState<RequestState<UserData>> = mutableStateOf(RequestState.Idle)
    val currentSessionUserData = _currentSessionUserData

    private val _businessProfile: MutableState<RequestState<BusinessProfile?>> = mutableStateOf(RequestState.Idle)
    val businessProfile = _businessProfile

    fun onOpen(validSession: SessionState.Valid) = viewModelScope.launch(ioDispatcher){
        Timber.d("Called : fun $TAG.onOpen()")
        getCurrentSessionUserData(validSession)
        getBusinessProfile()
    }

    private fun getCurrentSessionUserData(validSession: SessionState.Valid) = viewModelScope.launch(ioDispatcher){
        Timber.d("Called : fun $TAG.getCurrentUserSession()")
        _currentSessionUserData.value = RequestState.Loading
        _currentSessionUserData.value = try{
            RequestState.Success(validSession.userData)
        }catch (e: Throwable){
            RequestState.Error(e)
        }
    }

    private fun getBusinessProfile() = viewModelScope.launch(ioDispatcher){
        Timber.d("Called : fun $TAG.getBusinessProfile()")
        _businessProfile.value = RequestState.Loading
        businessProfileRepository.getBusinessProfile().onEach {
            Timber.d("getBusinessProfile onEach: $it")
        }.collect { businessProfile ->
            _businessProfile.value = RequestState.Success(businessProfile)
        }
    }

    fun handleSignOut() = viewModelScope.launch(ioDispatcher){
        Timber.d("Called : fun $TAG.handleSignOut()")
        _currentSessionUserData.value = RequestState.Loading
        authRepository.clearAuthSessionToken()
    }

    fun testGenerate() = viewModelScope.launch(ioDispatcher){
        businessProfileRepository.testGenerate()
    }
}

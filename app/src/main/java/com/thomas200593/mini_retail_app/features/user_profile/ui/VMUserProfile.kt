package com.thomas200593.mini_retail_app.features.user_profile.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thomas200593.mini_retail_app.core.data.local.session.SessionState
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatchers.Dispatchers.IO
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState
import com.thomas200593.mini_retail_app.features.auth.entity.UserData
import com.thomas200593.mini_retail_app.features.auth.repository.RepoAuth
import com.thomas200593.mini_retail_app.features.business.domain.GetBizProfileSummaryUseCase
import com.thomas200593.mini_retail_app.features.business.entity.business_profile.dto.BizProfileSummary
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

private val TAG = VMUserProfile::class.simpleName

@HiltViewModel
class VMUserProfile @Inject constructor(
    private val repoAuth: RepoAuth,
    private val ucGetBizProfileSummary: GetBizProfileSummaryUseCase,
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher,
): ViewModel(){

    private val _currentSessionUserData: MutableState<ResourceState<UserData>> = mutableStateOf(ResourceState.Idle)
    val currentSessionUserData = _currentSessionUserData

    private val _businessProfileSummary: MutableState<ResourceState<BizProfileSummary>> = mutableStateOf(ResourceState.Idle)
    val businessProfileSummary = _businessProfileSummary

    fun onOpen(validSession: SessionState.Valid) = viewModelScope.launch(ioDispatcher){
        Timber.d("Called : fun $TAG.onOpen()")
        getCurrentSessionUserData(validSession)
        getBusinessProfileSummary()
    }

    private fun getCurrentSessionUserData(validSession: SessionState.Valid) = viewModelScope.launch(ioDispatcher){
        Timber.d("Called : fun $TAG.getCurrentUserSession()")
        _currentSessionUserData.value = ResourceState.Loading
        _currentSessionUserData.value = try{
            ResourceState.Success(validSession.userData)
        }catch (e: Throwable){
            ResourceState.Error(e)
        }
    }

    private fun getBusinessProfileSummary() = viewModelScope.launch(ioDispatcher){
        Timber.d("Called : fun $TAG.getBusinessProfile()")
        _businessProfileSummary.value = ResourceState.Loading
        ucGetBizProfileSummary.invoke().collect{ bps ->
            _businessProfileSummary.value = bps
        }
    }

    fun handleSignOut() = viewModelScope.launch(ioDispatcher){
        Timber.d("Called : fun $TAG.handleSignOut()")
        _currentSessionUserData.value = ResourceState.Loading
        repoAuth.clearAuthSessionToken()
    }
}
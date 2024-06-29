package com.thomas200593.mini_retail_app.features.business.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thomas200593.mini_retail_app.core.data.local.session.SessionState
import com.thomas200593.mini_retail_app.core.design_system.dispatchers.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.dispatchers.Dispatchers
import com.thomas200593.mini_retail_app.core.design_system.util.RequestState
import com.thomas200593.mini_retail_app.features.business.navigation.DestinationBusiness
import com.thomas200593.mini_retail_app.features.business.repository.BusinessRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

private val TAG = BusinessViewModel::class.simpleName

@HiltViewModel
class BusinessViewModel @Inject constructor(
    private val businessRepository: BusinessRepository,
    @Dispatcher(Dispatchers.Dispatchers.IO) private val ioDispatcher: CoroutineDispatcher
): ViewModel() {

    private val _businessMenuPreferences: MutableState<RequestState<Set<DestinationBusiness>>> = mutableStateOf(RequestState.Idle)
    val businessMenuPreferences = _businessMenuPreferences

    fun onOpen(sessionState: SessionState) = viewModelScope.launch(ioDispatcher){
        Timber.d("Called : fun $TAG.onOpen()")
        getBusinessMenuPreferences(sessionState)
    }

    private suspend fun getBusinessMenuPreferences(sessionState: SessionState) = viewModelScope.launch(ioDispatcher){
        Timber.d("Called : fun $TAG.getBusinessMenuPreferences()")
        _businessMenuPreferences.value = RequestState.Loading
        _businessMenuPreferences.value = try{
            RequestState.Success(businessRepository.getBusinessMenuData(sessionState))
        }catch (e: Throwable){
            RequestState.Error(e)
        }
    }
}

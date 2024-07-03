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
import javax.inject.Inject

private val TAG = BusinessViewModel::class.simpleName

@HiltViewModel
class BusinessViewModel @Inject constructor(
    private val repository: BusinessRepository,
    @Dispatcher(Dispatchers.Dispatchers.IO) private val ioDispatcher: CoroutineDispatcher
): ViewModel() {
    private val _menuData: MutableState<RequestState<Set<DestinationBusiness>>> = mutableStateOf(RequestState.Idle)
    val menuData = _menuData

    fun onOpen(sessionState: SessionState) = viewModelScope.launch(ioDispatcher){
        getMenuData(sessionState)
    }

    private suspend fun getMenuData(sessionState: SessionState) = viewModelScope.launch(ioDispatcher){
        _menuData.value = RequestState.Loading
        _menuData.value = try{ RequestState.Success(repository.getMenuData(sessionState)) }
        catch (e: Throwable){ RequestState.Error(e) }
    }
}

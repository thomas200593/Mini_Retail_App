package com.thomas200593.mini_retail_app.features.business.business.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thomas200593.mini_retail_app.core.data.local.session.SessionState
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatchers.Dispatchers.IO
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState
import com.thomas200593.mini_retail_app.features.business.business.navigation.DestinationBusiness
import com.thomas200593.mini_retail_app.features.business.business.repository.RepoBusiness
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VMBusiness @Inject constructor(
    private val repository: RepoBusiness,
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher
): ViewModel() {
    private val _menuData: MutableState<ResourceState<Set<DestinationBusiness>>> = mutableStateOf(ResourceState.Idle)
    val menuData = _menuData

    fun onOpen(sessionState: SessionState) = viewModelScope.launch(ioDispatcher){
        getMenuData(sessionState)
    }

    private suspend fun getMenuData(sessionState: SessionState) = viewModelScope.launch(ioDispatcher){
        _menuData.value = ResourceState.Loading
        _menuData.value = try{ ResourceState.Success(repository.getMenuData(sessionState)) }
        catch (e: Throwable){ ResourceState.Error(e) }
    }
}

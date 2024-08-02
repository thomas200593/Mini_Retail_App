package com.thomas200593.mini_retail_app.features.business.biz.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thomas200593.mini_retail_app.core.data.local.session.SessionState
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatchers.Dispatchers.IO
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState
import com.thomas200593.mini_retail_app.features.business.biz.navigation.DestBiz
import com.thomas200593.mini_retail_app.features.business.biz.repository.RepoBiz
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VMBiz @Inject constructor(
    private val repository: RepoBiz,
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher
): ViewModel() {
    private val _menuData: MutableState<ResourceState<Set<DestBiz>>> = mutableStateOf(ResourceState.Idle)
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

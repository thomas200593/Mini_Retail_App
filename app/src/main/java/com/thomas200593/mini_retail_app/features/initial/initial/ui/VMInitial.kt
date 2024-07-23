package com.thomas200593.mini_retail_app.features.initial.initial.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatchers.Dispatchers.IO
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState.*
import com.thomas200593.mini_retail_app.features.initial.initial.domain.UCGetInitialData
import com.thomas200593.mini_retail_app.features.initial.initial.entity.Initial
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VMInitial @Inject constructor(
    private val ucGetInitialData: UCGetInitialData,
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher
): ViewModel(){
    private val _uiState: MutableStateFlow<ResourceState<Initial>> = MutableStateFlow(Idle)
    val uiState = _uiState.asStateFlow()
    fun onOpen() = viewModelScope.launch(ioDispatcher){ getUiState() }
    private suspend fun getUiState() = viewModelScope.launch(ioDispatcher){
        _uiState.value = Loading
        try { ucGetInitialData.invoke().flowOn(ioDispatcher).collect{ initialData -> _uiState.value = initialData } }
        catch (e: Throwable){ _uiState.value = Error(e) }
    }
}
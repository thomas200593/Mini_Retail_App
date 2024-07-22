package com.thomas200593.mini_retail_app.features.initial.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatchers
import com.thomas200593.mini_retail_app.core.design_system.util.RequestState
import com.thomas200593.mini_retail_app.features.initial.domain.GetInitialDataUseCase
import com.thomas200593.mini_retail_app.features.initial.entity.Initial
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InitialViewModel @Inject constructor(
    private val useCase: GetInitialDataUseCase,
    @Dispatcher(Dispatchers.Dispatchers.IO) private val ioDispatcher: CoroutineDispatcher
): ViewModel(){
    private val _uiState: MutableStateFlow<RequestState<Initial>> = MutableStateFlow(RequestState.Idle)
    val uiState = _uiState.asStateFlow()
    fun onOpen() = viewModelScope.launch(ioDispatcher){ getUiState() }
    private suspend fun getUiState() = viewModelScope.launch(ioDispatcher){
        _uiState.value = RequestState.Loading
        try { useCase.invoke().flowOn(ioDispatcher).collect{ initialData -> _uiState.value = initialData } }
        catch (e: Throwable){ _uiState.value = RequestState.Error(e) }
    }
}
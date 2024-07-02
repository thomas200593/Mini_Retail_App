package com.thomas200593.mini_retail_app.features.initial.ui

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thomas200593.mini_retail_app.core.design_system.dispatchers.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.dispatchers.Dispatchers
import com.thomas200593.mini_retail_app.core.design_system.util.RequestState
import com.thomas200593.mini_retail_app.features.initial.domain.GetInitialDataUseCase
import com.thomas200593.mini_retail_app.features.initial.entity.Initial
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class InitialViewModel @Inject constructor(
    private val useCase: GetInitialDataUseCase,
    @Dispatcher(Dispatchers.Dispatchers.IO) private val ioDispatcher: CoroutineDispatcher
): ViewModel(){
    private val _showErrorScreen = mutableStateOf(Pair<Boolean, Throwable?>(false, null))
    val showErrorScreen = _showErrorScreen

    private val _showLoadingScreen = mutableStateOf(false)
    val showLoadingScreen = _showLoadingScreen

    private val _initialData: MutableStateFlow<RequestState<Initial>> = MutableStateFlow(RequestState.Idle)
    val initialData = _initialData

    fun onOpen() = viewModelScope.launch(ioDispatcher){
        getInitialData()
    }

    private suspend fun getInitialData() = viewModelScope.launch(ioDispatcher){
        _initialData.value = RequestState.Loading
        try { useCase.invoke().flowOn(ioDispatcher)
            .collect{ initialData -> _initialData.value = initialData }
        }catch (e: Throwable){ _initialData.value = RequestState.Error(e) }
    }

    suspend fun setLoadingScreen(loadScreenVal: Boolean) = withContext(ioDispatcher){
        _showLoadingScreen.value = loadScreenVal
    }

    suspend fun setErrorScreen(errorScreenVal: Boolean, throwable: Throwable?) = withContext(ioDispatcher){
        _showErrorScreen.value = Pair(errorScreenVal, throwable)
    }
}
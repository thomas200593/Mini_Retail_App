package com.thomas200593.mini_retail_app.features.initial.initial.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatchers.Dispatchers.IO
import com.thomas200593.mini_retail_app.core.design_system.util.HlpStateFlow.update
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState.Error
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState.Idle
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState.Loading
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
): ViewModel() {
    data class UiState(
        val initData: ResourceState<Initial> = Idle
    )
    sealed class UiEvents {
        data object OnOpen: UiEvents()
    }

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    fun onEvent(events: UiEvents) = viewModelScope.launch(ioDispatcher) {
        when(events){
            UiEvents.OnOpen -> viewModelScope.launch {
                _uiState.update { it.copy(initData = Loading) }
                try { ucGetInitialData.invoke().flowOn(ioDispatcher)
                    .collect{ data -> _uiState.update { prev -> prev.copy(initData = data) } } }
                catch (e: Throwable){ _uiState.update { it.copy(initData = Error(e)) } }
            }
        }
    }
}
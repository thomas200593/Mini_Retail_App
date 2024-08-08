package com.thomas200593.mini_retail_app.features.initial.initial.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatchers.Dispatchers.IO
import com.thomas200593.mini_retail_app.core.design_system.util.HlpStateFlow.update
import com.thomas200593.mini_retail_app.features.initial.initial.domain.UCGetInitialData
import com.thomas200593.mini_retail_app.features.initial.initial.entity.Initial
import com.thomas200593.mini_retail_app.features.initial.initial.ui.VMInitial.UiStateInitial.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VMInitial @Inject constructor(
    private val ucGetInitialData: UCGetInitialData,
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher
): ViewModel() {
    sealed interface UiStateInitial {
        data object Loading: UiStateInitial
        data class Success(val initial: Initial): UiStateInitial
        data class Error(val t: Throwable): UiStateInitial
    }
    data class UiState(
        val initial: UiStateInitial = Loading
    )
    sealed class UiEvents {
        data object OnOpenEvents: UiEvents()
    }

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    fun onEvent(events: UiEvents) {
        when(events){
            UiEvents.OnOpenEvents -> onOpenEvent()
        }
    }
    private fun onOpenEvent() = viewModelScope.launch {
        ucGetInitialData.invoke().flowOn(ioDispatcher)
            .catch { e -> _uiState.update { prev -> prev.copy(initial = Error(e)) } }
            .collect{ _uiState.update { prev -> prev.copy(initial = Success(it.data)) } }
    }
}
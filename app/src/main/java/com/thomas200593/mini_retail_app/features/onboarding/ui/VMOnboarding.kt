package com.thomas200593.mini_retail_app.features.onboarding.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatchers.Dispatchers.IO
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.di.Dispatcher
import com.thomas200593.mini_retail_app.features.onboarding.entity.Onboarding.OnboardingPage
import com.thomas200593.mini_retail_app.features.onboarding.repository.RepoOnboarding
import com.thomas200593.mini_retail_app.features.onboarding.ui.VMOnboarding.UiEvents.ButtonEvents
import com.thomas200593.mini_retail_app.features.onboarding.ui.VMOnboarding.UiEvents.OnFinishedOnboardingEvent
import com.thomas200593.mini_retail_app.features.onboarding.ui.VMOnboarding.UiEvents.OnOpenEvents
import com.thomas200593.mini_retail_app.features.onboarding.ui.VMOnboarding.UiEvents.TabRowEvents
import com.thomas200593.mini_retail_app.features.onboarding.ui.VMOnboarding.UiStateOnboardingPages.Loading
import com.thomas200593.mini_retail_app.features.onboarding.ui.VMOnboarding.UiStateOnboardingPages.Success
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VMOnboarding @Inject constructor(
    private val repoOnboarding: RepoOnboarding,
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher
): ViewModel() {
    sealed interface UiStateOnboardingPages{
        data object Loading: UiStateOnboardingPages
        data class Success(val onboardingPages: List<OnboardingPage>): UiStateOnboardingPages
    }
    data class UiState(
        val onboardingPages: UiStateOnboardingPages = Loading,
        val screenState: ScreenState = ScreenState()
    )
    data class ScreenState(
        val currentPage: Int = 0,
        val maxPage: Int = 0
    )
    sealed class UiEvents{
        data object OnOpenEvents: UiEvents()
        sealed class ButtonEvents: UiEvents(){
            sealed class ButtonNextEvents: ButtonEvents(){ data object OnClick: ButtonNextEvents() }
        }
        sealed class TabRowEvents: UiEvents(){
            sealed class TabPageSelection: TabRowEvents(){ data class OnSelect(val index: Int): TabPageSelection() }
        }
        data object OnFinishedOnboardingEvent: UiEvents()
    }

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    fun onEvent(events: UiEvents){
        when(events) {
            OnOpenEvents -> onOpenEvent()
            ButtonEvents.ButtonNextEvents.OnClick -> btnNextOnClickEvent()
            is TabRowEvents.TabPageSelection.OnSelect -> tabPageOnSelectEvent(events.index)
            OnFinishedOnboardingEvent -> onFinishedOnboardingEvent()
        }
    }
    private fun onOpenEvent() = viewModelScope.launch(ioDispatcher) {
        repoOnboarding.getOnboardingPages().let { list ->
            _uiState.update {
                it.copy(
                    onboardingPages = Success(list),
                    screenState = it.screenState.copy(
                        maxPage = list.size
                    )
                )
            }
        }
    }
    private fun btnNextOnClickEvent() = _uiState.update {
        val nextPage = (it.screenState.currentPage + 1)
            .coerceAtMost(it.screenState.maxPage - 1)
        it.copy(screenState = it.screenState.copy(currentPage = nextPage))
    }
    private fun tabPageOnSelectEvent(index: Int) =
        _uiState.update { it.copy(screenState = it.screenState.copy(currentPage = index)) }
    private fun onFinishedOnboardingEvent() =
        viewModelScope.launch(ioDispatcher) { repoOnboarding.hideOnboarding() }
}
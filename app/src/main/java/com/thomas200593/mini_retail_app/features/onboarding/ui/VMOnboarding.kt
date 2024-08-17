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
import com.thomas200593.mini_retail_app.features.onboarding.ui.VMOnboarding.UiEvents.TabsEvents
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
        val currentPage: Int = 0
    )
    sealed class UiEvents{
        data object OnOpenEvents: UiEvents()
        sealed class ButtonEvents: UiEvents(){
            sealed class ButtonNextEvents: ButtonEvents(){ data object OnClick: ButtonNextEvents() }
        }
        sealed class TabsEvents: UiEvents(){
            sealed class TabPageSelection: TabsEvents(){ data class OnSelect(val index: Int): TabPageSelection() }
        }
        data object OnFinishedOnboardingEvent: UiEvents()
    }

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    fun onEvent(events: UiEvents){
        when(events){
            OnOpenEvents -> onOpenEvent()
            ButtonEvents.ButtonNextEvents.OnClick -> onBtnNextOnClick()
            is TabsEvents.TabPageSelection.OnSelect -> onTabPageSelectEvent(events.index)
            OnFinishedOnboardingEvent -> onFinishedOnboardingEvent()
        }
    }
    private fun onOpenEvent() = viewModelScope.launch(ioDispatcher) {
        _uiState.update { it.copy(onboardingPages = Success(repoOnboarding.getOnboardingPages())) }
    }
    private fun onBtnNextOnClick() = _uiState.update {
        it.copy(screenState = it.screenState.copy(currentPage = if(it.screenState.currentPage < 3){it.screenState.currentPage + 1}else{it.screenState.currentPage}))
    }
    private fun onTabPageSelectEvent(index: Int) = _uiState.update {
        it.copy(screenState = it.screenState.copy(currentPage = index))
    }
    private fun onFinishedOnboardingEvent() = viewModelScope.launch(ioDispatcher) {
        repoOnboarding.hideOnboarding()
    }
}
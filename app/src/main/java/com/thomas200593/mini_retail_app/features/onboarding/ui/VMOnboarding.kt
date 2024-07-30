package com.thomas200593.mini_retail_app.features.onboarding.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatchers.Dispatchers.IO
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState.Error
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState.Idle
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState.Loading
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState.Success
import com.thomas200593.mini_retail_app.features.onboarding.entity.Onboarding.OnboardingPage
import com.thomas200593.mini_retail_app.features.onboarding.repository.RepoOnboarding
import com.thomas200593.mini_retail_app.features.onboarding.ui.VMOnboarding.UiEvents.ButtonEvents
import com.thomas200593.mini_retail_app.features.onboarding.ui.VMOnboarding.UiEvents.OnFinishedOnboardingEvent
import com.thomas200593.mini_retail_app.features.onboarding.ui.VMOnboarding.UiEvents.OnOpenEvents
import com.thomas200593.mini_retail_app.features.onboarding.ui.VMOnboarding.UiEvents.TabsEvents
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
    data class UiState(
        val onboardingPages: ResourceState<List<OnboardingPage>> = Idle,
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
        _uiState.update { it.copy(onboardingPages = Loading) }
        try {
            _uiState.update {
                it.copy(onboardingPages = Success(repoOnboarding.getOnboardingPages()))
            }
        }
        catch (e: Throwable){ _uiState.update { it.copy(onboardingPages = Error(e)) } }
    }
    private fun onBtnNextOnClick() {
        _uiState.update {
            it.copy(screenState = it.screenState.copy(currentPage = it.screenState.currentPage + 1))
        }
    }
    private fun onTabPageSelectEvent(index: Int) {
        _uiState.update {
            it.copy(screenState = it.screenState.copy(currentPage = index))
        }
    }
    private fun onFinishedOnboardingEvent() = viewModelScope.launch(ioDispatcher) {
        repoOnboarding.hideOnboarding()
    }
}

/*import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatchers.Dispatchers.IO
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState.Error
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState.Idle
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState.Loading
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState.Success
import com.thomas200593.mini_retail_app.features.onboarding.entity.Onboarding.OnboardingPage
import com.thomas200593.mini_retail_app.features.onboarding.repository.RepoOnboarding
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VMOnboarding @Inject constructor(
    private val repoOnboarding: RepoOnboarding,
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher
): ViewModel(){
    private val _onboardingPages : MutableState<ResourceState<List<OnboardingPage>>> = mutableStateOf(Idle)
    val onboardingPages = _onboardingPages
    private val _currentPage : MutableStateFlow<Int> = MutableStateFlow(0)
    val currentPage: StateFlow<Int> = _currentPage
    private val _isOnboardingFinished: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isOnboardingFinished: StateFlow<Boolean> = _isOnboardingFinished
    fun onOpen() = viewModelScope.launch(ioDispatcher) { getOnboardingPages() }
    private fun getOnboardingPages() = viewModelScope.launch(ioDispatcher) {
        _onboardingPages.value = Loading
        _onboardingPages.value = try{ Success(repoOnboarding.getOnboardingPages()) }
        catch (e: Throwable){ Error(e) }
    }
    fun hideOnboarding() = viewModelScope
        .launch(ioDispatcher) { repoOnboarding.hideOnboarding(); _isOnboardingFinished.value = true }
    fun onNextButtonClicked() = viewModelScope.launch(ioDispatcher){ _currentPage.value += 1 }
    fun onSelectedPage(index: Int) = viewModelScope.launch(ioDispatcher){ _currentPage.value = index }
}*/

package com.thomas200593.mini_retail_app.features.onboarding.ui

import androidx.compose.runtime.MutableState
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
}
package com.thomas200593.mini_retail_app.features.onboarding.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thomas200593.mini_retail_app.core.design_system.dispatchers.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.dispatchers.Dispatchers
import com.thomas200593.mini_retail_app.core.design_system.util.RequestState
import com.thomas200593.mini_retail_app.features.onboarding.entity.Onboarding
import com.thomas200593.mini_retail_app.features.onboarding.repository.OnboardingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val onboardingRepository: OnboardingRepository,
    @Dispatcher(Dispatchers.Dispatchers.IO) private val ioDispatcher: CoroutineDispatcher
): ViewModel(){
    private val _onboardingPages : MutableState<RequestState<List<Onboarding.OnboardingPage>>> =
        mutableStateOf(RequestState.Idle)
    val onboardingPages = _onboardingPages

    private val _currentPage : MutableStateFlow<Int> = MutableStateFlow(0)
    val currentPage: StateFlow<Int> = _currentPage

    private val _isOnboardingFinished: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isOnboardingFinished: StateFlow<Boolean> = _isOnboardingFinished

    fun onOpen() = viewModelScope.launch(ioDispatcher) {
        getOnboardingPages()
    }

    private fun getOnboardingPages() = viewModelScope.launch(ioDispatcher) {
        _onboardingPages.value = RequestState.Loading
        _onboardingPages.value = try{
            RequestState.Success(onboardingRepository.getOnboardingPages())
        }catch (e: Throwable){
            RequestState.Error(e)
        }
    }

    fun hideOnboarding() = viewModelScope.launch(ioDispatcher) {
        onboardingRepository.hideOnboarding().also {
            _isOnboardingFinished.value = true
        }
    }

    fun onNextButtonClicked() = viewModelScope.launch(ioDispatcher){
        _currentPage.value += 1
    }

    fun onSelectedPage(index: Int) = viewModelScope.launch(ioDispatcher){
        _currentPage.value = index
    }
}
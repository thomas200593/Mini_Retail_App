package com.thomas200593.mini_retail_app.features.onboarding.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thomas200593.mini_retail_app.features.onboarding.repository.OnboardingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val onboardingRepository: OnboardingRepository
): ViewModel(){
    private val _onboardingFinished: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val onboardingFinished: StateFlow<Boolean> = _onboardingFinished

    suspend fun hideOnboarding() = viewModelScope.launch {
        onboardingRepository.hideOnboarding().also {
            _onboardingFinished.value = true
        }
    }
}
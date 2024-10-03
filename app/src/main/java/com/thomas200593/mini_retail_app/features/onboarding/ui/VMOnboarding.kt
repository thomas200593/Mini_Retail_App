package com.thomas200593.mini_retail_app.features.onboarding.ui

import androidx.appcompat.app.AppCompatDelegate.setApplicationLocales
import androidx.core.os.LocaleListCompat.create
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatchers.Dispatchers.IO
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.di.Dispatcher
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_language.entity.Language
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_language.repository.RepoConfGenLanguage
import com.thomas200593.mini_retail_app.features.onboarding.domain.UCGetOnboardingData
import com.thomas200593.mini_retail_app.features.onboarding.entity.Onboarding
import com.thomas200593.mini_retail_app.features.onboarding.repository.RepoOnboarding
import com.thomas200593.mini_retail_app.features.onboarding.ui.VMOnboarding.UiEvents.ButtonEvents.BtnNextEvents
import com.thomas200593.mini_retail_app.features.onboarding.ui.VMOnboarding.UiEvents.DropdownEvents.DropdownLanguagesEvents
import com.thomas200593.mini_retail_app.features.onboarding.ui.VMOnboarding.UiEvents.OnOpenEvents
import com.thomas200593.mini_retail_app.features.onboarding.ui.VMOnboarding.UiEvents.TabRowEvents.TabPageSelectionEvents
import com.thomas200593.mini_retail_app.features.onboarding.ui.VMOnboarding.UiStateOnboardingPages.Loading
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class VMOnboarding @Inject constructor(
    private val repoOnboarding: RepoOnboarding,
    private val repoConfGenLanguage: RepoConfGenLanguage,
    private val ucGetOnboardingData: UCGetOnboardingData,
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher
): ViewModel() {
    sealed interface UiStateOnboardingPages{
        data object Loading: UiStateOnboardingPages
        data class Success(val onboardingData: Onboarding.OnboardingData) : UiStateOnboardingPages
    }
    data class UiState(
        val onboardingPages: UiStateOnboardingPages = Loading,
        val screenState: ScreenState = ScreenState()
    )
    data class ScreenState(
        val currentPageIndex: Int = 0,
        val maxPageIndex: Int = 0
    )
    sealed class UiEvents{
        data object OnOpenEvents: UiEvents()
        sealed class ButtonEvents: UiEvents(){
            sealed class BtnNextEvents: ButtonEvents(){
                data object OnNextClickEvents: BtnNextEvents()
                data object OnFinishClickEvents: BtnNextEvents()
            }
        }
        sealed class DropdownEvents: UiEvents(){
            sealed class DropdownLanguagesEvents: DropdownEvents(){
                data class OnSelectEvents(val language: Language): DropdownLanguagesEvents()
            }
        }
        sealed class TabRowEvents: UiEvents(){
            sealed class TabPageSelectionEvents: TabRowEvents(){
                data class OnSelectEvents(val index: Int): TabPageSelectionEvents()
            }
        }
    }

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    fun onEvent(events: UiEvents){
        when(events) {
            is OnOpenEvents -> onOpenEvent()
            is BtnNextEvents.OnNextClickEvents -> btnNextOnClickEvent()
            is BtnNextEvents.OnFinishClickEvents -> onFinishedOnboardingEvent()
            is TabPageSelectionEvents.OnSelectEvents -> tabPageOnSelectEvent(events.index)
            is DropdownLanguagesEvents.OnSelectEvents -> handleLangChange(events.language)
        }
    }

    private fun handleLangChange(language: Language) = viewModelScope.launch(ioDispatcher) {
        repoConfGenLanguage.setLanguage(language)
        setApplicationLocales( create(Locale(language.code)) )
    }
    private fun onOpenEvent() = viewModelScope.launch(ioDispatcher) {
        ucGetOnboardingData.invoke().collectLatest { data ->
            _uiState.update {
                it.copy(
                    onboardingPages = UiStateOnboardingPages.Success(data),
                    screenState = it.screenState.copy(
                        maxPageIndex = data.listOfOnboardingPages.size - 1
                    )
                )
            }
        }
    }
    private fun btnNextOnClickEvent() = _uiState.update {
        val nextPage = (it.screenState.currentPageIndex + 1).coerceAtMost(it.screenState.maxPageIndex)
        it.copy(
            screenState = it.screenState.copy(
                currentPageIndex = nextPage
            )
        )
    }
    private fun tabPageOnSelectEvent(index: Int) = _uiState.update {
        it.copy(
            screenState = it.screenState.copy(
                currentPageIndex = index
            )
        )
    }
    private fun onFinishedOnboardingEvent() = viewModelScope.launch(ioDispatcher) {
        repoOnboarding.hideOnboarding()
    }
}
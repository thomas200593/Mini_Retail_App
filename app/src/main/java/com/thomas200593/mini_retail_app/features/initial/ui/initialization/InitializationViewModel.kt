package com.thomas200593.mini_retail_app.features.initial.ui.initialization

import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.os.LocaleListCompat
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thomas200593.mini_retail_app.core.design_system.dispatchers.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.dispatchers.Dispatchers
import com.thomas200593.mini_retail_app.core.design_system.util.RequestState
import com.thomas200593.mini_retail_app.features.app_config.entity.Language
import com.thomas200593.mini_retail_app.features.app_config.repository.ConfigGeneralRepository
import com.thomas200593.mini_retail_app.features.business.entity.business_profile.dto.BusinessProfileSummary
import com.thomas200593.mini_retail_app.features.initial.domain.GetInitializationDataUseCase
import com.thomas200593.mini_retail_app.features.initial.domain.SetDefaultInitialBizProfileUseCase
import com.thomas200593.mini_retail_app.features.initial.entity.Initialization
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class InitializationViewModel @Inject constructor(
    private val cfgGeneralRepository: ConfigGeneralRepository,
    private val getUiStateUseCase: GetInitializationDataUseCase,
    private val setDefaultUseCase: SetDefaultInitialBizProfileUseCase,
    private val savedStateHandle: SavedStateHandle,
    @Dispatcher(Dispatchers.Dispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
): ViewModel() {

    companion object{
        private const val KEY_SHOW_WELCOME_MESSAGE = "showWelcomeMessage"
        private const val KEY_SHOW_INPUT_MANUAL_FORM = "showInputManualForm"
    }
    var showWelcomeMessage by mutableStateOf(savedStateHandle[KEY_SHOW_WELCOME_MESSAGE] ?: true)
        private set

    var showInputManualForm by mutableStateOf(savedStateHandle[KEY_SHOW_INPUT_MANUAL_FORM] ?: false)
        private set

    private val _uiState: MutableState<RequestState<Initialization>> = mutableStateOf(RequestState.Idle)
    val uiState = _uiState

    fun onOpen() = viewModelScope.launch(ioDispatcher) {
        getUiState()
    }

    private fun getUiState() = viewModelScope.launch(ioDispatcher){
        _uiState.value = RequestState.Loading
        try{ getUiStateUseCase.invoke().flowOn(ioDispatcher).collect{ data -> _uiState.value = data } }
        catch (e:Throwable){ _uiState.value = RequestState.Error(e) }
    }

    fun setLanguage(language: Language) = viewModelScope.launch(ioDispatcher) {
        cfgGeneralRepository.setLanguage(language)
        AppCompatDelegate.setApplicationLocales( LocaleListCompat.create(Locale(language.code)) )
    }

    fun setBizProfileDefault(businessProfileSummary: BusinessProfileSummary) = viewModelScope.launch(ioDispatcher) {
        setDefaultUseCase.invoke(businessProfileSummary)
    }

    fun onBeginManualInit() = viewModelScope.launch(ioDispatcher) {
        showWelcomeMessage = false
        savedStateHandle[KEY_SHOW_WELCOME_MESSAGE] = false

        showInputManualForm = true
        savedStateHandle[KEY_SHOW_INPUT_MANUAL_FORM] = true
    }

    fun onCancelManualInit() = viewModelScope.launch(ioDispatcher) {
        showWelcomeMessage = true
        savedStateHandle[KEY_SHOW_WELCOME_MESSAGE] = true

        showInputManualForm = false
        savedStateHandle[KEY_SHOW_INPUT_MANUAL_FORM] = false
    }

    fun onSubmitManualInit() {}
}
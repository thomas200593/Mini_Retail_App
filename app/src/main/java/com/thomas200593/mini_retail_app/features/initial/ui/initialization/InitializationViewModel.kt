package com.thomas200593.mini_retail_app.features.initial.ui.initialization

import androidx.appcompat.app.AppCompatDelegate
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
import com.thomas200593.mini_retail_app.features.initial.ui.initialization.InitializationScreenUiEvent.InitializationUiEventStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class InitializationViewModel @Inject constructor(
    getUiStateUseCase: GetInitializationDataUseCase,
    private val cfgGeneralRepository: ConfigGeneralRepository,
    private val setDefaultUseCase: SetDefaultInitialBizProfileUseCase,
    private val savedStateHandle: SavedStateHandle,
    @Dispatcher(Dispatchers.Dispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
): ViewModel() {
    val uiState = getUiStateUseCase.invoke()
        .flowOn(ioDispatcher)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = RequestState.Loading
        )

    private val keyUiWelcomeMessage = "uiWelcomeMessage"
    var uiWelcomeMessage by mutableStateOf(savedStateHandle[keyUiWelcomeMessage] ?: true)
        private set

    private val keyUiInputBizProfileForm = "uiInputBizProfileForm"
    var uiInputBizProfileForm by mutableStateOf(savedStateHandle[keyUiInputBizProfileForm] ?: false)
        private set

    private val keyUiEventStatus = "initializationUiEventStatus"
    var initializationUiEventStatus by mutableStateOf(savedStateHandle[keyUiEventStatus] ?: InitializationUiEventStatus.IDLE.name)
        private set

    val initializationScreenUiEvent = InitializationScreenUiEvent(this)

    fun setLanguage(language: Language) = viewModelScope.launch(ioDispatcher) {
        cfgGeneralRepository.setLanguage(language)
        AppCompatDelegate.setApplicationLocales( LocaleListCompat.create(Locale(language.code)) )
    }

    fun beginSetBizProfileManual() = viewModelScope.launch(ioDispatcher) {
        uiWelcomeMessage = false;savedStateHandle[keyUiWelcomeMessage] = false
        uiInputBizProfileForm = true;savedStateHandle[keyUiInputBizProfileForm] = true
    }

    fun onCancelManualInit() = viewModelScope.launch(ioDispatcher) {
        initializationScreenUiEvent.initializationUiFormState = InitializationScreenUiEvent.InitializationUiFormState()
        uiWelcomeMessage = true; savedStateHandle[keyUiWelcomeMessage] = true
        uiInputBizProfileForm = false; savedStateHandle[keyUiInputBizProfileForm] = false
    }

    fun setBizProfileDefault(businessProfileSummary: BusinessProfileSummary) = viewModelScope.launch(ioDispatcher) {
        initializationUiEventStatus = InitializationUiEventStatus.LOADING.name
        savedStateHandle[keyUiEventStatus] = InitializationUiEventStatus.LOADING.name

        initializationUiEventStatus = try{
            val result = setDefaultUseCase.invoke(businessProfileSummary)
            if(result !=null){
                savedStateHandle[keyUiEventStatus] = InitializationUiEventStatus.SUCCESS.name
                InitializationUiEventStatus.SUCCESS.name
            } else{
                savedStateHandle[keyUiEventStatus] = InitializationUiEventStatus.FAILED.name
                InitializationUiEventStatus.FAILED.name
            }
        }
        catch (e: Throwable){
            savedStateHandle[keyUiEventStatus] = InitializationUiEventStatus.FAILED.name
            InitializationUiEventStatus.FAILED.name
        }
    }

    fun onSubmitManualInit(businessProfileSummary: BusinessProfileSummary) = viewModelScope.launch(ioDispatcher) {
        initializationUiEventStatus = InitializationUiEventStatus.LOADING.name
        savedStateHandle[keyUiEventStatus] = InitializationUiEventStatus.LOADING.name

        initializationUiEventStatus = try{
            val result = setDefaultUseCase.invoke(businessProfileSummary)
            if(result != null){
                savedStateHandle[keyUiEventStatus] = InitializationUiEventStatus.SUCCESS.name
                InitializationUiEventStatus.SUCCESS.name
            } else{
                savedStateHandle[keyUiEventStatus] = InitializationUiEventStatus.FAILED.name
                InitializationUiEventStatus.FAILED.name
            }
        }catch (e: Throwable){
            savedStateHandle[keyUiEventStatus] = InitializationUiEventStatus.FAILED.name
            InitializationUiEventStatus.FAILED.name
        }
    }
}
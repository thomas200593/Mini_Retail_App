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
import com.thomas200593.mini_retail_app.features.app_config.repository.AppConfigRepository
import com.thomas200593.mini_retail_app.features.app_config.repository.ConfigGeneralRepository
import com.thomas200593.mini_retail_app.features.business.entity.business_profile.dto.BusinessProfileSummary
import com.thomas200593.mini_retail_app.features.initial.domain.SetDefaultInitialBizProfileUseCase
import com.thomas200593.mini_retail_app.features.initial.entity.FirstTimeStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class InitializationViewModel @Inject constructor(
    private val appConfigRepository: AppConfigRepository,
    private val cfgGeneralRepository: ConfigGeneralRepository,
    private val setDefaultUseCase: SetDefaultInitialBizProfileUseCase,
    private val savedStateHandle: SavedStateHandle,
    @Dispatcher(Dispatchers.Dispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
): ViewModel() {

    companion object{
        private const val KEY_SHOW_WELCOME_MESSAGE = "showWelcomeMessage"
        private const val KEY_SHOW_INPUT_MANUAL_FORM = "showInputManualForm"
        private const val KEY_SHOW_PROGRESS_DIALOG = "showProgressDialog"
    }

    var showWelcomeMessage by
    mutableStateOf(value = savedStateHandle.get<Boolean>(KEY_SHOW_WELCOME_MESSAGE) ?: true)
        private set

    var showInputManualForm by
    mutableStateOf(value = savedStateHandle.get<Boolean>(KEY_SHOW_INPUT_MANUAL_FORM) ?: false)
        private set

    var showProgressDialog by
    mutableStateOf(value = savedStateHandle.get<Boolean>(KEY_SHOW_PROGRESS_DIALOG) ?: false)
        private set

    private val _languages: MutableState<RequestState<Set<Language>>> = mutableStateOf(RequestState.Idle)
    val languages = _languages
    val configCurrent = appConfigRepository.configCurrent.flowOn(ioDispatcher)
        .catch { RequestState.Error(it) }
        .map { RequestState.Success(it) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = RequestState.Loading
        )
    private val _initialDefaultSetupUiState: MutableState<RequestState<BusinessProfileSummary>> = mutableStateOf(RequestState.Idle)
    val initialDefaultSetupUiState = _initialDefaultSetupUiState

    fun onOpen() = viewModelScope.launch(ioDispatcher) {
        getLanguages()
        if(isInitStateValid()){ resettleInitState() }
    }

    private fun resettleInitState() {
        savedStateHandle[KEY_SHOW_WELCOME_MESSAGE] = showWelcomeMessage
        savedStateHandle[KEY_SHOW_INPUT_MANUAL_FORM] = showInputManualForm
    }

    private suspend fun isInitStateValid(): Boolean = withContext(ioDispatcher){
        !((!savedStateHandle.contains(KEY_SHOW_INPUT_MANUAL_FORM)) ||
        (!savedStateHandle.contains(KEY_SHOW_WELCOME_MESSAGE)))
    }

    private fun getLanguages() = viewModelScope.launch(ioDispatcher) {
        _languages.value = RequestState.Loading
        _languages.value = try { RequestState.Success(cfgGeneralRepository.getLanguages()) }
        catch (e: Throwable){ RequestState.Error(e) }
    }

    fun setLanguage(language: Language) = viewModelScope.launch(ioDispatcher) {
        //TODO save all state to savedStateHandle

        cfgGeneralRepository.setLanguage(language)
        AppCompatDelegate.setApplicationLocales( LocaleListCompat.create(Locale(language.code)) )
    }

    fun initSetupDefault(bizProfileSummary: BusinessProfileSummary) = viewModelScope.launch(ioDispatcher){
        this@InitializationViewModel.showInputManualForm = false
        savedStateHandle[KEY_SHOW_INPUT_MANUAL_FORM] = false

        this@InitializationViewModel.showWelcomeMessage = true
        savedStateHandle[KEY_SHOW_WELCOME_MESSAGE] = true

        _initialDefaultSetupUiState.value = RequestState.Loading
        _initialDefaultSetupUiState.value = try {
            val result = setDefaultUseCase.invoke(bizProfileSummary)
            if(result != null){
                appConfigRepository.setFirstTimeStatus(FirstTimeStatus.NO)
                RequestState.Success(result)
            }
            else{ RequestState.Empty }
        }
        catch (e: Throwable){ RequestState.Error(e) }
    }

    fun initSetupManual() = viewModelScope.launch(ioDispatcher){
        this@InitializationViewModel.showInputManualForm = true
        savedStateHandle[KEY_SHOW_INPUT_MANUAL_FORM] = true

        this@InitializationViewModel.showWelcomeMessage = false
        savedStateHandle[KEY_SHOW_WELCOME_MESSAGE] = false
    }
}
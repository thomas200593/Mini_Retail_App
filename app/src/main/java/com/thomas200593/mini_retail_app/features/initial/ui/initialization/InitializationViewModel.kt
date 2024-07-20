package com.thomas200593.mini_retail_app.features.initial.ui.initialization

import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.core.os.LocaleListCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thomas200593.mini_retail_app.R
import com.thomas200593.mini_retail_app.core.design_system.dispatchers.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.dispatchers.Dispatchers.Dispatchers.IO
import com.thomas200593.mini_retail_app.core.design_system.util.RequestState
import com.thomas200593.mini_retail_app.core.ui.component.Form.Component.UseCase.InputFieldValidation
import com.thomas200593.mini_retail_app.core.ui.component.Form.Component.UseCase.UiText
import com.thomas200593.mini_retail_app.features.app_config.entity.Language
import com.thomas200593.mini_retail_app.features.app_config.repository.ConfigGeneralRepository
import com.thomas200593.mini_retail_app.features.business.entity.business_profile.dto.BusinessProfileSummary
import com.thomas200593.mini_retail_app.features.initial.domain.GetInitializationDataUseCase
import com.thomas200593.mini_retail_app.features.initial.domain.SetDefaultInitialBizProfileUseCase
import com.thomas200593.mini_retail_app.features.initial.entity.Initialization
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class InitializationViewModel @Inject constructor(
    private val getInitializationDataUseCase: GetInitializationDataUseCase,
    private val cfgGeneralRepository: ConfigGeneralRepository,
    private val setDefaultUseCase: SetDefaultInitialBizProfileUseCase,
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher
): ViewModel(){
    var uiState = mutableStateOf(
        InitializationUiState(
            initBizProfileResult = InitializationUiEvent.InitBizProfileResult.Idle,
            initUiPropertiesState = InitUiPropertiesState()
        )
    );  private set

    fun onEvent(event: InitializationUiEvent){
        when(event){
            InitializationUiEvent.OnOpen -> viewModelScope.launch(ioDispatcher) {
                uiState.value = uiState.value.copy(initializationData = RequestState.Loading)
                getInitializationDataUseCase.invoke().flowOn(ioDispatcher)
                    .catch { error -> uiState.value = uiState.value.copy(initializationData = RequestState.Error(error)) }
                    .collectLatest{ initializationData -> uiState.value = uiState.value.copy(initializationData = initializationData) }
            }
            is InitializationUiEvent.OnChangeLanguage -> viewModelScope.launch(ioDispatcher) {
                cfgGeneralRepository.setLanguage(language = event.language)
                AppCompatDelegate.setApplicationLocales( LocaleListCompat.create(Locale(event.language.code)) )
            }
            is InitializationUiEvent.BeginInitBizProfileDefault -> viewModelScope.launch(ioDispatcher) {
                uiState.value = uiState.value.copy(
                    initBizProfileResult = InitializationUiEvent.InitBizProfileResult.Loading,
                    initUiPropertiesState = uiState.value.initUiPropertiesState.copy(
                        uiEnableLoadingDialog = mutableStateOf(true),
                        uiEnableSuccessDialog = mutableStateOf(false),
                        uiEnableEmptyDialog = mutableStateOf(false),
                        uiEnableErrorDialog = mutableStateOf(false)
                    )
                )
                try {
                    val result = setDefaultUseCase.invoke(event.bizProfile)
                    if(result != null){
                        uiState.value = uiState.value.copy(
                            initBizProfileResult = InitializationUiEvent.InitBizProfileResult.Success(result),
                            initUiPropertiesState = uiState.value.initUiPropertiesState.copy(
                                uiEnableLoadingDialog = mutableStateOf(false),
                                uiEnableSuccessDialog = mutableStateOf(true),
                                uiEnableEmptyDialog = mutableStateOf(false),
                                uiEnableErrorDialog = mutableStateOf(false)
                            )
                        )
                    }
                    else{
                        uiState.value = uiState.value.copy(
                            initBizProfileResult = InitializationUiEvent.InitBizProfileResult.Empty,
                            initUiPropertiesState = uiState.value.initUiPropertiesState.copy(
                                uiEnableLoadingDialog = mutableStateOf(false),
                                uiEnableSuccessDialog = mutableStateOf(false),
                                uiEnableEmptyDialog = mutableStateOf(true),
                                uiEnableErrorDialog = mutableStateOf(false)
                            )
                        )
                    }
                }catch (e: Throwable){
                    uiState.value = uiState.value.copy(
                        initBizProfileResult = InitializationUiEvent.InitBizProfileResult.Error(e),
                        initUiPropertiesState = uiState.value.initUiPropertiesState.copy(
                            uiEnableLoadingDialog = mutableStateOf(false),
                            uiEnableSuccessDialog = mutableStateOf(false),
                            uiEnableEmptyDialog = mutableStateOf(false),
                            uiEnableErrorDialog = mutableStateOf(true)
                        )
                    )
                }
            }
            InitializationUiEvent.BeginInitBizProfileManual -> viewModelScope.launch(ioDispatcher) {
                uiState.value = uiState.value
                    .copy(initUiPropertiesState = uiState.value.initUiPropertiesState
                            .copy(uiEnableWelcomeMessage = false, uiEnableInitManualForm = true)
                    )
            }
            is InitializationUiEvent.OnUiFormLegalNameChanged -> viewModelScope.launch(ioDispatcher){
                uiState.value = uiState.value.copy(
                    initUiPropertiesState = uiState.value.initUiPropertiesState
                        .copy(uiFormLegalName = event.legalName)
                )
                enableSubmitButton()
            }
            is InitializationUiEvent.OnUiFormCommonNameChanged -> viewModelScope.launch(ioDispatcher){
                uiState.value = uiState.value.copy(
                    initUiPropertiesState = uiState.value.initUiPropertiesState
                        .copy(uiFormCommonName = event.commonName)
                )
                enableSubmitButton()
            }
            is InitializationUiEvent.OnUiFormSubmitInitManual -> viewModelScope.launch(ioDispatcher){
                uiState.value = uiState.value.copy(
                    initBizProfileResult = InitializationUiEvent.InitBizProfileResult.Loading,
                    initUiPropertiesState = uiState.value.initUiPropertiesState.copy(
                        uiEnableLoadingDialog = mutableStateOf(true),
                        uiEnableSuccessDialog = mutableStateOf(false),
                        uiEnableEmptyDialog = mutableStateOf(false),
                        uiEnableErrorDialog = mutableStateOf(false)
                    )
                )
                try {
                    val result = setDefaultUseCase.invoke(event.bizProfile)
                    if(result != null){
                        uiState.value = uiState.value.copy(
                            initBizProfileResult = InitializationUiEvent.InitBizProfileResult.Success(result),
                            initUiPropertiesState = uiState.value.initUiPropertiesState.copy(
                                uiEnableLoadingDialog = mutableStateOf(false),
                                uiEnableSuccessDialog = mutableStateOf(true),
                                uiEnableEmptyDialog = mutableStateOf(false),
                                uiEnableErrorDialog = mutableStateOf(false)
                            )
                        )
                    }
                    else{
                        uiState.value = uiState.value.copy(
                            initBizProfileResult = InitializationUiEvent.InitBizProfileResult.Empty,
                            initUiPropertiesState = uiState.value.initUiPropertiesState.copy(
                                uiEnableLoadingDialog = mutableStateOf(false),
                                uiEnableSuccessDialog = mutableStateOf(false),
                                uiEnableEmptyDialog = mutableStateOf(true),
                                uiEnableErrorDialog = mutableStateOf(false)
                            )
                        )
                    }
                }catch (e: Throwable){
                    uiState.value = uiState.value.copy(
                        initBizProfileResult = InitializationUiEvent.InitBizProfileResult.Error(e),
                        initUiPropertiesState = uiState.value.initUiPropertiesState.copy(
                            uiEnableLoadingDialog = mutableStateOf(false),
                            uiEnableSuccessDialog = mutableStateOf(false),
                            uiEnableEmptyDialog = mutableStateOf(false),
                            uiEnableErrorDialog = mutableStateOf(true)
                        )
                    )
                }
            }
            InitializationUiEvent.OnUiFormCancelInitManual -> viewModelScope.launch(ioDispatcher){
                uiState.value = uiState.value.copy(initUiPropertiesState = InitUiPropertiesState())
            }
        }
    }

    private fun validateLegalName(): Boolean {
        val result = InputFieldValidation.RegularTextValidation().execute(
            input = uiState.value.initUiPropertiesState.uiFormLegalName,
            required = true,
            maxLength = 100,
        )
        uiState.value = uiState.value.copy(
            initUiPropertiesState = uiState.value.initUiPropertiesState
                .copy(uiFormLegalNameError = result.errorMessage)
        )
        return result.isSuccess
    }
    private fun validateCommonName(): Boolean {
        val result = InputFieldValidation.RegularTextValidation().execute(
            input = uiState.value.initUiPropertiesState.uiFormCommonName,
            required = true,
            maxLength = 100
        )
        uiState.value = uiState.value.copy(
            initUiPropertiesState = uiState.value.initUiPropertiesState
                .copy(uiFormCommonNameError = result.errorMessage)
        )
        return result.isSuccess
    }
    private fun enableSubmitButton() {
        val result = validateLegalName() && validateCommonName()
        uiState.value = uiState.value.copy(
            initUiPropertiesState = uiState.value.initUiPropertiesState
                .copy(uiFormEnableSubmitBtn = result)
        )
    }
}

sealed class InitializationUiEvent{
    data object OnOpen: InitializationUiEvent()
    data class OnChangeLanguage(val language: Language): InitializationUiEvent()
    data object BeginInitBizProfileManual: InitializationUiEvent()
    data class OnUiFormLegalNameChanged(val legalName: String): InitializationUiEvent()
    data class OnUiFormCommonNameChanged(val commonName: String): InitializationUiEvent()
    data class OnUiFormSubmitInitManual(val bizProfile: BusinessProfileSummary): InitializationUiEvent()
    data object OnUiFormCancelInitManual: InitializationUiEvent()
    data class BeginInitBizProfileDefault(val bizProfile: BusinessProfileSummary): InitializationUiEvent()
    sealed class InitBizProfileResult {
        data object Idle: InitBizProfileResult()
        data object Loading: InitBizProfileResult()
        data object Empty: InitBizProfileResult()
        data class Success(val bizProfileSummary: BusinessProfileSummary): InitBizProfileResult()
        data class Error(val t: Throwable?): InitBizProfileResult()
    }
}

data class InitializationUiState(
    val initializationData: RequestState<Initialization> = RequestState.Idle,
    val initUiPropertiesState: InitUiPropertiesState,
    val initBizProfileResult: InitializationUiEvent.InitBizProfileResult
)

data class InitUiPropertiesState(
    val uiEnableWelcomeMessage: Boolean = true,
    val uiEnableInitManualForm: Boolean = false,
    val uiEnableLoadingDialog: MutableState<Boolean> = mutableStateOf(false),
    val uiEnableEmptyDialog: MutableState<Boolean> = mutableStateOf(false),
    val uiEnableSuccessDialog: MutableState<Boolean> = mutableStateOf(false),
    val uiEnableErrorDialog: MutableState<Boolean> = mutableStateOf(false),

    val uiFormLegalName: String = String(),
    val uiFormLegalNameError: UiText? = UiText.StringResource(R.string.str_field_required),
    val uiFormCommonName: String = String(),
    val uiFormCommonNameError: UiText? = UiText.StringResource(R.string.str_field_required),
    val uiFormEnableSubmitBtn: Boolean = false
)
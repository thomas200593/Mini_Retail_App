package com.thomas200593.mini_retail_app.features.initial.initial.ui.initialization

import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.runtime.mutableStateOf
import androidx.core.os.LocaleListCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatchers.Dispatchers.IO
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState
import com.thomas200593.mini_retail_app.core.ui.component.CustomForm.Component.UseCase.InputFieldValidation
import com.thomas200593.mini_retail_app.features.app_conf._g_language.repository.RepositoryAppCfgGeneralLanguage
import com.thomas200593.mini_retail_app.features.initial.initial.domain.GetInitializationDataUseCase
import com.thomas200593.mini_retail_app.features.initial.initial.domain.SetDefaultInitialBizProfileUseCase
import com.thomas200593.mini_retail_app.features.initial.initial.entity.InitializationUiFormState
import com.thomas200593.mini_retail_app.features.initial.initial.entity.InitializationUiState
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
    private val repositoryAppCfgGeneralLanguage: RepositoryAppCfgGeneralLanguage,
    private val setDefaultUseCase: SetDefaultInitialBizProfileUseCase,
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher
): ViewModel(){
    var uiState = mutableStateOf(
        InitializationUiState(
            initializationUiFormState = InitializationUiFormState(
                initBizProfileResult = InitializationUiEvent.InitBizProfileResult.Idle
            )
        )
    );  private set

    fun onEvent(event: InitializationUiEvent){
        when(event){
            InitializationUiEvent.OnOpen -> viewModelScope.launch(ioDispatcher) {
                uiState.value = uiState.value.copy(initializationData = ResourceState.Loading)
                getInitializationDataUseCase.invoke().flowOn(ioDispatcher)
                    .catch { error -> uiState.value = uiState.value.copy(initializationData = ResourceState.Error(error)) }
                    .collectLatest{ initializationData -> uiState.value = uiState.value.copy(initializationData = initializationData) }
            }
            is InitializationUiEvent.OnChangeLanguage -> viewModelScope.launch(ioDispatcher) {
                repositoryAppCfgGeneralLanguage.setLanguage(language = event.language)
                AppCompatDelegate.setApplicationLocales( LocaleListCompat.create(Locale(event.language.code)) )
            }
            is InitializationUiEvent.BeginInitBizProfileDefault -> viewModelScope.launch(ioDispatcher) {
                uiState.value = uiState.value.copy(
                    uiEnableLoadingDialog = mutableStateOf(true),
                    uiEnableSuccessDialog = mutableStateOf(false),
                    uiEnableEmptyDialog = mutableStateOf(false),
                    uiEnableErrorDialog = mutableStateOf(false),
                    initializationUiFormState = InitializationUiFormState(
                        initBizProfileResult = InitializationUiEvent.InitBizProfileResult.Loading,
                    )
                )
                try {
                    val result = setDefaultUseCase.invoke(event.bizProfile)
                    if(result != null){
                        uiState.value = uiState.value.copy(
                            uiEnableLoadingDialog = mutableStateOf(false),
                            uiEnableSuccessDialog = mutableStateOf(true),
                            uiEnableEmptyDialog = mutableStateOf(false),
                            uiEnableErrorDialog = mutableStateOf(false),
                            initializationUiFormState = uiState.value.initializationUiFormState.copy(
                                initBizProfileResult = InitializationUiEvent.InitBizProfileResult.Success(
                                    result
                                )
                            )
                        )
                    }
                    else{
                        uiState.value = uiState.value.copy(
                            uiEnableLoadingDialog = mutableStateOf(false),
                            uiEnableSuccessDialog = mutableStateOf(false),
                            uiEnableEmptyDialog = mutableStateOf(true),
                            uiEnableErrorDialog = mutableStateOf(false),
                            initializationUiFormState = uiState.value.initializationUiFormState.copy(
                                initBizProfileResult = InitializationUiEvent.InitBizProfileResult.Empty,
                            )
                        )
                    }
                }catch (e: Throwable){
                    uiState.value = uiState.value.copy(
                        uiEnableLoadingDialog = mutableStateOf(false),
                        uiEnableSuccessDialog = mutableStateOf(false),
                        uiEnableEmptyDialog = mutableStateOf(false),
                        uiEnableErrorDialog = mutableStateOf(true),
                        initializationUiFormState = uiState.value.initializationUiFormState.copy(
                            initBizProfileResult = InitializationUiEvent.InitBizProfileResult.Error(
                                e
                            ),
                        )
                    )
                }
            }
            InitializationUiEvent.BeginInitBizProfileManual -> viewModelScope.launch(ioDispatcher) {
                uiState.value = uiState.value.copy(uiEnableWelcomeMessage = false, uiEnableInitManualForm = true)
            }
            is InitializationUiEvent.OnUiFormLegalNameChanged -> viewModelScope.launch(ioDispatcher){
                uiState.value = uiState.value.copy(
                    initializationUiFormState = uiState.value.initializationUiFormState
                        .copy(uiFormLegalName = event.legalName)
                )
                enableSubmitButton()
            }
            is InitializationUiEvent.OnUiFormCommonNameChanged -> viewModelScope.launch(ioDispatcher){
                uiState.value = uiState.value.copy(
                    initializationUiFormState = uiState.value.initializationUiFormState
                        .copy(uiFormCommonName = event.commonName)
                )
                enableSubmitButton()
            }
            is InitializationUiEvent.OnUiFormSubmitInitManual -> viewModelScope.launch(ioDispatcher){
                uiState.value = uiState.value.copy(
                    uiEnableLoadingDialog = mutableStateOf(true),
                    uiEnableSuccessDialog = mutableStateOf(false),
                    uiEnableEmptyDialog = mutableStateOf(false),
                    uiEnableErrorDialog = mutableStateOf(false),
                    initializationUiFormState = uiState.value.initializationUiFormState.copy(
                        initBizProfileResult = InitializationUiEvent.InitBizProfileResult.Loading,
                    )
                )
                try {
                    val result = setDefaultUseCase.invoke(event.bizProfile)
                    if(result != null){
                        uiState.value = uiState.value.copy(
                            uiEnableLoadingDialog = mutableStateOf(false),
                            uiEnableSuccessDialog = mutableStateOf(true),
                            uiEnableEmptyDialog = mutableStateOf(false),
                            uiEnableErrorDialog = mutableStateOf(false),
                            initializationUiFormState = uiState.value.initializationUiFormState.copy(
                                initBizProfileResult = InitializationUiEvent.InitBizProfileResult.Success(
                                    result
                                ),
                            )
                        )
                    }
                    else{
                        uiState.value = uiState.value.copy(
                            uiEnableLoadingDialog = mutableStateOf(false),
                            uiEnableSuccessDialog = mutableStateOf(false),
                            uiEnableEmptyDialog = mutableStateOf(true),
                            uiEnableErrorDialog = mutableStateOf(false),
                            initializationUiFormState = uiState.value.initializationUiFormState.copy(
                                initBizProfileResult = InitializationUiEvent.InitBizProfileResult.Empty
                            )
                        )
                    }
                }catch (e: Throwable){
                    uiState.value = uiState.value.copy(
                        uiEnableLoadingDialog = mutableStateOf(false),
                        uiEnableSuccessDialog = mutableStateOf(false),
                        uiEnableEmptyDialog = mutableStateOf(false),
                        uiEnableErrorDialog = mutableStateOf(true),
                        initializationUiFormState = uiState.value.initializationUiFormState.copy(
                            initBizProfileResult = InitializationUiEvent.InitBizProfileResult.Error(
                                e
                            ),
                        )
                    )
                }
            }
            InitializationUiEvent.OnUiFormCancelInitManual -> viewModelScope.launch(ioDispatcher){
                uiState.value = uiState.value.copy(
                    uiEnableLoadingDialog = mutableStateOf(false),
                    uiEnableSuccessDialog = mutableStateOf(false),
                    uiEnableEmptyDialog = mutableStateOf(false),
                    uiEnableErrorDialog = mutableStateOf(false),
                    uiEnableInitManualForm = false,
                    uiEnableWelcomeMessage = true,
                    initializationUiFormState = InitializationUiFormState(
                        initBizProfileResult = InitializationUiEvent.InitBizProfileResult.Idle,
                    )
                )
            }
        }
    }

    private fun validateLegalName(): Boolean {
        val result = InputFieldValidation.RegularTextValidation().execute(
            input = uiState.value.initializationUiFormState.uiFormLegalName,
            required = true,
            maxLength = 100,
        )
        uiState.value = uiState.value.copy(
            initializationUiFormState = uiState.value.initializationUiFormState
                .copy(uiFormLegalNameError = result.errorMessage)
        )
        return result.isSuccess
    }
    private fun validateCommonName(): Boolean {
        val result = InputFieldValidation.RegularTextValidation().execute(
            input = uiState.value.initializationUiFormState.uiFormCommonName,
            required = true,
            maxLength = 100
        )
        uiState.value = uiState.value.copy(
            initializationUiFormState = uiState.value.initializationUiFormState
                .copy(uiFormCommonNameError = result.errorMessage)
        )
        return result.isSuccess
    }
    private fun enableSubmitButton() {
        val result = validateLegalName() && validateCommonName()
        uiState.value = uiState.value.copy(
            initializationUiFormState = uiState.value.initializationUiFormState
                .copy(uiFormEnableSubmitBtn = result)
        )
    }
}
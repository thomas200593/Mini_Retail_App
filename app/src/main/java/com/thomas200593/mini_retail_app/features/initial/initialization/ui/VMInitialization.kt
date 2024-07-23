package com.thomas200593.mini_retail_app.features.initial.initialization.ui

import androidx.appcompat.app.AppCompatDelegate.setApplicationLocales
import androidx.compose.runtime.mutableStateOf
import androidx.core.os.LocaleListCompat.create
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatchers.Dispatchers.IO
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState.Error
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState.Loading
import com.thomas200593.mini_retail_app.core.ui.component.CustomForm.Component.UseCase.InputFieldValidation.RegularTextValidation
import com.thomas200593.mini_retail_app.features.app_conf._g_language.repository.RepoConfGenLanguage
import com.thomas200593.mini_retail_app.features.initial.initialization.domain.UCGetInitializationData
import com.thomas200593.mini_retail_app.features.initial.initialization.domain.UCSetDefaultInitialBizProfile
import com.thomas200593.mini_retail_app.features.initial.initialization.entity.InitializationUiFormState
import com.thomas200593.mini_retail_app.features.initial.initialization.entity.InitializationUiState
import com.thomas200593.mini_retail_app.features.initial.initialization.ui.InitializationUiEvent.BeginInitBizProfileDefault
import com.thomas200593.mini_retail_app.features.initial.initialization.ui.InitializationUiEvent.BeginInitBizProfileManual
import com.thomas200593.mini_retail_app.features.initial.initialization.ui.InitializationUiEvent.InitBizProfileResult
import com.thomas200593.mini_retail_app.features.initial.initialization.ui.InitializationUiEvent.InitBizProfileResult.Idle
import com.thomas200593.mini_retail_app.features.initial.initialization.ui.InitializationUiEvent.OnChangeLanguage
import com.thomas200593.mini_retail_app.features.initial.initialization.ui.InitializationUiEvent.OnOpen
import com.thomas200593.mini_retail_app.features.initial.initialization.ui.InitializationUiEvent.OnUiFormCancelInitManual
import com.thomas200593.mini_retail_app.features.initial.initialization.ui.InitializationUiEvent.OnUiFormCommonNameChanged
import com.thomas200593.mini_retail_app.features.initial.initialization.ui.InitializationUiEvent.OnUiFormLegalNameChanged
import com.thomas200593.mini_retail_app.features.initial.initialization.ui.InitializationUiEvent.OnUiFormSubmitInitManual
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class VMInitialization @Inject constructor(
    private val ucGetInitializationData: UCGetInitializationData,
    private val ucSetDefaultInitialBizProfile: UCSetDefaultInitialBizProfile,
    private val repoConfGenLanguage: RepoConfGenLanguage,
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher
): ViewModel(){
    var uiState = mutableStateOf(
        InitializationUiState(
            initializationUiFormState = InitializationUiFormState(initBizProfileResult = Idle)
        )
    );  private set

    fun onEvent(event: InitializationUiEvent){
        when(event){
            OnOpen -> viewModelScope.launch(ioDispatcher) {
                uiState.value = uiState.value.copy(initializationData = Loading)
                ucGetInitializationData.invoke().flowOn(ioDispatcher)
                    .catch { error -> uiState.value =
                        uiState.value.copy(initializationData = Error(error)) }
                    .collectLatest{ initializationData -> uiState.value =
                        uiState.value.copy(initializationData = initializationData) }
            }
            is OnChangeLanguage -> viewModelScope.launch(ioDispatcher) {
                repoConfGenLanguage.setLanguage(language = event.language)
                setApplicationLocales( create(Locale(event.language.code)) )
            }
            is BeginInitBizProfileDefault -> viewModelScope.launch(ioDispatcher) {
                uiState.value = uiState.value.copy(
                    uiEnableLoadingDialog = mutableStateOf(true),
                    uiEnableSuccessDialog = mutableStateOf(false),
                    uiEnableEmptyDialog = mutableStateOf(false),
                    uiEnableErrorDialog = mutableStateOf(false),
                    initializationUiFormState = InitializationUiFormState(
                        initBizProfileResult = InitBizProfileResult.Loading,
                    )
                )
                try {
                    val result = ucSetDefaultInitialBizProfile.invoke(event.bizProfile)
                    if(result != null){
                        uiState.value = uiState.value.copy(
                            uiEnableLoadingDialog = mutableStateOf(false),
                            uiEnableSuccessDialog = mutableStateOf(true),
                            uiEnableEmptyDialog = mutableStateOf(false),
                            uiEnableErrorDialog = mutableStateOf(false),
                            initializationUiFormState = uiState.value.initializationUiFormState.copy(
                                initBizProfileResult = InitBizProfileResult.Success(result)
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
                                initBizProfileResult = InitBizProfileResult.Empty,
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
                            initBizProfileResult = InitBizProfileResult.Error(e),
                        )
                    )
                }
            }
            BeginInitBizProfileManual -> viewModelScope.launch(ioDispatcher) {
                uiState.value = uiState.value.copy(uiEnableWelcomeMessage = false, uiEnableInitManualForm = true)
            }
            is OnUiFormLegalNameChanged -> viewModelScope.launch(ioDispatcher){
                uiState.value = uiState.value.copy(
                    initializationUiFormState = uiState.value.initializationUiFormState
                        .copy(uiFormLegalName = event.legalName)
                )
                enableSubmitButton()
            }
            is OnUiFormCommonNameChanged -> viewModelScope.launch(ioDispatcher){
                uiState.value = uiState.value.copy(
                    initializationUiFormState = uiState.value.initializationUiFormState
                        .copy(uiFormCommonName = event.commonName)
                )
                enableSubmitButton()
            }
            is OnUiFormSubmitInitManual -> viewModelScope.launch(ioDispatcher){
                uiState.value = uiState.value.copy(
                    uiEnableLoadingDialog = mutableStateOf(true),
                    uiEnableSuccessDialog = mutableStateOf(false),
                    uiEnableEmptyDialog = mutableStateOf(false),
                    uiEnableErrorDialog = mutableStateOf(false),
                    initializationUiFormState = uiState.value.initializationUiFormState.copy(
                        initBizProfileResult = InitBizProfileResult.Loading,
                    )
                )
                try {
                    val result = ucSetDefaultInitialBizProfile.invoke(event.bizProfile)
                    if(result != null){
                        uiState.value = uiState.value.copy(
                            uiEnableLoadingDialog = mutableStateOf(false),
                            uiEnableSuccessDialog = mutableStateOf(true),
                            uiEnableEmptyDialog = mutableStateOf(false),
                            uiEnableErrorDialog = mutableStateOf(false),
                            initializationUiFormState = uiState.value.initializationUiFormState.copy(
                                initBizProfileResult = InitBizProfileResult.Success(result),
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
                                initBizProfileResult = InitBizProfileResult.Empty
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
                            initBizProfileResult = InitBizProfileResult.Error(
                                e
                            )
                        )
                    )
                }
            }
            OnUiFormCancelInitManual -> viewModelScope.launch(ioDispatcher){
                uiState.value = uiState.value.copy(
                    uiEnableLoadingDialog = mutableStateOf(false),
                    uiEnableSuccessDialog = mutableStateOf(false),
                    uiEnableEmptyDialog = mutableStateOf(false),
                    uiEnableErrorDialog = mutableStateOf(false),
                    uiEnableInitManualForm = false,
                    uiEnableWelcomeMessage = true,
                    initializationUiFormState = InitializationUiFormState(
                        initBizProfileResult = Idle
                    )
                )
            }
        }
    }

    private fun validateLegalName(): Boolean {
        val result = RegularTextValidation().execute(
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
        val result = RegularTextValidation().execute(
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
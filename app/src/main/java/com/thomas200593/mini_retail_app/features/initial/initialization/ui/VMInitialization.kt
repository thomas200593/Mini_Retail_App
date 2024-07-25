package com.thomas200593.mini_retail_app.features.initial.initialization.ui

import androidx.appcompat.app.AppCompatDelegate.setApplicationLocales
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.core.os.LocaleListCompat.create
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thomas200593.mini_retail_app.R.string.str_field_required
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatchers
import com.thomas200593.mini_retail_app.core.design_system.util.HlpStateFlow.update
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState.Error
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState.Idle
import com.thomas200593.mini_retail_app.core.ui.component.CustomForm.Component.UseCase.InputFieldValidation.RegularTextValidation
import com.thomas200593.mini_retail_app.core.ui.component.CustomForm.Component.UseCase.UiText
import com.thomas200593.mini_retail_app.core.ui.component.CustomForm.Component.UseCase.UiText.StringResource
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_language.entity.Language
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_language.repository.RepoConfGenLanguage
import com.thomas200593.mini_retail_app.features.business.entity.business_profile.dto.BizProfileSummary
import com.thomas200593.mini_retail_app.features.initial.initialization.domain.UCGetInitializationData
import com.thomas200593.mini_retail_app.features.initial.initialization.domain.UCSetInitialBizProfile
import com.thomas200593.mini_retail_app.features.initial.initialization.entity.Initialization
import com.thomas200593.mini_retail_app.features.initial.initialization.ui.VMInitialization.UiEvents.ButtonEvents.OnBeginDefaultInitBizProfile
import com.thomas200593.mini_retail_app.features.initial.initialization.ui.VMInitialization.UiEvents.ButtonEvents.OnBeginManualInitBizProfile
import com.thomas200593.mini_retail_app.features.initial.initialization.ui.VMInitialization.UiEvents.FormEvents.OnFormCommonNameChanged
import com.thomas200593.mini_retail_app.features.initial.initialization.ui.VMInitialization.UiEvents.FormEvents.OnFormLegalNameChanged
import com.thomas200593.mini_retail_app.features.initial.initialization.ui.VMInitialization.UiEvents.InitBizProfileResult
import com.thomas200593.mini_retail_app.features.initial.initialization.ui.VMInitialization.UiEvents.InitBizProfileResult.Loading
import com.thomas200593.mini_retail_app.features.initial.initialization.ui.VMInitialization.UiEvents.LanguageSelectionEvents.OnChangeLanguage
import com.thomas200593.mini_retail_app.features.initial.initialization.ui.VMInitialization.UiEvents.ScreenEvents.OnOpen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class VMInitialization @Inject constructor(
    private val ucGetInitializationData: UCGetInitializationData,
    private val ucSetInitBizProfile: UCSetInitialBizProfile,
    private val repoConfGenLanguage: RepoConfGenLanguage,
    @Dispatcher(Dispatchers.Dispatchers.IO) private val ioDispatcher: CoroutineDispatcher
): ViewModel(){
    data class UiState(
        val initDataState: ResourceState<Initialization> = Idle,
        val ui: UI,
        val formState: FormState,
        val dialogState: DialogState,
        val initBizUiResult: InitBizProfileResult
    )
    data class UI(
        val uiEnableWelcomeMessage: Boolean = true,
        val uiEnableInitManualForm: Boolean = false,
    )
    data class FormState(
        val uiFormLegalName: String = String(),
        val uiFormLegalNameError: UiText? = StringResource(str_field_required),
        val uiFormCommonName: String = String(),
        val uiFormCommonNameError: UiText? = StringResource(str_field_required),
        val uiFormEnableSubmitBtn: Boolean = false,
    )
    data class DialogState(
        val uiEnableLoadingDialog: MutableState<Boolean> = mutableStateOf(false),
        val uiEnableEmptyDialog: MutableState<Boolean> = mutableStateOf(false),
        val uiEnableSuccessDialog: MutableState<Boolean> = mutableStateOf(false),
        val uiEnableErrorDialog: MutableState<Boolean> = mutableStateOf(false)
    )
    sealed class UiEvents{
        sealed class ScreenEvents: UiEvents(){
            data object OnOpen: ScreenEvents()
        }
        sealed class LanguageSelectionEvents: UiEvents(){
            data class OnChangeLanguage(val language: Language): LanguageSelectionEvents()
        }
        sealed class FormEvents: UiEvents(){
            data class OnFormLegalNameChanged(val legalName: String): FormEvents()
            data class OnFormCommonNameChanged(val commonName: String): FormEvents()
            data class OnFormSubmit(val bizProfileSummary: BizProfileSummary): FormEvents()
            data object OnFormCancel: FormEvents()
        }
        sealed class ButtonEvents: UiEvents(){
            data class OnBeginDefaultInitBizProfile(val bizProfileSummary: BizProfileSummary): ButtonEvents()
            data object OnBeginManualInitBizProfile: ButtonEvents()
        }
        sealed class InitBizProfileResult {
            data object Idle: InitBizProfileResult()
            data object Loading: InitBizProfileResult()
            data object Empty: InitBizProfileResult()
            data class Error(val t: Throwable): InitBizProfileResult()
            data class Success(val data: BizProfileSummary): InitBizProfileResult()
        }
    }

    private val _uiState = MutableStateFlow(
        UiState(
            ui = UI(),
            formState = FormState(),
            dialogState = DialogState(),
            initBizUiResult = InitBizProfileResult.Idle
        )
    )
    val uiState = _uiState.asStateFlow()

    fun onEvent(events: UiEvents){
        when(events){
            OnOpen -> viewModelScope.launch(ioDispatcher) {
                _uiState.update { prev -> prev.copy(initDataState = ResourceState.Loading) }
                ucGetInitializationData.invoke().flowOn(ioDispatcher)
                    .catch { e -> _uiState.update { prev -> prev.copy(initDataState = Error(e)) } }
                    .collectLatest { data -> _uiState.update { prev -> prev.copy(initDataState = data) } }
            }
            is OnChangeLanguage -> viewModelScope.launch(ioDispatcher) {
                repoConfGenLanguage.setLanguage(events.language)
                setApplicationLocales( create(Locale(events.language.code)) )
            }
            is OnBeginDefaultInitBizProfile -> viewModelScope.launch(ioDispatcher) {
                _uiState.update { prev ->
                    prev.copy(
                        dialogState = prev.dialogState.copy(
                            uiEnableLoadingDialog = mutableStateOf(true),
                            uiEnableEmptyDialog = mutableStateOf(false),
                            uiEnableSuccessDialog = mutableStateOf(false),
                            uiEnableErrorDialog = mutableStateOf(false)
                        ),
                        initBizUiResult = Loading
                    )
                }
                try{
                    val result = ucSetInitBizProfile.invoke(events.bizProfileSummary)
                    if(result != null) {
                        _uiState.update { prev ->
                            prev.copy(
                                dialogState = prev.dialogState.copy(
                                    uiEnableLoadingDialog = mutableStateOf(false),
                                    uiEnableEmptyDialog = mutableStateOf(false),
                                    uiEnableSuccessDialog = mutableStateOf(true),
                                    uiEnableErrorDialog = mutableStateOf(false)
                                ),
                                initBizUiResult = InitBizProfileResult.Success(data = result)
                            )
                        }
                    }
                    else {
                        _uiState.update { prev ->
                            prev.copy(
                                dialogState = prev.dialogState.copy(
                                    uiEnableLoadingDialog = mutableStateOf(false),
                                    uiEnableEmptyDialog = mutableStateOf(true),
                                    uiEnableSuccessDialog = mutableStateOf(false),
                                    uiEnableErrorDialog = mutableStateOf(false)
                                ),
                                initBizUiResult = InitBizProfileResult.Empty
                            )
                        }
                    }
                } catch (e: Throwable) {
                    _uiState.update { prev ->
                        prev.copy(
                            dialogState = prev.dialogState.copy(
                                uiEnableLoadingDialog = mutableStateOf(false),
                                uiEnableEmptyDialog = mutableStateOf(false),
                                uiEnableSuccessDialog = mutableStateOf(false),
                                uiEnableErrorDialog = mutableStateOf(true)
                            ),
                            initBizUiResult = InitBizProfileResult.Error(e)
                        )
                    }
                }
            }
            OnBeginManualInitBizProfile -> viewModelScope.launch(ioDispatcher) {
                _uiState.update { prev -> prev.copy(ui = prev.ui.copy(uiEnableInitManualForm = true, uiEnableWelcomeMessage = false)) }
            }
            is OnFormLegalNameChanged -> viewModelScope.launch(ioDispatcher) {
                _uiState.update { prev -> prev.copy(formState = prev.formState.copy(uiFormLegalName = events.legalName)) }
                enableFormSubmitButton()
            }
            is OnFormCommonNameChanged -> viewModelScope.launch(ioDispatcher) {
                _uiState.update { prev -> prev.copy(formState = prev.formState.copy(uiFormCommonName = events.commonName)) }
                enableFormSubmitButton()
            }
            is UiEvents.FormEvents.OnFormSubmit -> viewModelScope.launch(ioDispatcher) {
                _uiState.update { prev ->
                    prev.copy(
                        dialogState = prev.dialogState.copy(
                            uiEnableLoadingDialog = mutableStateOf(true),
                            uiEnableEmptyDialog = mutableStateOf(false),
                            uiEnableSuccessDialog = mutableStateOf(false),
                            uiEnableErrorDialog = mutableStateOf(false)
                        ),
                        initBizUiResult = Loading
                    )
                }
                try{
                    val result = ucSetInitBizProfile.invoke(events.bizProfileSummary)
                    if(result != null) {
                        _uiState.update { prev ->
                            prev.copy(
                                dialogState = prev.dialogState.copy(
                                    uiEnableLoadingDialog = mutableStateOf(false),
                                    uiEnableEmptyDialog = mutableStateOf(false),
                                    uiEnableSuccessDialog = mutableStateOf(true),
                                    uiEnableErrorDialog = mutableStateOf(false)
                                ),
                                initBizUiResult = InitBizProfileResult.Success(data = result)
                            )
                        }
                    }
                    else {
                        _uiState.update { prev ->
                            prev.copy(
                                dialogState = prev.dialogState.copy(
                                    uiEnableLoadingDialog = mutableStateOf(false),
                                    uiEnableEmptyDialog = mutableStateOf(true),
                                    uiEnableSuccessDialog = mutableStateOf(false),
                                    uiEnableErrorDialog = mutableStateOf(false)
                                ),
                                initBizUiResult = InitBizProfileResult.Empty
                            )
                        }
                    }
                } catch (e: Throwable) {
                    _uiState.update { prev ->
                        prev.copy(
                            dialogState = prev.dialogState.copy(
                                uiEnableLoadingDialog = mutableStateOf(false),
                                uiEnableEmptyDialog = mutableStateOf(false),
                                uiEnableSuccessDialog = mutableStateOf(false),
                                uiEnableErrorDialog = mutableStateOf(true)
                            ),
                            initBizUiResult = InitBizProfileResult.Error(e)
                        )
                    }
                }
            }
            UiEvents.FormEvents.OnFormCancel -> viewModelScope.launch(ioDispatcher) {
                _uiState.update { prev ->
                    prev.copy(
                        formState = FormState(),
                        ui = prev.ui.copy(uiEnableInitManualForm = false, uiEnableWelcomeMessage = true)
                    )
                }
            }
        }
    }
    private fun formValidateLegalName(): Boolean {
        val result = RegularTextValidation().execute(
            input = _uiState.value.formState.uiFormLegalName,
            required = true,
            maxLength = 100
        )
        _uiState.update { prev -> prev.copy(formState = prev.formState.copy(uiFormLegalNameError = result.errorMessage)) }
        return result.isSuccess
    }
    private fun formValidateCommonName(): Boolean {
        val result = RegularTextValidation().execute(
            input = _uiState.value.formState.uiFormCommonName,
            required = true,
            maxLength = 100
        )
        _uiState.update { prev -> prev.copy(formState = prev.formState.copy(uiFormCommonNameError = result.errorMessage)) }
        return result.isSuccess
    }
    private fun enableFormSubmitButton() {
        val result = formValidateLegalName() && formValidateCommonName()
        _uiState.update { prev -> prev.copy(formState = prev.formState.copy(uiFormEnableSubmitBtn = result)) }
    }
}
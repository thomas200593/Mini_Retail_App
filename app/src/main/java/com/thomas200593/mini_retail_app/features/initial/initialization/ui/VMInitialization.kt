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
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState.Empty
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState.Error
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState.Idle
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState.Loading
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState.Success
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
import com.thomas200593.mini_retail_app.features.initial.initialization.ui.VMInitialization.UiEvents.FormEvents.OnFormCancel
import com.thomas200593.mini_retail_app.features.initial.initialization.ui.VMInitialization.UiEvents.FormEvents.OnFormCommonNameChanged
import com.thomas200593.mini_retail_app.features.initial.initialization.ui.VMInitialization.UiEvents.FormEvents.OnFormLegalNameChanged
import com.thomas200593.mini_retail_app.features.initial.initialization.ui.VMInitialization.UiEvents.FormEvents.OnFormSubmit
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
        val ui: UI = UI(),
        val formState: FormState = FormState(),
        val dialogState: DialogState = DialogState(),
        val initBizUiResult: ResourceState<BizProfileSummary> = Idle
    )
    data class UI(
        val uiEnableWelcomeMessage: Boolean = true,
        val uiEnableInitManualForm: Boolean = false,
    )
    data class FormState(
        val uiFormLegalName: String = "",
        val uiFormLegalNameError: UiText? = StringResource(str_field_required),
        val uiFormCommonName: String = "",
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
    }

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    fun onEvent(events: UiEvents) = viewModelScope.launch(ioDispatcher) {
        when(events){
            OnOpen -> fetchInitData()
            is OnChangeLanguage -> changeLanguage(events.language)
            is OnBeginDefaultInitBizProfile -> handleBizProfileInitialization(events.bizProfileSummary)
            OnBeginManualInitBizProfile -> enableManualForm()
            is OnFormLegalNameChanged -> updateFormLegalName(events.legalName)
            is OnFormCommonNameChanged -> updateFormCommonName(events.commonName)
            is OnFormSubmit -> handleBizProfileInitialization(events.bizProfileSummary)
            OnFormCancel -> resetFormAndUI()
        }
    }
    private fun fetchInitData() = viewModelScope.launch(ioDispatcher) {
        _uiState.update { it.copy(initDataState = Loading) }
        ucGetInitializationData.invoke().flowOn(ioDispatcher)
            .catch { e -> _uiState.update { it.copy(initDataState = Error(e)) } }
            .collectLatest { data -> _uiState.update { it.copy(initDataState = data) } }
    }
    private fun changeLanguage(language: Language) = viewModelScope.launch(ioDispatcher) {
        repoConfGenLanguage.setLanguage(language); setApplicationLocales( create(Locale(language.code)) )
    }
    private fun enableManualForm() = viewModelScope.launch(ioDispatcher) {
        _uiState.update { it.copy(ui = it.ui.copy(uiEnableInitManualForm = true, uiEnableWelcomeMessage = false)) }
    }
    private fun updateFormLegalName(legalName: String) = viewModelScope.launch(ioDispatcher) {
        _uiState.update { it.copy(formState = it.formState.copy(uiFormLegalName = legalName)) }
        enableFormSubmitButton()
    }
    private fun formValidateLegalName(): Boolean {
        val result = RegularTextValidation().execute(input = _uiState.value.formState.uiFormLegalName, required = true, maxLength = 100)
        _uiState.update { it.copy(formState = it.formState.copy(uiFormLegalNameError = result.errorMessage)) }
        return result.isSuccess
    }
    private fun updateFormCommonName(commonName: String) = viewModelScope.launch(ioDispatcher) {
        _uiState.update { it.copy(formState = it.formState.copy(uiFormCommonName = commonName)) }
        enableFormSubmitButton()
    }
    private fun formValidateCommonName(): Boolean {
        val result = RegularTextValidation().execute(input = _uiState.value.formState.uiFormCommonName, required = true, maxLength = 100)
        _uiState.update { it.copy(formState = it.formState.copy(uiFormCommonNameError = result.errorMessage)) }
        return result.isSuccess
    }
    private fun enableFormSubmitButton() = viewModelScope.launch(ioDispatcher) {
        val result = formValidateLegalName() && formValidateCommonName()
        _uiState.update { it.copy(formState = it.formState.copy(uiFormEnableSubmitBtn = result)) }
    }
    private fun handleBizProfileInitialization(bizProfileSummary: BizProfileSummary) = viewModelScope.launch(ioDispatcher) {
        viewModelScope.launch(ioDispatcher) {
            updateDialogState(loading = true, empty = false, success = false, error = false)
            _uiState.update { it.copy(initBizUiResult = Loading) }
            try {
                val result = ucSetInitBizProfile.invoke(bizProfileSummary)
                if (result != null) {
                    updateDialogState(loading = false, empty = false, success = true, error = false)
                    _uiState.update { it.copy(initBizUiResult = Success(data = result)) }
                } else {
                    updateDialogState(loading = false, empty = true, success = false, error = false)
                    _uiState.update { it.copy(initBizUiResult = Empty) }
                }
            } catch (e: Throwable) {
                updateDialogState(loading = false, empty = false, success = false, error = true)
                _uiState.update { it.copy(initBizUiResult = Error(e)) }
            }
        }
    }
    private fun resetFormAndUI() = viewModelScope.launch(ioDispatcher)  {
        _uiState.update { it.copy(formState = FormState(), ui = UI()) }
    }
    private fun updateDialogState(
        loading: Boolean = false,
        empty: Boolean = false,
        success: Boolean = false, error: Boolean = false) = viewModelScope.launch(ioDispatcher) {
        _uiState.update { it.copy(
            dialogState = it.dialogState.copy(
                uiEnableLoadingDialog = mutableStateOf(loading),
                uiEnableEmptyDialog = mutableStateOf(empty),
                uiEnableSuccessDialog = mutableStateOf(success),
                uiEnableErrorDialog = mutableStateOf(error)
            )
        ) }
    }
}
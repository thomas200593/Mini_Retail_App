package com.thomas200593.mini_retail_app.features.initial.initialization.ui

import androidx.appcompat.app.AppCompatDelegate.setApplicationLocales
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.core.os.LocaleListCompat.create
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thomas200593.mini_retail_app.R
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
import com.thomas200593.mini_retail_app.features.initial.initialization.ui.VMInitialization.UiEvents.ScreenEvents.ButtonEvents
import com.thomas200593.mini_retail_app.features.initial.initialization.ui.VMInitialization.UiEvents.ScreenEvents.FormEvents
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
        val initialization: ResourceState<Initialization> = Idle,
        val screenState: ScreenState = ScreenState(),
        val formState: FormState = FormState(),
        val dialogState: DialogState = DialogState(),
        val initBizProfileResult: ResourceState<BizProfileSummary> = Idle
    )
    data class ScreenState(
        val welcomeMessageEnabled: Boolean = true,
        val initBizProfileManualFormEnabled: Boolean = false,
    )
    data class FormState(
        val fldLegalNameValue: String = "",
        val fldLegalNameError: UiText? = StringResource(R.string.str_field_required),
        val fldCommonNameValue: String = "",
        val fldCommonNameError: UiText? = StringResource(R.string.str_field_required),
        val fldSubmitBtnEnabled: Boolean = false,
    )
    data class DialogState(
        val dlgLoadingEnabled: MutableState<Boolean> = mutableStateOf(false),
        val dlgEmptyEnabled: MutableState<Boolean> = mutableStateOf(false),
        val dlgSuccessEnabled: MutableState<Boolean> = mutableStateOf(false),
        val dlgErrorEnabled: MutableState<Boolean> = mutableStateOf(false)
    )
    sealed class UiEvents{
        sealed class ScreenEvents: UiEvents(){
            data object OnOpen: ScreenEvents()
            sealed class FormEvents: ScreenEvents(){
                data class OnFormLegalNameChanged(val legalName: String): FormEvents()
                data class OnFormCommonNameChanged(val commonName: String): FormEvents()
                data class OnFormSubmit(val bizProfileSummary: BizProfileSummary): FormEvents()
                data object OnFormCancel: FormEvents()
            }
            sealed class ButtonEvents: ScreenEvents(){
                sealed class BtnInitDefaultBizProfile: ButtonEvents(){
                    data class OnClick(val bizProfileSummary: BizProfileSummary): BtnInitDefaultBizProfile()
                }
                sealed class BtnInitManualBizProfile: ButtonEvents(){
                    data object OnClick: BtnInitManualBizProfile()
                }
                sealed class BtnChangeLanguage: ButtonEvents(){
                    data class OnSelect(val language: Language): BtnChangeLanguage()
                }
            }
        }
    }

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    fun onEvent(events: UiEvents) = viewModelScope.launch(ioDispatcher) {
        when(events){
            OnOpen -> getInitializationData()
            is ButtonEvents.BtnChangeLanguage.OnSelect -> doChangeLanguage(events.language)
            is ButtonEvents.BtnInitDefaultBizProfile.OnClick -> doInitBizProfile(events.bizProfileSummary)
            ButtonEvents.BtnInitManualBizProfile.OnClick -> doShowForm()
            is FormEvents.OnFormLegalNameChanged -> updateFormLegalName(events.legalName)
            is FormEvents.OnFormCommonNameChanged -> updateFormCommonName(events.commonName)
            is FormEvents.OnFormSubmit -> doInitBizProfile(events.bizProfileSummary)
            FormEvents.OnFormCancel -> doResetForm()
        }
    }
    private fun getInitializationData() = viewModelScope.launch(ioDispatcher) {
        _uiState.update { it.copy(initialization = Loading) }
        ucGetInitializationData.invoke().flowOn(ioDispatcher)
            .catch { e -> _uiState.update { it.copy(initialization = Error(e)) } }
            .collectLatest { data -> _uiState.update { it.copy(initialization = data) } }
    }
    private fun doChangeLanguage(language: Language) = viewModelScope.launch(ioDispatcher) {
        repoConfGenLanguage.setLanguage(language)
        setApplicationLocales( create(Locale(language.code)) )
    }
    private fun doShowForm() = viewModelScope.launch(ioDispatcher) {
        _uiState.update {
            it.copy(
                screenState = it.screenState.copy(
                    initBizProfileManualFormEnabled = true,
                    welcomeMessageEnabled = false
                )
            )
        }
    }
    private fun updateFormLegalName(legalName: String) = viewModelScope.launch(ioDispatcher) {
        _uiState.update {
            it.copy(
                formState = it.formState.copy(
                    fldLegalNameValue = legalName
                )
            )
        }
        enableFormSubmitButton()
    }
    private fun formValidateLegalName(): Boolean {
        val result = RegularTextValidation().execute(
            input = _uiState.value.formState.fldLegalNameValue,
            required = true,
            maxLength = 100
        )
        _uiState.update {
            it.copy(
                formState = it.formState.copy(
                    fldLegalNameError = result.errorMessage
                )
            )
        }
        return result.isSuccess
    }
    private fun updateFormCommonName(commonName: String) = viewModelScope.launch(ioDispatcher) {
        _uiState.update {
            it.copy(
                formState = it.formState.copy(
                    fldCommonNameValue = commonName
                )
            )
        }
        enableFormSubmitButton()
    }
    private fun formValidateCommonName(): Boolean {
        val result = RegularTextValidation().execute(
            input = _uiState.value.formState.fldCommonNameValue,
            required = true,
            maxLength = 100
        )
        _uiState.update {
            it.copy(
                formState = it.formState.copy(
                    fldCommonNameError = result.errorMessage
                )
            )
        }
        return result.isSuccess
    }
    private fun enableFormSubmitButton() = viewModelScope.launch(ioDispatcher) {
        val result = formValidateLegalName() && formValidateCommonName()
        _uiState.update { it.copy(formState = it.formState.copy(fldSubmitBtnEnabled = result)) }
    }
    private fun doInitBizProfile(bizProfileSummary: BizProfileSummary) = viewModelScope.launch(ioDispatcher) {
        viewModelScope.launch(ioDispatcher) {
            updateDialogState(loading = true, empty = false, success = false, error = false)
            _uiState.update { it.copy(initBizProfileResult = Loading) }
            try {
                val result = ucSetInitBizProfile.invoke(bizProfileSummary)
                if (result != null) {
                    updateDialogState(loading = false, empty = false, success = true, error = false)
                    _uiState.update { it.copy(initBizProfileResult = Success(data = result)) }
                } else {
                    updateDialogState(loading = false, empty = true, success = false, error = false)
                    _uiState.update { it.copy(initBizProfileResult = Empty) }
                }
            } catch (e: Throwable) {
                updateDialogState(loading = false, empty = false, success = false, error = true)
                _uiState.update { it.copy(initBizProfileResult = Error(e)) }
            }
        }
    }
    private fun doResetForm() = viewModelScope.launch(ioDispatcher)  {
        _uiState.update { it.copy(formState = FormState(), screenState = ScreenState()) }
    }
    private fun updateDialogState(
        loading: Boolean = false,
        empty: Boolean = false,
        success: Boolean = false, error: Boolean = false
    ) = viewModelScope.launch(ioDispatcher) {
        _uiState.update {
            it.copy(
                dialogState = it.dialogState.copy(
                    dlgLoadingEnabled = mutableStateOf(loading),
                    dlgEmptyEnabled = mutableStateOf(empty),
                    dlgSuccessEnabled = mutableStateOf(success),
                    dlgErrorEnabled = mutableStateOf(error)
                )
            )
        }
    }
}
package com.thomas200593.mini_retail_app.features.initial.initialization.ui

import androidx.appcompat.app.AppCompatDelegate.setApplicationLocales
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.core.os.LocaleListCompat.create
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thomas200593.mini_retail_app.R
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatchers.Dispatchers.IO
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
import com.thomas200593.mini_retail_app.features.initial.initialization.ui.VMInitialization.UiEvents.ButtonEvents.BtnInitDefaultBizProfileEvents
import com.thomas200593.mini_retail_app.features.initial.initialization.ui.VMInitialization.UiEvents.ButtonEvents.BtnInitManualBizProfileEvents
import com.thomas200593.mini_retail_app.features.initial.initialization.ui.VMInitialization.UiEvents.DropdownEvents.DDLanguage
import com.thomas200593.mini_retail_app.features.initial.initialization.ui.VMInitialization.UiEvents.InputFormEvents.BtnCancelEvents
import com.thomas200593.mini_retail_app.features.initial.initialization.ui.VMInitialization.UiEvents.InputFormEvents.BtnSubmitEvents
import com.thomas200593.mini_retail_app.features.initial.initialization.ui.VMInitialization.UiEvents.InputFormEvents.CommonNameEvents
import com.thomas200593.mini_retail_app.features.initial.initialization.ui.VMInitialization.UiEvents.InputFormEvents.LegalNameEvents
import com.thomas200593.mini_retail_app.features.initial.initialization.ui.VMInitialization.UiEvents.OnOpenEvents
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
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher
): ViewModel() {
    data class UiState(
        val initialization: ResourceState<Initialization> = Idle,
        val welcomePanelState: WelcomePanelState = WelcomePanelState(),
        val inputFormState: InputFormState = InputFormState(),
        val dialogState: DialogState = DialogState(),
        val initBizProfileResult: ResourceState<BizProfileSummary> = Idle
    )
    data class WelcomePanelState(val visible: Boolean = true)
    data class InputFormState(
        val visible: Boolean = false,
        val legalName: String = String(),
        val legalNameError: UiText? = StringResource(R.string.str_field_required),
        val commonName: String = String(),
        val commonNameError: UiText? = StringResource(R.string.str_field_required),
        val fldSubmitBtnEnabled: Boolean = false,
    )
    data class DialogState(
        val dlgLoadingEnabled: MutableState<Boolean> = mutableStateOf(false),
        val dlgEmptyEnabled: MutableState<Boolean> = mutableStateOf(false),
        val dlgSuccessEnabled: MutableState<Boolean> = mutableStateOf(false),
        val dlgErrorEnabled: MutableState<Boolean> = mutableStateOf(false)
    )
    sealed class UiEvents{
        data object OnOpenEvents: UiEvents()
        sealed class InputFormEvents: UiEvents() {
            sealed class LegalNameEvents: InputFormEvents() {
                data class ValueChanged(val legalName: String): LegalNameEvents()
            }
            sealed class CommonNameEvents: InputFormEvents(){
                data class ValueChanged(val commonName: String): CommonNameEvents()
            }
            sealed class BtnSubmitEvents: InputFormEvents(){
                data class OnClick(val bizProfileSummary: BizProfileSummary): BtnSubmitEvents()
            }
            sealed class BtnCancelEvents: InputFormEvents(){
                data object OnClick: BtnCancelEvents()
            }
        }
        sealed class DropdownEvents: UiEvents(){
            sealed class DDLanguage: DropdownEvents(){
                data class OnSelect(val language: Language): DDLanguage()
            }
        }
        sealed class ButtonEvents: UiEvents(){
            sealed class BtnInitDefaultBizProfileEvents: ButtonEvents(){
                data class OnClick(val bizProfileSummary: BizProfileSummary): BtnInitDefaultBizProfileEvents()
            }
            sealed class BtnInitManualBizProfileEvents: ButtonEvents(){
                data object OnClick: BtnInitManualBizProfileEvents()
            }
        }
    }

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    fun onEvent(events: UiEvents) {
        when(events){
            OnOpenEvents -> onOpenEvent()
            is DDLanguage.OnSelect -> onSelectLanguageEvent(events.language)
            is BtnInitDefaultBizProfileEvents.OnClick -> doInitBizProfile(events.bizProfileSummary)
            BtnInitManualBizProfileEvents.OnClick -> doShowForm()
            is LegalNameEvents.ValueChanged -> formLegalNameValueChanged(events.legalName)
            is CommonNameEvents.ValueChanged -> formCommonNameValueChanged(events.commonName)
            is BtnSubmitEvents.OnClick -> doInitBizProfile(events.bizProfileSummary)
            BtnCancelEvents.OnClick -> doResetForm()
        }
    }
    private fun onOpenEvent() = viewModelScope.launch(ioDispatcher) {
        _uiState.update { it.copy(initialization = Loading) }
        ucGetInitializationData.invoke().flowOn(ioDispatcher)
            .catch { e -> _uiState.update { it.copy(initialization = Error(e)) } }
            .collectLatest { data -> _uiState.update { it.copy(initialization = data) } }
    }
    private fun onSelectLanguageEvent(language: Language) = viewModelScope.launch(ioDispatcher) {
        repoConfGenLanguage.setLanguage(language)
        setApplicationLocales( create(Locale(language.code)) )
    }
    private fun doShowForm() {
        _uiState.update {
            it.copy(
                welcomePanelState = it.welcomePanelState.copy(visible = false),
                inputFormState = it.inputFormState.copy(visible = true)
            )
        }
    }
    private fun formLegalNameValueChanged(legalName: String) {
        _uiState.update { it.copy(inputFormState = it.inputFormState.copy(legalName = legalName)) }
        formSubmitBtnShouldEnable()
    }
    private fun formLegalNameValidation(): Boolean {
        val result = RegularTextValidation()
            .execute(
                input = _uiState.value.inputFormState.legalName,
                required = true,
                maxLength = 100
            )
        _uiState.update {
            it.copy(
                inputFormState = it.inputFormState.copy(
                    legalNameError = result.errorMessage
                )
            )
        }
        return result.isSuccess
    }
    private fun formCommonNameValueChanged(commonName: String) {
        _uiState.update {
            it.copy(
                inputFormState = it.inputFormState.copy(
                    commonName = commonName
                )
            )
        }
        formSubmitBtnShouldEnable()
    }
    private fun formCommonNameValidation(): Boolean {
        val result = RegularTextValidation()
            .execute(
                input = _uiState.value.inputFormState.commonName, required = true, maxLength = 100
            )
        _uiState.update {
            it.copy(
                inputFormState = it.inputFormState.copy(
                    commonNameError = result.errorMessage
                )
            )
        }
        return result.isSuccess
    }
    private fun formSubmitBtnShouldEnable() {
        val result = formLegalNameValidation() && formCommonNameValidation()
        _uiState.update {
            it.copy(
                inputFormState = it.inputFormState.copy(
                    fldSubmitBtnEnabled = result
                )
            )
        }
    }
    private fun doInitBizProfile(bizProfileSummary: BizProfileSummary) =
        viewModelScope.launch(ioDispatcher) {
            updateDialogState(
                dlgLoadingEnabled = true,
                dlgEmptyEnabled = false,
                dlgSuccessEnabled = false,
                dlgErrorEnabled = false
            )
            _uiState.update { it.copy(initBizProfileResult = Loading) }
            try {
                val result = ucSetInitBizProfile.invoke(bizProfileSummary)
                if (result != null) {
                    updateDialogState(
                        dlgLoadingEnabled = false,
                        dlgEmptyEnabled = false,
                        dlgSuccessEnabled = true,
                        dlgErrorEnabled = false
                    )
                    _uiState.update { it.copy(initBizProfileResult = Success(data = result)) }
                } else {
                    updateDialogState(
                        dlgLoadingEnabled = false,
                        dlgEmptyEnabled = true,
                        dlgSuccessEnabled = false,
                        dlgErrorEnabled = false
                    )
                    _uiState.update { it.copy(initBizProfileResult = Empty) }
                }
            } catch (e: Throwable) {
                updateDialogState(
                    dlgLoadingEnabled = false,
                    dlgEmptyEnabled = false,
                    dlgSuccessEnabled = false,
                    dlgErrorEnabled = true
                )
                _uiState.update { it.copy(initBizProfileResult = Error(e)) }
            }
        }
    private fun doResetForm() {
        _uiState.update {
            it.copy(
                welcomePanelState = WelcomePanelState(),
                dialogState = DialogState(),
                inputFormState = InputFormState()
            )
        }
    }
    private fun updateDialogState(
        dlgLoadingEnabled: Boolean = false,
        dlgEmptyEnabled: Boolean = false,
        dlgSuccessEnabled: Boolean = false,
        dlgErrorEnabled: Boolean = false
    ) {
        _uiState.update {
            it.copy(
                dialogState = it.dialogState.copy(
                    dlgLoadingEnabled = mutableStateOf(dlgLoadingEnabled),
                    dlgEmptyEnabled = mutableStateOf(dlgEmptyEnabled),
                    dlgSuccessEnabled = mutableStateOf(dlgSuccessEnabled),
                    dlgErrorEnabled = mutableStateOf(dlgErrorEnabled)
                )
            )
        }
    }
}
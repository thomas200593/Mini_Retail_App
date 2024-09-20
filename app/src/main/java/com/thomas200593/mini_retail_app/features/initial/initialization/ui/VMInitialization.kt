package com.thomas200593.mini_retail_app.features.initial.initialization.ui

import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.core.os.LocaleListCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thomas200593.mini_retail_app.R
import com.thomas200593.mini_retail_app.core.data.local.database.entity_common.RepoIndustries
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatchers.Dispatchers.IO
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.di.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState
import com.thomas200593.mini_retail_app.core.ui.component.CustomForm.Component.UseCase.InputFieldValidation.RegularTextValidation
import com.thomas200593.mini_retail_app.core.ui.component.CustomForm.Component.UseCase.UiText
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_language.entity.Language
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_language.repository.RepoConfGenLanguage
import com.thomas200593.mini_retail_app.features.business.biz_profile.entity.BizProfileShort
import com.thomas200593.mini_retail_app.features.initial.initialization.domain.UCGetInitializationData
import com.thomas200593.mini_retail_app.features.initial.initialization.domain.UCSetInitialBizProfile
import com.thomas200593.mini_retail_app.features.initial.initialization.entity.Initialization
import com.thomas200593.mini_retail_app.features.initial.initialization.ui.VMInitialization.UiEvents.ButtonEvents.BtnInitDefaultBizProfileEvents
import com.thomas200593.mini_retail_app.features.initial.initialization.ui.VMInitialization.UiEvents.ButtonEvents.BtnInitManualBizProfileEvents
import com.thomas200593.mini_retail_app.features.initial.initialization.ui.VMInitialization.UiEvents.DialogEvents.DlgResError
import com.thomas200593.mini_retail_app.features.initial.initialization.ui.VMInitialization.UiEvents.DialogEvents.DlgResSuccess
import com.thomas200593.mini_retail_app.features.initial.initialization.ui.VMInitialization.UiEvents.DropdownEvents.DDIndustry
import com.thomas200593.mini_retail_app.features.initial.initialization.ui.VMInitialization.UiEvents.DropdownEvents.DDLanguage
import com.thomas200593.mini_retail_app.features.initial.initialization.ui.VMInitialization.UiEvents.InputFormEvents.BtnCancelEvents
import com.thomas200593.mini_retail_app.features.initial.initialization.ui.VMInitialization.UiEvents.InputFormEvents.BtnSubmitEvents
import com.thomas200593.mini_retail_app.features.initial.initialization.ui.VMInitialization.UiEvents.InputFormEvents.CommonNameEvents
import com.thomas200593.mini_retail_app.features.initial.initialization.ui.VMInitialization.UiEvents.InputFormEvents.LegalNameEvents
import com.thomas200593.mini_retail_app.features.initial.initialization.ui.VMInitialization.UiEvents.OnOpenEvents
import com.thomas200593.mini_retail_app.features.initial.initialization.ui.VMInitialization.UiStateInitialization.Loading
import com.thomas200593.mini_retail_app.features.initial.initialization.ui.VMInitialization.UiStateInitialization.Success
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class VMInitialization @Inject constructor(
    private val ucGetInitializationData: UCGetInitializationData,
    private val ucSetInitBizProfile: UCSetInitialBizProfile,
    private val repoConfGenLanguage: RepoConfGenLanguage,
    private val repoIndustries: RepoIndustries,
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {
    sealed interface UiStateInitialization{
        data object Loading: UiStateInitialization
        data class Success(val data: Initialization): UiStateInitialization
    }
    data class UiState(
        val initialization: UiStateInitialization = Loading,
        val panelWelcomeMessageState: PanelWelcomeMessageState = PanelWelcomeMessageState(),
        val panelInputFormState: PanelInputFormState = PanelInputFormState(),
        val dialogState: DialogState = DialogState(),
        val initBizProfileOperationResult: ResourceState<BizProfileShort> = ResourceState.Idle
    )
    data class DialogState(
        val dlgResLoading: MutableState<Boolean> = mutableStateOf(false),
        val dlgResSuccess: MutableState<Boolean> = mutableStateOf(false),
        val dlgResError: MutableState<Boolean> = mutableStateOf(false)
    )
    data class PanelWelcomeMessageState(val visible: Boolean = true)
    data class PanelInputFormState(
        val visible: Boolean = false,
        val legalName: String = String(),
        val legalNameError: UiText? = UiText.StringResource(R.string.str_field_required),
        val commonName: String = String(),
        val commonNameError: UiText? = UiText.StringResource(R.string.str_field_required),
        val industryKey: String = String(),
        val fldSubmitBtnEnabled: Boolean = false
    )

    sealed interface UiEvents{
        data object OnOpenEvents: UiEvents
        sealed interface InputFormEvents: UiEvents {
            sealed interface LegalNameEvents: InputFormEvents {
                data class ValueChanged(val legalName: String): LegalNameEvents
            }
            sealed interface CommonNameEvents: InputFormEvents {
                data class ValueChanged(val commonName: String): CommonNameEvents
            }
            sealed interface BtnSubmitEvents: InputFormEvents {
                data class OnClick(val bizProfileShort: BizProfileShort): BtnSubmitEvents
            }
            sealed interface BtnCancelEvents: InputFormEvents {
                data object OnClick: BtnCancelEvents
            }
        }
        sealed interface DropdownEvents: UiEvents {
            sealed interface DDLanguage: DropdownEvents {
                data class OnSelect(val language: Language): DDLanguage
            }
            sealed interface DDIndustry: DropdownEvents {
                data class OnSelect(val industryKey: String): DDIndustry
            }
        }
        sealed interface ButtonEvents: UiEvents {
            sealed interface BtnInitDefaultBizProfileEvents: ButtonEvents {
                data class OnClick(val bizProfileShort: BizProfileShort): BtnInitDefaultBizProfileEvents
            }
            sealed interface BtnInitManualBizProfileEvents: ButtonEvents {
                data object OnClick: BtnInitManualBizProfileEvents
            }
        }
        sealed interface DialogEvents: UiEvents {
            sealed interface DlgResSuccess: DialogEvents {
                data object OnConfirm: DlgResSuccess
            }
            sealed interface DlgResError: DialogEvents {
                data object OnDismiss: DlgResError
            }
        }
    }

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    fun onEvent(events: UiEvents) {
        when(events) {
            is OnOpenEvents -> onOpenEvent()
            is DDLanguage.OnSelect -> onSelectLanguageEvent(events.language)
            is BtnInitDefaultBizProfileEvents.OnClick -> doInitBizProfile(events.bizProfileShort)
            is BtnInitManualBizProfileEvents.OnClick -> doShowPanelInputForm()
            is LegalNameEvents.ValueChanged -> frmValChgLegalName(events.legalName)
            is CommonNameEvents.ValueChanged -> frmValChgCommonName(events.commonName)
            is DDIndustry.OnSelect -> frmValChgIndustry(events.industryKey)
            is BtnSubmitEvents.OnClick -> doInitBizProfile(events.bizProfileShort)
            is BtnCancelEvents.OnClick -> doResetUiState()
            is DlgResSuccess.OnConfirm -> doResetUiState()
            is DlgResError.OnDismiss -> doResetUiState()
        }
    }

    private fun updateDialogState(
        dlgResLoading: Boolean = false,
        dlgResSuccess: Boolean = false,
        dlgResError: Boolean = false
    ) = _uiState.update {
        it.copy(
            dialogState = it.dialogState.copy(
                dlgResLoading = mutableStateOf(dlgResLoading),
                dlgResSuccess = mutableStateOf(dlgResSuccess),
                dlgResError = mutableStateOf(dlgResError)
            )
        )
    }
    private fun resetDialogState() =
        _uiState.update { it.copy(dialogState = DialogState()) }

    private fun updatePanelWelcomeMessageState(
        visible: Boolean = true
    ) = _uiState.update {
        it.copy(
            panelWelcomeMessageState = it.panelWelcomeMessageState.copy(
                visible = visible
            )
        )
    }
    private fun resetPanelWelcomeMessageState() =
        _uiState.update { it.copy(panelWelcomeMessageState = PanelWelcomeMessageState()) }

    private fun updatePanelInputFormState(
        visible: Boolean = PanelInputFormState().visible,
        legalName: String = PanelInputFormState().legalName,
        legalNameError: UiText? = PanelInputFormState().legalNameError,
        commonName: String = PanelInputFormState().commonName,
        commonNameError: UiText? = PanelInputFormState().commonNameError,
        industryKey: String = repoIndustries.getDefaultKey(),
        fldSubmitBtnEnabled: Boolean = PanelInputFormState().fldSubmitBtnEnabled
    ) = _uiState.update {
        it.copy(
            panelInputFormState = it.panelInputFormState.copy(
                visible = visible,
                legalName = legalName,
                legalNameError = legalNameError,
                commonName = commonName,
                commonNameError = commonNameError,
                industryKey = industryKey,
                fldSubmitBtnEnabled = fldSubmitBtnEnabled
            )
        )
    }
    private fun resetPanelInputFormState() =
        _uiState.update { it.copy(panelInputFormState = PanelInputFormState()) }
    private fun onOpenEvent() = viewModelScope.launch {
        ucGetInitializationData().flowOn(ioDispatcher)
            .collect { result -> _uiState.update { it.copy(initialization = Success(result.data)) } }
    }
    private fun onSelectLanguageEvent(language: Language) = viewModelScope.launch {
        repoConfGenLanguage.setLanguage(language)
        AppCompatDelegate.setApplicationLocales(LocaleListCompat.create(Locale(language.code)))
    }
    private fun doInitBizProfile(bizProfileShort: BizProfileShort) = viewModelScope.launch {
        resetDialogState(); updateDialogState(dlgResLoading = true)
        updatePanelWelcomeMessageState(true); updatePanelInputFormState(visible = false)
        val operationResult = runCatching { ucSetInitBizProfile.invoke(bizProfileShort) }
            .fold(
                onSuccess = { result -> if(result != null) Pair(true, result) else Pair(false, null) },
                onFailure = { throwable -> Pair(false, throwable) }
            )
        resetDialogState()
        val (isSuccess, resultOrError) = operationResult
        if(isSuccess && resultOrError is BizProfileShort) {
            _uiState.update { it.copy(initBizProfileOperationResult = ResourceState.Success(data = resultOrError)) }
            updateDialogState(dlgResSuccess = true)
        } else if(resultOrError is Throwable) {
            _uiState.update { it.copy(initBizProfileOperationResult = ResourceState.Error(t = resultOrError)) }
            updateDialogState(dlgResError = true)
        } else {
            _uiState.update { it.copy(initBizProfileOperationResult = ResourceState.Empty) }
            updateDialogState(dlgResError = true)
        }
    }
    private fun doShowPanelInputForm()
    { updatePanelWelcomeMessageState(visible = false); updatePanelInputFormState(visible = true) }
    private fun doResetUiState() {
        resetPanelInputFormState()
        resetPanelWelcomeMessageState()
        resetDialogState()
        _uiState.update { it.copy(initBizProfileOperationResult = ResourceState.Idle) }
        onOpenEvent()
    }

    //Forms
    private fun frmValChgLegalName(legalName: String) {
        _uiState.update { it.copy(panelInputFormState = it.panelInputFormState.copy(legalName = legalName)) }
        formSubmitBtnShouldEnable()
    }
    private fun frmVldLegalName(): Boolean {
        val result = RegularTextValidation()
            .execute(
                input = _uiState.value.panelInputFormState.legalName,
                required = true,
                maxLength = 100
            )
        _uiState.update { it.copy(panelInputFormState = it.panelInputFormState.copy(legalNameError = result.errorMessage)) }
        return result.isSuccess
    }
    private fun frmValChgCommonName(commonName: String) {
        _uiState.update { it.copy(panelInputFormState = it.panelInputFormState.copy(commonName = commonName)) }
        formSubmitBtnShouldEnable()
    }
    private fun frmVldCommonName(): Boolean {
        val result = RegularTextValidation()
            .execute(
                input = _uiState.value.panelInputFormState.commonName,
                required = true,
                maxLength = 100
            )
        _uiState.update { it.copy(panelInputFormState = it.panelInputFormState.copy(commonNameError = result.errorMessage)) }
        return result.isSuccess
    }
    private fun frmValChgIndustry(industryKey: String) =
        _uiState.update { it.copy(panelInputFormState = it.panelInputFormState.copy(industryKey = industryKey)) }
    private fun formSubmitBtnShouldEnable() {
        val result = frmVldLegalName() && frmVldCommonName()
        _uiState.update { it.copy(panelInputFormState = it.panelInputFormState.copy(fldSubmitBtnEnabled = result)) }
    }
}
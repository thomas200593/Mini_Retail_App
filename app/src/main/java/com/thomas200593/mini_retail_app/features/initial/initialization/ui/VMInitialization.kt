package com.thomas200593.mini_retail_app.features.initial.initialization.ui

import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.core.os.LocaleListCompat
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thomas200593.mini_retail_app.R
import com.thomas200593.mini_retail_app.core.data.local.database.entity_common.AuditTrail
import com.thomas200593.mini_retail_app.core.data.local.database.entity_common.Industries
import com.thomas200593.mini_retail_app.core.data.local.database.entity_common.LegalDocumentType
import com.thomas200593.mini_retail_app.core.data.local.database.entity_common.LegalType
import com.thomas200593.mini_retail_app.core.data.local.database.entity_common.RepoIndustries
import com.thomas200593.mini_retail_app.core.data.local.database.entity_common.RepoLegalDocType
import com.thomas200593.mini_retail_app.core.data.local.database.entity_common.RepoLegalType
import com.thomas200593.mini_retail_app.core.data.local.database.entity_common.RepoTaxation
import com.thomas200593.mini_retail_app.core.data.local.database.entity_common.Taxation
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatchers.Dispatchers.IO
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.di.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.util.HlpCountry
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState
import com.thomas200593.mini_retail_app.core.ui.component.form.domain.RegularTextValidation
import com.thomas200593.mini_retail_app.core.ui.component.form.state.UiText
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_country.entity.Country
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_language.entity.Language
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_language.repository.RepoConfGenLanguage
import com.thomas200593.mini_retail_app.features.business.biz_profile.entity.BizName
import com.thomas200593.mini_retail_app.features.business.biz_profile.entity.BizProfileShort
import com.thomas200593.mini_retail_app.features.initial.initialization.domain.UCGetInitializationData
import com.thomas200593.mini_retail_app.features.initial.initialization.domain.UCSetInitialBizProfile
import com.thomas200593.mini_retail_app.features.initial.initialization.entity.Initialization
import com.thomas200593.mini_retail_app.features.initial.initialization.ui.VMInitialization.UiEvents.ButtonEvents.BtnInitDefaultBizProfileEvents
import com.thomas200593.mini_retail_app.features.initial.initialization.ui.VMInitialization.UiEvents.ButtonEvents.BtnInitManualBizProfileEvents
import com.thomas200593.mini_retail_app.features.initial.initialization.ui.VMInitialization.UiEvents.ButtonEvents.BtnToggleIndustryAdditionalInfoEvents
import com.thomas200593.mini_retail_app.features.initial.initialization.ui.VMInitialization.UiEvents.ButtonEvents.BtnToggleLegalDocTypeAdditionalInfoEvents
import com.thomas200593.mini_retail_app.features.initial.initialization.ui.VMInitialization.UiEvents.ButtonEvents.BtnToggleLegalDocTypeUsageEvents
import com.thomas200593.mini_retail_app.features.initial.initialization.ui.VMInitialization.UiEvents.ButtonEvents.BtnToggleLegalTypeAdditionalInfoEvents
import com.thomas200593.mini_retail_app.features.initial.initialization.ui.VMInitialization.UiEvents.ButtonEvents.BtnToggleTaxInclusionEvents
import com.thomas200593.mini_retail_app.features.initial.initialization.ui.VMInitialization.UiEvents.DialogEvents.DlgResError
import com.thomas200593.mini_retail_app.features.initial.initialization.ui.VMInitialization.UiEvents.DialogEvents.DlgResSuccess
import com.thomas200593.mini_retail_app.features.initial.initialization.ui.VMInitialization.UiEvents.DropdownEvents.DDIndustry
import com.thomas200593.mini_retail_app.features.initial.initialization.ui.VMInitialization.UiEvents.DropdownEvents.DDLanguage
import com.thomas200593.mini_retail_app.features.initial.initialization.ui.VMInitialization.UiEvents.DropdownEvents.DDLegalDocType
import com.thomas200593.mini_retail_app.features.initial.initialization.ui.VMInitialization.UiEvents.DropdownEvents.DDLegalType
import com.thomas200593.mini_retail_app.features.initial.initialization.ui.VMInitialization.UiEvents.DropdownEvents.DDTaxIssuerCountry
import com.thomas200593.mini_retail_app.features.initial.initialization.ui.VMInitialization.UiEvents.DropdownEvents.DDTaxationType
import com.thomas200593.mini_retail_app.features.initial.initialization.ui.VMInitialization.UiEvents.InputFormEvents.BtnCancelEvents
import com.thomas200593.mini_retail_app.features.initial.initialization.ui.VMInitialization.UiEvents.InputFormEvents.BtnSubmitEvents
import com.thomas200593.mini_retail_app.features.initial.initialization.ui.VMInitialization.UiEvents.InputFormEvents.CommonNameEvents
import com.thomas200593.mini_retail_app.features.initial.initialization.ui.VMInitialization.UiEvents.InputFormEvents.IndustryAdditionalInfoEvents
import com.thomas200593.mini_retail_app.features.initial.initialization.ui.VMInitialization.UiEvents.InputFormEvents.LegalDocTypeAdditionalInfoEvents
import com.thomas200593.mini_retail_app.features.initial.initialization.ui.VMInitialization.UiEvents.InputFormEvents.LegalNameEvents
import com.thomas200593.mini_retail_app.features.initial.initialization.ui.VMInitialization.UiEvents.InputFormEvents.LegalTypeAdditionalInfoEvents
import com.thomas200593.mini_retail_app.features.initial.initialization.ui.VMInitialization.UiEvents.InputFormEvents.TaxIdDocNumberEvents
import com.thomas200593.mini_retail_app.features.initial.initialization.ui.VMInitialization.UiEvents.InputFormEvents.TaxRatePercentageEvents
import com.thomas200593.mini_retail_app.features.initial.initialization.ui.VMInitialization.UiEvents.OnOpenEvents
import com.thomas200593.mini_retail_app.features.initial.initialization.ui.VMInitialization.UiStateInitialization.Loading
import com.thomas200593.mini_retail_app.features.initial.initialization.ui.VMInitialization.UiStateInitialization.Success
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ulid.ULID
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class VMInitialization @Inject constructor(
    private val ucGetInitializationData: UCGetInitializationData,
    private val ucSetInitBizProfile: UCSetInitialBizProfile,
    private val repoConfGenLanguage: RepoConfGenLanguage,
    private val repoIndustries: RepoIndustries,
    private val repoLegalType: RepoLegalType,
    private val repoLegalDocType: RepoLegalDocType,
    private val repoTaxation: RepoTaxation,
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    companion object {
        private const val SSH_KEY_INDUSTRY_TYPE_KEY = "ssh_key_industry_type_key"
        private const val SSH_KEY_BIZ_LEGAL_TYPE_KEY = "ssh_key_biz_legal_type_key"
        private const val SSH_KEY_BIZ_LEGAL_DOC_TYPE_KEY = "ssh_key_biz_legal_doc_type_key"
        private const val SSH_KEY_TAXATION_TYPE_KEY = "ssh_key_taxation_type_key"
        private const val SSH_KEY_TAX_ISSUER_COUNTRY_ISO_CODE_KEY = "ssh_key_tax_issuer_country_iso_code_key"
    }
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
        val industryAdditionalInfoUsage: Boolean = false,
        val industryAdditionalInfo: String = String(),
        val legalTypeKey: String = String(),
        val legalTypeAdditionalInfoUsage: Boolean = false,
        val legalTypeAdditionalInfo: String = String(),
        val legalDocTypeUsage: Boolean = false,
        val legalDocTypeKey: String = String(),
        val legalDocTypeAdditionalInfoUsage: Boolean = false,
        val legalDocTypeAdditionalInfo: String = String(),
        val taxationTypeKey: String = String(),
        val taxIdDocNumber: String = String(),
        val taxIssuerCountry: Country = HlpCountry.COUNTRY_DEFAULT,
        val taxRatePercentage: Double = 0.00,
        val taxIncluded: Boolean = false,
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
            sealed interface IndustryAdditionalInfoEvents: InputFormEvents {
                data class ValueChanged(val additionalInfo: String): IndustryAdditionalInfoEvents
            }
            sealed interface LegalTypeAdditionalInfoEvents: InputFormEvents {
                data class ValueChanged(val additionalInfo: String): LegalTypeAdditionalInfoEvents
            }
            sealed interface LegalDocTypeAdditionalInfoEvents: InputFormEvents {
                data class ValueChanged(val additionalInfo: String): LegalDocTypeAdditionalInfoEvents
            }
            sealed interface TaxIdDocNumberEvents : InputFormEvents {
                data class ValueChanged(val taxIdDocNumber: String): TaxIdDocNumberEvents
            }
            sealed interface TaxRatePercentageEvents : InputFormEvents {
                data class ValueChanged(val taxRatePercentage: Int): TaxRatePercentageEvents
            }
            sealed interface BtnSubmitEvents: InputFormEvents {
                data object OnClick: BtnSubmitEvents
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
            sealed interface DDLegalType: DropdownEvents {
                data class OnSelect(val legalTypeKey: String): DDLegalType
            }
            sealed interface DDLegalDocType: DropdownEvents {
                data class OnSelect(val legalDocTypeKey: String): DDLegalDocType
            }
            sealed interface DDTaxationType: DropdownEvents {
                data class OnSelect(val taxationTypeKey: String): DDTaxationType
            }
            sealed interface DDTaxIssuerCountry: DropdownEvents {
                data class OnSelect(val taxIssuerCountry: Country): DDTaxIssuerCountry
            }
        }
        sealed interface ButtonEvents: UiEvents {
            sealed interface BtnInitDefaultBizProfileEvents: ButtonEvents {
                data object OnClick: BtnInitDefaultBizProfileEvents
            }
            sealed interface BtnInitManualBizProfileEvents: ButtonEvents {
                data object OnClick: BtnInitManualBizProfileEvents
            }
            sealed interface BtnToggleTaxInclusionEvents : ButtonEvents {
                data class OnClick(val taxIncluded: Boolean): BtnToggleTaxInclusionEvents
            }
            sealed interface BtnToggleIndustryAdditionalInfoEvents : ButtonEvents {
                data class OnCheckedChange(val checked: Boolean): BtnToggleIndustryAdditionalInfoEvents
            }
            sealed interface BtnToggleLegalTypeAdditionalInfoEvents : ButtonEvents {
                data class OnCheckedChange(val checked: Boolean): BtnToggleLegalTypeAdditionalInfoEvents
            }
            sealed interface BtnToggleLegalDocTypeUsageEvents : ButtonEvents {
                data class OnCheckedChange(val checked: Boolean) : BtnToggleLegalDocTypeUsageEvents
            }
            sealed interface BtnToggleLegalDocTypeAdditionalInfoEvents : ButtonEvents {
                data class OnCheckedChange(val checked: Boolean) : BtnToggleLegalDocTypeAdditionalInfoEvents
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

    private val _bizProfileDefault = BizProfileShort(
        seqId = 0,
        genId = ULID.randomULID(),
        bizName = BizName(legalName = "My-Corporation", commonName = "My Corp"),
        bizIndustry = Industries(identityKey = repoIndustries.getIdentityKeyDefault()),
        bizLegalType = LegalType(
            identifierKey = repoLegalType.getIdentityKeyDefault(),
            legalDocumentType = LegalDocumentType(
                identifierKey = repoLegalDocType.getIdentityKeyDefault()
            )
        ),
        bizTaxation = Taxation(
            identifierKey = repoTaxation.getIdentityKeyDefault()
        ),
        auditTrail = AuditTrail()
    )
    private val _regularTextValidation = RegularTextValidation()
    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    fun onEvent(events: UiEvents) {
        when(events) {
            is OnOpenEvents -> onOpenEvent()
            is DDLanguage.OnSelect -> onSelectLanguageEvent(events.language)
            is BtnInitDefaultBizProfileEvents.OnClick -> doInitBizProfile(_bizProfileDefault)
            is BtnInitManualBizProfileEvents.OnClick -> doShowPanelInputForm()
            is LegalNameEvents.ValueChanged -> frmValChgLegalName(events.legalName)
            is CommonNameEvents.ValueChanged -> frmValChgCommonName(events.commonName)
            is DDIndustry.OnSelect -> frmValChgIndustry(events.industryKey)
            is BtnToggleIndustryAdditionalInfoEvents.OnCheckedChange -> frmValChgIndustryAdditionalInfoUsage(events.checked)
            is IndustryAdditionalInfoEvents.ValueChanged ->
                frmValChgIndustryAdditionalInfo(events.additionalInfo.take(100))
            is DDLegalType.OnSelect -> frmValChgLegalType(events.legalTypeKey)
            is LegalTypeAdditionalInfoEvents.ValueChanged ->
                frmValChgLegalTypeAdditionalInfo(events.additionalInfo.take(100))
            is DDLegalDocType.OnSelect -> frmValChgLegalDocType(events.legalDocTypeKey)
            is BtnToggleLegalTypeAdditionalInfoEvents.OnCheckedChange -> frmValChgLegalTypeAdditionalInfoUsage(events.checked)
            is BtnToggleLegalDocTypeUsageEvents.OnCheckedChange -> frmValChgLegalDocTypeUsage(events.checked)
            is BtnToggleLegalDocTypeAdditionalInfoEvents.OnCheckedChange -> frmValChgLegalDocTypeAdditionalInfoUsage(events.checked)
            is LegalDocTypeAdditionalInfoEvents.ValueChanged ->
                frmValChgLegalDocTypeAdditionalInfo(events.additionalInfo.take(100))
            is DDTaxationType.OnSelect -> frmValChgTaxationType(events.taxationTypeKey)
            is TaxIdDocNumberEvents.ValueChanged -> frmValChgTaxIdDocNumber(events.taxIdDocNumber)
            is DDTaxIssuerCountry.OnSelect -> frmValChgTaxIssuerCountry(events.taxIssuerCountry)
            is TaxRatePercentageEvents.ValueChanged -> frmValChgTaxRatePercentage(events.taxRatePercentage)
            is BtnToggleTaxInclusionEvents.OnClick -> frmValChgTaxIncluded(events.taxIncluded)
            is BtnSubmitEvents.OnClick -> doInitBizProfile(
                bizProfileShort = BizProfileShort(
                    seqId = 0,
                    genId = ULID.randomULID(),
                    bizName = BizName(
                        legalName = uiState.value.panelInputFormState.legalName.trim(),
                        commonName = uiState.value.panelInputFormState.commonName.trim()
                    ),
                    bizIndustry = Industries(
                        identityKey = uiState.value.panelInputFormState.industryKey,
                        additionalInfo = uiState.value.panelInputFormState.industryAdditionalInfo.trim()
                    ),
                    bizLegalType = LegalType(
                        identifierKey = uiState.value.panelInputFormState.legalTypeKey,
                        additionalInfo = uiState.value.panelInputFormState.legalTypeAdditionalInfo.trim(),
                        legalDocumentType = LegalDocumentType(
                            identifierKey = uiState.value.panelInputFormState.legalDocTypeKey,
                            additionalInfo = uiState.value.panelInputFormState.legalDocTypeAdditionalInfo.trim()
                        )
                    ),
                    bizTaxation = Taxation(
                        identifierKey = uiState.value.panelInputFormState.taxationTypeKey,
                        taxIdDocNumber = uiState.value.panelInputFormState.taxIdDocNumber.trim(),
                        taxIncluded = uiState.value.panelInputFormState.taxIncluded,
                        taxIssuerCountry = uiState.value.panelInputFormState.taxIssuerCountry,
                        taxRatePercentage = uiState.value.panelInputFormState.taxRatePercentage
                    ),
                    auditTrail = AuditTrail()
                )
            )
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
    private fun resetDialogState() = _uiState.update {
        it.copy(dialogState = DialogState())
    }
    private fun resetPanelWelcomeMessageState() = _uiState.update {
        it.copy(panelWelcomeMessageState = PanelWelcomeMessageState())
    }
    private fun resetPanelInputFormState() = _uiState.update {
        it.copy(panelInputFormState = PanelInputFormState())
    }
    private fun onOpenEvent() = viewModelScope.launch {
        ucGetInitializationData().flowOn(ioDispatcher).collectLatest { result ->
            _uiState.update {
                it.copy(
                    initialization = Success(data = result.data),
                    panelInputFormState = it.panelInputFormState.copy(
                        industryKey = savedStateHandle.get<String>(SSH_KEY_INDUSTRY_TYPE_KEY) ?: repoIndustries.getIdentityKeyDefault(),
                        legalTypeKey = savedStateHandle.get<String>(SSH_KEY_BIZ_LEGAL_TYPE_KEY) ?: repoLegalType.getIdentityKeyDefault(),
                        legalDocTypeKey = savedStateHandle.get<String>(SSH_KEY_BIZ_LEGAL_DOC_TYPE_KEY) ?: repoLegalDocType.getIdentityKeyDefault(),
                        taxationTypeKey = savedStateHandle.get<String>(SSH_KEY_TAXATION_TYPE_KEY) ?: repoTaxation.getIdentityKeyDefault(),
                        taxIssuerCountry = savedStateHandle.get<String>(SSH_KEY_TAX_ISSUER_COUNTRY_ISO_CODE_KEY)
                            ?.let { isoCode ->
                                Country(
                                    isoCode = isoCode,
                                    iso03Country = Locale("", isoCode).isO3Country,
                                    displayName = Locale("", isoCode).displayName
                                )
                            } ?: HlpCountry.COUNTRY_DEFAULT
                    )
                )
            }
        }
    }
    private fun onSelectLanguageEvent(language: Language) = viewModelScope.launch {
        repoConfGenLanguage.setLanguage(language)
        AppCompatDelegate.setApplicationLocales(LocaleListCompat.create(Locale(language.code)))
    }
    private fun doInitBizProfile(bizProfileShort: BizProfileShort) = viewModelScope.launch {
        resetDialogState(); updateDialogState(dlgResLoading = true)
        _uiState.update {
            it.copy(
                panelWelcomeMessageState = it.panelWelcomeMessageState.copy(visible = true),
                panelInputFormState = it.panelInputFormState.copy(visible = false)
            )
        }
        val operationResult = runCatching {
            ucSetInitBizProfile.invoke(bizProfileShort)
        }.fold(
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
    private fun doShowPanelInputForm() {
        _uiState.update {
            it.copy(
                panelWelcomeMessageState = it.panelWelcomeMessageState.copy(visible = false),
                panelInputFormState = it.panelInputFormState.copy(visible = true)
            )
        }
    }
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
        frmVldLegalName()
        formSubmitBtnShouldEnable()
    }
    private fun frmVldLegalName(): Boolean {
        val result = _regularTextValidation.execute(
            input = _uiState.value.panelInputFormState.legalName,
            required = true,
            maxLength = 100
        )
        _uiState.update {
            it.copy(
                panelInputFormState = it.panelInputFormState.copy(
                    legalNameError = result.errorMessage
                )
            )
        }
        return result.isSuccess
    }
    private fun frmValChgCommonName(commonName: String) = viewModelScope.launch(ioDispatcher) {
        _uiState.update { it.copy(panelInputFormState = it.panelInputFormState.copy(commonName = commonName)) }
        frmVldCommonName()
        formSubmitBtnShouldEnable()
    }
    private fun frmVldCommonName(): Boolean {
        val result = _regularTextValidation.execute(
            input = _uiState.value.panelInputFormState.commonName,
            required = true,
            maxLength = 100
        )
        _uiState.update {
            it.copy(
                panelInputFormState = it.panelInputFormState.copy(
                    commonNameError = result.errorMessage
                )
            )
        }
        return result.isSuccess
    }
    private fun frmValChgIndustry(industryKey: String) {
        _uiState.update {
            it.copy(
                panelInputFormState = it.panelInputFormState.copy(
                    industryKey = industryKey
                )
            )
        }
        savedStateHandle[SSH_KEY_INDUSTRY_TYPE_KEY] = industryKey
    }
    private fun frmValChgIndustryAdditionalInfoUsage(checked: Boolean) = _uiState.update {
        it.copy(
            panelInputFormState = it.panelInputFormState.copy(
                industryAdditionalInfoUsage = checked,
                industryAdditionalInfo = if (!checked) String() else it.panelInputFormState.industryAdditionalInfo
            )
        )
    }
    private fun frmValChgIndustryAdditionalInfo(industryAdditionalInfo: String) = _uiState.update {
        it.copy(
            panelInputFormState = it.panelInputFormState.copy(
                industryAdditionalInfo = industryAdditionalInfo
            )
        )
    }
    private fun frmValChgLegalType(legalTypeKey: String) {
        _uiState.update {
            it.copy(
                panelInputFormState = it.panelInputFormState.copy(
                    legalTypeKey = legalTypeKey
                )
            )
        }
        savedStateHandle[SSH_KEY_BIZ_LEGAL_TYPE_KEY] = legalTypeKey
    }
    private fun frmValChgLegalTypeAdditionalInfoUsage(checked: Boolean) = _uiState.update {
        it.copy(
            panelInputFormState = it.panelInputFormState.copy(
                legalTypeAdditionalInfoUsage = checked,
                legalTypeAdditionalInfo = if(!checked) String() else it.panelInputFormState.legalTypeAdditionalInfo
            )
        )
    }
    private fun frmValChgLegalTypeAdditionalInfo(legalTypeAdditionalInfo: String) = _uiState.update {
        it.copy(
            panelInputFormState = it.panelInputFormState.copy(
                legalTypeAdditionalInfo = legalTypeAdditionalInfo
            )
        )
    }
    private fun frmValChgLegalDocTypeUsage(checked: Boolean) {
        val updatedLegalDocTypeKey =
            if (!checked) repoLegalDocType.getIdentityKeyDefault()
            else uiState.value.panelInputFormState.legalDocTypeKey
        _uiState.update {
            it.copy(
                panelInputFormState = it.panelInputFormState.copy(
                    legalDocTypeUsage = checked,
                    legalDocTypeKey = updatedLegalDocTypeKey
                )
            )
        }
        savedStateHandle[SSH_KEY_BIZ_LEGAL_DOC_TYPE_KEY] = updatedLegalDocTypeKey
    }
    private fun frmValChgLegalDocType(legalDocTypeKey: String) {
        _uiState.update {
            it.copy(
                panelInputFormState = it.panelInputFormState.copy(
                    legalDocTypeKey = legalDocTypeKey
                )
            )
        }
        savedStateHandle[SSH_KEY_BIZ_LEGAL_DOC_TYPE_KEY] = legalDocTypeKey
    }
    private fun frmValChgLegalDocTypeAdditionalInfoUsage(checked: Boolean) = _uiState.update {
        it.copy(
            panelInputFormState = it.panelInputFormState.copy(
                legalDocTypeAdditionalInfoUsage = checked,
                legalDocTypeAdditionalInfo = if(!checked) String() else it.panelInputFormState.legalDocTypeAdditionalInfo
            )
        )
    }
    private fun frmValChgLegalDocTypeAdditionalInfo(legalDocTypeAdditionalInfo: String) = _uiState.update {
        it.copy(
            panelInputFormState = it.panelInputFormState.copy(
                legalDocTypeAdditionalInfo = legalDocTypeAdditionalInfo
            )
        )
    }
    private fun frmValChgTaxationType(taxationTypeKey: String) {
        _uiState.update {
            it.copy(
                panelInputFormState = it.panelInputFormState.copy(
                    taxationTypeKey = taxationTypeKey
                )
            )
        }
        savedStateHandle[SSH_KEY_TAXATION_TYPE_KEY] = taxationTypeKey
    }
    private fun frmValChgTaxIdDocNumber(taxIdDocNumber: String) = _uiState.update {
        it.copy(
            panelInputFormState = it.panelInputFormState.copy(
                taxIdDocNumber = taxIdDocNumber
            )
        )
    }
    private fun frmValChgTaxIssuerCountry(taxIssuerCountry: Country) {
        _uiState.update {
            it.copy(
                panelInputFormState = it.panelInputFormState.copy(
                    taxIssuerCountry = taxIssuerCountry
                )
            )
        }
        savedStateHandle[SSH_KEY_TAX_ISSUER_COUNTRY_ISO_CODE_KEY] = taxIssuerCountry.isoCode
    }
    private fun frmValChgTaxRatePercentage(taxRatePercentage: Int) = _uiState.update {
        it.copy(
            panelInputFormState = it.panelInputFormState.copy(
                taxRatePercentage = taxRatePercentage.toDouble()
            )
        )
    }
    private fun frmValChgTaxIncluded(taxIncluded: Boolean) = _uiState.update {
        it.copy(
            panelInputFormState = it.panelInputFormState.copy(
                taxIncluded = taxIncluded
            )
        )
    }
    private fun formSubmitBtnShouldEnable() {
        val fldSubmitBtnShouldEnable = (frmVldLegalName() && frmVldCommonName())
        _uiState.update {
            it.copy(
                panelInputFormState = it.panelInputFormState.copy(
                    fldSubmitBtnEnabled = fldSubmitBtnShouldEnable
                )
            )
        }
    }
}
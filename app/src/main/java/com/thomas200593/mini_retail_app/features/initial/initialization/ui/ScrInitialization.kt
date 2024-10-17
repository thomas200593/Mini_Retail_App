package com.thomas200593.mini_retail_app.features.initial.initialization.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType.Companion.PrimaryNotEditable
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.thomas200593.mini_retail_app.BuildConfig
import com.thomas200593.mini_retail_app.R
import com.thomas200593.mini_retail_app.app.ui.LocalStateApp
import com.thomas200593.mini_retail_app.app.ui.StateApp
import com.thomas200593.mini_retail_app.core.design_system.util.HlpStringArray.Handler.StringArrayResource
import com.thomas200593.mini_retail_app.core.design_system.util.HlpStringArray.StringArrayResources.BizIndustries
import com.thomas200593.mini_retail_app.core.design_system.util.HlpStringArray.StringArrayResources.BizLegalDocType
import com.thomas200593.mini_retail_app.core.design_system.util.HlpStringArray.StringArrayResources.BizLegalType
import com.thomas200593.mini_retail_app.core.design_system.util.HlpStringArray.StringArrayResources.BizTaxationType
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState
import com.thomas200593.mini_retail_app.core.ui.common.CustomIcons
import com.thomas200593.mini_retail_app.core.ui.common.CustomIcons.Country.country
import com.thomas200593.mini_retail_app.core.ui.common.CustomThemes
import com.thomas200593.mini_retail_app.core.ui.component.CustomButton.Common.AppIconButton
import com.thomas200593.mini_retail_app.core.ui.component.CustomDialog.AlertDialogContext
import com.thomas200593.mini_retail_app.core.ui.component.CustomDialog.AppAlertDialog
import com.thomas200593.mini_retail_app.core.ui.component.CustomPanel.LoadingScreen
import com.thomas200593.mini_retail_app.core.ui.component.CustomPanel.ThreeRowCardItem
import com.thomas200593.mini_retail_app.core.ui.component.form.CustomForm.TextInput
import com.thomas200593.mini_retail_app.features.app_conf.app_config.entity.AppConfig.ConfigCurrent
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_country.entity.Country
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_language.entity.Language
import com.thomas200593.mini_retail_app.features.initial.initial.navigation.navToInitial
import com.thomas200593.mini_retail_app.features.initial.initialization.entity.Initialization
import com.thomas200593.mini_retail_app.features.initial.initialization.ui.VMInitialization.DialogState
import com.thomas200593.mini_retail_app.features.initial.initialization.ui.VMInitialization.PanelInputFormState
import com.thomas200593.mini_retail_app.features.initial.initialization.ui.VMInitialization.PanelWelcomeMessageState
import com.thomas200593.mini_retail_app.features.initial.initialization.ui.VMInitialization.UiEvents.ButtonEvents.BtnInitDefaultBizProfileEvents
import com.thomas200593.mini_retail_app.features.initial.initialization.ui.VMInitialization.UiEvents.ButtonEvents.BtnInitManualBizProfileEvents
import com.thomas200593.mini_retail_app.features.initial.initialization.ui.VMInitialization.UiEvents.ButtonEvents.BtnToggleIndustryAdditionalInfoEvents
import com.thomas200593.mini_retail_app.features.initial.initialization.ui.VMInitialization.UiEvents.ButtonEvents.BtnToggleLegalDocTypeAdditionalInfoEvents
import com.thomas200593.mini_retail_app.features.initial.initialization.ui.VMInitialization.UiEvents.ButtonEvents.BtnToggleLegalDocTypeUsageEvents
import com.thomas200593.mini_retail_app.features.initial.initialization.ui.VMInitialization.UiEvents.ButtonEvents.BtnToggleLegalTypeAdditionalInfoEvents
import com.thomas200593.mini_retail_app.features.initial.initialization.ui.VMInitialization.UiEvents.ButtonEvents.BtnToggleTaxInclusionEvents
import com.thomas200593.mini_retail_app.features.initial.initialization.ui.VMInitialization.UiEvents.DialogEvents.DlgInitBizProfileCancel
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
import com.thomas200593.mini_retail_app.features.initial.initialization.ui.VMInitialization.UiState
import com.thomas200593.mini_retail_app.features.initial.initialization.ui.VMInitialization.UiStateInitialization.Loading
import com.thomas200593.mini_retail_app.features.initial.initialization.ui.VMInitialization.UiStateInitialization.Success
import kotlinx.coroutines.launch

@Composable
fun ScrInitialization(
    vm: VMInitialization = hiltViewModel(),
    stateApp: StateApp = LocalStateApp.current
) {
    val coroutineScope = rememberCoroutineScope()
    val uiState by vm.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) { vm.onEvent(OnOpenEvents) }

    ScrInitialization(
        uiState = uiState,
        ddLanguageOnSelect = { vm.onEvent(DDLanguage.OnSelect(it)) },
        btnInitDefaultBizProfileOnClick = { vm.onEvent(BtnInitDefaultBizProfileEvents.OnClick) },
        btnInitManualBizProfileOnClick = { vm.onEvent(BtnInitManualBizProfileEvents.OnClick) },
        legalNameOnValueChange = { vm.onEvent(LegalNameEvents.ValueChange(it)) },
        commonNameOnValueChange = { vm.onEvent(CommonNameEvents.ValueChange(it)) },
        ddIndustryOnSelect = { vm.onEvent(DDIndustry.OnSelect(it)) },
        btnToggleIndustryAdditionalInfoOnCheckedChange = {
            vm.onEvent(BtnToggleIndustryAdditionalInfoEvents.OnCheckedChange(it))
        },
        industryAdditionalInfoOnValueChange = { vm.onEvent(IndustryAdditionalInfoEvents.ValueChange(it)) },
        ddLegalTypeOnSelect = { vm.onEvent(DDLegalType.OnSelect(it)) },
        btnToggleLegalTypeAdditionalInfoOnCheckedChange = {
            vm.onEvent(BtnToggleLegalTypeAdditionalInfoEvents.OnCheckedChange(it))
        },
        legalTypeAdditionalInfoOnValueChange = { vm.onEvent(LegalTypeAdditionalInfoEvents.ValueChange(it)) },
        btnToggleLegalDocTypeUsageOnCheckedChange = {
            vm.onEvent(BtnToggleLegalDocTypeUsageEvents.OnCheckedChange(it))
        },
        ddLegalDocTypeOnSelect = { vm.onEvent(DDLegalDocType.OnSelect(it)) },
        btnToggleLegalDocTypeAdditionalInfoOnCheckedChange = {
            vm.onEvent(BtnToggleLegalDocTypeAdditionalInfoEvents.OnCheckedChange(it))
        },
        legalDocTypeAdditionalInfoValueChange = { vm.onEvent(LegalDocTypeAdditionalInfoEvents.ValueChange(it)) },
        ddTaxationTypeOnSelect = { vm.onEvent(DDTaxationType.OnSelect(it)) },
        taxIdDocNumberOnValueChange = { vm.onEvent(TaxIdDocNumberEvents.ValueChange(it)) },
        ddTaxIssuerCountryOnSelect = { vm.onEvent(DDTaxIssuerCountry.OnSelect(it)) },
        taxRatePercentageOnValueChange = { vm.onEvent(TaxRatePercentageEvents.ValueChange(it)) },
        btnToggleTaxIncludedOnCheckedChange = { vm.onEvent(BtnToggleTaxInclusionEvents.OnCheckedChange(it)) },
        btnSubmitOnClick = { vm.onEvent(BtnSubmitEvents.OnClick) },
        btnCancelOnClick = { vm.onEvent(BtnCancelEvents.OnClick) },
        dlgInitBizProfileCancelOnConfirm = { vm.onEvent(DlgInitBizProfileCancel.OnConfirm) },
        dlgInitBizProfileCancelOnDismiss = { vm.onEvent(DlgInitBizProfileCancel.OnDismiss) },
        dlgInitBizProfileSuccessOnConfirm = {
            vm.onEvent(DlgResSuccess.OnConfirm)
                .also { coroutineScope.launch { stateApp.navController.navToInitial() } }
        },
        dlgInitBizProfileErrorOnDismiss = {
            vm.onEvent(DlgResError.OnDismiss)
                .also { coroutineScope.launch { stateApp.navController.navToInitial() } }
        }
    )
}

@Composable
private fun ScrInitialization(
    uiState: UiState,
    ddLanguageOnSelect: (Language) -> Unit,
    btnInitDefaultBizProfileOnClick: () -> Unit,
    btnInitManualBizProfileOnClick: () -> Unit,
    legalNameOnValueChange: (String) -> Unit,
    commonNameOnValueChange: (String) -> Unit,
    ddIndustryOnSelect: (String) -> Unit,
    btnToggleIndustryAdditionalInfoOnCheckedChange: (Boolean) -> Unit,
    industryAdditionalInfoOnValueChange: (String) -> Unit,
    ddLegalTypeOnSelect: (String) -> Unit,
    btnToggleLegalTypeAdditionalInfoOnCheckedChange: (Boolean) -> Unit,
    legalTypeAdditionalInfoOnValueChange: (String) -> Unit,
    btnToggleLegalDocTypeUsageOnCheckedChange: (Boolean) -> Unit,
    ddLegalDocTypeOnSelect: (String) -> Unit,
    btnToggleLegalDocTypeAdditionalInfoOnCheckedChange: (Boolean) -> Unit,
    legalDocTypeAdditionalInfoValueChange: (String) -> Unit,
    ddTaxationTypeOnSelect: (String) -> Unit,
    taxIdDocNumberOnValueChange: (String) -> Unit,
    ddTaxIssuerCountryOnSelect: (Country) -> Unit,
    taxRatePercentageOnValueChange: (Int) -> Unit,
    btnToggleTaxIncludedOnCheckedChange: (Boolean) -> Unit,
    btnSubmitOnClick: () -> Unit,
    btnCancelOnClick: () -> Unit,
    dlgInitBizProfileCancelOnConfirm: () -> Unit,
    dlgInitBizProfileCancelOnDismiss: () -> Unit,
    dlgInitBizProfileSuccessOnConfirm: () -> Unit,
    dlgInitBizProfileErrorOnDismiss: () -> Unit
) {
    HandleDialogs(
        uiState = uiState,
        dlgInitBizProfileCancelOnConfirm = dlgInitBizProfileCancelOnConfirm,
        dlgInitBizProfileCancelOnDismiss = dlgInitBizProfileCancelOnDismiss,
        dlgInitBizProfileSuccessOnConfirm = dlgInitBizProfileSuccessOnConfirm,
        dlgInitBizProfileErrorOnDismiss = dlgInitBizProfileErrorOnDismiss
    )
    when(uiState.initialization) {
        Loading -> LoadingScreen()
        is Success -> ScreenContent(
            uiState = uiState,
            initData = uiState.initialization.data,
            ddLanguageOnSelect = ddLanguageOnSelect,
            btnInitBizProfileDefaultOnClick = btnInitDefaultBizProfileOnClick,
            btnInitBizProfileManualOnClick = btnInitManualBizProfileOnClick,
            legalNameOnValueChange = legalNameOnValueChange,
            commonNameOnValueChange = commonNameOnValueChange,
            ddIndustryOnSelect = ddIndustryOnSelect,
            btnToggleIndustryAdditionalInfoOnCheckedChange = btnToggleIndustryAdditionalInfoOnCheckedChange,
            industryAdditionalInfoOnValueChange = industryAdditionalInfoOnValueChange,
            ddLegalTypeOnSelect = ddLegalTypeOnSelect,
            btnToggleLegalTypeAdditionalInfoOnCheckedChange = btnToggleLegalTypeAdditionalInfoOnCheckedChange,
            legalTypeAdditionalInfoOnValueChange = legalTypeAdditionalInfoOnValueChange,
            btnToggleLegalDocTypeUsageOnCheckedChange = btnToggleLegalDocTypeUsageOnCheckedChange,
            ddLegalDocTypeOnSelect = ddLegalDocTypeOnSelect,
            btnToggleLegalDocTypeAdditionalInfoOnCheckedChange = btnToggleLegalDocTypeAdditionalInfoOnCheckedChange,
            legalDocTypeAdditionalInfoValueChange = legalDocTypeAdditionalInfoValueChange,
            ddTaxationTypeOnSelect = ddTaxationTypeOnSelect,
            taxIdDocNumberOnValueChange = taxIdDocNumberOnValueChange,
            ddTaxIssuerCountryOnSelect = ddTaxIssuerCountryOnSelect,
            taxRatePercentageOnValueChange = taxRatePercentageOnValueChange,
            btnToggleTaxIncludedOnCheckedChange = btnToggleTaxIncludedOnCheckedChange,
            btnSubmitOnClick = btnSubmitOnClick,
            btnCancelOnClick = btnCancelOnClick
        )
    }
}

@Composable
private fun HandleDialogs(
    uiState: UiState,
    dlgInitBizProfileCancelOnConfirm: () -> Unit,
    dlgInitBizProfileCancelOnDismiss: () -> Unit,
    dlgInitBizProfileSuccessOnConfirm: () -> Unit,
    dlgInitBizProfileErrorOnDismiss: () -> Unit
) {
    AppAlertDialog(
        showDialog = uiState.dialogState.dlgInputBizProfileCancelConfirmation,
        dialogContext = AlertDialogContext.CONFIRMATION,
        showTitle = true,
        title = { Text(stringResource(id = R.string.str_cancel_input_biz_profile)) },
        showBody = true,
        body = { Text(stringResource(id = R.string.str_entries_delete_body)) },
        useConfirmButton = true,
        confirmButton = {
            AppIconButton(
                onClick = dlgInitBizProfileCancelOnConfirm,
                icon = Icons.Default.Check,
                text = stringResource(id = R.string.str_yes),
                containerColor = MaterialTheme.colorScheme.error,
                contentColor = MaterialTheme.colorScheme.onError
            )
        },
        useDismissButton = true,
        dismissButton = {
            AppIconButton(
                onClick = dlgInitBizProfileCancelOnDismiss,
                icon = Icons.Default.Close,
                text = stringResource(id = R.string.str_no)
            )
        }
    )
    AppAlertDialog(
        showDialog = uiState.dialogState.dlgResLoading,
        dialogContext = AlertDialogContext.INFORMATION,
        showIcon = true,
        showTitle = true,
        title = { Text(stringResource(id = R.string.str_loading)) },
        showBody = true,
        body = { Text(stringResource(id = R.string.str_biz_profile_init_loading)) }
    )
    AppAlertDialog(
        showDialog = uiState.dialogState.dlgResSuccess,
        dialogContext = AlertDialogContext.SUCCESS,
        showIcon = true,
        showTitle = true,
        title = { Text(stringResource(id = R.string.str_success)) },
        showBody = true,
        body = { Text(stringResource(id = R.string.str_biz_profile_init_success)) },
        useConfirmButton = true,
        confirmButton = {
            AppIconButton(
                onClick = dlgInitBizProfileSuccessOnConfirm,
                icon = Icons.Default.Check,
                text = stringResource(id = R.string.str_ok)
            )
        }
    )
    AppAlertDialog(
        showDialog = uiState.dialogState.dlgResError,
        dialogContext = AlertDialogContext.ERROR,
        showIcon = true,
        showTitle = true,
        title = { Text(stringResource(id = R.string.str_error)) },
        showBody = true,
        body = {
            when(uiState.initBizProfileOperationResult){
                is ResourceState.Empty -> Text(stringResource(id = R.string.str_biz_profile_init_failed))
                is ResourceState.Error -> Text(uiState.initBizProfileOperationResult.t.toString())
                else -> Unit
            }
        },
        useDismissButton = true,
        dismissButton = {
            AppIconButton(
                onClick = dlgInitBizProfileErrorOnDismiss,
                icon = Icons.Default.Close,
                text = stringResource(id = R.string.str_close),
                containerColor = MaterialTheme.colorScheme.error,
                contentColor = MaterialTheme.colorScheme.onError
            )
        }
    )
}

@Composable
private fun ScreenContent(
    uiState: UiState,
    initData: Initialization,
    ddLanguageOnSelect: (Language) -> Unit,
    btnInitBizProfileManualOnClick: () -> Unit,
    btnInitBizProfileDefaultOnClick: () -> Unit,
    legalNameOnValueChange: (String) -> Unit,
    commonNameOnValueChange: (String) -> Unit,
    ddIndustryOnSelect: (String) -> Unit,
    btnToggleIndustryAdditionalInfoOnCheckedChange: (Boolean) -> Unit,
    industryAdditionalInfoOnValueChange: (String) -> Unit,
    ddLegalTypeOnSelect: (String) -> Unit,
    btnToggleLegalTypeAdditionalInfoOnCheckedChange: (Boolean) -> Unit,
    legalTypeAdditionalInfoOnValueChange: (String) -> Unit,
    btnToggleLegalDocTypeUsageOnCheckedChange: (Boolean) -> Unit,
    ddLegalDocTypeOnSelect: (String) -> Unit,
    btnToggleLegalDocTypeAdditionalInfoOnCheckedChange: (Boolean) -> Unit,
    legalDocTypeAdditionalInfoValueChange: (String) -> Unit,
    ddTaxationTypeOnSelect: (String) -> Unit,
    taxIdDocNumberOnValueChange: (String) -> Unit,
    ddTaxIssuerCountryOnSelect: (Country) -> Unit,
    taxRatePercentageOnValueChange: (Int) -> Unit,
    btnToggleTaxIncludedOnCheckedChange: (Boolean) -> Unit,
    btnSubmitOnClick: () -> Unit,
    btnCancelOnClick: () -> Unit
) {
    Surface {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .imePadding(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Top)
        ) {
            PartLang(
                languages = initData.languages,
                configCurrent = initData.configCurrent,
                ddLanguageOnSelect = ddLanguageOnSelect
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1.0f)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Top)
            ) {
                if(uiState.panelWelcomeMessageState.visible) {
                    PartWelcomeMsg(
                        btnInitBizProfileManualOnClick = btnInitBizProfileManualOnClick,
                        btnInitBizProfileDefaultOnClick = btnInitBizProfileDefaultOnClick
                    )
                }
                if(uiState.panelInputFormState.visible) {
                    PartInitBizProfile(
                        industries = initData.industries,
                        legalType = initData.legalType,
                        legalDocType = initData.legalDocType,
                        taxationType = initData.taxation.first,
                        taxIssuerCountry = initData.taxation.second,
                        inputFormState = uiState.panelInputFormState,
                        legalNameOnValueChange = legalNameOnValueChange,
                        commonNameOnValueChange = commonNameOnValueChange,
                        ddIndustryOnSelect = ddIndustryOnSelect,
                        btnToggleIndustryAdditionalInfoOnCheckedChange = btnToggleIndustryAdditionalInfoOnCheckedChange,
                        industryAdditionalInfoOnValueChange = industryAdditionalInfoOnValueChange,
                        ddLegalTypeOnSelect = ddLegalTypeOnSelect,
                        btnToggleLegalTypeAdditionalInfoOnCheckedChange = btnToggleLegalTypeAdditionalInfoOnCheckedChange,
                        legalTypeAdditionalInfoOnValueChange = legalTypeAdditionalInfoOnValueChange,
                        btnToggleLegalDocTypeUsageOnCheckedChange = btnToggleLegalDocTypeUsageOnCheckedChange,
                        ddLegalDocTypeOnSelect = ddLegalDocTypeOnSelect,
                        btnToggleLegalDocTypeAdditionalInfoOnCheckedChange = btnToggleLegalDocTypeAdditionalInfoOnCheckedChange,
                        legalDocTypeAdditionalInfoValueChange = legalDocTypeAdditionalInfoValueChange,
                        ddTaxationTypeOnSelect = ddTaxationTypeOnSelect,
                        taxIdDocNumberOnValueChange = taxIdDocNumberOnValueChange,
                        ddTaxIssuerCountryOnSelect = ddTaxIssuerCountryOnSelect,
                        taxRatePercentageOnValueChange = taxRatePercentageOnValueChange,
                        btnToggleTaxIncludedOnCheckedChange = btnToggleTaxIncludedOnCheckedChange
                    )
                }
            }
            if(uiState.panelInputFormState.visible){
                PartBtnInitBizProfile(
                    inputFormState = uiState.panelInputFormState,
                    btnSubmitOnClick = btnSubmitOnClick,
                    btnCancelOnClick = btnCancelOnClick
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PartLang(
    languages: Set<Language>,
    configCurrent: ConfigCurrent,
    ddLanguageOnSelect: (Language) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(1.0f)
            .padding(8.dp),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {
        var expanded by remember { mutableStateOf(false) }
        ExposedDropdownMenuBox(
            modifier = Modifier.fillMaxWidth(0.4f),
            expanded = expanded,
            onExpandedChange = { expanded = expanded.not() }
        ) {
            TextButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(PrimaryNotEditable, true),
                border = BorderStroke(1.dp, colorResource(R.color.charcoal_gray)),
                shape = MaterialTheme.shapes.medium,
                onClick = { expanded = true }) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        modifier = Modifier.size(24.dp),
                        imageVector = ImageVector.vectorResource(id = configCurrent.language.iconRes),
                        contentDescription = null
                    )
                    Text(
                        modifier = Modifier.weight(0.8f),
                        text = stringResource(id = configCurrent.language.title),
                        textAlign = TextAlign.Center,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
            ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                languages.forEach {
                    DropdownMenuItem(
                        modifier = Modifier.fillMaxWidth(),
                        leadingIcon = {
                            Image(
                                modifier = Modifier.size(24.dp),
                                imageVector = ImageVector.vectorResource(id = it.iconRes),
                                contentDescription = null
                            )
                        },
                        text =
                        {
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = stringResource(id = it.title)
                            )
                        },
                        onClick = {
                            expanded = false
                            ddLanguageOnSelect(it)
                        },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                    )
                }
            }
        }
    }
}

@Composable
private fun PartWelcomeMsg(
    btnInitBizProfileManualOnClick: () -> Unit,
    btnInitBizProfileDefaultOnClick: () -> Unit
) {
    Column(
        modifier = Modifier.padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically)
    ) {
        Surface(
            modifier = Modifier.size(150.dp),
            color = Color.Transparent,
            shape = MaterialTheme.shapes.medium
        ) { Image(imageVector = ImageVector.vectorResource(id = CustomIcons.App.app), contentDescription = null) }
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(id = R.string.app_name),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurface,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "${BuildConfig.VERSION_NAME} - ${BuildConfig.BUILD_TYPE}",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurface,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                text = stringResource(R.string.str_init_welcome_message),
                style = MaterialTheme.typography.labelLarge,
                textAlign = TextAlign.Justify
            )
            AppIconButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = btnInitBizProfileManualOnClick,
                icon = ImageVector.vectorResource(id = CustomIcons.Emotion.happy),
                text = stringResource(R.string.str_init_setup_yes)
            )
        }
    }
    TextButton(onClick = btnInitBizProfileDefaultOnClick) {
        Text(
            text = stringResource(R.string.str_init_setup_no),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PartInitBizProfile(
    industries: Map<String, String>,
    legalType: Map<String, String>,
    legalDocType: Map<String, String>,
    taxationType: Map<String, String>,
    taxIssuerCountry: List<Country>,
    inputFormState: PanelInputFormState,
    legalNameOnValueChange: (String) -> Unit,
    commonNameOnValueChange: (String) -> Unit,
    ddIndustryOnSelect: (String) -> Unit,
    btnToggleIndustryAdditionalInfoOnCheckedChange: (Boolean) -> Unit,
    industryAdditionalInfoOnValueChange: (String) -> Unit,
    ddLegalTypeOnSelect: (String) -> Unit,
    btnToggleLegalTypeAdditionalInfoOnCheckedChange: (Boolean) -> Unit,
    legalTypeAdditionalInfoOnValueChange: (String) -> Unit,
    btnToggleLegalDocTypeUsageOnCheckedChange: (Boolean) -> Unit,
    ddLegalDocTypeOnSelect: (String) -> Unit,
    btnToggleLegalDocTypeAdditionalInfoOnCheckedChange: (Boolean) -> Unit,
    legalDocTypeAdditionalInfoValueChange: (String) -> Unit,
    ddTaxationTypeOnSelect: (String) -> Unit,
    taxIdDocNumberOnValueChange: (String) -> Unit,
    ddTaxIssuerCountryOnSelect: (Country) -> Unit,
    taxRatePercentageOnValueChange: (Int) -> Unit,
    btnToggleTaxIncludedOnCheckedChange: (Boolean) -> Unit
) {
    Surface(
        modifier = Modifier.padding(8.dp),
        shape = MaterialTheme.shapes.medium,
        border = BorderStroke(1.dp, colorResource(R.color.charcoal_gray)),
        shadowElevation = 5.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterVertically)
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(id = R.string.str_business_profile),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(id = R.string.str_business_profile_desc),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp)
                    .border(BorderStroke(1.dp, colorResource(R.color.charcoal_gray))),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                var sectionExpanded by remember { mutableStateOf(true) }
                ThreeRowCardItem(
                    cardShape = RectangleShape,
                    firstRowContent = {
                        Surface(modifier = Modifier.size(ButtonDefaults.IconSize)) {
                            Icon(
                                imageVector = ImageVector.vectorResource(id = country),
                                contentDescription = null
                            )
                        }
                    },
                    secondRowContent = { Text(stringResource(R.string.str_biz_identity)) },
                    thirdRowContent = {
                        Surface(
                            onClick = { sectionExpanded = sectionExpanded.not() },
                            modifier = Modifier
                                .fillMaxWidth()
                                .size(ButtonDefaults.IconSize)
                        ) {
                            Icon(
                                imageVector =
                                    if (sectionExpanded) Icons.Default.KeyboardArrowDown
                                    else Icons.AutoMirrored.Default.KeyboardArrowRight,
                                contentDescription = null
                            )
                        }
                    }
                )
                if(sectionExpanded) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically)
                    ) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = stringResource(id = R.string.str_biz_name),
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.onSurface,
                            fontWeight = FontWeight.Bold,
                            maxLines = 1
                        )
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = stringResource(id = R.string.str_company_legal_name),
                            textAlign = TextAlign.Start,
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurface,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        TextInput(
                            value = inputFormState.legalName,
                            onValueChange = { legalNameOnValueChange(it) },
                            placeholder = stringResource(R.string.str_company_legal_name),
                            singleLine = true,
                            isError = inputFormState.legalNameError != null,
                            errorMessage = inputFormState.legalNameError
                        )
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = stringResource(id = R.string.str_company_common_name),
                            textAlign = TextAlign.Start,
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurface,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        TextInput(
                            value = inputFormState.commonName,
                            onValueChange = { commonNameOnValueChange(it) },
                            placeholder = stringResource(R.string.str_company_common_name),
                            singleLine = true,
                            isError = inputFormState.commonNameError != null,
                            errorMessage = inputFormState.commonNameError
                        )
                        HorizontalDivider(thickness = 2.dp, color = colorResource(R.color.charcoal_gray))
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = stringResource(id = R.string.str_biz_industry),
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.onSurface,
                            fontWeight = FontWeight.Bold,
                            maxLines = 1
                        )
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = stringResource(id = R.string.str_biz_industry_id),
                            textAlign = TextAlign.Start,
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurface,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(1.0f),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            var expanded by remember { mutableStateOf(false) }
                            ExposedDropdownMenuBox(
                                modifier = Modifier.fillMaxWidth(1.0f),
                                expanded = expanded,
                                onExpandedChange = { expanded = expanded.not() }
                            ) {
                                TextButton(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .menuAnchor(PrimaryNotEditable, true),
                                    border = BorderStroke(1.dp, colorResource(R.color.charcoal_gray)),
                                    shape = MaterialTheme.shapes.small,
                                    enabled = !(
                                        inputFormState.industryKey.isEmpty() ||
                                        inputFormState.industryKey.isBlank()
                                    ),
                                    onClick = { expanded = true }
                                ) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        StringArrayResource(BizIndustries)
                                            .findByKey(inputFormState.industryKey)?.let {
                                                Text(
                                                    modifier = Modifier.fillMaxWidth(),
                                                    text = it,
                                                    textAlign = TextAlign.Start,
                                                    maxLines = 1,
                                                    overflow = TextOverflow.Ellipsis
                                                )
                                            }
                                    }
                                }
                                ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                                    industries.forEach {
                                        DropdownMenuItem(
                                            modifier = Modifier.fillMaxWidth(),
                                            text = {
                                                Text(
                                                    modifier = Modifier.fillMaxWidth(),
                                                    text = it.value
                                                )
                                            },
                                            onClick = {
                                                expanded = false
                                                ddIndustryOnSelect(it.key)
                                            },
                                            contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                                        )
                                    }
                                }
                            }
                        }
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = stringResource(id = R.string.str_additional_info),
                            textAlign = TextAlign.Start,
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurface,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(1.0f),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.Top
                        ) {
                            val rowParams by remember(inputFormState.industryAdditionalInfoUsage) {
                                derivedStateOf {
                                    if (inputFormState.industryAdditionalInfoUsage) Triple(0.8f, 0.2f, Arrangement.End)
                                    else Triple(1.0f, 1.0f, Arrangement.Start)
                                }
                            }
                            val (leftRowWeight, rightRowWeight, rightRowArrangement) = rowParams
                            if(inputFormState.industryAdditionalInfoUsage) {
                                Row(
                                    modifier = Modifier.weight(leftRowWeight),
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    TextInput(
                                        value = inputFormState.industryAdditionalInfo,
                                        onValueChange = { industryAdditionalInfoOnValueChange(it) },
                                        placeholder = stringResource(R.string.str_additional_info),
                                        singleLine = true
                                    )
                                }
                            }
                            Row(
                                modifier = Modifier.weight(rightRowWeight),
                                horizontalArrangement = rightRowArrangement,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                var checked by remember { mutableStateOf(inputFormState.industryAdditionalInfoUsage) }
                                Switch(
                                    modifier = Modifier,
                                    checked = checked,
                                    onCheckedChange = {
                                        checked = checked.not()
                                        btnToggleIndustryAdditionalInfoOnCheckedChange(checked)
                                    }
                                )
                            }
                        }
                    }
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp)
                    .border(BorderStroke(1.dp, colorResource(R.color.charcoal_gray))),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                var sectionExpanded by remember { mutableStateOf(false) }
                ThreeRowCardItem(
                    cardShape = RectangleShape,
                    firstRowContent = {
                        Surface(modifier = Modifier.size(ButtonDefaults.IconSize)) {
                            Icon(
                                imageVector = ImageVector.vectorResource(id = country),
                                contentDescription = null
                            )
                        }
                    },
                    secondRowContent = { Text(stringResource(R.string.str_biz_legal)) },
                    thirdRowContent = {
                        Surface(
                            onClick = { sectionExpanded = sectionExpanded.not() },
                            modifier = Modifier
                                .fillMaxWidth()
                                .size(ButtonDefaults.IconSize)
                        ) {
                            Icon(
                                imageVector =
                                    if (sectionExpanded) Icons.Default.KeyboardArrowDown
                                    else Icons.AutoMirrored.Default.KeyboardArrowRight,
                                contentDescription = null
                            )
                        }
                    }
                )
                if(sectionExpanded) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically)
                    ) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = stringResource(id = R.string.str_biz_legal),
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.onSurface,
                            fontWeight = FontWeight.Bold,
                            maxLines = 1
                        )
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = stringResource(id = R.string.str_biz_legal_id),
                            textAlign = TextAlign.Start,
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurface,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(1.0f),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            var expanded by remember { mutableStateOf(false) }
                            ExposedDropdownMenuBox(
                                modifier = Modifier.fillMaxWidth(1.0f),
                                expanded = expanded,
                                onExpandedChange = { expanded = expanded.not() }
                            ) {
                                TextButton(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .menuAnchor(PrimaryNotEditable, true),
                                    border = BorderStroke(1.dp, colorResource(R.color.charcoal_gray)),
                                    shape = MaterialTheme.shapes.small,
                                    enabled = !(
                                        inputFormState.legalTypeKey.isEmpty() ||
                                        inputFormState.legalTypeKey.isBlank()
                                    ),
                                    onClick = { expanded = true }
                                ) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        StringArrayResource(BizLegalType)
                                            .findByKey(inputFormState.legalTypeKey)?.let {
                                                Text(
                                                    modifier = Modifier.fillMaxWidth(),
                                                    text = it,
                                                    textAlign = TextAlign.Start,
                                                    maxLines = 1,
                                                    overflow = TextOverflow.Ellipsis
                                                )
                                            }
                                    }
                                }
                                ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                                    legalType.forEach {
                                        DropdownMenuItem(
                                            modifier = Modifier.fillMaxWidth(),
                                            text = {
                                                Text(
                                                    modifier = Modifier.fillMaxWidth(),
                                                    text = it.value
                                                )
                                            },
                                            onClick = {
                                                expanded = false
                                                ddLegalTypeOnSelect(it.key)
                                            },
                                            contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                                        )
                                    }
                                }
                            }
                        }
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = stringResource(id = R.string.str_additional_info),
                            textAlign = TextAlign.Start,
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurface,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(1.0f),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.Top
                        ) {
                            val rowParams by remember(inputFormState.legalTypeAdditionalInfoUsage) {
                                derivedStateOf {
                                    if(inputFormState.legalTypeAdditionalInfoUsage) Triple(0.8f, 0.2f, Arrangement.End)
                                    else Triple(1.0f, 1.0f, Arrangement.Start)
                                }
                            }
                            val (leftRowWeight, rightRowWeight, rightRowArrangement) = rowParams
                            if(inputFormState.legalTypeAdditionalInfoUsage) {
                                Row(
                                    modifier = Modifier.weight(leftRowWeight),
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    TextInput(
                                        value = inputFormState.legalTypeAdditionalInfo,
                                        onValueChange = { legalTypeAdditionalInfoOnValueChange(it) },
                                        placeholder = stringResource(R.string.str_additional_info),
                                        singleLine = true
                                    )
                                }
                            }
                            Row(
                                modifier = Modifier.weight(rightRowWeight),
                                horizontalArrangement = rightRowArrangement,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                var checked by remember { mutableStateOf(inputFormState.legalTypeAdditionalInfoUsage) }
                                Switch(
                                    modifier = Modifier,
                                    checked = checked,
                                    onCheckedChange = {
                                        checked = checked.not()
                                        btnToggleLegalTypeAdditionalInfoOnCheckedChange(checked)
                                    }
                                )
                            }
                        }
                        if(inputFormState.legalDocTypeUsage) {
                            HorizontalDivider(thickness = 2.dp, color = colorResource(R.color.charcoal_gray))
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = stringResource(id = R.string.str_biz_legal_docs),
                                textAlign = TextAlign.Center,
                                color = MaterialTheme.colorScheme.onSurface,
                                fontWeight = FontWeight.Bold,
                                maxLines = 1
                            )
                        }
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = stringResource(id = R.string.str_biz_legal_doc_type_id),
                            textAlign = TextAlign.Start,
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurface,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(1.0f),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.Top
                        ) {
                            val rowParams by remember(inputFormState.legalDocTypeUsage) {
                                derivedStateOf {
                                    if(inputFormState.legalDocTypeUsage) Triple(0.8f, 0.2f, Arrangement.End)
                                    else Triple(1.0f, 1.0f, Arrangement.Start)
                                }
                            }
                            val (leftRowWeight, rightRowWeight, rightRowArrangement) = rowParams
                            if(inputFormState.legalDocTypeUsage) {
                                Row(
                                    modifier = Modifier.weight(leftRowWeight),
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    var expanded by remember { mutableStateOf(false) }
                                    ExposedDropdownMenuBox(
                                        modifier = Modifier.fillMaxWidth(),
                                        expanded = expanded,
                                        onExpandedChange = { expanded = expanded.not() }
                                    ) {
                                        TextButton(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .menuAnchor(PrimaryNotEditable, true),
                                            border = BorderStroke(1.dp, colorResource(R.color.charcoal_gray)),
                                            shape = MaterialTheme.shapes.small,
                                            enabled = !(
                                                inputFormState.legalDocTypeKey.isEmpty() ||
                                                inputFormState.legalDocTypeKey.isBlank()
                                            ),
                                            onClick = { expanded = true }
                                        ) {
                                            Row(
                                                modifier = Modifier.fillMaxWidth(),
                                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                StringArrayResource(BizLegalDocType)
                                                    .findByKey(inputFormState.legalDocTypeKey)?.let {
                                                        Text(
                                                            modifier = Modifier.fillMaxWidth(),
                                                            text = it,
                                                            textAlign = TextAlign.Start,
                                                            maxLines = 1,
                                                            overflow = TextOverflow.Ellipsis
                                                        )
                                                    }
                                            }
                                        }
                                        ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                                            legalDocType.forEach {
                                                DropdownMenuItem(
                                                    modifier = Modifier.fillMaxWidth(),
                                                    text = {
                                                        Text(
                                                            modifier = Modifier.fillMaxWidth(),
                                                            text = it.value
                                                        )
                                                    },
                                                    onClick = {
                                                        expanded = false
                                                        ddLegalDocTypeOnSelect(it.key)
                                                    },
                                                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                            Row(
                                modifier = Modifier.weight(rightRowWeight),
                                horizontalArrangement = rightRowArrangement,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                var checked by remember { mutableStateOf(inputFormState.legalDocTypeUsage) }
                                Switch(
                                    modifier = Modifier,
                                    checked = checked,
                                    onCheckedChange = {
                                        checked = checked.not()
                                        btnToggleLegalDocTypeUsageOnCheckedChange(checked)
                                    }
                                )
                            }
                        }
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = stringResource(id = R.string.str_additional_info),
                            textAlign = TextAlign.Start,
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurface,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(1.0f),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.Top
                        ) {
                            val rowParams by remember(inputFormState.legalDocTypeAdditionalInfoUsage) {
                                derivedStateOf {
                                    if (inputFormState.legalDocTypeAdditionalInfoUsage) Triple(0.8f, 0.2f, Arrangement.End)
                                    else Triple(1.0f, 1.0f, Arrangement.Start)
                                }
                            }
                            val (leftRowWeight, rightRowWeight, rightRowArrangement) = rowParams
                            if(inputFormState.legalDocTypeAdditionalInfoUsage) {
                                Row(
                                    modifier = Modifier.weight(leftRowWeight),
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    TextInput(
                                        value = inputFormState.legalDocTypeAdditionalInfo,
                                        onValueChange = { legalDocTypeAdditionalInfoValueChange(it) },
                                        placeholder = stringResource(R.string.str_additional_info),
                                        singleLine = true
                                    )
                                }
                            }
                            Row(
                                modifier = Modifier.weight(rightRowWeight),
                                horizontalArrangement = rightRowArrangement,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                var checked by remember { mutableStateOf(inputFormState.legalDocTypeAdditionalInfoUsage) }
                                Switch(
                                    modifier = Modifier,
                                    checked = checked,
                                    onCheckedChange = {
                                        checked = checked.not()
                                        btnToggleLegalDocTypeAdditionalInfoOnCheckedChange(checked)
                                    }
                                )
                            }
                        }
                    }
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp)
                    .border(BorderStroke(1.dp, colorResource(R.color.charcoal_gray))),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                var sectionExpanded by remember { mutableStateOf(false) }
                ThreeRowCardItem(
                    cardShape = RectangleShape,
                    firstRowContent = {
                        Surface(modifier = Modifier.size(ButtonDefaults.IconSize)) {
                            Icon(
                                imageVector = ImageVector.vectorResource(id = country),
                                contentDescription = null
                            )
                        }
                    },
                    secondRowContent = { Text(stringResource(R.string.str_biz_taxation)) },
                    thirdRowContent = {
                        Surface(
                            onClick = { sectionExpanded = sectionExpanded.not() },
                            modifier = Modifier
                                .fillMaxWidth()
                                .size(ButtonDefaults.IconSize)
                        ) {
                            Icon(
                                imageVector =
                                    if (sectionExpanded) Icons.Default.KeyboardArrowDown
                                    else Icons.AutoMirrored.Default.KeyboardArrowRight,
                                contentDescription = null
                            )
                        }
                    }
                )
                if(sectionExpanded) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically)
                    ) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = stringResource(id = R.string.str_biz_taxation),
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.onSurface,
                            fontWeight = FontWeight.Bold,
                            maxLines = 1
                        )
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = stringResource(R.string.str_biz_tax_type_id),
                            textAlign = TextAlign.Start,
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurface,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(1.0f),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            var expanded by remember { mutableStateOf(false) }
                            ExposedDropdownMenuBox(
                                modifier = Modifier.fillMaxWidth(1.0f),
                                expanded = expanded,
                                onExpandedChange = { expanded = expanded.not() }
                            ) {
                                TextButton(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .menuAnchor(PrimaryNotEditable, true),
                                    border = BorderStroke(1.dp, colorResource(R.color.charcoal_gray)),
                                    shape = MaterialTheme.shapes.small,
                                    enabled = !(
                                        inputFormState.taxationTypeKey.isEmpty() ||
                                        inputFormState.taxationTypeKey.isBlank()
                                    ),
                                    onClick = { expanded = true }
                                ) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        StringArrayResource(BizTaxationType)
                                            .findByKey(inputFormState.taxationTypeKey)?.let {
                                                Text(
                                                    modifier = Modifier.fillMaxWidth(),
                                                    text = it,
                                                    textAlign = TextAlign.Start,
                                                    maxLines = 1,
                                                    overflow = TextOverflow.Ellipsis
                                                )
                                            }
                                    }
                                }
                                ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                                    taxationType.forEach {
                                        DropdownMenuItem(
                                            modifier = Modifier.fillMaxWidth(),
                                            text = {
                                                Text(
                                                    modifier = Modifier.fillMaxWidth(),
                                                    text = it.value
                                                )
                                            },
                                            onClick = {
                                                expanded = false
                                                ddTaxationTypeOnSelect(it.key)
                                            },
                                            contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                                        )
                                    }
                                }
                            }
                        }
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = stringResource(R.string.str_biz_tax_id_doc_number),
                            textAlign = TextAlign.Start,
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurface,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        TextInput(
                            value = inputFormState.taxIdDocNumber,
                            onValueChange = { taxIdDocNumberOnValueChange(it) },
                            placeholder = stringResource(R.string.str_biz_tax_id_doc_number),
                            singleLine = true
                        )
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = stringResource(id = R.string.str_biz_tax_id_issuer_country),
                            textAlign = TextAlign.Start,
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurface,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(1.0f),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            var expanded by remember { mutableStateOf(false) }
                            ExposedDropdownMenuBox(
                                modifier = Modifier.fillMaxWidth(1.0f),
                                expanded = expanded,
                                onExpandedChange = { expanded = expanded.not() }
                            ) {
                                TextButton(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .menuAnchor(PrimaryNotEditable, true),
                                    border = BorderStroke(1.dp, colorResource(R.color.charcoal_gray)),
                                    shape = MaterialTheme.shapes.small,
                                    enabled = !(
                                        inputFormState.taxIssuerCountry.displayName.isEmpty() ||
                                        inputFormState.taxIssuerCountry.displayName.isBlank()
                                    ),
                                    onClick = { expanded = true }
                                ) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        inputFormState.taxIssuerCountry.displayName.let{
                                            Text(
                                                modifier = Modifier.fillMaxWidth(),
                                                text = it,
                                                textAlign = TextAlign.Start,
                                                maxLines = 1,
                                                overflow = TextOverflow.Ellipsis
                                            )
                                        }
                                    }
                                }
                                ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                                    taxIssuerCountry.forEach {
                                        DropdownMenuItem(
                                            modifier = Modifier.fillMaxWidth(),
                                            text = {
                                                Text(
                                                    modifier = Modifier.fillMaxWidth(),
                                                    text = it.displayName
                                                )
                                            },
                                            onClick = {
                                                expanded = false
                                                ddTaxIssuerCountryOnSelect(it)
                                            },
                                            contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                                        )
                                    }
                                }
                            }
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(1.0f),
                            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
                            verticalAlignment = Alignment.Top
                        ) {
                            Column(
                                modifier = Modifier.weight(0.5f),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically)
                            ) {
                                Text(
                                    modifier = Modifier.fillMaxWidth(),
                                    text = stringResource(id = R.string.str_biz_tax_rate_percentage),
                                    textAlign = TextAlign.Start,
                                    style = MaterialTheme.typography.labelMedium,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                                TextInput(
                                    value = inputFormState.taxRatePercentage.toInt().toString(),
                                    keyboardType = KeyboardType.Number,
                                    onValueChange = {
                                        val newValue = it.take(3).toIntOrNull() ?: 0
                                        taxRatePercentageOnValueChange(newValue)
                                    },
                                    placeholder = stringResource(R.string.str_biz_tax_rate_percentage),
                                    singleLine = true
                                )
                            }
                            Column(
                                modifier = Modifier.weight(0.5f),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically)
                            ) {
                                Text(
                                    modifier = Modifier.fillMaxWidth(),
                                    text = stringResource(id = R.string.str_biz_tax_included),
                                    textAlign = TextAlign.Start,
                                    style = MaterialTheme.typography.labelMedium,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Row(
                                    modifier = Modifier.fillMaxWidth(1.0f),
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row(
                                        modifier = Modifier.weight(0.5f),
                                        horizontalArrangement = Arrangement.Center,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        val text by remember(inputFormState.taxIncluded) {
                                            derivedStateOf { if(inputFormState.taxIncluded) R.string.str_yes else R.string.str_no }
                                        }
                                        Text(
                                            modifier = Modifier.fillMaxWidth(),
                                            text = stringResource(text),
                                            textAlign = TextAlign.Center,
                                            color = MaterialTheme.colorScheme.onSurface,
                                            fontWeight = FontWeight.Bold,
                                            maxLines = 1
                                        )
                                    }
                                    Row(
                                        modifier = Modifier.weight(0.5f),
                                        horizontalArrangement = Arrangement.End,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        var checked by remember { mutableStateOf(inputFormState.taxIncluded) }
                                        Switch(
                                            modifier = Modifier,
                                            checked = checked,
                                            onCheckedChange = {
                                                checked = checked.not()
                                                btnToggleTaxIncludedOnCheckedChange(it)
                                            }
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun PartBtnInitBizProfile(
    inputFormState: PanelInputFormState,
    btnSubmitOnClick: () -> Unit,
    btnCancelOnClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(1.0f)
            .height(56.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        val btnSubmitWeight by remember(inputFormState.fldSubmitBtnEnabled)
            { derivedStateOf { if (inputFormState.fldSubmitBtnEnabled) 0.5f else 1.0f } }
        val btnCancelWeight by remember(inputFormState.fldSubmitBtnEnabled)
            { derivedStateOf { if (inputFormState.fldSubmitBtnEnabled) 0.5f else 1.0f } }
        val showSubmitButton by remember(inputFormState.fldSubmitBtnEnabled)
            { derivedStateOf { inputFormState.fldSubmitBtnEnabled } }
        if (showSubmitButton) {
            AppIconButton(
                modifier = Modifier.weight(btnSubmitWeight),
                onClick = btnSubmitOnClick,
                icon = ImageVector.vectorResource(id = CustomIcons.Emotion.neutral),
                text = stringResource(id = R.string.str_save)
            )
        }
        AppIconButton(
            modifier = Modifier.weight(btnCancelWeight),
            onClick = btnCancelOnClick,
            icon = ImageVector.vectorResource(id = CustomIcons.Emotion.neutral),
            text = stringResource(id = R.string.str_cancel),
            containerColor = MaterialTheme.colorScheme.errorContainer,
            contentColor = MaterialTheme.colorScheme.onErrorContainer
        )
    }
}

@Composable
@Preview
private fun Preview() = CustomThemes.ApplicationTheme {
    ScrInitialization(
        uiState = UiState(
            dialogState = DialogState(),
            panelWelcomeMessageState = PanelWelcomeMessageState(visible = false),
            panelInputFormState = PanelInputFormState(
                visible = true,
                industryKey = "biz_industry_00001"
            ),
            initBizProfileOperationResult = ResourceState.Idle,
            initialization = Success(
                data = Initialization(
                    configCurrent = ConfigCurrent(),
                    languages = setOf(Language.EN, Language.ID),
                    industries = mapOf(
                        Pair("biz_industry_00001", "Others"),
                        Pair("biz_industry_00002", "Agriculture; plantations; other rural sectors")
                    ),
                    legalType = mapOf(),
                    legalDocType = mapOf(),
                    taxation = Pair(mapOf(), listOf())
                )
            )
        ),
        ddLanguageOnSelect = {},
        btnInitDefaultBizProfileOnClick = {},
        btnInitManualBizProfileOnClick = {},
        legalNameOnValueChange = {},
        commonNameOnValueChange = {},
        ddIndustryOnSelect = {},
        btnToggleIndustryAdditionalInfoOnCheckedChange = {},
        industryAdditionalInfoOnValueChange = {},
        ddLegalTypeOnSelect = {},
        btnToggleLegalTypeAdditionalInfoOnCheckedChange = {},
        legalTypeAdditionalInfoOnValueChange = {},
        btnToggleLegalDocTypeUsageOnCheckedChange = {},
        ddLegalDocTypeOnSelect = {},
        btnToggleLegalDocTypeAdditionalInfoOnCheckedChange = {},
        legalDocTypeAdditionalInfoValueChange = {},
        ddTaxationTypeOnSelect = {},
        taxIdDocNumberOnValueChange = {},
        ddTaxIssuerCountryOnSelect = {},
        taxRatePercentageOnValueChange = {},
        btnToggleTaxIncludedOnCheckedChange = {},
        btnSubmitOnClick = {},
        btnCancelOnClick = {},
        dlgInitBizProfileCancelOnConfirm = {},
        dlgInitBizProfileCancelOnDismiss = {},
        dlgInitBizProfileSuccessOnConfirm = {},
        dlgInitBizProfileErrorOnDismiss = {}
    )
}
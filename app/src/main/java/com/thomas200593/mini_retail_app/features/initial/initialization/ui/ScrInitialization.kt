package com.thomas200593.mini_retail_app.features.initial.initialization.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
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
import com.thomas200593.mini_retail_app.core.ui.common.CustomThemes
import com.thomas200593.mini_retail_app.core.ui.component.CustomButton.Common.AppIconButton
import com.thomas200593.mini_retail_app.core.ui.component.CustomDialog.AlertDialogContext
import com.thomas200593.mini_retail_app.core.ui.component.CustomDialog.AppAlertDialog
import com.thomas200593.mini_retail_app.core.ui.component.CustomPanel.LoadingScreen
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
        onSelectLanguage = { vm.onEvent(DDLanguage.OnSelect(it)) },
        onInitBizProfileDefaultBtnClicked = { vm.onEvent(BtnInitDefaultBizProfileEvents.OnClick) },
        onInitBizProfileManualBtnClicked = { vm.onEvent(BtnInitManualBizProfileEvents.OnClick) },
        onLegalNameValueChanged = { vm.onEvent(LegalNameEvents.ValueChanged(it)) },
        onCommonNameValueChanged = { vm.onEvent(CommonNameEvents.ValueChanged(it)) },
        onIndustryValueChanged = { vm.onEvent(DDIndustry.OnSelect(it)) },
        onIndustryAdditionalInfoValueChanged = { vm.onEvent(IndustryAdditionalInfoEvents.ValueChanged(it)) },
        onLegalTypeValueChanged = { vm.onEvent(DDLegalType.OnSelect(it)) },
        onLegalTypeAdditionalInfoValueChanged = { vm.onEvent(LegalTypeAdditionalInfoEvents.ValueChanged(it)) },
        onLegalDocTypeValueChanged = { vm.onEvent(DDLegalDocType.OnSelect(it)) },
        onLegalDocTypeAdditionalInfoValueChanged = { vm.onEvent(LegalDocTypeAdditionalInfoEvents.ValueChanged(it)) },
        onTaxationTypeValueChanged = { vm.onEvent(DDTaxationType.OnSelect(it)) },
        onTaxIdDocNumberValueChanged = { vm.onEvent(TaxIdDocNumberEvents.ValueChanged(it)) },
        onTaxIssuerCountryValueChanged = { vm.onEvent(DDTaxIssuerCountry.OnSelect(it)) },
        onTaxRatePercentageValueChanged = { vm.onEvent(TaxRatePercentageEvents.ValueChanged(it)) },
        onTaxIncludedValueChanged = { vm.onEvent(BtnToggleTaxInclusionEvents.OnClick(it)) },
        onFormSubmitBtnClicked = { vm.onEvent(BtnSubmitEvents.OnClick) },
        onFormCancelBtnClicked = { vm.onEvent(BtnCancelEvents.OnClick) },
        onInitBizProfileSuccess = {
            vm.onEvent(DlgResSuccess.OnConfirm)
                .also { coroutineScope.launch { stateApp.navController.navToInitial() } }
        },
        onInitBizProfileError = {
            vm.onEvent(DlgResError.OnDismiss)
                .also { coroutineScope.launch { stateApp.navController.navToInitial() } }
        }
    )
}

@Composable
private fun ScrInitialization(
    uiState: UiState,
    onSelectLanguage: (Language) -> Unit,
    onInitBizProfileDefaultBtnClicked: () -> Unit,
    onInitBizProfileManualBtnClicked: () -> Unit,
    onLegalNameValueChanged: (String) -> Unit,
    onCommonNameValueChanged: (String) -> Unit,
    onIndustryValueChanged: (String) -> Unit,
    onIndustryAdditionalInfoValueChanged: (String) -> Unit,
    onLegalTypeValueChanged: (String) -> Unit,
    onLegalTypeAdditionalInfoValueChanged: (String) -> Unit,
    onLegalDocTypeValueChanged: (String) -> Unit,
    onLegalDocTypeAdditionalInfoValueChanged: (String) -> Unit,
    onTaxationTypeValueChanged: (String) -> Unit,
    onTaxIdDocNumberValueChanged: (String) -> Unit,
    onTaxIssuerCountryValueChanged: (Country) -> Unit,
    onTaxRatePercentageValueChanged: (Int) -> Unit,
    onTaxIncludedValueChanged: (Boolean) -> Unit,
    onFormSubmitBtnClicked: () -> Unit,
    onFormCancelBtnClicked: () -> Unit,
    onInitBizProfileSuccess: () -> Unit,
    onInitBizProfileError: () -> Unit
) {
    HandleDialogs(
        uiState = uiState,
        onInitBizProfileSuccess = onInitBizProfileSuccess,
        onInitBizProfileError = onInitBizProfileError
    )
    when(uiState.initialization) {
        Loading -> LoadingScreen()
        is Success -> ScreenContent(
            uiState = uiState,
            initData = uiState.initialization.data,
            onSelectLanguage = onSelectLanguage,
            onInitBizProfileDefaultBtnClicked = onInitBizProfileDefaultBtnClicked,
            onInitBizProfileManualBtnClicked = onInitBizProfileManualBtnClicked,
            onLegalNameValueChanged = onLegalNameValueChanged,
            onCommonNameValueChanged = onCommonNameValueChanged,
            onIndustryValueChanged = onIndustryValueChanged,
            onIndustryAdditionalInfoValueChanged = onIndustryAdditionalInfoValueChanged,
            onLegalTypeValueChanged = onLegalTypeValueChanged,
            onLegalTypeAdditionalInfoValueChanged = onLegalTypeAdditionalInfoValueChanged,
            onLegalDocTypeValueChanged = onLegalDocTypeValueChanged,
            onLegalDocTypeAdditionalInfoValueChanged = onLegalDocTypeAdditionalInfoValueChanged,
            onTaxationTypeValueChanged = onTaxationTypeValueChanged,
            onTaxIdDocNumberValueChanged = onTaxIdDocNumberValueChanged,
            onTaxIssuerCountryValueChanged = onTaxIssuerCountryValueChanged,
            onTaxRatePercentageValueChanged = onTaxRatePercentageValueChanged,
            onTaxIncludedValueChanged = onTaxIncludedValueChanged,
            onFormSubmitBtnClicked = onFormSubmitBtnClicked,
            onFormCancelBtnClicked = onFormCancelBtnClicked
        )
    }
}

@Composable
private fun HandleDialogs(
    uiState: UiState,
    onInitBizProfileSuccess: () -> Unit,
    onInitBizProfileError: () -> Unit
) {
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
            TextButton (onClick = { onInitBizProfileSuccess() })
            { Text(stringResource(id = R.string.str_ok)) }
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
            TextButton(onClick = { onInitBizProfileError() })
            { Text(stringResource(id = R.string.str_ok)) }
        }
    )
}

@Composable
private fun ScreenContent(
    uiState: UiState,
    initData: Initialization,
    onSelectLanguage: (Language) -> Unit,
    onInitBizProfileDefaultBtnClicked: () -> Unit,
    onInitBizProfileManualBtnClicked: () -> Unit,
    onLegalNameValueChanged: (String) -> Unit,
    onCommonNameValueChanged: (String) -> Unit,
    onIndustryValueChanged: (String) -> Unit,
    onIndustryAdditionalInfoValueChanged: (String) -> Unit,
    onLegalTypeValueChanged: (String) -> Unit,
    onLegalTypeAdditionalInfoValueChanged: (String) -> Unit,
    onLegalDocTypeValueChanged: (String) -> Unit,
    onLegalDocTypeAdditionalInfoValueChanged: (String) -> Unit,
    onTaxationTypeValueChanged: (String) -> Unit,
    onTaxIdDocNumberValueChanged: (String) -> Unit,
    onTaxIssuerCountryValueChanged: (Country) -> Unit,
    onTaxRatePercentageValueChanged: (Int) -> Unit,
    onTaxIncludedValueChanged: (Boolean) -> Unit,
    onFormSubmitBtnClicked: () -> Unit,
    onFormCancelBtnClicked: () -> Unit
) {
    Surface {
        Column(
            modifier = Modifier.fillMaxSize().padding(8.dp).verticalScroll(rememberScrollState())
                .imePadding(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(30.dp, Alignment.Top)
        ) {
            LanguageSection(
                languages = initData.languages,
                configCurrent = initData.configCurrent,
                onSelectLanguage = onSelectLanguage
            )
            if(uiState.panelWelcomeMessageState.visible) {
                PanelWelcomeMessage(
                    onInitBizProfileDefaultBtnClicked = onInitBizProfileDefaultBtnClicked,
                    onInitBizProfileManualBtnClicked = onInitBizProfileManualBtnClicked
                )
            }
            if(uiState.panelInputFormState.visible) {
                PanelFormInitManualBizProfile(
                    industries = initData.industries,
                    legalType = initData.legalType,
                    legalDocType = initData.legalDocType,
                    taxationType = initData.taxation.first,
                    taxIssuerCountry = initData.taxation.second,
                    inputFormState = uiState.panelInputFormState,
                    onLegalNameValueChanged = onLegalNameValueChanged,
                    onCommonNameValueChanged = onCommonNameValueChanged,
                    onIndustryValueChanged = onIndustryValueChanged,
                    onIndustryAdditionalInfoValueChanged = onIndustryAdditionalInfoValueChanged,
                    onLegalTypeValueChanged = onLegalTypeValueChanged,
                    onLegalTypeAdditionalInfoValueChanged = onLegalTypeAdditionalInfoValueChanged,
                    onLegalDocTypeValueChanged = onLegalDocTypeValueChanged,
                    onLegalDocTypeAdditionalInfoValueChanged = onLegalDocTypeAdditionalInfoValueChanged,
                    onTaxationTypeValueChanged = onTaxationTypeValueChanged,
                    onTaxIdDocNumberValueChanged = onTaxIdDocNumberValueChanged,
                    onTaxIssuerCountryValueChanged = onTaxIssuerCountryValueChanged,
                    onTaxRatePercentageValueChanged = onTaxRatePercentageValueChanged,
                    onTaxIncludedValueChanged = onTaxIncludedValueChanged,
                    onFormSubmitBtnClicked = onFormSubmitBtnClicked,
                    onFormCancelBtnClicked = onFormCancelBtnClicked
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LanguageSection(
    languages: Set<Language>,
    configCurrent: ConfigCurrent,
    onSelectLanguage: (Language) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(1.0f),
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
                modifier = Modifier.fillMaxWidth().menuAnchor(PrimaryNotEditable, true),
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
                            onSelectLanguage(it)
                        },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                    )
                }
            }
        }
    }
}

@Composable
private fun PanelWelcomeMessage(
    onInitBizProfileManualBtnClicked: () -> Unit,
    onInitBizProfileDefaultBtnClicked: () -> Unit
) {
    Column(
        modifier = Modifier,
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
        modifier = Modifier.fillMaxWidth().padding(16.dp),
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
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                text = stringResource(R.string.str_init_welcome_message),
                style = MaterialTheme.typography.labelLarge,
                textAlign = TextAlign.Justify
            )
            AppIconButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = { onInitBizProfileManualBtnClicked() },
                icon = ImageVector.vectorResource(id = CustomIcons.Emotion.happy),
                text = stringResource(R.string.str_init_setup_yes)
            )
        }
    }
    TextButton(onClick = { onInitBizProfileDefaultBtnClicked() }) {
        Text(
            text = stringResource(R.string.str_init_setup_no),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PanelFormInitManualBizProfile(
    industries: Map<String, String>,
    legalType: Map<String, String>,
    legalDocType: Map<String, String>,
    taxationType: Map<String, String>,
    taxIssuerCountry: List<Country>,
    inputFormState: PanelInputFormState,
    onLegalNameValueChanged: (String) -> Unit,
    onCommonNameValueChanged: (String) -> Unit,
    onIndustryValueChanged: (String) -> Unit,
    onIndustryAdditionalInfoValueChanged: (String) -> Unit,
    onLegalTypeValueChanged: (String) -> Unit,
    onLegalTypeAdditionalInfoValueChanged: (String) -> Unit,
    onLegalDocTypeValueChanged: (String) -> Unit,
    onLegalDocTypeAdditionalInfoValueChanged: (String) -> Unit,
    onTaxationTypeValueChanged: (String) -> Unit,
    onTaxIdDocNumberValueChanged: (String) -> Unit,
    onTaxIssuerCountryValueChanged: (Country) -> Unit,
    onTaxRatePercentageValueChanged: (Int) -> Unit,
    onTaxIncludedValueChanged: (Boolean) -> Unit,
    onFormSubmitBtnClicked: () -> Unit,
    onFormCancelBtnClicked: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.colorScheme.surfaceContainerHighest
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically)
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
            HorizontalDivider(
                thickness = 2.dp,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = R.string.str_biz_name),
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            HorizontalDivider(
                thickness = 2.dp,
                color = MaterialTheme.colorScheme.onSurface
            )
            TextInput(
                value = inputFormState.legalName,
                onValueChange = { onLegalNameValueChanged(it) },
                label = stringResource(R.string.str_company_legal_name),
                placeholder = stringResource(R.string.str_company_legal_name),
                singleLine = true,
                isError = inputFormState.legalNameError != null,
                errorMessage = inputFormState.legalNameError
            )
            TextInput(
                value = inputFormState.commonName,
                onValueChange = { onCommonNameValueChanged(it) },
                label = stringResource(R.string.str_company_common_name),
                placeholder = stringResource(R.string.str_company_common_name),
                singleLine = true,
                isError = inputFormState.commonNameError != null,
                errorMessage = inputFormState.commonNameError
            )
            HorizontalDivider(
                thickness = 2.dp,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = R.string.str_biz_industry),
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            HorizontalDivider(
                thickness = 2.dp,
                color = MaterialTheme.colorScheme.onSurface
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
                        modifier = Modifier.fillMaxWidth().menuAnchor(PrimaryNotEditable, true),
                        border = BorderStroke(1.dp, colorResource(R.color.charcoal_gray)),
                        shape = MaterialTheme.shapes.small,
                        enabled = !(inputFormState.industryKey.isEmpty() || inputFormState.industryKey.isBlank()),
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
                        industries.forEach{
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
                                    onIndustryValueChanged(it.key)
                                },
                                contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                            )
                        }
                    }
                }
            }
            TextInput(
                value = inputFormState.industryAdditionalInfo,
                onValueChange = { onIndustryAdditionalInfoValueChanged(it) },
                label = stringResource(R.string.str_additional_info),
                placeholder = stringResource(R.string.str_biz_industry_additional_info),
                singleLine = true
            )
            HorizontalDivider(
                thickness = 2.dp,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = R.string.str_biz_legal),
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            HorizontalDivider(
                thickness = 2.dp,
                color = MaterialTheme.colorScheme.onSurface
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
                        enabled = !(inputFormState.legalTypeKey.isEmpty() || inputFormState.legalTypeKey.isBlank()),
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
                        legalType.forEach{
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
                                    onLegalTypeValueChanged(it.key)
                                },
                                contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                            )
                        }
                    }
                }
            }
            TextInput(
                value = inputFormState.legalTypeAdditionalInfo,
                onValueChange = { onLegalTypeAdditionalInfoValueChanged(it) },
                label = stringResource(R.string.str_additional_info),
                placeholder = stringResource(R.string.str_biz_legal_type_additional_info),
                singleLine = true
            )
            HorizontalDivider(
                thickness = 2.dp,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(R.string.str_biz_legal_docs),
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            HorizontalDivider(
                thickness = 2.dp,
                color = MaterialTheme.colorScheme.onSurface
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
                        enabled = !(inputFormState.legalDocTypeKey.isEmpty() || inputFormState.legalDocTypeKey.isBlank()),
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
                        ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                            legalDocType.forEach{
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
                                        onLegalDocTypeValueChanged(it.key)
                                    },
                                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                                )
                            }
                        }
                    }
                }
            }
            TextInput(
                value = inputFormState.legalDocTypeAdditionalInfo,
                onValueChange = { onLegalDocTypeAdditionalInfoValueChanged(it) },
                label = stringResource(R.string.str_additional_info),
                placeholder = stringResource(R.string.str_biz_legal_doc_type_additional_info),
                singleLine = true
            )
            HorizontalDivider(
                thickness = 2.dp,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(R.string.str_biz_taxation),
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            HorizontalDivider(
                thickness = 2.dp,
                color = MaterialTheme.colorScheme.onSurface
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
                        enabled = !(inputFormState.taxationTypeKey.isEmpty() || inputFormState.taxationTypeKey.isBlank()),
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
                        ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                            taxationType.forEach{
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
                                        onTaxationTypeValueChanged(it.key)
                                    },
                                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                                )
                            }
                        }
                    }
                }
            }
            TextInput(
                value = inputFormState.taxIdDocNumber,
                onValueChange = { onTaxIdDocNumberValueChanged(it) },
                label = stringResource(R.string.str_biz_tax_id_doc_number),
                placeholder = stringResource(R.string.str_biz_tax_id_doc_number),
                singleLine = true
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(R.string.str_biz_tax_id_issuer_country),
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
                        modifier = Modifier.fillMaxWidth().menuAnchor(PrimaryNotEditable, true),
                        border = BorderStroke(1.dp, colorResource(R.color.charcoal_gray)),
                        shape = MaterialTheme.shapes.small,
                        enabled = !(inputFormState.taxIssuerCountry.displayName.isEmpty() || inputFormState.taxIssuerCountry.displayName.isBlank()),
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
                                        onTaxIssuerCountryValueChanged(it)
                                    },
                                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                                )
                            }
                        }
                    }
                }
            }
            TextInput(
                value = inputFormState.taxRatePercentage.toInt().toString(),
                keyboardType = KeyboardType.Number,
                onValueChange = {
                    val newValue = it.take(3).toIntOrNull() ?: 0
                    onTaxRatePercentageValueChanged(newValue)
                },
                label = stringResource(R.string.str_biz_tax_rate_percentage),
                placeholder = stringResource(R.string.str_biz_tax_rate_percentage),
                singleLine = true
            )
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(R.string.str_biz_tax_included),
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
                            text = stringResource(text),
                            textAlign = TextAlign.Start,
                            modifier = Modifier.fillMaxWidth()
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
                                onTaxIncludedValueChanged(checked)
                            }
                        )
                    }
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(1.0f),
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
                        onClick = onFormSubmitBtnClicked,
                        icon = ImageVector.vectorResource(id = CustomIcons.Emotion.neutral),
                        text = stringResource(id = R.string.str_save)
                    )
                }
                AppIconButton(
                    modifier = Modifier.weight(btnCancelWeight),
                    onClick = onFormCancelBtnClicked,
                    icon = ImageVector.vectorResource(id = CustomIcons.Emotion.neutral),
                    text = stringResource(id = R.string.str_cancel),
                    containerColor = MaterialTheme.colorScheme.errorContainer,
                    contentColor = MaterialTheme.colorScheme.onErrorContainer
                )
            }
        }
    }
}

@Composable
@Preview
private fun Preview() = CustomThemes.ApplicationTheme {
    ScrInitialization(
        onSelectLanguage = {},
        onInitBizProfileDefaultBtnClicked = {},
        onInitBizProfileManualBtnClicked = {},
        onFormCancelBtnClicked = {},
        onFormSubmitBtnClicked = {},
        onInitBizProfileSuccess = {},
        onInitBizProfileError = {},
        onLegalNameValueChanged = {},
        onCommonNameValueChanged = {},
        onIndustryValueChanged = {},
        onIndustryAdditionalInfoValueChanged = {},
        onLegalTypeValueChanged = {},
        onLegalTypeAdditionalInfoValueChanged = {},
        onLegalDocTypeValueChanged = {},
        onLegalDocTypeAdditionalInfoValueChanged = {},
        onTaxationTypeValueChanged = {},
        onTaxIssuerCountryValueChanged = {},
        onTaxIdDocNumberValueChanged = {},
        onTaxRatePercentageValueChanged = {},
        onTaxIncludedValueChanged = {},
        uiState = UiState(
            dialogState = DialogState(),
            panelInputFormState = PanelInputFormState(
                visible = true
            ),
            panelWelcomeMessageState = PanelWelcomeMessageState(
                visible = false
            ),
            initBizProfileOperationResult = ResourceState.Idle,
            initialization = Success(
                data = Initialization(
                    configCurrent = ConfigCurrent(),
                    languages = setOf(Language.EN, Language.ID),
                    industries = mapOf(),
                    legalType = mapOf(),
                    legalDocType = mapOf(),
                    taxation = Pair(
                        first = mapOf(),
                        second = listOf()
                    )
                )
            )
        )
    )
}
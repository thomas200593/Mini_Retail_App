package com.thomas200593.mini_retail_app.features.initial.initialization.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
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
import com.thomas200593.mini_retail_app.core.data.local.database.entity_common.AuditTrail
import com.thomas200593.mini_retail_app.core.data.local.database.entity_common.Industries
import com.thomas200593.mini_retail_app.core.data.local.database.entity_common.LegalType
import com.thomas200593.mini_retail_app.core.data.local.database.entity_common.Taxation
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState
import com.thomas200593.mini_retail_app.core.ui.common.CustomIcons
import com.thomas200593.mini_retail_app.core.ui.common.CustomThemes
import com.thomas200593.mini_retail_app.core.ui.component.CustomButton.Common.AppIconButton
import com.thomas200593.mini_retail_app.core.ui.component.CustomDialog.AlertDialogContext
import com.thomas200593.mini_retail_app.core.ui.component.CustomDialog.AppAlertDialog
import com.thomas200593.mini_retail_app.core.ui.component.CustomForm.Component.TextInput
import com.thomas200593.mini_retail_app.core.ui.component.CustomPanel.LoadingScreen
import com.thomas200593.mini_retail_app.features.app_conf.app_config.entity.AppConfig
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_language.entity.Language
import com.thomas200593.mini_retail_app.features.business.biz_profile.entity.BizName
import com.thomas200593.mini_retail_app.features.business.biz_profile.entity.BizProfileShort
import com.thomas200593.mini_retail_app.features.initial.initial.navigation.navToInitial
import com.thomas200593.mini_retail_app.features.initial.initialization.entity.Initialization
import com.thomas200593.mini_retail_app.features.initial.initialization.ui.VMInitialization.UiEvents.ButtonEvents.BtnInitDefaultBizProfileEvents
import com.thomas200593.mini_retail_app.features.initial.initialization.ui.VMInitialization.UiEvents.ButtonEvents.BtnInitManualBizProfileEvents
import com.thomas200593.mini_retail_app.features.initial.initialization.ui.VMInitialization.UiEvents.DialogEvents.DlgResError
import com.thomas200593.mini_retail_app.features.initial.initialization.ui.VMInitialization.UiEvents.DialogEvents.DlgResSuccess
import com.thomas200593.mini_retail_app.features.initial.initialization.ui.VMInitialization.UiEvents.DropdownEvents.DDLanguage
import com.thomas200593.mini_retail_app.features.initial.initialization.ui.VMInitialization.UiEvents.InputFormEvents.BtnCancelEvents
import com.thomas200593.mini_retail_app.features.initial.initialization.ui.VMInitialization.UiEvents.InputFormEvents.BtnSubmitEvents
import com.thomas200593.mini_retail_app.features.initial.initialization.ui.VMInitialization.UiEvents.InputFormEvents.CommonNameEvents
import com.thomas200593.mini_retail_app.features.initial.initialization.ui.VMInitialization.UiEvents.InputFormEvents.LegalNameEvents
import com.thomas200593.mini_retail_app.features.initial.initialization.ui.VMInitialization.UiEvents.OnOpenEvents
import com.thomas200593.mini_retail_app.features.initial.initialization.ui.VMInitialization.UiState
import com.thomas200593.mini_retail_app.features.initial.initialization.ui.VMInitialization.UiStateInitialization.Loading
import com.thomas200593.mini_retail_app.features.initial.initialization.ui.VMInitialization.UiStateInitialization.Success
import kotlinx.coroutines.launch
import ulid.ULID

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
        onInitBizProfileDefaultBtnClicked = { vm.onEvent(BtnInitDefaultBizProfileEvents.OnClick(it)) },
        onInitBizProfileManualBtnClicked = { vm.onEvent(BtnInitManualBizProfileEvents.OnClick) },
        onLegalNameValueChanged = { vm.onEvent(LegalNameEvents.ValueChanged(it)) },
        onCommonNameValueChanged = { vm.onEvent(CommonNameEvents.ValueChanged(it)) },
        onFormSubmitBtnClicked = { vm.onEvent(BtnSubmitEvents.OnClick(it)) },
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
    onInitBizProfileDefaultBtnClicked: (BizProfileShort) -> Unit,
    onInitBizProfileManualBtnClicked: () -> Unit,
    onLegalNameValueChanged: (String) -> Unit,
    onCommonNameValueChanged: (String) -> Unit,
    onFormSubmitBtnClicked: (BizProfileShort) -> Unit,
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
    onInitBizProfileDefaultBtnClicked: (BizProfileShort) -> Unit,
    onInitBizProfileManualBtnClicked: () -> Unit,
    onLegalNameValueChanged: (String) -> Unit,
    onCommonNameValueChanged: (String) -> Unit,
    onFormSubmitBtnClicked: (BizProfileShort) -> Unit,
    onFormCancelBtnClicked: () -> Unit
) {
    Surface {
        Column(
            modifier = Modifier.fillMaxSize().padding(8.dp).verticalScroll(rememberScrollState()),
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
                    inputFormState = uiState.panelInputFormState,
                    onLegalNameValueChanged = onLegalNameValueChanged,
                    onCommonNameValueChanged = onCommonNameValueChanged,
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
    configCurrent: AppConfig.ConfigCurrent,
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
            onExpandedChange = { expanded = !expanded }
        ) {
            TextButton(
                modifier = Modifier.fillMaxWidth().menuAnchor(PrimaryNotEditable, true),
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
    onInitBizProfileDefaultBtnClicked: (BizProfileShort) -> Unit
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
    TextButton(
        onClick = {
            onInitBizProfileDefaultBtnClicked(
                BizProfileShort(
                    seqId = 0,
                    genId = ULID.randomULID(),
                    bizName = BizName(legalName = "My-Company Corp", commonName = "My Company"),
                    bizIndustry = Industries(identityKey = 0),
                    bizLegalType = LegalType(identifierKey = 0),
                    bizTaxation = Taxation(identifierKey = 0),
                    auditTrail = AuditTrail()
                )
            )
        }
    ) {
        Text(
            text = stringResource(R.string.str_init_setup_no),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@Composable
private fun PanelFormInitManualBizProfile(
    inputFormState: VMInitialization.PanelInputFormState,
    onLegalNameValueChanged: (String) -> Unit,
    onCommonNameValueChanged: (String) -> Unit,
    onFormSubmitBtnClicked: (BizProfileShort) -> Unit,
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
            Row(
                modifier = Modifier.fillMaxWidth(1.0f),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (inputFormState.fldSubmitBtnEnabled) {
                    AppIconButton(
                        modifier = Modifier.weight(0.5f),
                        onClick = {
                            onFormSubmitBtnClicked(
                                BizProfileShort(
                                    seqId = 0,
                                    genId = ULID.randomULID(),
                                    bizName = BizName(
                                        legalName = inputFormState.legalName,
                                        commonName = inputFormState.commonName
                                    ),
                                    bizIndustry = Industries(identityKey = 0),
                                    bizLegalType = LegalType(identifierKey = 0),
                                    bizTaxation = Taxation(identifierKey = 0),
                                    auditTrail = AuditTrail()
                                )
                            )
                        },
                        icon = ImageVector.vectorResource(id = CustomIcons.Emotion.neutral),
                        text = stringResource(id = R.string.str_save)
                    )
                }
                AppIconButton(
                    modifier = Modifier.weight(if (inputFormState.fldSubmitBtnEnabled) 0.5f else 1.0f),
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
    Column(modifier = Modifier.fillMaxSize()) {
        ScrInitialization (
            onSelectLanguage = {},
            onInitBizProfileDefaultBtnClicked = {},
            onInitBizProfileManualBtnClicked = {},
            onLegalNameValueChanged = {},
            onCommonNameValueChanged = {},
            onFormSubmitBtnClicked = {},
            onFormCancelBtnClicked = {},
            onInitBizProfileSuccess = {},
            onInitBizProfileError = {},
            uiState = UiState(
                initBizProfileOperationResult = ResourceState.Idle,
                dialogState = VMInitialization.DialogState(),
                panelInputFormState = VMInitialization.PanelInputFormState(),
                panelWelcomeMessageState = VMInitialization.PanelWelcomeMessageState(),
                initialization = Success(
                    data = Initialization(
                        configCurrent = AppConfig.ConfigCurrent(),
                        languages = setOf(Language.EN, Language.ID)
                    )
                )
            )
        )
    }
}
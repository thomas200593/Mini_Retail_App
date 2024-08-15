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
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign.Companion.Center
import androidx.compose.ui.text.style.TextAlign.Companion.Justify
import androidx.compose.ui.text.style.TextOverflow.Companion.Ellipsis
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.thomas200593.mini_retail_app.BuildConfig.BUILD_TYPE
import com.thomas200593.mini_retail_app.BuildConfig.VERSION_NAME
import com.thomas200593.mini_retail_app.R.string
import com.thomas200593.mini_retail_app.app.ui.LocalStateApp
import com.thomas200593.mini_retail_app.app.ui.StateApp
import com.thomas200593.mini_retail_app.core.data.local.database.entity_common.AuditTrail
import com.thomas200593.mini_retail_app.core.data.local.database.entity_common.Industries
import com.thomas200593.mini_retail_app.core.data.local.database.entity_common.LegalType
import com.thomas200593.mini_retail_app.core.data.local.database.entity_common.Taxation
import com.thomas200593.mini_retail_app.core.ui.common.CustomIcons.App.app
import com.thomas200593.mini_retail_app.core.ui.common.CustomIcons.Emotion.happy
import com.thomas200593.mini_retail_app.core.ui.common.CustomIcons.Emotion.neutral
import com.thomas200593.mini_retail_app.core.ui.common.CustomThemes
import com.thomas200593.mini_retail_app.core.ui.component.CustomButton.Common.AppIconButton
import com.thomas200593.mini_retail_app.core.ui.component.CustomDialog.AlertDialogContext.ERROR
import com.thomas200593.mini_retail_app.core.ui.component.CustomDialog.AlertDialogContext.INFORMATION
import com.thomas200593.mini_retail_app.core.ui.component.CustomDialog.AlertDialogContext.SUCCESS
import com.thomas200593.mini_retail_app.core.ui.component.CustomDialog.AppAlertDialog
import com.thomas200593.mini_retail_app.core.ui.component.CustomForm.Component.TextInput
import com.thomas200593.mini_retail_app.core.ui.component.CustomPanel.ErrorScreen
import com.thomas200593.mini_retail_app.core.ui.component.CustomPanel.LoadingScreen
import com.thomas200593.mini_retail_app.features.app_conf.app_config.entity.AppConfig.ConfigCurrent
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_language.entity.Language
import com.thomas200593.mini_retail_app.features.business.biz_profile.entity.BizName
import com.thomas200593.mini_retail_app.features.business.biz_profile.entity.BizProfileShort
import com.thomas200593.mini_retail_app.features.initial.initial.navigation.navToInitial
import com.thomas200593.mini_retail_app.features.initial.initialization.entity.Initialization
import com.thomas200593.mini_retail_app.features.initial.initialization.ui.VMInitialization.InputFormState
import com.thomas200593.mini_retail_app.features.initial.initialization.ui.VMInitialization.UiEvents.ButtonEvents.BtnInitDefaultBizProfileEvents
import com.thomas200593.mini_retail_app.features.initial.initialization.ui.VMInitialization.UiEvents.ButtonEvents.BtnInitManualBizProfileEvents
import com.thomas200593.mini_retail_app.features.initial.initialization.ui.VMInitialization.UiEvents.DropdownEvents.DDLanguage.OnSelect
import com.thomas200593.mini_retail_app.features.initial.initialization.ui.VMInitialization.UiEvents.InputFormEvents.BtnCancelEvents
import com.thomas200593.mini_retail_app.features.initial.initialization.ui.VMInitialization.UiEvents.InputFormEvents.BtnSubmitEvents
import com.thomas200593.mini_retail_app.features.initial.initialization.ui.VMInitialization.UiEvents.InputFormEvents.CommonNameEvents
import com.thomas200593.mini_retail_app.features.initial.initialization.ui.VMInitialization.UiEvents.InputFormEvents.LegalNameEvents
import com.thomas200593.mini_retail_app.features.initial.initialization.ui.VMInitialization.UiEvents.OnOpenEvents
import com.thomas200593.mini_retail_app.features.initial.initialization.ui.VMInitialization.UiState
import com.thomas200593.mini_retail_app.features.initial.initialization.ui.VMInitialization.UiStateInitialization.Error
import com.thomas200593.mini_retail_app.features.initial.initialization.ui.VMInitialization.UiStateInitialization.Loading
import com.thomas200593.mini_retail_app.features.initial.initialization.ui.VMInitialization.UiStateInitialization.Success
import ulid.ULID.Companion.randomULID

@Composable
fun ScrInitialization(
    vm: VMInitialization = hiltViewModel(),
    stateApp: StateApp = LocalStateApp.current
){
    val uiState by vm.uiState.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {vm.onEvent(OnOpenEvents)}
    ScrInitialization(
        uiState = uiState,
        onSelectLanguage = { vm.onEvent(OnSelect(it)) },
        onInitBizProfileDefaultBtnClicked = { vm.onEvent(BtnInitDefaultBizProfileEvents.OnClick(it)) },
        onInitBizProfileManualBtnClicked = { vm.onEvent(BtnInitManualBizProfileEvents.OnClick) },
        onLegalNameValueChanged = { vm.onEvent(LegalNameEvents.ValueChanged(it)) },
        onCommonNameValueChanged = { vm.onEvent(CommonNameEvents.ValueChanged(it)) },
        onFormSubmitBtnClicked = { vm.onEvent(BtnSubmitEvents.OnClick(it)) },
        onFormCancelBtnClicked = { vm.onEvent(BtnCancelEvents.OnClick) },
        onInitBizProfileSuccess = { stateApp.navController.navToInitial() },
        onInitBizProfileError = { stateApp.navController.navToInitial() }
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
    when(uiState.initialization){
        Loading -> LoadingScreen()
        is Error -> ErrorScreen(
            title = stringResource(id = string.str_error),
            errorMessage = uiState.initialization.t.stackTraceToString(),
            showIcon = true
        )
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
        showDialog = uiState.dialogState.dlgLoadingEnabled,
        dialogContext = INFORMATION,
        showIcon = true,
        showTitle = true,
        title = { Text(stringResource(id = string.str_loading)) },
        showBody = true,
        body = { Text(stringResource(id = string.str_biz_profile_init_loading)) }
    )
    AppAlertDialog(
        showDialog = uiState.dialogState.dlgSuccessEnabled,
        dialogContext = SUCCESS,
        showIcon = true,
        showTitle = true,
        title = { Text(stringResource(id = string.str_success)) },
        showBody = true,
        body = { Text(stringResource(id = string.str_biz_profile_init_success)) },
        useConfirmButton = true,
        confirmButton = {
            TextButton(onClick = { onInitBizProfileSuccess() })
            { Text(stringResource(id = string.str_ok)) }
        }
    )
    AppAlertDialog(
        showDialog = uiState.dialogState.dlgErrorEnabled,
        dialogContext = ERROR,
        showIcon = true,
        showTitle = true,
        title = { Text(stringResource(id = string.str_error)) },
        showBody = true,
        body = { Text(stringResource(id = string.str_biz_profile_init_failed)) },
        useDismissButton = true,
        dismissButton = {
            TextButton(onClick = { onInitBizProfileError() })
            { Text(stringResource(id = string.str_ok)) }
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
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(30.dp, Alignment.Top)
    ) {
        LanguageSection(
            languages = initData.languages,
            configCurrent = initData.configCurrent,
            onSelectLanguage = onSelectLanguage
        )
        if(uiState.welcomePanelState.visible){
            WelcomeMessage(
                onInitBizProfileDefaultBtnClicked = onInitBizProfileDefaultBtnClicked,
                onInitBizProfileManualBtnClicked = onInitBizProfileManualBtnClicked
            )
        }
        if(uiState.inputFormState.visible){
            InitManualForm(
                inputFormState = uiState.inputFormState,
                onLegalNameValueChanged = onLegalNameValueChanged,
                onCommonNameValueChanged = onCommonNameValueChanged,
                onFormSubmitBtnClicked = onFormSubmitBtnClicked,
                onFormCancelBtnClicked = onFormCancelBtnClicked
            )
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
    ){
        var expanded by remember { mutableStateOf(false) }
        ExposedDropdownMenuBox(
            modifier = Modifier.fillMaxWidth(0.4f),
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            TextButton(modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(), onClick = { expanded = true }) {
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
                        textAlign = Center,
                        maxLines = 1,
                        overflow = Ellipsis
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
                        { Text(modifier = Modifier.fillMaxWidth(), text = stringResource(id = it.title)) },
                        onClick = {
                            expanded = false
                            onSelectLanguage.invoke(it)
                        },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                    )
                }
            }
        }
    }
}

@Composable
private fun WelcomeMessage(
    onInitBizProfileDefaultBtnClicked: (BizProfileShort) -> Unit,
    onInitBizProfileManualBtnClicked: () -> Unit,
) {
    Column(
        modifier = Modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically)
    ) {
        Surface(
            modifier = Modifier.size(150.dp),
            color = Color.Transparent,
            shape = shapes.medium
        ) { Image(imageVector = ImageVector.vectorResource(id = app), contentDescription = null) }
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(id = string.app_name),
            textAlign = Center,
            style = typography.titleLarge,
            color = colorScheme.onSurface,
            maxLines = 1,
            overflow = Ellipsis
        )
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "$VERSION_NAME - $BUILD_TYPE",
            textAlign = Center,
            style = typography.labelMedium,
            color = colorScheme.onSurface,
            maxLines = 1,
            overflow = Ellipsis
        )
    }
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = shapes.medium,
        color = colorScheme.primaryContainer,
        contentColor = colorScheme.onPrimaryContainer,
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
                text = stringResource(string.str_init_welcome_message),
                style = typography.labelLarge,
                textAlign = Justify
            )
            AppIconButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = { onInitBizProfileManualBtnClicked.invoke() },
                icon = ImageVector.vectorResource(id = happy),
                text = stringResource(string.str_init_setup_yes)
            )
        }
    }
    TextButton(
        onClick = {
            onInitBizProfileDefaultBtnClicked.invoke(
                BizProfileShort(
                    seqId = 0,
                    genId = randomULID(),
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
            text = stringResource(string.str_init_setup_no),
            textAlign = Center,
            style = typography.titleMedium
        )
    }
}

@Composable
fun InitManualForm(
    inputFormState: InputFormState,
    onLegalNameValueChanged: (String) -> Unit,
    onCommonNameValueChanged: (String) -> Unit,
    onFormSubmitBtnClicked: (BizProfileShort) -> Unit,
    onFormCancelBtnClicked: () -> Unit,
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = shapes.medium,
        color = colorScheme.surfaceContainerHighest
    ){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically)
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = string.str_business_profile),
                textAlign = Center,
                style = typography.titleLarge,
                color = colorScheme.onSurface,
                maxLines = 1,
                overflow = Ellipsis
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = string.str_business_profile_desc),
                textAlign = Center,
                style = typography.labelMedium,
                color = colorScheme.onSurface,
                maxLines = 1,
                overflow = Ellipsis
            )
            HorizontalDivider(
                thickness = 2.dp,
                color = colorScheme.onSurface
            )
            TextInput(
                value = inputFormState.legalName,
                onValueChange = { onLegalNameValueChanged(it) },
                label = stringResource(string.str_company_legal_name),
                placeholder = stringResource(string.str_company_legal_name),
                singleLine = true,
                isError = inputFormState.legalNameError != null,
                errorMessage = inputFormState.legalNameError
            )
            TextInput(
                value = inputFormState.commonName,
                onValueChange = { onCommonNameValueChanged(it) },
                label = stringResource(string.str_company_common_name),
                placeholder = stringResource(string.str_company_common_name),
                singleLine = true,
                isError = inputFormState.commonNameError != null,
                errorMessage = inputFormState.commonNameError
            )
            Row(
                modifier = Modifier.fillMaxWidth(1.0f),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if(inputFormState.fldSubmitBtnEnabled){
                    AppIconButton(
                        modifier = Modifier.weight(0.5f),
                        onClick = {
                            onFormSubmitBtnClicked.invoke(
                                BizProfileShort(
                                    seqId = 0,
                                    genId = randomULID(),
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
                        icon = ImageVector.vectorResource(id = neutral),
                        text = stringResource(id = string.str_save)
                    )
                }
                AppIconButton(
                    modifier = Modifier.weight(if(inputFormState.fldSubmitBtnEnabled){0.5f}else{1.0f}),
                    onClick = onFormCancelBtnClicked,
                    icon = ImageVector.vectorResource(id = neutral),
                    text = stringResource(id = string.str_cancel),
                    containerColor = colorScheme.errorContainer,
                    contentColor = colorScheme.onErrorContainer
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() = CustomThemes.ApplicationTheme {
    Column(modifier = Modifier.fillMaxSize()) {
        ScrInitialization(
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
                initialization = Success(
                    data = Initialization(
                        configCurrent = ConfigCurrent(),
                        languages = setOf()
                    )
                ),
                welcomePanelState = VMInitialization.WelcomePanelState(
                    visible = false
                ),
                inputFormState = InputFormState(
                    visible = true
                ),
                dialogState = VMInitialization.DialogState()
            ),
        )
    }
}
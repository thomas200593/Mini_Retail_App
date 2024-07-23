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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.thomas200593.mini_retail_app.BuildConfig
import com.thomas200593.mini_retail_app.R
import com.thomas200593.mini_retail_app.app.ui.StateApp
import com.thomas200593.mini_retail_app.app.ui.LocalStateApp
import com.thomas200593.mini_retail_app.core.data.local.database.entity_common.AuditTrail
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState
import com.thomas200593.mini_retail_app.core.ui.common.CustomIcons
import com.thomas200593.mini_retail_app.core.ui.component.CustomButton.Common.AppIconButton
import com.thomas200593.mini_retail_app.core.ui.component.CustomPanel.EmptyScreen
import com.thomas200593.mini_retail_app.core.ui.component.CustomPanel.ErrorScreen
import com.thomas200593.mini_retail_app.core.ui.component.CustomPanel.LoadingScreen
import com.thomas200593.mini_retail_app.core.ui.component.CustomDialog
import com.thomas200593.mini_retail_app.core.ui.component.CustomDialog.AppAlertDialog
import com.thomas200593.mini_retail_app.core.ui.component.CustomForm.Component.TextInput
import com.thomas200593.mini_retail_app.features.app_conf.app_config.entity.AppConfig
import com.thomas200593.mini_retail_app.features.app_conf._g_language.entity.Language
import com.thomas200593.mini_retail_app.features.business.entity.business_profile.BizName
import com.thomas200593.mini_retail_app.features.business.entity.business_profile.dto.BizProfileSummary
import com.thomas200593.mini_retail_app.features.initial.initialization.entity.InitializationUiFormState
import com.thomas200593.mini_retail_app.features.initial.initialization.entity.Initialization
import com.thomas200593.mini_retail_app.features.initial.initialization.entity.InitializationUiState
import com.thomas200593.mini_retail_app.features.initial.initial.navigation.navToInitial
import kotlinx.coroutines.launch
import ulid.ULID

@Composable
fun InitializationScreen(
    viewModel: InitializationViewModel = hiltViewModel(),
    stateApp: StateApp = LocalStateApp.current
){
    val uiState = viewModel.uiState
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) { viewModel.onEvent(InitializationUiEvent.OnOpen) }

    AppAlertDialog(
        showDialog = uiState.value.uiEnableLoadingDialog,
        dialogContext = CustomDialog.AlertDialogContext.INFORMATION,
        showIcon = true,
        showTitle = true,
        title = { Text(stringResource(id = R.string.str_loading)) },
        showBody = true,
        body = { Text(stringResource(R.string.str_biz_profile_init_loading)) }
    )

    AppAlertDialog(
        showDialog = uiState.value.uiEnableSuccessDialog,
        dialogContext = CustomDialog.AlertDialogContext.SUCCESS,
        showIcon = true,
        showTitle = true,
        title = { Text(stringResource(id = R.string.str_success)) },
        showBody = true,
        body = { Text(stringResource(R.string.str_biz_profile_init_success)) },
        useConfirmButton = true,
        confirmButton = {
            TextButton(onClick = { coroutineScope.launch { stateApp.navController.navToInitial() } })
            { Text(stringResource(id = R.string.str_ok)) }
        }
    )

    AppAlertDialog(
        showDialog = uiState.value.uiEnableErrorDialog,
        dialogContext = CustomDialog.AlertDialogContext.ERROR,
        showIcon = true,
        showTitle = true,
        title = { Text(stringResource(id = R.string.str_error)) },
        showBody = true,
        body = { Text(stringResource(R.string.str_biz_profile_init_failed)) },
        useDismissButton = true,
        dismissButton = {
            TextButton(onClick = { coroutineScope.launch { stateApp.navController.navToInitial() } })
            { Text(stringResource(id = R.string.str_ok)) }
        }
    )

    ScreenContent(
        uiState = uiState,
        onChangeLanguage = { viewModel.onEvent(InitializationUiEvent.OnChangeLanguage(it)) },
        onInitBizProfileDefault = { viewModel.onEvent(InitializationUiEvent.BeginInitBizProfileDefault(it)) },
        onInitBizProfileManual = { viewModel.onEvent(InitializationUiEvent.BeginInitBizProfileManual) },
        onUiFormLegalNameChanged = { viewModel.onEvent(InitializationUiEvent.OnUiFormLegalNameChanged(it)) },
        onUiFormCommonNameChanged = { viewModel.onEvent(InitializationUiEvent.OnUiFormCommonNameChanged(it))},
        onUiFormSubmitInitManual = { viewModel.onEvent(InitializationUiEvent.OnUiFormSubmitInitManual(it)) },
        onUiFormCancelInitManual = { viewModel.onEvent(InitializationUiEvent.OnUiFormCancelInitManual) }
    )
}

@Composable
private fun ScreenContent(
    uiState: MutableState<InitializationUiState>,
    onChangeLanguage: (Language) -> Unit,
    onInitBizProfileDefault: (BizProfileSummary) -> Unit,
    onInitBizProfileManual: () -> Unit,
    onUiFormLegalNameChanged: (String) -> Unit,
    onUiFormCommonNameChanged: (String) -> Unit,
    onUiFormSubmitInitManual: (BizProfileSummary) -> Unit,
    onUiFormCancelInitManual: () -> Unit
) {
    when(uiState.value.initializationData){
        ResourceState.Idle, ResourceState.Loading -> { LoadingScreen() }
        ResourceState.Empty -> {
            EmptyScreen(
                title = stringResource(id = R.string.str_empty_message_title),
                emptyMessage = stringResource(id = R.string.str_empty_message),
                showIcon = true
            )
        }
        is ResourceState.Error -> {
            ErrorScreen(
                title = stringResource(id = R.string.str_error),
                errorMessage = stringResource(id = R.string.str_error_fetching_preferences),
                showIcon = true
            )
        }
        is ResourceState.Success -> {
            val initializationData = (uiState.value.initializationData as ResourceState.Success<Initialization>).data
            val initUiPropertiesState = uiState.value.initializationUiFormState
            val asd = uiState.value

            SuccessSection(
                initializationData = initializationData,
                onChangeLanguage = onChangeLanguage,
                asd = asd,
                initializationUiFormState = initUiPropertiesState,
                onInitBizProfileDefault = onInitBizProfileDefault,
                onInitBizProfileManual = onInitBizProfileManual,
                onUiFormLegalNameChanged = onUiFormLegalNameChanged,
                onUiFormCommonNameChanged = onUiFormCommonNameChanged,
                onUiFormSubmitInitManual = onUiFormSubmitInitManual,
                onUiFormCancelInitManual = onUiFormCancelInitManual
            )
        }
    }
}

@Composable
private fun SuccessSection(
    initializationData: Initialization,
    initializationUiFormState: InitializationUiFormState,
    onChangeLanguage: (Language) -> Unit,
    onInitBizProfileDefault: (BizProfileSummary) -> Unit,
    onInitBizProfileManual: () -> Unit,
    onUiFormLegalNameChanged: (String) -> Unit,
    onUiFormCommonNameChanged: (String) -> Unit,
    onUiFormSubmitInitManual: (BizProfileSummary) -> Unit,
    onUiFormCancelInitManual: () -> Unit,
    asd: InitializationUiState
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(8.dp).verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(30.dp, Alignment.Top)
    ) {
        LanguageSection(
            languages = initializationData.languages,
            configCurrent = initializationData.configCurrent,
            onChangeLanguage = onChangeLanguage
        )
        if(asd.uiEnableWelcomeMessage){
            WelcomeMessage(
                onInitBizProfileDefault = onInitBizProfileDefault,
                onInitBizProfileManual = onInitBizProfileManual
            )
        }
        if(asd.uiEnableInitManualForm){
            InputManualForm(
                initializationUiFormState = initializationUiFormState,
                onUiFormLegalNameChanged = onUiFormLegalNameChanged,
                onUiFormCommonNameChanged = onUiFormCommonNameChanged,
                onUiFormSubmitInitManual = onUiFormSubmitInitManual,
                onUiFormCancelInitManual = onUiFormCancelInitManual
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LanguageSection(
    languages: Set<Language>,
    configCurrent: AppConfig.ConfigCurrent,
    onChangeLanguage: (Language) -> Unit
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
            TextButton(
                modifier = Modifier.fillMaxWidth().menuAnchor(), onClick = { expanded = true }
            ) {
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
                languages.forEach { language ->
                    DropdownMenuItem(
                        modifier = Modifier.fillMaxWidth(),
                        leadingIcon = {
                            Image(
                                modifier = Modifier.size(24.dp),
                                imageVector = ImageVector.vectorResource(id = language.iconRes),
                                contentDescription = null
                            )
                        },
                        text = { Text(modifier = Modifier.fillMaxWidth(), text = stringResource(id = language.title)) },
                        onClick = {
                            expanded = false
                            onChangeLanguage.invoke(language)
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
    onInitBizProfileDefault: (BizProfileSummary) -> Unit,
    onInitBizProfileManual: () -> Unit
){
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
                onClick = { onInitBizProfileManual.invoke() },
                icon = ImageVector.vectorResource(id = CustomIcons.Emotion.happy),
                text = stringResource(R.string.str_init_setup_yes)
            )
        }
    }
    TextButton(
        onClick = {
            onInitBizProfileDefault.invoke(
                BizProfileSummary(
                    seqId = 0,
                    genId = ULID.randomULID(),
                    bizName = BizName(
                        legalName = "My-Company Corp",
                        commonName = "My Company"
                    ),
                    bizIndustry = null,
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
private fun InputManualForm(
    onUiFormSubmitInitManual: (BizProfileSummary) -> Unit,
    onUiFormCancelInitManual: () -> Unit,
    initializationUiFormState: InitializationUiFormState,
    onUiFormLegalNameChanged: (String) -> Unit,
    onUiFormCommonNameChanged: (String) -> Unit,
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.colorScheme.surfaceContainerHighest
    ){
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
                value = initializationUiFormState.uiFormLegalName,
                onValueChange = { onUiFormLegalNameChanged(it) },
                label = stringResource(R.string.str_company_legal_name),
                placeholder = stringResource(R.string.str_company_legal_name),
                singleLine = true,
                isError = initializationUiFormState.uiFormLegalNameError != null,
                errorMessage = initializationUiFormState.uiFormLegalNameError
            )
            TextInput(
                value = initializationUiFormState.uiFormCommonName,
                onValueChange = { onUiFormCommonNameChanged(it) },
                label = stringResource(R.string.str_company_common_name),
                placeholder = stringResource(R.string.str_company_common_name),
                singleLine = true,
                isError = initializationUiFormState.uiFormCommonNameError != null,
                errorMessage = initializationUiFormState.uiFormCommonNameError
            )
            Row(
                modifier = Modifier.fillMaxWidth(1.0f),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if(initializationUiFormState.uiFormEnableSubmitBtn){
                    AppIconButton(
                        modifier = Modifier.weight(0.5f),
                        onClick = {
                            onUiFormSubmitInitManual.invoke(
                                BizProfileSummary(
                                    seqId = 0,
                                    genId = ULID.randomULID(),
                                    bizName = BizName(
                                        legalName = initializationUiFormState.uiFormLegalName,
                                        commonName = initializationUiFormState.uiFormCommonName,
                                    ),
                                    bizIndustry = null,
                                    auditTrail = AuditTrail()
                                )
                            )
                        },
                        icon = ImageVector.vectorResource(id = CustomIcons.Emotion.neutral),
                        text = stringResource(id = R.string.str_save)
                    )
                }
                AppIconButton(
                    modifier = Modifier.weight(if(initializationUiFormState.uiFormEnableSubmitBtn){0.5f}else{1.0f}),
                    onClick = onUiFormCancelInitManual,
                    icon = ImageVector.vectorResource(id = CustomIcons.Emotion.neutral),
                    text = stringResource(id = R.string.str_cancel)
                )
            }
        }
    }
}
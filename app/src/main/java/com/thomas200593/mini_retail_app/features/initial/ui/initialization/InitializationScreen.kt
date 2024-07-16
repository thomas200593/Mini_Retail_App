package com.thomas200593.mini_retail_app.features.initial.ui.initialization

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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.thomas200593.mini_retail_app.BuildConfig
import com.thomas200593.mini_retail_app.R
import com.thomas200593.mini_retail_app.app.ui.AppState
import com.thomas200593.mini_retail_app.app.ui.LocalAppState
import com.thomas200593.mini_retail_app.core.data.local.database.entity_common.AuditTrail
import com.thomas200593.mini_retail_app.core.design_system.util.RequestState
import com.thomas200593.mini_retail_app.core.ui.common.Icons
import com.thomas200593.mini_retail_app.core.ui.component.Button.Common.AppIconButton
import com.thomas200593.mini_retail_app.core.ui.component.CommonMessagePanel.EmptyScreen
import com.thomas200593.mini_retail_app.core.ui.component.CommonMessagePanel.ErrorScreen
import com.thomas200593.mini_retail_app.core.ui.component.CommonMessagePanel.LoadingScreen
import com.thomas200593.mini_retail_app.core.ui.component.Dialog
import com.thomas200593.mini_retail_app.core.ui.component.Dialog.AppAlertDialog
import com.thomas200593.mini_retail_app.core.ui.component.Form.Component.TextInput
import com.thomas200593.mini_retail_app.features.app_config.entity.AppConfig
import com.thomas200593.mini_retail_app.features.app_config.entity.Language
import com.thomas200593.mini_retail_app.features.business.entity.business_profile.BizName
import com.thomas200593.mini_retail_app.features.business.entity.business_profile.dto.BusinessProfileSummary
import com.thomas200593.mini_retail_app.features.initial.entity.Initialization
import com.thomas200593.mini_retail_app.features.initial.navigation.navigateToInitial
import com.thomas200593.mini_retail_app.features.initial.ui.initialization.InitializationScreenUiEvent.InitializationUiEventStatus
import com.thomas200593.mini_retail_app.features.initial.ui.initialization.InitializationScreenUiEvent.InitializationUiFormEvent
import kotlinx.coroutines.launch
import ulid.ULID

@Composable
fun InitializationScreen(
    viewModel: InitializationViewModel = hiltViewModel(),
    appState: AppState = LocalAppState.current
){
    val coroutineScope = rememberCoroutineScope()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val uiEvent = viewModel.initializationScreenUiEvent
    val uiWelcomeMessage = viewModel.uiWelcomeMessage
    val uiInputBizProfileForm = viewModel.uiInputBizProfileForm
    val uiEventStatus = viewModel.initializationUiEventStatus
    val uiLoadingDlg = remember { mutableStateOf(false) }
    val uiSuccessDlg = remember { mutableStateOf(false) }
    val uiErrorDlg = remember { mutableStateOf(false) }

    LaunchedEffect(uiEventStatus) {
        when(uiEventStatus){
            InitializationUiEventStatus.IDLE.name -> { uiLoadingDlg.value = false; uiSuccessDlg.value = false; uiErrorDlg.value = false }
            InitializationUiEventStatus.LOADING.name -> { uiLoadingDlg.value = true; uiSuccessDlg.value = false; uiErrorDlg.value = false }
            InitializationUiEventStatus.SUCCESS.name -> { uiLoadingDlg.value = false; uiSuccessDlg.value = true; uiErrorDlg.value= false }
            InitializationUiEventStatus.FAILED.name -> { uiLoadingDlg.value = false; uiSuccessDlg.value = false; uiErrorDlg.value = true }
        }
    }

    AppAlertDialog(
        showDialog = uiLoadingDlg,
        dialogContext = Dialog.AlertDialogContext.INFORMATION,
        showIcon = true,
        showTitle = true,
        title = { Text(stringResource(id = R.string.str_loading)) },
        showBody = true,
        body = { Text(stringResource(R.string.str_biz_profile_init_loading)) }
    )

    AppAlertDialog(
        showDialog = uiSuccessDlg,
        dialogContext = Dialog.AlertDialogContext.SUCCESS,
        showIcon = true,
        showTitle = true,
        title = { Text(stringResource(id = R.string.str_success)) },
        showBody = true,
        body = { Text(stringResource(R.string.str_biz_profile_init_success)) },
        useConfirmButton = true,
        confirmButton = {
            TextButton(
                onClick = {
                    uiSuccessDlg.value = false
                    coroutineScope.launch { appState.navController.navigateToInitial() }
                }
            ) { Text(stringResource(id = R.string.str_ok)) }
        }
    )

    AppAlertDialog(
        showDialog = uiErrorDlg,
        dialogContext = Dialog.AlertDialogContext.ERROR,
        showIcon = true,
        showTitle = true,
        title = { Text(stringResource(id = R.string.str_error)) },
        showBody = true,
        body = { Text(stringResource(R.string.str_biz_profile_init_failed)) },
        useDismissButton = true,
        dismissButton = {
            TextButton(
                onClick = {
                    uiSuccessDlg.value = false
                    coroutineScope.launch { appState.navController.navigateToInitial() }
                }
            ) { Text(stringResource(id = R.string.str_ok)) }
        }
    )

    ScreenContent(
        uiState = uiState,
        uiEvent = uiEvent,
        uiWelcomeMessage = uiWelcomeMessage,
        uiInputBizProfileForm = uiInputBizProfileForm,
        onSetLanguage = viewModel::setLanguage,
        onSetBizProfileDefault = viewModel::setBizProfileDefault,
        onBeginSetBizProfileManual = viewModel::beginSetBizProfileManual,
        onSubmitForm = { viewModel.initializationScreenUiEvent.onFormEvent(InitializationUiFormEvent.Submit) },
        onCancelForm = viewModel::onCancelManualInit
    )
}

@Composable
fun ScreenContent(
    uiState: RequestState<Initialization>,
    uiEvent: InitializationScreenUiEvent,
    uiWelcomeMessage: Boolean,
    uiInputBizProfileForm: Boolean,
    onSetLanguage: (Language) -> Unit,
    onSetBizProfileDefault: (BusinessProfileSummary) -> Unit,
    onBeginSetBizProfileManual: () -> Unit,
    onSubmitForm: () -> Unit,
    onCancelForm: () -> Unit,
) {
    when(uiState){
        RequestState.Idle, RequestState.Loading -> { LoadingScreen() }
        RequestState.Empty -> {
            EmptyScreen(
                title = stringResource(id = R.string.str_empty_message_title),
                emptyMessage = stringResource(id = R.string.str_empty_message),
                showIcon = true
            )
        }
        is RequestState.Error -> {
            ErrorScreen(
                title = stringResource(id = R.string.str_error),
                errorMessage = stringResource(id = R.string.str_error_fetching_preferences),
                showIcon = true
            )
        }
        is RequestState.Success -> {
            SuccessScreen(
                uiState = uiState,
                uiEvent = uiEvent,
                uiWelcomeMessage = uiWelcomeMessage,
                uiInputBizProfileForm = uiInputBizProfileForm,
                onSetLanguage = onSetLanguage,
                onSetBizProfileDefault = onSetBizProfileDefault,
                onBeginSetBizProfileManual = onBeginSetBizProfileManual,
                onSubmitForm = onSubmitForm,
                onCancelForm = onCancelForm
            )
        }
    }
}

@Composable
private fun SuccessScreen(
    uiState: RequestState.Success<Initialization>,
    uiEvent: InitializationScreenUiEvent,
    uiWelcomeMessage: Boolean,
    uiInputBizProfileForm: Boolean,
    onSetLanguage: (Language) -> Unit,
    onSetBizProfileDefault: (BusinessProfileSummary) -> Unit,
    onBeginSetBizProfileManual: () -> Unit,
    onSubmitForm: () -> Unit,
    onCancelForm: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(8.dp).verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(30.dp, Alignment.Top)
    ) {
        LanguageSection(
            languages = uiState.data.languages,
            configCurrent = uiState.data.configCurrent,
            onSetLanguage = onSetLanguage
        )
        if(uiWelcomeMessage) {
            WelcomeHeader(
                onManualInit = onBeginSetBizProfileManual,
                onDefaultInit = onSetBizProfileDefault
            )
        }
        if(uiInputBizProfileForm){
            InputManualForm(
                onSubmitInit = onSubmitForm,
                onCancelInit = onCancelForm,
                formUseCase = uiEvent
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LanguageSection(
    languages: Set<Language>,
    configCurrent: AppConfig.ConfigCurrent,
    onSetLanguage: (Language) -> Unit
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
                            onSetLanguage.invoke(language)
                        },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                    )
                }
            }
        }
    }
}

@Composable
private fun WelcomeHeader(
    onManualInit: () -> Unit,
    onDefaultInit: (BusinessProfileSummary) -> Unit
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
        ) { Image(imageVector = ImageVector.vectorResource(id = Icons.App.app), contentDescription = null) }
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
                onClick = { onManualInit.invoke() },
                icon = ImageVector.vectorResource(id = Icons.Emotion.happy),
                text = stringResource(R.string.str_init_setup_yes)
            )
        }
    }
    TextButton(
        onClick = {
            onDefaultInit.invoke(
                BusinessProfileSummary(
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
    onSubmitInit: () -> Unit,
    onCancelInit: () -> Unit,
    formUseCase: InitializationScreenUiEvent
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
                value = formUseCase.initializationUiFormState.legalName,
                onValueChange = { formUseCase.onFormEvent(InitializationUiFormEvent.LegalNameChanged(it)) },
                label = stringResource(R.string.str_company_legal_name),
                placeholder = stringResource(R.string.str_company_legal_name),
                singleLine = true,
                isError = formUseCase.initializationUiFormState.legalNameError != null,
                errorMessage = formUseCase.initializationUiFormState.legalNameError
            )
            TextInput(
                value = formUseCase.initializationUiFormState.commonName,
                onValueChange = { formUseCase.onFormEvent(InitializationUiFormEvent.CommonNameChanged(it)) },
                label = stringResource(R.string.str_company_common_name),
                placeholder = stringResource(R.string.str_company_common_name),
                singleLine = true,
                isError = formUseCase.initializationUiFormState.commonNameError != null,
                errorMessage = formUseCase.initializationUiFormState.commonNameError
            )
            Row(
                modifier = Modifier.fillMaxWidth(1.0f),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if(formUseCase.initializationUiFormState.submitButtonEnabled){
                    AppIconButton(
                        modifier = Modifier.weight(if(formUseCase.initializationUiFormState.submitButtonEnabled){0.5f}else{0.0f}),
                        onClick = onSubmitInit,
                        icon = ImageVector.vectorResource(id = Icons.Emotion.neutral),
                        text = stringResource(id = R.string.str_save)
                    )
                }
                AppIconButton(
                    modifier = Modifier.weight(if(formUseCase.initializationUiFormState.submitButtonEnabled){0.5f}else{1.0f}),
                    onClick = onCancelInit,
                    icon = ImageVector.vectorResource(id = Icons.Emotion.neutral),
                    text = stringResource(id = R.string.str_cancel)
                )
            }
        }
    }
}
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
import com.thomas200593.mini_retail_app.features.app_config.entity.ConfigCurrent
import com.thomas200593.mini_retail_app.features.app_config.entity.Language
import com.thomas200593.mini_retail_app.features.business.entity.business_profile.BizName
import com.thomas200593.mini_retail_app.features.business.entity.business_profile.dto.BusinessProfileSummary
import com.thomas200593.mini_retail_app.features.initial.entity.Initialization
import com.thomas200593.mini_retail_app.features.initial.navigation.navigateToInitial
import com.thomas200593.mini_retail_app.features.initial.ui.initialization.InitializationViewModel.Companion.Status
import com.thomas200593.mini_retail_app.features.initial.ui.initialization.InitializationViewModel.InitBizProfileManual.FormEvent
import kotlinx.coroutines.launch
import ulid.ULID

@Composable
fun InitializationScreen(
    viewModel: InitializationViewModel = hiltViewModel(),
    appState: AppState = LocalAppState.current
){
    val coroutineScope = rememberCoroutineScope()
    val uiState by viewModel.uiState
    val showWelcomeMessage = viewModel.showWelcomeMessage
    val showInputManualForm = viewModel.showInputManualForm
    val formUseCase = viewModel.initBizProfileManual
    val initBizProfileProgress by viewModel.initBizProfileProgress.collectAsStateWithLifecycle()

    val showLoadingDialog = remember { mutableStateOf(false) }
    val showSuccessDialog = remember { mutableStateOf(false) }
    val showErrorDialog = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) { viewModel.onOpen() }

    LaunchedEffect(initBizProfileProgress) {
        when(initBizProfileProgress){
            Status.IDLE.name -> { showLoadingDialog.value = false; showSuccessDialog.value = false; showErrorDialog.value = false }
            Status.LOADING.name -> { showLoadingDialog.value = true; showSuccessDialog.value = false; showErrorDialog.value = false }
            Status.SUCCESS.name -> { showLoadingDialog.value = false; showSuccessDialog.value = true; showErrorDialog.value= false }
            Status.FAILED.name -> { showLoadingDialog.value = false; showSuccessDialog.value = false; showErrorDialog.value = true }
        }
    }

    AppAlertDialog(
        showDialog = showLoadingDialog,
        dialogContext = Dialog.AlertDialogContext.INFORMATION,
        showIcon = true,
        showTitle = true,
        title = { Text(stringResource(id = R.string.str_loading)) },
        showBody = true,
        body = { Text(stringResource(R.string.str_biz_profile_init_loading)) }
    )

    AppAlertDialog(
        showDialog = showSuccessDialog,
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
                    showSuccessDialog.value = false
                    coroutineScope.launch { appState.navController.navigateToInitial() }
                }
            ) { Text(stringResource(id = R.string.str_ok)) }
        }
    )

    AppAlertDialog(
        showDialog = showErrorDialog,
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
                    showSuccessDialog.value = false
                    coroutineScope.launch { appState.navController.navigateToInitial() }
                }
            ) { Text(stringResource(id = R.string.str_ok)) }
        }
    )

    ScreenContent(
        uiState = uiState,
        formUseCase = formUseCase,
        showWelcomeMessage = showWelcomeMessage,
        showInputManualForm = showInputManualForm,
        onLanguageChanged = viewModel::setLanguage,
        onDefaultInit = viewModel::setBizProfileDefault,
        onManualInit = viewModel::onBeginManualInit,
        onSubmitManualInit = { viewModel.initBizProfileManual.onEvent(FormEvent.Submit) },
        onCancelManualInit = viewModel::onCancelManualInit
    )
}

@Composable
fun ScreenContent(
    uiState: RequestState<Initialization>,
    formUseCase: InitializationViewModel.InitBizProfileManual,
    showWelcomeMessage: Boolean,
    showInputManualForm: Boolean,
    onLanguageChanged: (Language) -> Unit,
    onDefaultInit: (BusinessProfileSummary) -> Unit,
    onManualInit: () -> Unit,
    onSubmitManualInit: () -> Unit,
    onCancelManualInit: () -> Unit,
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
                formUseCase = formUseCase,
                showWelcomeMessage = showWelcomeMessage,
                showInputManualForm = showInputManualForm,
                onLanguageChanged = onLanguageChanged,
                onDefaultInit = onDefaultInit,
                onManualInit = onManualInit,
                onSubmitManualInit = onSubmitManualInit,
                onCancelManualInit = onCancelManualInit
            )
        }
    }
}

@Composable
private fun SuccessScreen(
    uiState: RequestState.Success<Initialization>,
    formUseCase: InitializationViewModel.InitBizProfileManual,
    showWelcomeMessage: Boolean,
    showInputManualForm: Boolean,
    onLanguageChanged: (Language) -> Unit,
    onDefaultInit: (BusinessProfileSummary) -> Unit,
    onManualInit: () -> Unit,
    onSubmitManualInit: () -> Unit,
    onCancelManualInit: () -> Unit
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
            languages = uiState.data.languages,
            configCurrent = uiState.data.configCurrent,
            onSetLanguage = onLanguageChanged
        )
        if(showWelcomeMessage) {
            WelcomeHeader(
                onManualInit = onManualInit,
                onDefaultInit = onDefaultInit
            )
        }
        if(showInputManualForm){
            InputManualForm(
                onSubmitInit = onSubmitManualInit,
                onCancelInit = onCancelManualInit,
                formUseCase = formUseCase
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LanguageSection(
    languages: Set<Language>,
    configCurrent: ConfigCurrent,
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
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(), onClick = { expanded = true }
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
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
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
    formUseCase: InitializationViewModel.InitBizProfileManual
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.colorScheme.surfaceContainerHighest
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
                value = formUseCase.formState.legalName,
                onValueChange = { formUseCase.onEvent(FormEvent.LegalNameChanged(it)) },
                label = "Company Legal Name",
                placeholder = "Your Company Legal Name",
                singleLine = true,
                isError = formUseCase.formState.legalNameError != null,
                errorMessage = formUseCase.formState.legalNameError
            )
            TextInput(
                value = formUseCase.formState.commonName,
                onValueChange = { formUseCase.onEvent(FormEvent.CommonNameChanged(it)) },
                label = "Company Common Name",
                placeholder = "Your Company Common Name",
                singleLine = true,
                isError = formUseCase.formState.commonNameError != null,
                errorMessage = formUseCase.formState.commonNameError
            )
            Row(
                modifier = Modifier.fillMaxWidth(1.0f),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if(formUseCase.formState.submitButtonEnabled){
                    AppIconButton(
                        modifier = Modifier.weight(if(formUseCase.formState.submitButtonEnabled){0.5f}else{0.0f}),
                        onClick = onSubmitInit,
                        icon = ImageVector.vectorResource(id = Icons.Emotion.neutral),
                        text = stringResource(id = R.string.str_save)
                    )
                }
                AppIconButton(
                    modifier = Modifier.weight(if(formUseCase.formState.submitButtonEnabled){0.5f}else{1.0f}),
                    onClick = onCancelInit,
                    icon = ImageVector.vectorResource(id = Icons.Emotion.neutral),
                    text = stringResource(id = R.string.str_cancel)
                )
            }
        }
    }
}
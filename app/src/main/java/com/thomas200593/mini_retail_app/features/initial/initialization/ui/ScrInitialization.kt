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
import androidx.compose.material3.ExposedDropdownMenuDefaults.ItemContentPadding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.MaterialTheme.typography
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
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign.Companion.Center
import androidx.compose.ui.text.style.TextAlign.Companion.Justify
import androidx.compose.ui.text.style.TextOverflow.Companion.Ellipsis
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.thomas200593.mini_retail_app.BuildConfig.BUILD_TYPE
import com.thomas200593.mini_retail_app.BuildConfig.VERSION_NAME
import com.thomas200593.mini_retail_app.R.string.app_name
import com.thomas200593.mini_retail_app.R.string.str_biz_profile_init_failed
import com.thomas200593.mini_retail_app.R.string.str_biz_profile_init_loading
import com.thomas200593.mini_retail_app.R.string.str_biz_profile_init_success
import com.thomas200593.mini_retail_app.R.string.str_business_profile
import com.thomas200593.mini_retail_app.R.string.str_business_profile_desc
import com.thomas200593.mini_retail_app.R.string.str_cancel
import com.thomas200593.mini_retail_app.R.string.str_company_common_name
import com.thomas200593.mini_retail_app.R.string.str_company_legal_name
import com.thomas200593.mini_retail_app.R.string.str_empty_message
import com.thomas200593.mini_retail_app.R.string.str_empty_message_title
import com.thomas200593.mini_retail_app.R.string.str_error
import com.thomas200593.mini_retail_app.R.string.str_error_fetching_preferences
import com.thomas200593.mini_retail_app.R.string.str_init_setup_no
import com.thomas200593.mini_retail_app.R.string.str_init_setup_yes
import com.thomas200593.mini_retail_app.R.string.str_init_welcome_message
import com.thomas200593.mini_retail_app.R.string.str_loading
import com.thomas200593.mini_retail_app.R.string.str_ok
import com.thomas200593.mini_retail_app.R.string.str_save
import com.thomas200593.mini_retail_app.R.string.str_success
import com.thomas200593.mini_retail_app.app.ui.LocalStateApp
import com.thomas200593.mini_retail_app.app.ui.StateApp
import com.thomas200593.mini_retail_app.core.data.local.database.entity_common.AuditTrail
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState.Empty
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState.Error
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState.Idle
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState.Loading
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState.Success
import com.thomas200593.mini_retail_app.core.ui.common.CustomIcons.App.app
import com.thomas200593.mini_retail_app.core.ui.common.CustomIcons.Emotion.happy
import com.thomas200593.mini_retail_app.core.ui.common.CustomIcons.Emotion.neutral
import com.thomas200593.mini_retail_app.core.ui.component.CustomButton.Common.AppIconButton
import com.thomas200593.mini_retail_app.core.ui.component.CustomDialog.AlertDialogContext.ERROR
import com.thomas200593.mini_retail_app.core.ui.component.CustomDialog.AlertDialogContext.INFORMATION
import com.thomas200593.mini_retail_app.core.ui.component.CustomDialog.AlertDialogContext.SUCCESS
import com.thomas200593.mini_retail_app.core.ui.component.CustomDialog.AppAlertDialog
import com.thomas200593.mini_retail_app.core.ui.component.CustomForm.Component.TextInput
import com.thomas200593.mini_retail_app.core.ui.component.CustomPanel.EmptyScreen
import com.thomas200593.mini_retail_app.core.ui.component.CustomPanel.ErrorScreen
import com.thomas200593.mini_retail_app.core.ui.component.CustomPanel.LoadingScreen
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_language.entity.Language
import com.thomas200593.mini_retail_app.features.app_conf.app_config.entity.AppConfig
import com.thomas200593.mini_retail_app.features.business.entity.business_profile.BizName
import com.thomas200593.mini_retail_app.features.business.entity.business_profile.dto.BizProfileSummary
import com.thomas200593.mini_retail_app.features.initial.initial.navigation.navToInitial
import com.thomas200593.mini_retail_app.features.initial.initialization.entity.Initialization
import com.thomas200593.mini_retail_app.features.initial.initialization.entity.InitializationUiFormState
import com.thomas200593.mini_retail_app.features.initial.initialization.entity.InitializationUiState
import com.thomas200593.mini_retail_app.features.initial.initialization.ui.UiEventInitialization.BeginInitBizProfileDefault
import com.thomas200593.mini_retail_app.features.initial.initialization.ui.UiEventInitialization.BeginInitBizProfileManual
import com.thomas200593.mini_retail_app.features.initial.initialization.ui.UiEventInitialization.OnChangeLanguage
import com.thomas200593.mini_retail_app.features.initial.initialization.ui.UiEventInitialization.OnOpen
import com.thomas200593.mini_retail_app.features.initial.initialization.ui.UiEventInitialization.OnUiFormCancelInitManual
import com.thomas200593.mini_retail_app.features.initial.initialization.ui.UiEventInitialization.OnUiFormCommonNameChanged
import com.thomas200593.mini_retail_app.features.initial.initialization.ui.UiEventInitialization.OnUiFormLegalNameChanged
import com.thomas200593.mini_retail_app.features.initial.initialization.ui.UiEventInitialization.OnUiFormSubmitInitManual
import kotlinx.coroutines.launch
import ulid.ULID.Companion.randomULID

@Composable
fun ScrInitialization(
    vm: VMInitialization = hiltViewModel(),
    stateApp: StateApp = LocalStateApp.current
){
    val uiState = vm.uiState
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) { vm.onEvent(OnOpen) }

    AppAlertDialog(
        showDialog = uiState.value.uiEnableLoadingDialog,
        dialogContext = INFORMATION,
        showIcon = true,
        showTitle = true,
        title = { Text(stringResource(id = str_loading)) },
        showBody = true,
        body = { Text(stringResource(str_biz_profile_init_loading)) }
    )

    AppAlertDialog(
        showDialog = uiState.value.uiEnableSuccessDialog,
        dialogContext = SUCCESS,
        showIcon = true,
        showTitle = true,
        title = { Text(stringResource(id = str_success)) },
        showBody = true,
        body = { Text(stringResource(str_biz_profile_init_success)) },
        useConfirmButton = true,
        confirmButton = {
            TextButton(onClick = { coroutineScope.launch { stateApp.navController.navToInitial() } })
            { Text(stringResource(id = str_ok)) }
        }
    )

    AppAlertDialog(
        showDialog = uiState.value.uiEnableErrorDialog,
        dialogContext = ERROR,
        showIcon = true,
        showTitle = true,
        title = { Text(stringResource(id = str_error)) },
        showBody = true,
        body = { Text(stringResource(str_biz_profile_init_failed)) },
        useDismissButton = true,
        dismissButton = {
            TextButton(onClick = { coroutineScope.launch { stateApp.navController.navToInitial() } })
            { Text(stringResource(id = str_ok)) }
        }
    )

    ScreenContent(
        uiState = uiState,
        onChangeLanguage = { vm.onEvent(OnChangeLanguage(it)) },
        onInitBizProfileDefault = { vm.onEvent(BeginInitBizProfileDefault(it)) },
        onInitBizProfileManual = { vm.onEvent(BeginInitBizProfileManual) },
        onUiFormLegalNameChanged = { vm.onEvent(OnUiFormLegalNameChanged(it)) },
        onUiFormCommonNameChanged = { vm.onEvent(OnUiFormCommonNameChanged(it))},
        onUiFormSubmitInitManual = { vm.onEvent(OnUiFormSubmitInitManual(it)) },
        onUiFormCancelInitManual = { vm.onEvent(OnUiFormCancelInitManual) }
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
        Idle, Loading -> { LoadingScreen() }
        Empty -> {
            EmptyScreen(
                title = stringResource(id = str_empty_message_title),
                emptyMessage = stringResource(id = str_empty_message),
                showIcon = true
            )
        }
        is Error -> {
            ErrorScreen(
                title = stringResource(id = str_error),
                errorMessage = stringResource(id = str_error_fetching_preferences),
                showIcon = true
            )
        }
        is Success -> {
            val initializationData = (uiState.value.initializationData as Success<Initialization>).data
            val initUiPropertiesState = uiState.value.initializationUiFormState
            val uiStateInitialization = uiState.value

            SuccessSection(
                initializationData = initializationData,
                onChangeLanguage = onChangeLanguage,
                uiStateInitialization = uiStateInitialization,
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
    uiStateInitialization: InitializationUiState
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
        if(uiStateInitialization.uiEnableWelcomeMessage){
            WelcomeMessage(
                onInitBizProfileDefault = onInitBizProfileDefault,
                onInitBizProfileManual = onInitBizProfileManual
            )
        }
        if(uiStateInitialization.uiEnableInitManualForm){
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
                        textAlign = Center,
                        maxLines = 1,
                        overflow = Ellipsis
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
                        contentPadding = ItemContentPadding
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
            color = Transparent,
            shape = shapes.medium
        ) { Image(imageVector = ImageVector.vectorResource(id = app), contentDescription = null) }
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(id = app_name),
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
        modifier = Modifier.fillMaxWidth().padding(16.dp),
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
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                text = stringResource(str_init_welcome_message),
                style = typography.labelLarge,
                textAlign = Justify
            )
            AppIconButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = { onInitBizProfileManual.invoke() },
                icon = ImageVector.vectorResource(id = happy),
                text = stringResource(str_init_setup_yes)
            )
        }
    }
    TextButton(
        onClick = {
            onInitBizProfileDefault.invoke(
                BizProfileSummary(
                    seqId = 0,
                    genId = randomULID(),
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
            text = stringResource(str_init_setup_no),
            textAlign = Center,
            style = typography.titleMedium
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
        shape = shapes.medium,
        color = colorScheme.surfaceContainerHighest
    ){
        Column(
            modifier = Modifier.fillMaxWidth().padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically)
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = str_business_profile),
                textAlign = Center,
                style = typography.titleLarge,
                color = colorScheme.onSurface,
                maxLines = 1,
                overflow = Ellipsis
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = str_business_profile_desc),
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
                value = initializationUiFormState.uiFormLegalName,
                onValueChange = { onUiFormLegalNameChanged(it) },
                label = stringResource(str_company_legal_name),
                placeholder = stringResource(str_company_legal_name),
                singleLine = true,
                isError = initializationUiFormState.uiFormLegalNameError != null,
                errorMessage = initializationUiFormState.uiFormLegalNameError
            )
            TextInput(
                value = initializationUiFormState.uiFormCommonName,
                onValueChange = { onUiFormCommonNameChanged(it) },
                label = stringResource(str_company_common_name),
                placeholder = stringResource(str_company_common_name),
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
                                    genId = randomULID(),
                                    bizName = BizName(
                                        legalName = initializationUiFormState.uiFormLegalName,
                                        commonName = initializationUiFormState.uiFormCommonName,
                                    ),
                                    bizIndustry = null,
                                    auditTrail = AuditTrail()
                                )
                            )
                        },
                        icon = ImageVector.vectorResource(id = neutral),
                        text = stringResource(id = str_save)
                    )
                }
                AppIconButton(
                    modifier = Modifier.weight(if(initializationUiFormState.uiFormEnableSubmitBtn){0.5f}else{1.0f}),
                    onClick = onUiFormCancelInitManual,
                    icon = ImageVector.vectorResource(id = neutral),
                    text = stringResource(id = str_cancel)
                )
            }
        }
    }
}
package com.thomas200593.mini_retail_app.features.initial.ui.initialization

import android.content.res.Configuration
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
import androidx.compose.ui.tooling.preview.Preview
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
import com.thomas200593.mini_retail_app.core.ui.common.Themes
import com.thomas200593.mini_retail_app.core.ui.component.Button.Common.AppIconButton
import com.thomas200593.mini_retail_app.core.ui.component.CommonMessagePanel.EmptyPanel
import com.thomas200593.mini_retail_app.core.ui.component.CommonMessagePanel.EmptyScreen
import com.thomas200593.mini_retail_app.core.ui.component.CommonMessagePanel.ErrorPanel
import com.thomas200593.mini_retail_app.core.ui.component.CommonMessagePanel.ErrorScreen
import com.thomas200593.mini_retail_app.core.ui.component.CommonMessagePanel.LoadingPanelCircularIndicator
import com.thomas200593.mini_retail_app.core.ui.component.CommonMessagePanel.LoadingScreen
import com.thomas200593.mini_retail_app.features.app_config.entity.ConfigCurrent
import com.thomas200593.mini_retail_app.features.app_config.entity.Language
import com.thomas200593.mini_retail_app.features.business.entity.business_profile.BizName
import com.thomas200593.mini_retail_app.features.business.entity.business_profile.dto.BusinessProfileSummary
import com.thomas200593.mini_retail_app.features.initial.navigation.navigateToInitial
import kotlinx.coroutines.launch
import ulid.ULID

@Composable
fun InitializationScreen(
    viewModel: InitializationViewModel = hiltViewModel(),
    appState: AppState = LocalAppState.current
){
    val coroutineScope = rememberCoroutineScope()
    val showWelcomeMessage = viewModel.showWelcomeMessage
    val showInputManualForm = viewModel.showInputManualForm

    val configCurrent by viewModel.configCurrent.collectAsStateWithLifecycle()
    val languages by viewModel.languages
    val initialDefaultSetupUiState by viewModel.initialDefaultSetupUiState

    LaunchedEffect(Unit) { viewModel.onOpen() }

    ScreenContent(
        languages = languages,
        configCurrent = configCurrent,
        onSetLanguage = viewModel::setLanguage,
        onInitSetupManual = viewModel::initSetupManual,
        onInitSetupDefault = viewModel::initSetupDefault,
        showWelcomeMessage = showWelcomeMessage,
        showInputManualForm = showInputManualForm,
        initialDefaultSetupUiState = initialDefaultSetupUiState,
        onLoadingGenerateDefaultBizProfile = {  },
        onErrorGenerateDefaultBizProfile = {  },
        onSuccessGenerateDefaultBizProfile = {
            coroutineScope.launch {
                appState.navController.navigateToInitial()
            }
        }
    )
}

@Composable
private fun ScreenContent(
    languages: RequestState<Set<Language>>,
    configCurrent: RequestState<ConfigCurrent>,
    onSetLanguage: (Language) -> Unit,
    onInitSetupManual: () -> Unit,
    onInitSetupDefault: (BusinessProfileSummary) -> Unit,
    showWelcomeMessage: Boolean,
    showInputManualForm: Boolean,
    initialDefaultSetupUiState: RequestState<BusinessProfileSummary>,
    onLoadingGenerateDefaultBizProfile: () -> Unit,
    onErrorGenerateDefaultBizProfile: (Throwable?) -> Unit,
    onSuccessGenerateDefaultBizProfile: (BusinessProfileSummary) -> Unit
) {
    when(configCurrent){
        RequestState.Idle, RequestState.Loading -> { LoadingScreen() }
        is RequestState.Error -> {
            ErrorScreen(
                title = stringResource(id = R.string.str_error),
                errorMessage = stringResource(id = R.string.str_error_fetching_preferences),
                showIcon = true
            )
        }
        RequestState.Empty -> {
            EmptyScreen(
                title = stringResource(id = R.string.str_empty_message_title),
                emptyMessage = stringResource(id = R.string.str_empty_message),
                showIcon = true
            )
        }
        is RequestState.Success -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(30.dp, Alignment.Top)
            ) {
                when(languages){
                    RequestState.Idle, RequestState.Loading -> { LoadingPanelCircularIndicator() }
                    RequestState.Empty -> {
                        EmptyPanel(
                            showIcon = true,
                            title = stringResource(id = R.string.str_empty_message_title),
                            emptyMessage = stringResource(id = R.string.str_empty_message)
                        )
                    }
                    is RequestState.Error -> {
                        ErrorPanel(
                            showIcon = true,
                            title = stringResource(id = R.string.str_error),
                            errorMessage = stringResource(id = R.string.str_error_fetching_preferences)
                        )
                    }
                    is RequestState.Success -> {
                        LanguageChangeSection(
                            languages = languages.data,
                            configCurrent = configCurrent.data,
                            onSetLanguage = onSetLanguage
                        )
                    }
                }

                //Header
                if(showWelcomeMessage){
                    WelcomeHeader(
                        onInitSetupManual = onInitSetupManual,
                        onInitSetupDefault = onInitSetupDefault
                    )
                }

                //Manual Form
                if(showInputManualForm){
                    Text(text = "Manual Input Form")
                }

                //Default Input
                when(initialDefaultSetupUiState){
                    RequestState.Idle -> Unit
                    RequestState.Loading -> { onLoadingGenerateDefaultBizProfile() }
                    is RequestState.Error -> { onErrorGenerateDefaultBizProfile(initialDefaultSetupUiState.t) }
                    RequestState.Empty -> { onErrorGenerateDefaultBizProfile(Throwable("Could not generate default use case data. (Duplicate?)")) }
                    is RequestState.Success -> { onSuccessGenerateDefaultBizProfile(initialDefaultSetupUiState.data) }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LanguageChangeSection(
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
fun WelcomeHeader(
    onInitSetupManual: () -> Unit,
    onInitSetupDefault: (BusinessProfileSummary) -> Unit
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
        ) {
            Image(imageVector = ImageVector.vectorResource(id = Icons.App.app), contentDescription = null)
        }
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
                onClick = { onInitSetupManual.invoke() },
                icon = ImageVector.vectorResource(id = Icons.Emotion.happy),
                text = stringResource(R.string.str_init_setup_yes)
            )
        }
    }
    TextButton(
        onClick = {
            onInitSetupDefault.invoke(
                BusinessProfileSummary(
                    seqId = 0,
                    genId = ULID.randomULID(),
                    bizName = BizName(),
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
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES, locale = "in")
fun Preview(){
    Themes.ApplicationTheme {
        Column(modifier = Modifier.fillMaxSize()) {
            ScreenContent(
                languages = RequestState.Success(Language.entries.toSet()),
                configCurrent = RequestState.Success(ConfigCurrent()),
                onSetLanguage = {},
                onInitSetupManual = {},
                onInitSetupDefault = {},
                showWelcomeMessage = true,
                showInputManualForm = false,
                initialDefaultSetupUiState = RequestState.Idle,
                onErrorGenerateDefaultBizProfile = {},
                onLoadingGenerateDefaultBizProfile = {},
                onSuccessGenerateDefaultBizProfile = {}
            )
        }
    }
}
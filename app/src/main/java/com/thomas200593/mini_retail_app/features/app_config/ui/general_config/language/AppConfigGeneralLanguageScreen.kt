package com.thomas200593.mini_retail_app.features.app_config.ui.general_config.language

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.outlined.KeyboardArrowRight
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.thomas200593.mini_retail_app.R
import com.thomas200593.mini_retail_app.app.ui.AppState
import com.thomas200593.mini_retail_app.app.ui.LocalAppState
import com.thomas200593.mini_retail_app.core.design_system.util.RequestState
import com.thomas200593.mini_retail_app.core.ui.common.Icons.Language.language
import com.thomas200593.mini_retail_app.core.ui.component.AppBar
import com.thomas200593.mini_retail_app.core.ui.component.CommonMessagePanel.EmptyScreen
import com.thomas200593.mini_retail_app.core.ui.component.CommonMessagePanel.ErrorScreen
import com.thomas200593.mini_retail_app.core.ui.component.CommonMessagePanel.LoadingScreen
import com.thomas200593.mini_retail_app.core.ui.component.CommonMessagePanel.ThreeRowCardItem
import com.thomas200593.mini_retail_app.features.app_config.entity.ConfigCurrent
import com.thomas200593.mini_retail_app.features.app_config.entity.Language
import kotlinx.coroutines.Job
import timber.log.Timber
import kotlin.reflect.KFunction1

private const val TAG = "AppConfigGeneralLanguageScreen"

@Composable
fun AppConfigGeneralLanguageScreen(
    viewModel: AppConfigGeneralLanguageViewModel = hiltViewModel(),
    appState: AppState = LocalAppState.current
) {
    Timber.d("Called : fun $TAG()")
    val configCurrent by viewModel.configCurrentUiState.collectAsStateWithLifecycle()
    val languagePreferences by viewModel.languagePreferences

    LaunchedEffect(Unit) {
        viewModel.onOpen()
    }

    TopAppBar(
        onNavigateBack = appState::onNavigateUp
    )
    ScreenContent(
        languagePreferences = languagePreferences,
        configCurrent = configCurrent,
        onSaveSelectedLanguage = viewModel::saveSelectedLanguage
    )
}

@Composable
private fun TopAppBar(
    onNavigateBack: () -> Unit
) {
    AppBar.ProvideTopAppBarNavigationIcon {
        Surface(
            onClick =  onNavigateBack,
            modifier = Modifier
        ) {
            Icon(
                modifier = Modifier,
                imageVector = Icons.AutoMirrored.Default.KeyboardArrowLeft,
                contentDescription = null
            )
        }
    }
    AppBar.ProvideTopAppBarTitle {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ){
            Icon(
                modifier = Modifier
                    .sizeIn(maxHeight = ButtonDefaults.IconSize),
                imageVector = ImageVector.vectorResource(id = language),
                contentDescription = null
            )
            Text(
                text = stringResource(id = R.string.str_lang),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
    AppBar.ProvideTopAppBarAction {
        Row(
            modifier = Modifier.padding(end = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ){
            Icon(
                modifier = Modifier
                    .sizeIn(maxHeight = ButtonDefaults.IconSize),
                imageVector = Icons.Default.Info,
                contentDescription = null
            )
        }
    }
}

@Composable
private fun ScreenContent(
    languagePreferences: RequestState<Set<Language>>,
    configCurrent: RequestState<ConfigCurrent>,
    onSaveSelectedLanguage: KFunction1<Language, Job>
) {
    when(configCurrent){
        RequestState.Idle, RequestState.Loading -> {
            LoadingScreen()
        }
        is RequestState.Error -> {
            ErrorScreen(
                title = stringResource(id = R.string.str_error),
                errorMessage = "Failed to get Preferences data.",
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
            when(languagePreferences){
                RequestState.Idle -> Unit
                RequestState.Loading -> {
                    LoadingScreen()
                }
                is RequestState.Error -> {
                    ErrorScreen(
                        title = stringResource(id = R.string.str_error),
                        errorMessage = "Failed to get Preferences data.",
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
                    val currentLanguage = configCurrent.data.currentLanguage
                    val appLanguagePreferences = languagePreferences.data

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(4.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "${stringResource(id = R.string.str_lang)} : ${stringResource(id = currentLanguage.title)}",
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(4.dp),
                            fontWeight = FontWeight.Bold,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            textAlign = TextAlign.Center,
                        )
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(4.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            items(count = appLanguagePreferences.count()){ index ->
                                val data = appLanguagePreferences.elementAt(index)
                                ThreeRowCardItem(
                                    firstRowContent = {
                                        Surface(modifier = Modifier.fillMaxWidth()) {
                                            Image(
                                                modifier = Modifier.height(20.dp),
                                                imageVector = ImageVector.vectorResource(data.iconRes),
                                                contentDescription = null
                                            )
                                        }
                                    },
                                    secondRowContent = {
                                        Text(
                                            text = stringResource(id = data.title),
                                            modifier = Modifier.fillMaxWidth(),
                                            textAlign = TextAlign.Start,
                                            fontWeight = FontWeight.Bold,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                    },
                                    thirdRowContent = {
                                        Surface(
                                            modifier = Modifier.fillMaxWidth(),
                                            onClick = { onSaveSelectedLanguage(data) }
                                        ) {
                                            Icon(
                                                imageVector = if (data == currentLanguage) Icons.Default.CheckCircle else Icons.AutoMirrored.Outlined.KeyboardArrowRight,
                                                contentDescription = null,
                                                tint = if (data == currentLanguage) Color.Green else MaterialTheme.colorScheme.onTertiaryContainer
                                            )
                                        }
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
package com.thomas200593.mini_retail_app.features.app_conf.conf_gen_theme.ui

import android.content.pm.ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.outlined.KeyboardArrowRight
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
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
import com.thomas200593.mini_retail_app.app.navigation.ScrGraphs
import com.thomas200593.mini_retail_app.app.ui.LocalStateApp
import com.thomas200593.mini_retail_app.app.ui.StateApp
import com.thomas200593.mini_retail_app.core.data.local.session.SessionState
import com.thomas200593.mini_retail_app.core.ui.component.app_bar.CustomAppBar.ProvideTopAppBarAction
import com.thomas200593.mini_retail_app.core.ui.component.app_bar.CustomAppBar.ProvideTopAppBarNavigationIcon
import com.thomas200593.mini_retail_app.core.ui.component.app_bar.CustomAppBar.ProvideTopAppBarTitle
import com.thomas200593.mini_retail_app.core.ui.component.CustomButton.Common.AppIconButton
import com.thomas200593.mini_retail_app.core.ui.component.dialog.CustomDialog.AlertDialogContext
import com.thomas200593.mini_retail_app.core.ui.component.dialog.CustomDialog.AppAlertDialog
import com.thomas200593.mini_retail_app.core.ui.component.CustomPanel.ThreeRowCardItem
import com.thomas200593.mini_retail_app.core.ui.component.CustomScreenUtil.LockScreenOrientation
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_theme.entity.ConfigThemes
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_theme.entity.Theme
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_theme.ui.VMConfGenTheme.UiEvents.ButtonEvents.BtnNavBackEvents
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_theme.ui.VMConfGenTheme.UiEvents.ButtonEvents.BtnScrDescEvents
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_theme.ui.VMConfGenTheme.UiEvents.ButtonEvents.BtnSetPrefThemeEvents
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_theme.ui.VMConfGenTheme.UiEvents.DialogEvents.DlgDenySetDataEvents
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_theme.ui.VMConfGenTheme.UiEvents.OnOpenEvents
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_theme.ui.VMConfGenTheme.UiStateConfigTheme.Loading
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_theme.ui.VMConfGenTheme.UiStateConfigTheme.Success
import kotlinx.coroutines.launch

@Composable
fun ScrConfGenTheme(
    vm: VMConfGenTheme = hiltViewModel(),
    stateApp: StateApp = LocalStateApp.current
) {
    LockScreenOrientation(SCREEN_ORIENTATION_PORTRAIT)

    val coroutineScope = rememberCoroutineScope()
    val uiState by vm.uiState.collectAsStateWithLifecycle()
    val sessionState by stateApp.isSessionValid.collectAsStateWithLifecycle()
    val currentScreen = ScrGraphs.getByRoute(stateApp.destCurrent)

    LaunchedEffect(sessionState, currentScreen)
    { currentScreen?.let { vm.onEvent(OnOpenEvents(sessionState, it)) } }

    ScrConfGenTheme(
        uiState = uiState,
        currentScreen = currentScreen,
        onNavigateBack = {
            vm.onEvent(BtnNavBackEvents.OnClick)
                .also { coroutineScope.launch { stateApp.onNavUp() } }
        },
        onShowScrDesc = { vm.onEvent(BtnScrDescEvents.OnClick) },
        onDismissDlgScrDesc = { vm.onEvent(BtnScrDescEvents.OnDismiss) },
        onSetData = { theme ->
            currentScreen?.let {
                when(sessionState) {
                    SessionState.Loading -> Unit
                    is SessionState.Invalid ->
                        if(it.usesAuth) vm.onEvent(BtnSetPrefThemeEvents.OnDeny)
                        else vm.onEvent(BtnSetPrefThemeEvents.OnAllow(theme))
                    is SessionState.Valid ->
                        vm.onEvent(BtnSetPrefThemeEvents.OnAllow(theme))
                }
            }
        },
        onDismissDlgDenySetData = {
            vm.onEvent(DlgDenySetDataEvents.OnDismiss)
                .also { coroutineScope.launch { stateApp.onNavUp() } }
        }
    )
}

@Composable
private fun ScrConfGenTheme(
    uiState: VMConfGenTheme.UiState,
    currentScreen: ScrGraphs?,
    onNavigateBack: () -> Unit,
    onShowScrDesc: (String) -> Unit,
    onDismissDlgScrDesc: () -> Unit,
    onSetData: (Theme) -> Unit,
    onDismissDlgDenySetData: () -> Unit
) {
    currentScreen?.let {
        HandleDialogs(
            uiState = uiState,
            currentScreen = it,
            onDismissDlgScrDesc = onDismissDlgScrDesc,
            onDismissDlgDenySetData = onDismissDlgDenySetData
        )
        TopAppBar(
            scrGraphs = it,
            onShowScrDesc = onShowScrDesc,
            onNavigateBack = onNavigateBack
        )
    }
    when(uiState.configThemes) {
        Loading -> Unit
        is Success -> ScreenContent(
            configThemes = uiState.configThemes.configThemes,
            onSetData = onSetData
        )
    }
}

@Composable
private fun HandleDialogs(
    uiState: VMConfGenTheme.UiState,
    currentScreen: ScrGraphs,
    onDismissDlgScrDesc: () -> Unit,
    onDismissDlgDenySetData: () -> Unit
) {
    AppAlertDialog(
        showDialog = uiState.dialogState.dlgLoadingAuth,
        dialogContext = AlertDialogContext.INFORMATION,
        showTitle = true,
        title = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) { CircularProgressIndicator() }
        },
        showBody = true,
        body = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) { Text(text = stringResource(id = R.string.str_authenticating)) }
        }
    )
    AppAlertDialog(
        showDialog = uiState.dialogState.dlgLoadingGetData,
        dialogContext = AlertDialogContext.INFORMATION,
        showTitle = true,
        title = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) { CircularProgressIndicator() }
        },
        showBody = true,
        body = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) { Text(text = stringResource(id = R.string.str_loading_data)) }
        }
    )
    AppAlertDialog(
        showDialog = uiState.dialogState.dlgDenySetData,
        dialogContext = AlertDialogContext.ERROR,
        showTitle = true,
        title = { Text(text = stringResource(id = R.string.str_error)) },
        showBody = true,
        body = {
            Column(
                modifier = Modifier.fillMaxWidth().verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) { Text(text = stringResource(id = R.string.str_deny_access_auth_required)) }
        },
        useDismissButton = true,
        dismissButton = {
            AppIconButton(
                onClick = onDismissDlgDenySetData,
                icon = Icons.Default.Close,
                text = stringResource(id = R.string.str_close),
                containerColor = MaterialTheme.colorScheme.error,
                contentColor = MaterialTheme.colorScheme.onError
            )
        }
    )
    AppAlertDialog(
        showDialog = uiState.dialogState.dlgScrDesc,
        dialogContext = AlertDialogContext.INFORMATION,
        showIcon = true,
        showTitle = true,
        title = { currentScreen.title?.let { Text(text = stringResource(id = it)) } },
        showBody = true,
        body = {
            currentScreen.description?.let {
                Column(
                    modifier = Modifier.fillMaxWidth().verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) { Text(text = stringResource(id = it)) }
            }
        },
        useDismissButton = true,
        dismissButton = {
            AppIconButton(
                onClick = onDismissDlgScrDesc,
                icon = Icons.Default.Close,
                text = stringResource(id = R.string.str_close)
            )
        }
    )
}

@Composable
private fun TopAppBar(
    scrGraphs: ScrGraphs,
    onShowScrDesc: (String) -> Unit,
    onNavigateBack: () -> Unit
) {
    ProvideTopAppBarNavigationIcon {
        Surface(
            onClick = onNavigateBack,
            modifier = Modifier
        ) {
            Icon(
                modifier = Modifier,
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                contentDescription = null
            )
        }
    }
    ProvideTopAppBarTitle {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            scrGraphs.iconRes?.let {
                Icon(
                    modifier = Modifier.sizeIn(maxHeight = ButtonDefaults.IconSize),
                    imageVector = ImageVector.vectorResource(id = it),
                    contentDescription = null
                )
            }
            scrGraphs.title?.let {
                Text(
                    text = stringResource(id = it),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
    scrGraphs.description?.let {
        ProvideTopAppBarAction {
            Row(
                modifier = Modifier,
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                val desc = stringResource(id = it)
                Surface(
                    onClick = { onShowScrDesc(desc) },
                    modifier = Modifier.sizeIn(maxHeight = ButtonDefaults.IconSize),
                ) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = null
                    )
                }
            }
        }
    }
}

@Composable
private fun ScreenContent(
    configThemes: ConfigThemes,
    onSetData: (Theme) -> Unit
) {
    val currentData = configThemes.configCurrent.theme
    val preferencesList = configThemes.themes
    Column(
        modifier = Modifier.fillMaxSize().padding(4.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "${stringResource(id = R.string.str_theme)} : ${stringResource(id = currentData.title)}",
            modifier = Modifier.fillMaxWidth().padding(4.dp),
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center,
        )
        LazyColumn (
            modifier = Modifier.fillMaxSize().padding(4.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(count = preferencesList.count()){
                val data = preferencesList.elementAt(it)
                ThreeRowCardItem(
                    firstRowContent = {
                        Surface(modifier = Modifier.fillMaxWidth()) {
                            Icon(
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
                        Text(
                            text = stringResource(id = data.description),
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Start,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    },
                    thirdRowContent = {
                        Surface(
                            modifier = Modifier.fillMaxWidth(),
                            onClick = { onSetData(data) }
                        ) {
                            Icon(
                                imageVector =
                                    if (data == currentData) Icons.Default.CheckCircle
                                    else Icons.AutoMirrored.Outlined.KeyboardArrowRight,
                                contentDescription = null,
                                tint =
                                    if (data == currentData) Color.Green
                                    else MaterialTheme.colorScheme.onTertiaryContainer
                            )
                        }
                    }
                )
            }
        }
    }
}
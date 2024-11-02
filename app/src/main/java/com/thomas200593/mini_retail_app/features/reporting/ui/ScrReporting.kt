package com.thomas200593.mini_retail_app.features.reporting.ui

import android.content.pm.ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.thomas200593.mini_retail_app.R
import com.thomas200593.mini_retail_app.app.navigation.ScrGraphs
import com.thomas200593.mini_retail_app.app.ui.LocalStateApp
import com.thomas200593.mini_retail_app.app.ui.StateApp
import com.thomas200593.mini_retail_app.core.ui.common.CustomThemes
import com.thomas200593.mini_retail_app.core.ui.component.app_bar.CustomAppBar.ProvideTopAppBarAction
import com.thomas200593.mini_retail_app.core.ui.component.app_bar.CustomAppBar.ProvideTopAppBarTitle
import com.thomas200593.mini_retail_app.core.ui.component.CustomButton.Common.AppIconButton
import com.thomas200593.mini_retail_app.core.ui.component.dialog.CustomDialog.AlertDialogContext
import com.thomas200593.mini_retail_app.core.ui.component.dialog.CustomDialog.AppAlertDialog
import com.thomas200593.mini_retail_app.core.ui.component.CustomPanel.LoadingScreen
import com.thomas200593.mini_retail_app.core.ui.component.CustomScreenUtil.LockScreenOrientation
import com.thomas200593.mini_retail_app.features.reporting.ui.VMReporting.UiEvents.ButtonEvents.BtnScrDescEvents
import com.thomas200593.mini_retail_app.features.reporting.ui.VMReporting.UiEvents.OnOpenEvents
import com.thomas200593.mini_retail_app.features.reporting.ui.VMReporting.UiState
import com.thomas200593.mini_retail_app.features.reporting.ui.VMReporting.UiStateReporting.Idle
import com.thomas200593.mini_retail_app.features.reporting.ui.VMReporting.UiStateReporting.Loading
import com.thomas200593.mini_retail_app.features.reporting.ui.VMReporting.UiStateReporting.Success

@Composable
fun ScrReporting(
    vm: VMReporting = hiltViewModel(),
    stateApp: StateApp = LocalStateApp.current
) {
    LockScreenOrientation(SCREEN_ORIENTATION_PORTRAIT)

    val uiState by vm.uiState.collectAsStateWithLifecycle()
    val sessionState by stateApp.isSessionValid.collectAsStateWithLifecycle()
    val currentScreen = ScrGraphs.getByRoute(stateApp.destCurrent)

    LaunchedEffect(sessionState) { vm.onEvent(OnOpenEvents(sessionState)) }

    ScrReporting(
        uiState = uiState,
        currentScreen = currentScreen,
        onShowScrDesc = { vm.onEvent(BtnScrDescEvents.OnClick) },
        onDismissDlgScrDesc = { vm.onEvent(BtnScrDescEvents.OnDismiss) },
        onDismissDlgSessionInvalid = { /*TODO*/ }
    )
}

@Composable
private fun ScrReporting(
    uiState: UiState,
    currentScreen: ScrGraphs?,
    onShowScrDesc: (String) -> Unit,
    onDismissDlgScrDesc: () -> Unit,
    onDismissDlgSessionInvalid: () -> Unit
) {
    currentScreen?.let {
        HandleDialogs(
            uiState = uiState,
            currentScreen = it,
            onDismissDlgScrDesc = onDismissDlgScrDesc,
            onDismissDlgSessionInvalid = onDismissDlgSessionInvalid
        )
        TopAppBar(
            scrGraphs = it,
            onShowScrDesc = onShowScrDesc
        )
    }
    when(uiState.uiStateReporting) {
        Idle -> Unit
        Loading -> LoadingScreen()
        is Success -> ScreenContent(
            data = Success(uiState.uiStateReporting.data)
        )
    }
}

@Composable
private fun HandleDialogs(
    uiState: UiState,
    currentScreen: ScrGraphs,
    onDismissDlgScrDesc: () -> Unit,
    onDismissDlgSessionInvalid: () -> Unit
) {
    AppAlertDialog(
        showDialog = uiState.dialogState.dlgSessionInvalid,
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
                onClick = onDismissDlgSessionInvalid,
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
    onShowScrDesc: (String) -> Unit
) {
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
private fun ScreenContent(data: Success) {
    Surface {
        Column(
            modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Data: ${data.data}")
        }
    }
}

@Composable
@Preview
private fun Preview() = CustomThemes.ApplicationTheme {
    ScrReporting(
        currentScreen = ScrGraphs.Reporting,
        onShowScrDesc = {},
        onDismissDlgScrDesc = {},
        onDismissDlgSessionInvalid = {},
        uiState = UiState()
    )
}
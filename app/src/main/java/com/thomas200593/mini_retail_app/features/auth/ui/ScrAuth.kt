package com.thomas200593.mini_retail_app.features.auth.ui

import android.app.Activity
import android.content.pm.ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.thomas200593.mini_retail_app.BuildConfig.BUILD_TYPE
import com.thomas200593.mini_retail_app.BuildConfig.VERSION_NAME
import com.thomas200593.mini_retail_app.R
import com.thomas200593.mini_retail_app.app.ui.LocalStateApp
import com.thomas200593.mini_retail_app.app.ui.StateApp
import com.thomas200593.mini_retail_app.core.ui.common.CustomIcons
import com.thomas200593.mini_retail_app.core.ui.component.CustomButton.Common.AppIconButton
import com.thomas200593.mini_retail_app.core.ui.component.CustomButton.Google.SignInWithGoogle
import com.thomas200593.mini_retail_app.core.ui.component.CustomButton.Google.handleClearCredential
import com.thomas200593.mini_retail_app.core.ui.component.CustomButton.Google.handleSignIn
import com.thomas200593.mini_retail_app.core.ui.component.CustomDialog
import com.thomas200593.mini_retail_app.core.ui.component.CustomDialog.AppAlertDialog
import com.thomas200593.mini_retail_app.core.ui.component.CustomScreenUtil.LockScreenOrientation
import com.thomas200593.mini_retail_app.features.app_conf.app_config.navigation.navToAppConfig
import com.thomas200593.mini_retail_app.features.auth.ui.VMAuth.AuthValidationResult.Error
import com.thomas200593.mini_retail_app.features.auth.ui.VMAuth.UiEvents.ButtonEvents.BtnAppConfigEvents
import com.thomas200593.mini_retail_app.features.auth.ui.VMAuth.UiEvents.ButtonEvents.BtnAuthGoogleEvents
import com.thomas200593.mini_retail_app.features.auth.ui.VMAuth.UiEvents.ButtonEvents.BtnTncEvents
import com.thomas200593.mini_retail_app.features.auth.ui.VMAuth.UiEvents.DialogEvents.DlgAuthFailedEvent
import com.thomas200593.mini_retail_app.features.auth.ui.VMAuth.UiEvents.DialogEvents.DlgAuthSuccessEvent
import com.thomas200593.mini_retail_app.features.auth.ui.VMAuth.UiEvents.OnOpenEvents
import com.thomas200593.mini_retail_app.features.auth.ui.VMAuth.UiState
import com.thomas200593.mini_retail_app.features.initial.initial.navigation.navToInitial
import com.thomas200593.mini_retail_app.work.workers.session_monitor.manager.ManagerWorkSessionMonitor
import kotlinx.coroutines.launch

@Composable
fun ScrAuth(
    vm: VMAuth = hiltViewModel(),
    stateApp: StateApp = LocalStateApp.current
) {
    LockScreenOrientation(SCREEN_ORIENTATION_PORTRAIT)

    val activityContext = (LocalContext.current as Activity)
    val appContext = activityContext.applicationContext
    val coroutineScope = rememberCoroutineScope()
    val uiState by vm.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        handleClearCredential(
            activityContext = activityContext,
            onClearSuccess = { vm.onEvent(OnOpenEvents) },
            onClearError = { vm.onEvent(OnOpenEvents) }
        )
    }

    ScrAuth(
        uiState = uiState,
        onNavToAppConfig = {
            vm.onEvent(BtnAppConfigEvents.OnClick)
                .also { coroutineScope.launch { stateApp.navController.navToAppConfig() } }
        },
        onAuthGoogle = {
            vm.onEvent(BtnAuthGoogleEvents.OnClick)
                .also {
                    coroutineScope.launch {
                        handleSignIn(
                            activityContext = activityContext,
                            onResultReceived = { vm.onEvent(BtnAuthGoogleEvents.OnResultReceived(it)) },
                            onError = { vm.onEvent(BtnAuthGoogleEvents.OnResultError(it)) },
                            onDialogDismissed = { vm.onEvent(BtnAuthGoogleEvents.OnDismissed(it)) }
                        )
                    }
                }
        },
        onNavToTnc = { vm.onEvent(BtnTncEvents.OnClick) },
        dlgAuthSuccessOnConfirm = {
            vm.onEvent(DlgAuthSuccessEvent.OnConfirm)
                .also {
                    coroutineScope.launch {
                        ManagerWorkSessionMonitor.initialize(appContext)
                        stateApp.navController.navToInitial()
                    }
                }
        },
        dlgAuthFailedOnDismiss = { vm.onEvent(DlgAuthFailedEvent.OnDismissed) }
    )
}

@Composable
private fun ScrAuth(
    uiState: UiState,
    onNavToAppConfig: () -> Unit,
    onAuthGoogle: () -> Unit,
    onNavToTnc: () -> Unit,
    dlgAuthSuccessOnConfirm: () -> Unit,
    dlgAuthFailedOnDismiss: () -> Unit
) {
    HandleDialogs(
        uiState = uiState,
        dlgAuthSuccessOnConfirm = dlgAuthSuccessOnConfirm,
        dlgAuthFailedOnDismiss = dlgAuthFailedOnDismiss
    )
    ScreenContent(
        uiState = uiState,
        onNavToAppConfig = onNavToAppConfig,
        onAuthGoogle = onAuthGoogle,
        onNavToTnc = onNavToTnc
    )
}

@Composable
private fun HandleDialogs(
    uiState: UiState,
    dlgAuthSuccessOnConfirm: () -> Unit,
    dlgAuthFailedOnDismiss: () -> Unit
) {
    AppAlertDialog(
        showDialog = uiState.dialogState.dlgAuthLoading,
        dialogContext = CustomDialog.AlertDialogContext.INFORMATION,
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
            ) { Text(text = stringResource(id = R.string.str_loading)) }
        }
    )
    AppAlertDialog(
        showDialog = uiState.dialogState.dlgAuthSuccess,
        dialogContext = CustomDialog.AlertDialogContext.SUCCESS,
        showIcon = true,
        showTitle = true,
        title = { Text(text = stringResource(R.string.str_auth_success)) },
        useConfirmButton = true,
        confirmButton = {
            AppIconButton(
                onClick = dlgAuthSuccessOnConfirm,
                icon = Icons.Default.Check,
                text = stringResource(id = R.string.str_ok)
            )
        }
    )
    AppAlertDialog(
        showDialog = uiState.dialogState.dlgAuthError,
        dialogContext = CustomDialog.AlertDialogContext.ERROR,
        showIcon = true,
        showTitle = true,
        title = { Text(text = stringResource(id = R.string.str_error)) },
        showBody = true,
        body = {
            Column(
                modifier = Modifier.fillMaxWidth().verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                when(uiState.authValidationResult) {
                    is Error -> Text(text = uiState.authValidationResult.throwable.toString())
                    else -> Unit
                }
            }
        },
        useDismissButton = true,
        dismissButton = {
            AppIconButton(
                onClick = dlgAuthFailedOnDismiss,
                icon = Icons.Default.Close,
                text = stringResource(id = R.string.str_close),
                containerColor = MaterialTheme.colorScheme.error,
                contentColor = MaterialTheme.colorScheme.onError
            )
        }
    )
}

@Composable
private fun ScreenContent(
    uiState: UiState,
    onNavToAppConfig: () -> Unit,
    onAuthGoogle: () -> Unit,
    onNavToTnc: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(48.dp, Alignment.Top)
    ) {
        AppConfigSection(onNavToAppConfig = onNavToAppConfig)
        AppTitleSection()
        WelcomeMessageSection()
        AuthWithGoogleSection(uiState = uiState, onAuthGoogle = onAuthGoogle)
        TncSection(onNavToTnc = onNavToTnc)
    }
}

@Composable
private fun AppConfigSection(onNavToAppConfig: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(0.5f),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            AppIconButton(
                containerColor = Color.Transparent,
                contentColor = MaterialTheme.colorScheme.onSurface,
                icon = ImageVector.vectorResource(id = CustomIcons.Setting.settings),
                text = stringResource(id = R.string.str_configuration),
                onClick = onNavToAppConfig
            )
        }
    }
}

@Composable
private fun AppTitleSection() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Image(
            imageVector = ImageVector.vectorResource(id = CustomIcons.App.app),
            contentDescription = stringResource(id = R.string.app_name),
            alignment = Alignment.Center,
            modifier = Modifier.fillMaxWidth().height(150.dp)
        )
        Text(
            text = stringResource(id = R.string.app_name),
            textAlign = TextAlign.Center,
            fontSize = MaterialTheme.typography.headlineLarge.fontSize,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = "$VERSION_NAME - $BUILD_TYPE",
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun WelcomeMessageSection() {
    Surface(
        modifier = Modifier.fillMaxWidth(0.9f),
        shape = MaterialTheme.shapes.small,
        color = MaterialTheme.colorScheme.secondaryContainer,
        shadowElevation = 5.dp
    ) {
        Text(
            text = stringResource(R.string.str_auth_welcome_message),
            color = MaterialTheme.colorScheme.onSecondaryContainer,
            modifier = Modifier.padding(12.dp),
        )
    }
}

@Composable
private fun AuthWithGoogleSection(uiState: UiState, onAuthGoogle: () -> Unit) {
    Surface(
        modifier = Modifier.fillMaxWidth(0.9f),
        shape = MaterialTheme.shapes.medium,
    ) {
        SignInWithGoogle(
            onClick = onAuthGoogle,
            btnLoadingState = uiState.btnGoogleUiState.loading
        )
    }
}

@Composable
private fun TncSection(onNavToTnc: () -> Unit) {
    Surface(onClick = onNavToTnc, color = Color.Transparent) {
        Text(
            text = stringResource(id = R.string.str_tnc),
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onTertiaryContainer,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(12.dp)
        )
    }
}
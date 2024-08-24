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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import com.thomas200593.mini_retail_app.R.string
import com.thomas200593.mini_retail_app.app.ui.LocalStateApp
import com.thomas200593.mini_retail_app.app.ui.StateApp
import com.thomas200593.mini_retail_app.core.ui.common.CustomIcons.App
import com.thomas200593.mini_retail_app.core.ui.common.CustomIcons.Setting
import com.thomas200593.mini_retail_app.core.ui.component.CustomButton.Common.AppIconButton
import com.thomas200593.mini_retail_app.core.ui.component.CustomButton.Google
import com.thomas200593.mini_retail_app.core.ui.component.CustomButton.Google.SignInWithGoogle
import com.thomas200593.mini_retail_app.core.ui.component.CustomButton.Google.handleClearCredential
import com.thomas200593.mini_retail_app.core.ui.component.CustomDialog.AlertDialogContext
import com.thomas200593.mini_retail_app.core.ui.component.CustomDialog.AppAlertDialog
import com.thomas200593.mini_retail_app.core.ui.component.CustomScreenUtil.LockScreenOrientation
import com.thomas200593.mini_retail_app.features.app_conf.app_config.navigation.navToAppConfig
import com.thomas200593.mini_retail_app.features.auth.ui.VMAuth.UiEvents.BtnAuthWithGoogle
import com.thomas200593.mini_retail_app.features.auth.ui.VMAuth.UiEvents.ScreenEvents
import com.thomas200593.mini_retail_app.features.auth.ui.VMAuth.UiState
import com.thomas200593.mini_retail_app.features.initial.initial.navigation.navToInitial
import com.thomas200593.mini_retail_app.work.workers.session_monitor.manager.ManagerWorkSessionMonitor.initialize
import kotlinx.coroutines.launch

@Composable
fun ScrAuth(
    vm: VMAuth = hiltViewModel(),
    stateApp: StateApp = LocalStateApp.current
) {
    LockScreenOrientation(orientation = SCREEN_ORIENTATION_PORTRAIT)

    val coroutineScope = rememberCoroutineScope()
    val activityContext = (LocalContext.current as Activity)
    val appContext = LocalContext.current.applicationContext
    val uiState by vm.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        handleClearCredential(
            activityContext = activityContext,
            onClearSuccess = { vm.onEvent(ScreenEvents.OnOpen) },
            onClearError = { vm.onEvent(ScreenEvents.OnOpen) }
        )
    }

    ScreenContent(
        uiState = uiState,
        onNavToConfig = {
            vm.onEvent(ScreenEvents.OnOpen)
            coroutineScope.launch { stateApp.navController.navToAppConfig(destAppConfig = null) }
        },
        onAuthWithGoogle = {
            coroutineScope.launch {
                vm.onEvent(BtnAuthWithGoogle.OnClick)
                Google.handleSignIn(
                    activityContext = activityContext,
                    onResultReceived = { vm.onEvent(BtnAuthWithGoogle.OnValidationAuthSession(it)) },
                    onError = {
                        coroutineScope.launch {
                            handleClearCredential(
                                activityContext = activityContext,
                                onClearSuccess = { vm.onEvent(ScreenEvents.OnOpen) },
                                onClearError = { vm.onEvent(ScreenEvents.OnOpen) }
                            )
                        }
                    },
                    onDialogDismissed = {
                        coroutineScope.launch {
                            handleClearCredential(
                                activityContext = activityContext,
                                onClearSuccess = { vm.onEvent(ScreenEvents.OnOpen) },
                                onClearError = { vm.onEvent(ScreenEvents.OnOpen) }
                            )
                        }
                    }
                )
            }
        }
    )
    AppAlertDialog(
        showDialog = uiState.dialogState.uiEnableLoadingDialog,
        dialogContext = AlertDialogContext.INFORMATION,
        showIcon = true,
        showTitle = true,
        title = { Text(stringResource(id = string.str_loading)) },
        showBody = true,
        body = { Text(stringResource(string.str_auth)) }
    )
    AppAlertDialog(
        showDialog = uiState.dialogState.uiEnableSuccessDialog,
        dialogContext = AlertDialogContext.SUCCESS,
        showIcon = true,
        showTitle = true,
        title = { Text(stringResource(id = string.str_success)) },
        showBody = true,
        body = { Text(stringResource(string.str_success)) },
        useConfirmButton = true,
        confirmButton = {
            TextButton(onClick = {
                vm.onEvent(BtnAuthWithGoogle.OnDismissSuccessDialog).also {
                    coroutineScope.launch { initialize(appContext); stateApp.navController.navToInitial() }
                }
            })
            { Text(stringResource(id = string.str_ok)) }
        }
    )
    AppAlertDialog(
        showDialog = uiState.dialogState.uiEnableErrorDialog,
        dialogContext = AlertDialogContext.ERROR,
        showIcon = true,
        showTitle = true,
        title = { Text(stringResource(id = string.str_error)) },
        showBody = true,
        body = { Text(stringResource(string.str_error)) },
        useDismissButton = true,
        dismissButton = {
            TextButton(onClick = { coroutineScope.launch { stateApp.navController.navToInitial() } })
            { Text(stringResource(id = string.str_ok)) }
        }
    )
}

@Composable
private fun ScreenContent(
    uiState: UiState,
    onNavToConfig: () -> Unit,
    onAuthWithGoogle: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(48.dp, Alignment.Top)
    ) {
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
                    icon = ImageVector.vectorResource(id = Setting.settings),
                    text = stringResource(id = string.str_configuration),
                    onClick = { onNavToConfig() }
                )
            }
        }

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Image(
                imageVector = ImageVector.vectorResource(id = App.app),
                contentDescription = stringResource(id = string.app_name),
                alignment = Alignment.Center,
                modifier = Modifier.height(150.dp)
            )
            Text(
                text = stringResource(id = string.app_name),
                fontSize = MaterialTheme.typography.headlineLarge.fontSize,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = "$VERSION_NAME - $BUILD_TYPE",
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.primary
            )
        }

        Surface(
            modifier = Modifier.fillMaxWidth(0.9f),
            shape = MaterialTheme.shapes.small,
            color = MaterialTheme.colorScheme.secondaryContainer,
            shadowElevation = 5.dp
        ) {
            Text(
                text = stringResource(string.str_auth_welcome_message),
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                modifier = Modifier.padding(12.dp),
            )
        }

        Surface(
            modifier = Modifier.fillMaxWidth(0.9f),
            shape = MaterialTheme.shapes.medium,
        ) {
            SignInWithGoogle(
                onClick = { onAuthWithGoogle.invoke() },
                btnLoadingState = uiState.authBtnGoogleState.uiInProgress
            )
        }

        Text(
            text = stringResource(id = string.str_tnc),
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onTertiaryContainer,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(12.dp)
        )
    }
}
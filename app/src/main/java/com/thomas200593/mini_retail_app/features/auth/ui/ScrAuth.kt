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
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.font.FontWeight.Companion.SemiBold
import androidx.compose.ui.text.style.TextAlign.Companion.Center
import androidx.compose.ui.text.style.TextOverflow.Companion.Ellipsis
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.thomas200593.mini_retail_app.BuildConfig.BUILD_TYPE
import com.thomas200593.mini_retail_app.BuildConfig.VERSION_NAME
import com.thomas200593.mini_retail_app.R
import com.thomas200593.mini_retail_app.R.string.str_auth
import com.thomas200593.mini_retail_app.R.string.str_auth_welcome_message
import com.thomas200593.mini_retail_app.R.string.str_configuration
import com.thomas200593.mini_retail_app.R.string.str_error
import com.thomas200593.mini_retail_app.R.string.str_loading
import com.thomas200593.mini_retail_app.R.string.str_ok
import com.thomas200593.mini_retail_app.R.string.str_success
import com.thomas200593.mini_retail_app.app.ui.LocalStateApp
import com.thomas200593.mini_retail_app.app.ui.StateApp
import com.thomas200593.mini_retail_app.core.ui.common.CustomIcons.App.app
import com.thomas200593.mini_retail_app.core.ui.common.CustomIcons.Setting.settings
import com.thomas200593.mini_retail_app.core.ui.component.CustomButton.Common.AppIconButton
import com.thomas200593.mini_retail_app.core.ui.component.CustomButton.Google
import com.thomas200593.mini_retail_app.core.ui.component.CustomButton.Google.SignInWithGoogle
import com.thomas200593.mini_retail_app.core.ui.component.CustomButton.Google.handleClearCredential
import com.thomas200593.mini_retail_app.core.ui.component.CustomDialog.AlertDialogContext.ERROR
import com.thomas200593.mini_retail_app.core.ui.component.CustomDialog.AlertDialogContext.INFORMATION
import com.thomas200593.mini_retail_app.core.ui.component.CustomDialog.AlertDialogContext.SUCCESS
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
){
    LockScreenOrientation(orientation = SCREEN_ORIENTATION_PORTRAIT)

    val coroutineScope = rememberCoroutineScope()
    val activityContext = (LocalContext.current as Activity)
    val appContext = LocalContext.current.applicationContext
    val uiState by vm.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) { handleClearCredential(
        activityContext = activityContext,
        onClearSuccess = { vm.onEvent(ScreenEvents.OnOpen) },
        onClearError = { vm.onEvent(ScreenEvents.OnOpen) }
    ) }

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
                    onError = { coroutineScope.launch {
                        handleClearCredential(
                            activityContext = activityContext,
                            onClearSuccess = { vm.onEvent(ScreenEvents.OnOpen) },
                            onClearError = { vm.onEvent(ScreenEvents.OnOpen) }
                        )
                    } },
                    onDialogDismissed = { coroutineScope.launch {
                        handleClearCredential(
                            activityContext = activityContext,
                            onClearSuccess = { vm.onEvent(ScreenEvents.OnOpen) },
                            onClearError = { vm.onEvent(ScreenEvents.OnOpen) }
                        )
                    } }
                )
            }
        }
    )
    AppAlertDialog(
        showDialog = uiState.dialogState.uiEnableLoadingDialog,
        dialogContext = INFORMATION,
        showIcon = true,
        showTitle = true,
        title = { Text(stringResource(id = str_loading)) },
        showBody = true,
        body = { Text(stringResource(str_auth)) }
    )
    AppAlertDialog(
        showDialog = uiState.dialogState.uiEnableSuccessDialog,
        dialogContext = SUCCESS,
        showIcon = true,
        showTitle = true,
        title = { Text(stringResource(id = str_success)) },
        showBody = true,
        body = { Text(stringResource(str_success)) },
        useConfirmButton = true,
        confirmButton = {
            TextButton(onClick = {
                vm.onEvent(BtnAuthWithGoogle.OnDismissSuccessDialog).also {
                    coroutineScope.launch { initialize(appContext); stateApp.navController.navToInitial() }
                }
            })
            { Text(stringResource(id = str_ok)) }
        }
    )
    AppAlertDialog(
        showDialog = uiState.dialogState.uiEnableErrorDialog,
        dialogContext = ERROR,
        showIcon = true,
        showTitle = true,
        title = { Text(stringResource(id = str_error)) },
        showBody = true,
        body = { Text(stringResource(str_error)) },
        useDismissButton = true,
        dismissButton = {
            TextButton(onClick = { coroutineScope.launch { stateApp.navController.navToInitial() } })
            { Text(stringResource(id = str_ok)) }
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
        modifier = Modifier.fillMaxSize().padding(8.dp),
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
                    containerColor = Transparent,
                    contentColor = colorScheme.onSurface,
                    icon = ImageVector.vectorResource(id = settings),
                    text = stringResource(id = str_configuration),
                    onClick = { onNavToConfig.invoke() }
                )
            }
        }

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Image(
                imageVector = ImageVector.vectorResource(id = app),
                contentDescription = stringResource(id = R.string.app_name),
                alignment = Alignment.Center,
                modifier = Modifier.height(150.dp)
            )
            Text(
                text = stringResource(id = R.string.app_name),
                fontSize = typography.headlineLarge.fontSize,
                fontWeight = Bold,
                color = colorScheme.onSurface
            )
            Text(
                text = "$VERSION_NAME - $BUILD_TYPE",
                fontWeight = SemiBold,
                color = colorScheme.primary
            )
        }

        Surface(
            modifier = Modifier.fillMaxWidth(0.9f),
            shape = shapes.small,
            color = colorScheme.secondaryContainer,
            shadowElevation = 5.dp
        ) {
            Text(
                text = stringResource(str_auth_welcome_message),
                color = colorScheme.onSecondaryContainer,
                modifier = Modifier.padding(12.dp),
            )
        }

        Surface(
            modifier = Modifier.fillMaxWidth(0.9f),
            shape = shapes.medium,
        ) {
            SignInWithGoogle(
                onClick = { onAuthWithGoogle.invoke() },
                btnLoadingState = uiState.authBtnGoogleState.uiInProgress
            )
        }

        Text(
            text = stringResource(id = R.string.str_tnc),
            fontWeight = SemiBold,
            color = colorScheme.onTertiaryContainer,
            maxLines = 1,
            overflow = Ellipsis,
            textAlign = Center,
            modifier = Modifier.padding(12.dp)
        )
    }
}
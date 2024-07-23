package com.thomas200593.mini_retail_app.features.auth.ui

import android.app.Activity
import android.content.pm.ActivityInfo
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.thomas200593.mini_retail_app.BuildConfig
import com.thomas200593.mini_retail_app.R
import com.thomas200593.mini_retail_app.app.ui.StateApp
import com.thomas200593.mini_retail_app.app.ui.LocalStateApp
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState
import com.thomas200593.mini_retail_app.core.ui.common.CustomIcons
import com.thomas200593.mini_retail_app.core.ui.common.CustomIcons.Setting.settings
import com.thomas200593.mini_retail_app.core.ui.component.CustomButton
import com.thomas200593.mini_retail_app.core.ui.component.CustomButton.Google.SignInWithGoogle
import com.thomas200593.mini_retail_app.core.ui.component.CustomButton.Google.handleClearCredential
import com.thomas200593.mini_retail_app.core.ui.component.CustomScreenUtil
import com.thomas200593.mini_retail_app.features.app_conf.app_config.navigation.navToAppConfig
import com.thomas200593.mini_retail_app.features.initial.initial.navigation.navToInitial
import com.thomas200593.mini_retail_app.work.workers.session_monitor.manager.ManagerWorkSessionMonitor
import kotlinx.coroutines.launch

@Composable
fun AuthScreen(
    viewModel: VMAuth = hiltViewModel(),
    stateApp: StateApp = LocalStateApp.current
){
    CustomScreenUtil.LockScreenOrientation(orientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
    val activityContext = (LocalContext.current as Activity)
    val appContext = LocalContext.current.applicationContext
    val coroutineScope = rememberCoroutineScope()
    val stateSIWGButton by viewModel.stateSIWGButton.collectAsStateWithLifecycle()
    val authSessionTokenState by viewModel.authSessionTokenState

    LaunchedEffect(Unit) {
        handleClearCredential(
            activityContext,
            onClearSuccess = { viewModel.onOpen() },
            onClearError = { viewModel.onOpen() }
        )
    }

    LaunchedEffect(authSessionTokenState) {
        when(authSessionTokenState){
            is ResourceState.Success -> {
                ManagerWorkSessionMonitor.initialize(appContext)
                stateApp.navController.navToInitial()
            }
            is ResourceState.Error -> {
                handleClearCredential(
                    activityContext = activityContext,
                    onClearSuccess = { viewModel.clearAuthSessionToken() },
                    onClearError = { viewModel.clearAuthSessionToken() }
                )
            }
            else -> Unit
        }
    }

    //TODO revert to Column Row
    ScreenContent(
        onNavigateToAppConfigScreen = {
            stateApp.navController.navToAppConfig(destAppConfig = null)
        },
        onSignInWithGoogleButton = {
            coroutineScope.launch {
                viewModel.updateAuthSIWGButtonState(true)
                CustomButton.Google.handleSignIn(
                    activityContext = activityContext,
                    coroutineScope = coroutineScope,
                    onResultReceived = { viewModel.verifyAndSaveAuthSession(it) },
                    onError = {
                        coroutineScope.launch {
                            handleClearCredential(
                                activityContext = activityContext,
                                onClearSuccess = { viewModel.clearAuthSessionToken() },
                                onClearError = { viewModel.clearAuthSessionToken() }
                            )
                        }
                    },
                    onDialogDismissed = {
                        coroutineScope.launch {
                            handleClearCredential(
                                activityContext = activityContext,
                                onClearSuccess = { viewModel.clearAuthSessionToken() },
                                onClearError = { viewModel.clearAuthSessionToken() }
                            )
                        }
                    }
                )
            }
        },
        stateSIWGButton = stateSIWGButton
    )
}

@Composable
private fun ScreenContent(
    modifier: Modifier = Modifier,
    onNavigateToAppConfigScreen: () -> Unit,
    onSignInWithGoogleButton: () -> Unit,
    stateSIWGButton: Boolean
){
    Surface(modifier = modifier) {
        ConstraintLayout(modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())) {
            val (
                btnConf, iconApp, txtAppTitle, txtAppVersion,
                txtAppWelcomeMessage, btnAuth, txtTermsAndConditions
            ) = createRefs()

            val startGuideline = createGuidelineFromStart(16.dp)
            val endGuideline = createGuidelineFromEnd(16.dp)
            val topGuideline = createGuidelineFromTop(16.dp)
            val centralGuideline = createGuidelineFromTop(.4f)

            Surface(
                onClick = { onNavigateToAppConfigScreen() },
                modifier = Modifier.constrainAs(btnConf) {
                    start.linkTo(startGuideline)
                    top.linkTo(topGuideline)
                }
            ){
                Row(
                    modifier = Modifier,
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ){
                    Icon(
                        modifier = Modifier.size(ButtonDefaults.IconSize),
                        imageVector = ImageVector.vectorResource(settings),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = stringResource(id = R.string.str_configuration),
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth(.6f)
                    .height(150.dp)
                    .constrainAs(iconApp) {
                        start.linkTo(startGuideline)
                        end.linkTo(endGuideline)
                        bottom.linkTo(centralGuideline)
                        top.linkTo(btnConf.bottom)
                    },
                contentAlignment = Alignment.Center
            ){
                Image(
                    imageVector = ImageVector.vectorResource(id = CustomIcons.App.app),
                    contentDescription = stringResource(id = R.string.app_name),
                    alignment = Alignment.Center,
                    modifier = Modifier
                )
            }

            Text(
                text = stringResource(id = R.string.app_name),
                fontSize = MaterialTheme.typography.headlineLarge.fontSize,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.constrainAs(txtAppTitle){
                    top.linkTo(centralGuideline)
                    centerHorizontallyTo(parent)
                }
            )

            Text(
                text = "${BuildConfig.VERSION_NAME} - ${BuildConfig.BUILD_TYPE}",
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.constrainAs(txtAppVersion){
                    top.linkTo(txtAppTitle.bottom)
                    centerHorizontallyTo(parent)
                }
            )

            Surface(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .constrainAs(txtAppWelcomeMessage) {
                        start.linkTo(startGuideline)
                        end.linkTo(endGuideline)
                        top.linkTo(txtAppVersion.bottom, 30.dp)
                        bottom.linkTo(btnAuth.top)
                    },
                shape = MaterialTheme.shapes.small,
                color = MaterialTheme.colorScheme.secondaryContainer,
                shadowElevation = 5.dp
            ) {
                Text(
                    text = stringResource(R.string.str_auth_welcome_message),
                    modifier = Modifier.padding(12.dp),
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
            }

            Surface(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .constrainAs(btnAuth) {
                        start.linkTo(startGuideline)
                        end.linkTo(endGuideline)
                        top.linkTo(txtAppWelcomeMessage.bottom, 36.dp)
                    },
                shape = MaterialTheme.shapes.medium,
            ) {
                SignInWithGoogle(
                    onClick = { onSignInWithGoogleButton() },
                    btnLoadingState = stateSIWGButton
                )
            }

            Surface(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .constrainAs(txtTermsAndConditions) {
                        top.linkTo(btnAuth.bottom, 16.dp)
                        start.linkTo(startGuideline)
                        end.linkTo(endGuideline)
                    }
            ){
                Text(
                    text = stringResource(id = R.string.str_tnc),
                    modifier = Modifier.padding(12.dp),
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onTertiaryContainer,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
package com.thomas200593.mini_retail_app.features.auth.ui

import android.app.Activity
import android.content.pm.ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
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
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.MaterialTheme.typography
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
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.font.FontWeight.Companion.SemiBold
import androidx.compose.ui.text.style.TextAlign.Companion.Center
import androidx.compose.ui.text.style.TextOverflow.Companion.Ellipsis
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.thomas200593.mini_retail_app.BuildConfig.BUILD_TYPE
import com.thomas200593.mini_retail_app.BuildConfig.VERSION_NAME
import com.thomas200593.mini_retail_app.R
import com.thomas200593.mini_retail_app.R.string.str_auth_welcome_message
import com.thomas200593.mini_retail_app.app.ui.LocalStateApp
import com.thomas200593.mini_retail_app.app.ui.StateApp
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState.Error
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState.Success
import com.thomas200593.mini_retail_app.core.ui.common.CustomIcons
import com.thomas200593.mini_retail_app.core.ui.common.CustomIcons.Setting.settings
import com.thomas200593.mini_retail_app.core.ui.component.CustomButton.Google
import com.thomas200593.mini_retail_app.core.ui.component.CustomButton.Google.SignInWithGoogle
import com.thomas200593.mini_retail_app.core.ui.component.CustomButton.Google.handleClearCredential
import com.thomas200593.mini_retail_app.core.ui.component.CustomScreenUtil.LockScreenOrientation
import com.thomas200593.mini_retail_app.features.app_conf.app_config.navigation.navToAppConfig
import com.thomas200593.mini_retail_app.features.initial.initial.navigation.navToInitial
import com.thomas200593.mini_retail_app.work.workers.session_monitor.manager.ManagerWorkSessionMonitor.initialize
import kotlinx.coroutines.launch

@Composable
fun ScrAuth(
    vm: VMAuth = hiltViewModel(),
    stateApp: StateApp = LocalStateApp.current
){
    LockScreenOrientation(orientation = SCREEN_ORIENTATION_PORTRAIT)
    val activityContext = (LocalContext.current as Activity)
    val appContext = LocalContext.current.applicationContext
    val coroutineScope = rememberCoroutineScope()
    val stateSIWGButton by vm.stateSIWGButton.collectAsStateWithLifecycle()
    val authSessionTokenState by vm.authSessionTokenState

    LaunchedEffect(Unit) {
        handleClearCredential(
            activityContext = activityContext,
            onClearSuccess = { vm.onOpen() },
            onClearError = { vm.onOpen() }
        )
    }

    LaunchedEffect(authSessionTokenState) {
        when(authSessionTokenState){
            is Success -> {
                initialize(appContext)
                stateApp.navController.navToInitial()
            }
            is Error -> {
                handleClearCredential(
                    activityContext = activityContext,
                    onClearSuccess = { vm.clearAuthSessionToken() },
                    onClearError = { vm.clearAuthSessionToken() }
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
                vm.updateAuthSIWGButtonState(true)
                Google.handleSignIn(
                    activityContext = activityContext,
                    coroutineScope = coroutineScope,
                    onResultReceived = { vm.verifyAndSaveAuthSession(it) },
                    onError = {
                        coroutineScope.launch {
                            handleClearCredential(
                                activityContext = activityContext,
                                onClearSuccess = { vm.clearAuthSessionToken() },
                                onClearError = { vm.clearAuthSessionToken() }
                            )
                        }
                    },
                    onDialogDismissed = {
                        coroutineScope.launch {
                            handleClearCredential(
                                activityContext = activityContext,
                                onClearSuccess = { vm.clearAuthSessionToken() },
                                onClearError = { vm.clearAuthSessionToken() }
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
        ConstraintLayout(modifier = modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
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
                        tint = colorScheme.onSurface
                    )
                    Text(
                        text = stringResource(id = R.string.str_configuration),
                        fontWeight = Bold
                    )
                }
            }

            Box(
                modifier = Modifier.fillMaxWidth(.6f).height(150.dp).constrainAs(iconApp) {
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
                fontSize = typography.headlineLarge.fontSize,
                fontWeight = Bold,
                color = colorScheme.onSurface,
                modifier = Modifier.constrainAs(txtAppTitle){
                    top.linkTo(centralGuideline)
                    centerHorizontallyTo(parent)
                }
            )

            Text(
                text = "$VERSION_NAME - $BUILD_TYPE",
                fontWeight = SemiBold,
                color = colorScheme.primary,
                modifier = Modifier.constrainAs(txtAppVersion){
                    top.linkTo(txtAppTitle.bottom)
                    centerHorizontallyTo(parent)
                }
            )

            Surface(
                modifier = Modifier.fillMaxWidth(0.9f).constrainAs(txtAppWelcomeMessage) {
                    start.linkTo(startGuideline)
                    end.linkTo(endGuideline)
                    top.linkTo(txtAppVersion.bottom, 30.dp)
                    bottom.linkTo(btnAuth.top)
                },
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
                modifier = Modifier.fillMaxWidth(0.9f).constrainAs(btnAuth) {
                    start.linkTo(startGuideline)
                    end.linkTo(endGuideline)
                    top.linkTo(txtAppWelcomeMessage.bottom, 36.dp)
                },
                shape = shapes.medium,
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
                    fontWeight = SemiBold,
                    color = colorScheme.onTertiaryContainer,
                    maxLines = 1,
                    overflow = Ellipsis,
                    textAlign = Center,
                    modifier = Modifier.padding(12.dp)
                )
            }
        }
    }
}
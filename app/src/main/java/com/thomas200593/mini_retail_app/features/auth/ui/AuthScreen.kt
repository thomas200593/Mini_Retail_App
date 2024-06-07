package com.thomas200593.mini_retail_app.features.auth.ui

import android.app.Activity
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.shapes
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
import com.thomas200593.mini_retail_app.core.design_system.util.RequestState
import com.thomas200593.mini_retail_app.core.ui.common.Icons
import com.thomas200593.mini_retail_app.core.ui.common.Icons.Setting.settings
import com.thomas200593.mini_retail_app.core.ui.component.Button
import com.thomas200593.mini_retail_app.core.ui.component.Button.Google.SignInWithGoogle
import com.thomas200593.mini_retail_app.features.auth.entity.AuthSessionToken
import com.thomas200593.mini_retail_app.features.auth.entity.OAuth2UserMetadata
import kotlinx.coroutines.launch
import timber.log.Timber

private const val TAG = "AuthScreen"

@Composable
fun AuthScreen(
    viewModel: AuthViewModel = hiltViewModel(),
    onNavigateToInitial: () -> Unit,
    onNavigateToAppConfig: () -> Unit,
){
    Timber.d("Called : %s", TAG)
    val activityContext = (LocalContext.current as Activity)
    val coroutineScope = rememberCoroutineScope()
    val stateSIWGButton by viewModel.stateSIWGButton.collectAsStateWithLifecycle()
    val authSessionTokenState by viewModel.authSessionTokenState

    LaunchedEffect(Unit) {
        viewModel.onOpen()
    }

    LaunchedEffect(authSessionTokenState) {
        when(authSessionTokenState){
            is RequestState.Success -> {
                val authSessionToken = (authSessionTokenState as RequestState.Success).data
                if(authSessionToken != null){
                    val userData = viewModel.mapAuthSessionTokenToUserData(authSessionToken)
                    val googleUserData = userData?.oAuth2UserMetadata as OAuth2UserMetadata.Google
                    val email = googleUserData.email
                    Toast.makeText(activityContext, "Welcome $email", Toast.LENGTH_LONG).show()
                    onNavigateToInitial()
                }else{
                    viewModel.clearAuthSessionToken()
                }
            }
            else -> Unit
        }
    }

    ScreenContent(
        stateSIWGButton = stateSIWGButton,
        onNavigateToAppConfigScreen = onNavigateToAppConfig,
        onSignInWithGoogleButton = {
            coroutineScope.launch {
                viewModel.updateAuthSIWGButtonState(true)
                Button.Google.handleSignIn(
                    activityContext = activityContext,
                    coroutineScope = coroutineScope,
                    onResultReceived = {
                        viewModel.verifyAndSaveAuthSession(it)
                    },
                    onError = {
                        viewModel.clearAuthSessionToken()
                    },
                    onDialogDismissed = {
                        viewModel.clearAuthSessionToken()
                    }
                )
            }
        },
        authSessionTokenState = authSessionTokenState
    )
}

@Composable
private fun ScreenContent(
    modifier: Modifier = Modifier,
    onNavigateToAppConfigScreen: () -> Unit,
    onSignInWithGoogleButton: () -> Unit,
    stateSIWGButton: Boolean,
    authSessionTokenState: RequestState<AuthSessionToken>
){
    Surface(
        modifier = modifier
    ) {
        ConstraintLayout(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            val (btnConf, iconApp, txtAppTitle, txtAppVersion, txtAppWelcomeMessage, btnAuth,
                txtTermsAndConditions) = createRefs()

            val startGuideline = createGuidelineFromStart(16.dp)
            val endGuideline = createGuidelineFromEnd(16.dp)
            val topGuideline = createGuidelineFromTop(16.dp)

            //Layout Central Partition
            val centralGuideline = createGuidelineFromTop(.4f)

            //Config Button
            Icon(
                imageVector = ImageVector.vectorResource(settings),
                contentDescription = null,
                modifier = Modifier
                    .size(48.dp)
                    .padding(8.dp)
                    .constrainAs(btnConf) {
                        start.linkTo(startGuideline)
                        top.linkTo(topGuideline)
                    }
                    .clickable(
                        onClick = { onNavigateToAppConfigScreen() },
                    ),
                tint = MaterialTheme.colorScheme.onSurface
            )
            //.Config Button

            //App Icon
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
                    imageVector = ImageVector.vectorResource(id = Icons.App.app),
                    contentDescription = stringResource(id = R.string.app_name),
                    alignment = Alignment.Center,
                    modifier = Modifier
                )
            }
            //.App Icon

            //App Name
            Text(
                text = stringResource(id = R.string.app_name),
                fontSize = MaterialTheme.typography.headlineLarge.fontSize,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier
                    .constrainAs(txtAppTitle){
                        top.linkTo(centralGuideline)
                        centerHorizontallyTo(parent)
                    }
            )
            //.App Name

            //App Version
            Text(
                text = "${BuildConfig.VERSION_NAME} - ${BuildConfig.BUILD_TYPE}",
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .constrainAs(txtAppVersion){
                        top.linkTo(txtAppTitle.bottom)
                        centerHorizontallyTo(parent)
                    }
            )
            //.App Version

            //App Welcome Message
            Surface(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .constrainAs(txtAppWelcomeMessage) {
                        start.linkTo(startGuideline)
                        end.linkTo(endGuideline)
                        top.linkTo(txtAppVersion.bottom, 30.dp)
                        bottom.linkTo(btnAuth.top)
                    },
                shape = shapes.small,
                color = MaterialTheme.colorScheme.secondaryContainer,
                shadowElevation = 5.dp
            ) {
                Text(
                    text = "Welcome to Application! To continue, please sign in into Your Account.",
                    modifier = Modifier.padding(12.dp),
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
            }
            //.App Welcome Message

            //Auth Button
            Surface(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .constrainAs(btnAuth) {
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
            //.Auth Button

            //Terms and Conditions
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
                    text = authSessionTokenState.toString(),
                    modifier = Modifier.padding(12.dp),
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onTertiaryContainer,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Center
                )
            }
            //.Terms and Conditions
        }
    }
}
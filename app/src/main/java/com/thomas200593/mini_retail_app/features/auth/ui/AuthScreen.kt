package com.thomas200593.mini_retail_app.features.auth.ui

import android.app.Activity
import androidx.compose.foundation.Image
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.thomas200593.mini_retail_app.BuildConfig
import com.thomas200593.mini_retail_app.R
import com.thomas200593.mini_retail_app.core.ui.common.Icons
import com.thomas200593.mini_retail_app.core.ui.common.Icons.Setting.settings
import com.thomas200593.mini_retail_app.core.ui.component.Button.SignInWithGoogleButton
import kotlinx.coroutines.launch
import timber.log.Timber

private const val TAG = "AuthScreen"

@Composable
fun AuthScreen(
    viewModel: AuthViewModel = hiltViewModel()
){
    Timber.d("Called : %s", TAG)
    val activityContext = (LocalContext.current as Activity)
    val coroutineScope = rememberCoroutineScope()
    val idToken by viewModel.idToken.collectAsStateWithLifecycle()
    val authState by viewModel.authState.collectAsStateWithLifecycle()

    ScreenContent(
        authState = authState,
        onSignInWithGoogleButton = {
            coroutineScope.launch {
                viewModel.updateAuthState(true)
            }
            coroutineScope.launch {
                startAuthWithGoogleForResult(
                    activityContext = activityContext,
                    coroutineScope = coroutineScope,
                    onResultReceived = {
                        viewModel.updateAuthState(true)
                        viewModel.verifyTokenBackend(it)
                    },
                    onError = {
                        viewModel.updateAuthState(false)
                        viewModel.errorToken(it)
                    },
                    onDialogDismissed = {
                        viewModel.updateAuthState(false)
                        viewModel.dismissToken(it)
                    }
                )
            }
        },
        idToken = idToken
    )
}

@Composable
private fun ScreenContent(
    modifier: Modifier = Modifier,
    onSignInWithGoogleButton: () -> Unit,
    idToken: String,
    authState: Boolean
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
            val bottomGuideline = createGuidelineFromBottom(0.1f)

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
                    },
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
                        bottom.linkTo(bottomGuideline, 16.dp)
                    },
                shape = shapes.medium,
            ) {
                SignInWithGoogleButton(
                    onClick = {
                        onSignInWithGoogleButton()
                    },
                    btnLoadingState = authState
                )
            }
            //.Auth Button

            //Terms and Conditions
            Text(
                text = idToken,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier
                    .constrainAs(txtTermsAndConditions){
                        top.linkTo(bottomGuideline)
                        centerHorizontallyTo(parent)
                    }
            )
            //.Terms and Conditions
        }
    }
}
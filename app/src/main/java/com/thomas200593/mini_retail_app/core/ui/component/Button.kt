package com.thomas200593.mini_retail_app.core.ui.component

import android.app.Activity
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Unspecified
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.credentials.ClearCredentialStateRequest
import androidx.credentials.CredentialManager.Companion.create
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.ClearCredentialException
import androidx.credentials.exceptions.GetCredentialCancellationException
import androidx.credentials.exceptions.GetCredentialException
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential.Companion.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential.Companion.createFrom
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.thomas200593.mini_retail_app.BuildConfig
import com.thomas200593.mini_retail_app.core.ui.common.Icons.Google.google_logo
import com.thomas200593.mini_retail_app.core.design_system.util.JWTHelper
import com.thomas200593.mini_retail_app.core.design_system.util.JWTHelper.GoogleOAuth2.validateToken
import com.thomas200593.mini_retail_app.features.auth.entity.AuthSessionToken
import com.thomas200593.mini_retail_app.features.auth.entity.OAuthProvider.GOOGLE
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

object Button {
    private val TAG_COMMON = Common::class.simpleName
    object Common{
        @Composable
        fun AppButton(){}

        @Composable
        fun AppIconButton(
            modifier: Modifier = Modifier,
            onClick: () -> Unit,
            icon: ImageVector,
            text: String = String(),
            shape: Shape = MaterialTheme.shapes.medium,
            padding: Dp = 8.dp,
            containerColor: Color = ButtonDefaults.buttonColors().containerColor,
            contentColor: Color = ButtonDefaults.buttonColors().contentColor,
            disabledContainerColor: Color = ButtonDefaults.buttonColors().disabledContainerColor,
            disabledContentColor: Color = ButtonDefaults.buttonColors().disabledContentColor
        ){
            Button(
                onClick = onClick,
                shape = shape,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(padding),
                colors = ButtonColors(
                    containerColor = containerColor,
                    contentColor = contentColor,
                    disabledContainerColor = disabledContainerColor,
                    disabledContentColor = disabledContentColor
                )
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(1.0f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        modifier = Modifier.sizeIn(maxWidth = ButtonDefaults.IconSize),
                        imageVector = icon,
                        contentDescription = null
                    )
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = text,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }

        @Composable
        fun AppOutlinedButton(){}

        @Composable
        fun AppOutlinedIconButton(){}

        @Composable
        fun AppTextButton(){}

        @Composable
        fun AppTextIconButton(){}
    }

    private val TAG_GOOGLE = Google::class.simpleName
    object Google{
        @Composable
        fun SignInWithGoogle(
            modifier: Modifier = Modifier,
            primaryText: String = "Sign in with Google",
            secondaryText: String = "Please wait...",
            googleIcon: Int = google_logo,
            btnLoadingState: Boolean = false,
            btnShape: Shape = MaterialTheme.shapes.medium,
            btnBorderStrokeWidth: Dp = 1.dp,
            btnBorderColor: Color = Color(0xFF747775),
            btnColor: Color = MaterialTheme.colorScheme.surface,
            btnShadowElevation: Dp = 9.dp,
            progressIndicatorColor: Color = MaterialTheme.colorScheme.primary,
            onClick: () -> Unit
        ){
            var btnText by remember { mutableStateOf(primaryText) }
            LaunchedEffect(btnLoadingState) {
                btnText = if(btnLoadingState) secondaryText else primaryText
            }
            Surface(
                modifier = modifier.clickable(
                    enabled = !btnLoadingState,
                    onClick = onClick
                ),
                shape = btnShape,
                border = BorderStroke(width = btnBorderStrokeWidth, color = btnBorderColor),
                color = btnColor,
                shadowElevation = btnShadowElevation
            ) {
                Row(
                    modifier = Modifier
                        .padding(10.dp)
                        .animateContentSize(
                            animationSpec = tween(
                                durationMillis = 300,
                                easing = LinearOutSlowInEasing
                            )
                        ),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ){
                    Icon(
                        imageVector = ImageVector.vectorResource(id = googleIcon),
                        contentDescription = null,
                        modifier = Modifier
                            .size(20.dp)
                            .padding(start = 2.dp),
                        tint = Unspecified
                    )
                    Text(
                        text = btnText,
                        modifier = Modifier.padding(start = 10.dp, end = 2.dp)
                    )
                    if(btnLoadingState){
                        Spacer(modifier = Modifier.width(16.dp))
                        CircularProgressIndicator(
                            modifier = Modifier.size(16.dp),
                            strokeWidth = 2.dp,
                            color = progressIndicatorColor
                        )
                    }
                }
            }
        }

        suspend fun handleSignIn(
            activityContext: Activity,
            coroutineScope: CoroutineScope,
            onResultReceived: (AuthSessionToken) -> Unit,
            onError: (Throwable) -> Unit,
            onDialogDismissed: (Throwable) -> Unit
        ){
            coroutineScope.launch {
                val credentialManager = create(activityContext)
                val googleIdOptions = GetGoogleIdOption.Builder()
                    .setFilterByAuthorizedAccounts(false)
                    .setNonce(JWTHelper.GoogleOAuth2.generateTokenNonce())
                    .setAutoSelectEnabled(false)
                    .setServerClientId(BuildConfig.GOOGLE_AUTH_WEB_ID)
                    .build()
                val credentialRequest = GetCredentialRequest.Builder()
                    .addCredentialOption(googleIdOptions)
                    .build()
                try{
                    val result = credentialManager
                        .getCredential(context = activityContext, request = credentialRequest)
                    when(val credential = result.credential){
                        is CustomCredential -> {
                            if(credential.type == TYPE_GOOGLE_ID_TOKEN_CREDENTIAL){
                                val googleIdCredential = createFrom(credential.data)
                                val authProvider = GOOGLE
                                val idToken = googleIdCredential.idToken
                                val authSessionToken = AuthSessionToken(authProvider, idToken)
                                if(validateToken(authSessionToken)) {
                                    onResultReceived(authSessionToken)
                                }
                                else { onError(Throwable("Token Validation Failed.")) }
                            }
                            else{ onError(Throwable("Unexpected type of Credential")) }
                        }
                        else -> { onError(Throwable("Unexpected type of Credential")) }
                    }
                }
                catch (e: GetCredentialException){ onError(e) }
                catch (e: GoogleIdTokenParsingException){ onError(e) }
                catch (e: GetCredentialCancellationException){ onDialogDismissed(e) }
            }
        }

        suspend fun handleClearCredential(
            activityContext: Activity,
            onClearSuccess: () -> Unit,
            onClearError: (Throwable) -> Unit
        ){
            val credentialManager = create(context = activityContext)
            val clearCredentialRequest = ClearCredentialStateRequest()
            try{
                credentialManager.clearCredentialState(request = clearCredentialRequest)
                onClearSuccess()
            }
            catch (t: ClearCredentialException){ onClearError(t) }
            catch (t: Exception){ onClearError(t) }
        }
    }
}
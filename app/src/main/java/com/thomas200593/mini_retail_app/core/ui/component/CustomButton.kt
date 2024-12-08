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
import androidx.compose.material3.ButtonDefaults.IconSize
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.shapes
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.style.TextAlign.Companion.Center
import androidx.compose.ui.text.style.TextOverflow.Companion.Ellipsis
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.credentials.ClearCredentialStateRequest
import androidx.credentials.CredentialManager.Companion.create
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialCancellationException
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential.Companion.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential.Companion.createFrom
import com.thomas200593.mini_retail_app.BuildConfig
import com.thomas200593.mini_retail_app.R
import com.thomas200593.mini_retail_app.core.design_system.util.HlpJwt.GoogleOAuth2
import com.thomas200593.mini_retail_app.core.design_system.util.HlpJwt.GoogleOAuth2.validateToken
import com.thomas200593.mini_retail_app.core.ui.common.CustomIcons.Google.google_logo
import com.thomas200593.mini_retail_app.features.auth.entity.AuthSessionToken
import com.thomas200593.mini_retail_app.features.auth.entity.OAuthProvider.GOOGLE

object CustomButton {
    object Common {
        @Composable
        fun AppIconButton(
            modifier: Modifier = Modifier,
            onClick: () -> Unit,
            enabled: Boolean = true,
            icon: ImageVector,
            text: String = String(),
            shape: Shape = shapes.medium,
            padding: Dp = 8.dp,
            containerColor: Color = buttonColors().containerColor,
            contentColor: Color = buttonColors().contentColor,
            disabledContainerColor: Color = buttonColors().disabledContainerColor,
            disabledContentColor: Color = buttonColors().disabledContentColor
        ) {
            Button(
                onClick = onClick,
                enabled = enabled,
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
                        modifier = Modifier.sizeIn(maxWidth = IconSize),
                        imageVector = icon,
                        contentDescription = null
                    )
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = text,
                        textAlign = Center,
                        fontWeight = Bold,
                        maxLines = 1,
                        overflow = Ellipsis
                    )
                }
            }
        }
    }

    object Google {
        @Composable
        fun SignInWithGoogle(
            modifier: Modifier = Modifier,
            primaryText: String = "Sign in with Google",
            secondaryText: String = "Please wait...",
            googleIcon: Int = google_logo,
            btnLoadingState: Boolean = false,
            btnShape: Shape = shapes.medium,
            btnBorderStrokeWidth: Dp = 1.dp,
            btnBorderColor: Color = colorResource(R.color.charcoal_gray),
            btnColor: Color = MaterialTheme.colorScheme.surface,
            btnShadowElevation: Dp = 9.dp,
            progressIndicatorColor: Color = MaterialTheme.colorScheme.primary,
            onClick: () -> Unit
        ) {
            var btnText by remember { mutableStateOf(primaryText) }
            LaunchedEffect(btnLoadingState) {
                btnText = if (btnLoadingState) secondaryText else primaryText
            }
            Surface(
                modifier = modifier.clickable(
                    enabled = !btnLoadingState,
                    onClick = onClick
                ),
                shape = btnShape,
                border = BorderStroke(btnBorderStrokeWidth, btnBorderColor),
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
                ) {
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
                    if (btnLoadingState) {
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
            onResultReceived: (AuthSessionToken) -> Unit,
            onError: (Throwable) -> Unit,
            onDialogDismissed: (Throwable) -> Unit
        ) {
            runCatching {
                val credentialManager = create(activityContext)
                val googleIdOptions = GetGoogleIdOption.Builder()
                    .setFilterByAuthorizedAccounts(false)
                    .setNonce(GoogleOAuth2.generateTokenNonce())
                    .setAutoSelectEnabled(false)
                    .setServerClientId(BuildConfig.GOOGLE_AUTH_WEB_ID)
                    .build()

                val credentialRequest = GetCredentialRequest.Builder()
                    .addCredentialOption(googleIdOptions)
                    .build()

                val result = credentialManager.getCredential(
                    context = activityContext,
                    request = credentialRequest
                )

                when (val credential = result.credential) {
                    is CustomCredential -> {
                        if (credential.type == TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                            val googleIdCredential = createFrom(credential.data)
                            val authProvider = GOOGLE
                            val idToken = googleIdCredential.idToken
                            val authSessionToken = AuthSessionToken(authProvider, idToken)
                            if (validateToken(authSessionToken)) {
                                authSessionToken
                            } else {
                                throw Throwable("Token Validation Failed.")
                            }
                        } else {
                            throw Throwable("Unexpected type of Credential")
                        }
                    }

                    else -> throw Throwable("Unexpected type of Credential")
                }
            }.fold(
                onSuccess = { authSessionToken -> onResultReceived(authSessionToken) },
                onFailure = { throwable ->
                    when (throwable) {
                        is GetCredentialCancellationException -> onDialogDismissed(throwable)
                        else -> onError(throwable)
                    }
                }
            )
        }

        suspend fun handleClearCredential(
            activityContext: Activity,
            onClearSuccess: () -> Unit,
            onClearError: (Throwable) -> Unit
        ) {
            runCatching {
                val credentialManager = create(context = activityContext)
                val clearCredentialRequest = ClearCredentialStateRequest()
                credentialManager.clearCredentialState(request = clearCredentialRequest)
            }.fold(
                onSuccess = { onClearSuccess() },
                onFailure = { throwable -> onClearError(throwable) }
            )
        }
    }
}
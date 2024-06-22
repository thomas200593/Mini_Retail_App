package com.thomas200593.mini_retail_app.features.user_profile.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import com.thomas200593.mini_retail_app.R
import com.thomas200593.mini_retail_app.app.ui.AppState
import com.thomas200593.mini_retail_app.app.ui.LocalAppState
import com.thomas200593.mini_retail_app.core.data.local.session.SessionState
import com.thomas200593.mini_retail_app.core.ui.common.Icons.Auth.session_expire
import com.thomas200593.mini_retail_app.core.ui.common.Icons.Setting.settings
import com.thomas200593.mini_retail_app.core.ui.component.CommonMessagePanel.LoadingPanelCircularIndicator
import com.thomas200593.mini_retail_app.core.ui.component.CommonMessagePanel.LoadingScreen
import com.thomas200593.mini_retail_app.features.app_config.navigation.navigateToAppConfig
import com.thomas200593.mini_retail_app.features.auth.entity.OAuth2UserMetadata
import com.thomas200593.mini_retail_app.features.auth.entity.OAuthProvider
import com.thomas200593.mini_retail_app.features.initial.navigation.navigateToInitial
import com.thomas200593.mini_retail_app.work.workers.session_monitor.manager.SessionMonitorWorkManager
import timber.log.Timber
import java.time.Instant

private const val TAG = "UserProfileScreen"

@Composable
fun UserProfileScreen(
    viewModel: UserProfileViewModel = hiltViewModel(),
    appState: AppState = LocalAppState.current
){
    Timber.d("Called : fun $TAG()")

    val applicationContext = LocalContext.current.applicationContext
    val sessionState by appState.isSessionValid.collectAsStateWithLifecycle()

    when(sessionState){
        is SessionState.Invalid -> {
            LaunchedEffect(Unit) {
                appState.navController.navigateToInitial()
            }
        }
        SessionState.Loading -> {
            LoadingScreen()
        }
        is SessionState.Valid -> {
            LaunchedEffect(Unit) {
                viewModel.onOpen()
            }
        }
    }

    ScreenContent(
        sessionState = sessionState,
        onNavigateToConfig = {
            appState.navController.navigateToAppConfig(null)
        },
        onSignedOut = {
            viewModel.handleSignOut()
            SessionMonitorWorkManager.terminate(applicationContext)
        }
    )
}

@Composable
private fun ScreenContent(
    modifier: Modifier = Modifier,
    sessionState: SessionState,
    onNavigateToConfig: () -> Unit,
    onSignedOut: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.Top),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ProfileSection(
            sessionState = sessionState,
            onNavigateToConfig = onNavigateToConfig
        )
        MenuSection()
        SignOutSection(
            onSignedOut = onSignedOut
        )
    }
}

@Composable
private fun ProfileSection(
    modifier: Modifier = Modifier,
    sessionState: SessionState,
    onNavigateToConfig: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when(sessionState){
            SessionState.Loading -> {
                LoadingPanelCircularIndicator()
            }
            is SessionState.Invalid -> {
                LoadingPanelCircularIndicator()
            }
            is SessionState.Valid -> {
                when(val provider = sessionState.userData.authSessionToken?.authProvider){
                    OAuthProvider.GOOGLE -> {
                        val data = (sessionState.userData.oAuth2UserMetadata as OAuth2UserMetadata.Google)
                        var infoExpanded by remember { mutableStateOf(false) }

                        Row(
                            modifier = Modifier.fillMaxWidth(1.0f),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.End
                        ) {
                            Surface(
                                modifier = Modifier.size(ButtonDefaults.IconSize),
                                onClick = {
                                    onNavigateToConfig()
                                }
                            ) {
                                Icon(
                                    imageVector = ImageVector.vectorResource(id = settings),
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
                        AsyncImage(
                            model = ImageRequest
                                .Builder(LocalContext.current)
                                .crossfade(1000)
                                .data(data = data.pictureUri)
                                .transformations(CircleCropTransformation()).build(),
                            contentDescription = null,
                            modifier = Modifier
                                .size(100.dp)
                                .border(2.dp, Color.Gray, CircleShape),
                            contentScale = ContentScale.Crop
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(1.0f),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ){
                            Text(
                                modifier = Modifier.weight(0.9f),
                                text = data.name,
                                color = MaterialTheme.colorScheme.onSurface,
                                fontSize = MaterialTheme.typography.titleLarge.fontSize,
                                fontWeight = FontWeight.Bold,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                textAlign = TextAlign.Center
                            )
                            Surface(
                                modifier = Modifier.weight(0.1f),
                                color = MaterialTheme.colorScheme.tertiaryContainer,
                                shape = MaterialTheme.shapes.extraSmall,
                                onClick = { infoExpanded = !infoExpanded }
                            ) {
                                Icon(
                                    imageVector = if(!infoExpanded) Icons.Default.KeyboardArrowDown else Icons.Default.KeyboardArrowUp,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.onTertiaryContainer
                                )
                            }
                        }
                        if(infoExpanded){
                            Row(
                                modifier = Modifier.fillMaxWidth(1.0f),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Surface(
                                    modifier = Modifier
                                        .weight(0.1f)
                                        .size(ButtonDefaults.IconSize),
                                    shape = MaterialTheme.shapes.extraSmall,
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Email,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.onPrimaryContainer
                                    )
                                }
                                Text(
                                    modifier = Modifier.weight(0.9f),
                                    text = data.email,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    textAlign = TextAlign.Start
                                )
                            }
                            Row(
                                modifier = Modifier.fillMaxWidth(1.0f),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Surface(
                                    modifier = Modifier
                                        .weight(0.1f)
                                        .size(ButtonDefaults.IconSize),
                                    shape = MaterialTheme.shapes.extraSmall,
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Lock,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.onPrimaryContainer
                                    )
                                }
                                Text(
                                    modifier = Modifier.weight(0.9f),
                                    text = provider.title,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    textAlign = TextAlign.Start
                                )
                            }
                            Row(
                                modifier = Modifier.fillMaxWidth(1.0f),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Surface(
                                    modifier = Modifier
                                        .weight(0.1f)
                                        .size(ButtonDefaults.IconSize),
                                    shape = MaterialTheme.shapes.extraSmall,
                                ) {
                                    Icon(
                                        imageVector = ImageVector.vectorResource(id = session_expire),
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.onPrimaryContainer
                                    )
                                }
                                Text(
                                    modifier = Modifier.weight(0.9f),
                                    text = Instant.ofEpochSecond(data.expiredAt.toLong()).toString(),
                                    color = MaterialTheme.colorScheme.onSurface,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    textAlign = TextAlign.Start
                                )
                            }
                        }
                        HorizontalDivider(thickness = 2.dp)
                    }
                    null -> Unit
                }
            }
        }
    }
}

@Composable
private fun MenuSection() {

}

@Composable
private fun SignOutSection(
    onSignedOut: () -> Unit
) {
    Button(
        onClick = onSignedOut,
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = ButtonColors(
            containerColor = MaterialTheme.colorScheme.errorContainer,
            contentColor = MaterialTheme.colorScheme.onErrorContainer,
            disabledContainerColor = ButtonDefaults.buttonColors().disabledContainerColor,
            disabledContentColor = ButtonDefaults.buttonColors().disabledContentColor
        )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(1.0f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                modifier = Modifier.weight(0.1f),
                imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                contentDescription = null
            )
            Text(
                modifier = Modifier.weight(0.9f),
                text = stringResource(id = R.string.str_auth_sign_out),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}
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
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Lock
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
import com.thomas200593.mini_retail_app.app.ui.LocalStateApp
import com.thomas200593.mini_retail_app.app.ui.StateApp
import com.thomas200593.mini_retail_app.core.data.local.session.SessionState
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState
import com.thomas200593.mini_retail_app.core.ui.common.CustomIcons.Auth.session_expire
import com.thomas200593.mini_retail_app.core.ui.common.CustomIcons.Emotion.sad
import com.thomas200593.mini_retail_app.core.ui.common.CustomIcons.Setting.settings
import com.thomas200593.mini_retail_app.core.ui.component.CustomButton.Common.AppIconButton
import com.thomas200593.mini_retail_app.core.ui.component.CustomPanel.EmptyPanel
import com.thomas200593.mini_retail_app.core.ui.component.CustomPanel.ErrorPanel
import com.thomas200593.mini_retail_app.core.ui.component.CustomPanel.LoadingPanelCircularIndicator
import com.thomas200593.mini_retail_app.core.ui.component.CustomPanel.LoadingScreen
import com.thomas200593.mini_retail_app.core.ui.component.CustomPanel.TextContentWithIcon
import com.thomas200593.mini_retail_app.features.app_conf.app_config.navigation.navToAppConfig
import com.thomas200593.mini_retail_app.features.auth.entity.OAuth2UserMetadata
import com.thomas200593.mini_retail_app.features.auth.entity.OAuthProvider
import com.thomas200593.mini_retail_app.features.auth.entity.UserData
import com.thomas200593.mini_retail_app.features.business.entity.business_profile.dto.BizProfileSummary
import com.thomas200593.mini_retail_app.features.initial.initial.navigation.navToInitial
import com.thomas200593.mini_retail_app.work.workers.session_monitor.manager.ManagerWorkSessionMonitor
import timber.log.Timber
import java.time.Instant

@Composable
fun ScrUserProfile(
    vm: VMUserProfile = hiltViewModel(),
    stateApp: StateApp = LocalStateApp.current
){
    val applicationContext = LocalContext.current.applicationContext
    val sessionState by stateApp.isSessionValid.collectAsStateWithLifecycle()
    val userData by vm.currentSessionUserData
    val businessProfileSummaryData by vm.businessProfileSummary

    when(sessionState){
        SessionState.Loading -> {
            LoadingScreen()
        }
        is SessionState.Invalid -> {
            LaunchedEffect(Unit) {
                stateApp.navController.navToInitial()
            }
        }
        is SessionState.Valid -> {
            LaunchedEffect(Unit) {
                vm.onOpen(sessionState as SessionState.Valid)
            }
        }
    }

    ScreenContent(
        userData = userData,
        businessProfileSummaryData = businessProfileSummaryData,
        onNavigateToConfig = {
            stateApp.navController.navToAppConfig(null)
        },
        onSignedOut = {
            vm.handleSignOut()
            ManagerWorkSessionMonitor.terminate(applicationContext)
        },
        onNavigateToBusinessProfile = {

        }
    )
}

@Composable
private fun ScreenContent(
    modifier: Modifier = Modifier,
    userData: ResourceState<UserData>,
    businessProfileSummaryData: ResourceState<BizProfileSummary>,
    onNavigateToConfig: () -> Unit,
    onSignedOut: () -> Unit,
    onNavigateToBusinessProfile: () -> Unit
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
            userData = userData,
            onNavigateToConfig = onNavigateToConfig,
            onSignedOut = onSignedOut
        )
        MenuSection(
            businessProfileSummaryData = businessProfileSummaryData,
            onNavigateToBusinessProfile = onNavigateToBusinessProfile
        )
        SignOutSection(
            onSignedOut = onSignedOut
        )
    }
}

@Composable
private fun ProfileSection(
    modifier: Modifier = Modifier,
    userData: ResourceState<UserData>,
    onNavigateToConfig: () -> Unit,
    onSignedOut: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when(userData){
            ResourceState.Idle, ResourceState.Loading -> {
                LoadingPanelCircularIndicator()
            }
            is ResourceState.Error -> {
                val error = userData.t
                ErrorPanel(
                    showIcon = true,
                    title = error.message,
                    errorMessage = error.cause.toString()
                )
            }
            ResourceState.Empty -> {
                EmptyPanel(
                    title = stringResource(id = R.string.str_empty_message_title),
                    emptyMessage = stringResource(id = R.string.str_empty_message),
                    showIcon = true
                )
            }
            is ResourceState.Success -> {
                val data = userData.data

                when(val provider = data.authSessionToken?.authProvider){
                    OAuthProvider.GOOGLE -> {
                        val userDetail = (data.oAuth2UserMetadata as OAuth2UserMetadata.Google)
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
                                .data(data = userDetail.pictureUri)
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
                                text = userDetail.name,
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
                            TextContentWithIcon(
                                icon = Icons.Default.Email,
                                text = userDetail.email
                            )
                            TextContentWithIcon(
                                icon = Icons.Default.Check,
                                text = when(userDetail.emailVerified){
                                    "true" -> stringResource(id = R.string.str_email_verified)
                                    "false" -> stringResource(id = R.string.str_email_not_verified)
                                    else -> {
                                        stringResource(id = R.string.str_email_not_verified)
                                    }
                                }
                            )
                            TextContentWithIcon(
                                icon = Icons.Default.Lock,
                                text = provider.title
                            )
                            TextContentWithIcon(
                                icon = ImageVector.vectorResource(id = session_expire),
                                text = Instant.ofEpochSecond(userDetail.expiredAt.toLong()).toString()
                            )
                        }
                        HorizontalDivider(thickness = 2.dp)
                    }
                    null -> {
                        Timber.e("Unknown Provider, Illegal Access Exception")
                        onSignedOut()
                    }
                }
            }
        }
    }
}

@Composable
private fun MenuSection(
    businessProfileSummaryData: ResourceState<BizProfileSummary>,
    onNavigateToBusinessProfile: () -> Unit
) {
    when(businessProfileSummaryData){
        ResourceState.Idle, ResourceState.Loading -> {
            LoadingPanelCircularIndicator()
        }
        is ResourceState.Error -> {
            ErrorPanel(
                showIcon = true,
                title = businessProfileSummaryData.t.message,
                errorMessage = businessProfileSummaryData.t.cause.toString()
            )
        }
        ResourceState.Empty -> {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                shape = MaterialTheme.shapes.medium,
                color = MaterialTheme.colorScheme.surfaceContainerHighest,
                contentColor = MaterialTheme.colorScheme.onSurface
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Top),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = sad),
                        contentDescription = null
                    )
                    Text(
                        text = stringResource(R.string.str_biz_profile_not_set),
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Start
                    )
                    HorizontalDivider()
                    AppIconButton(
                        onClick = { onNavigateToBusinessProfile() },
                        icon = Icons.Default.Info,
                        text = stringResource(id = R.string.str_detail)
                    )
                }
            }
        }
        is ResourceState.Success -> {
            val data = businessProfileSummaryData.data
            Text(text = "Data: $data")
        }
    }
}

@Composable
private fun SignOutSection(
    onSignedOut: () -> Unit
) {
    AppIconButton(
        onClick = onSignedOut,
        icon = Icons.AutoMirrored.Filled.ExitToApp,
        text = stringResource(id = R.string.str_auth_sign_out),
        containerColor = MaterialTheme.colorScheme.errorContainer,
        contentColor = MaterialTheme.colorScheme.onErrorContainer
    )
}
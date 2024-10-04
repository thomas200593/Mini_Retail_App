package com.thomas200593.mini_retail_app.features.user_profile.ui

import android.content.pm.ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
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
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.runtime.rememberCoroutineScope
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.navOptions
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import com.thomas200593.mini_retail_app.R
import com.thomas200593.mini_retail_app.app.ui.LocalStateApp
import com.thomas200593.mini_retail_app.app.ui.StateApp
import com.thomas200593.mini_retail_app.core.data.local.database.entity_common.AuditTrail
import com.thomas200593.mini_retail_app.core.data.local.database.entity_common.Industries
import com.thomas200593.mini_retail_app.core.data.local.database.entity_common.LegalType
import com.thomas200593.mini_retail_app.core.data.local.database.entity_common.Taxation
import com.thomas200593.mini_retail_app.core.design_system.util.HlpStringArray.Handler.StringArrayResource
import com.thomas200593.mini_retail_app.core.design_system.util.HlpStringArray.StringArrayResources.BizIndustries
import com.thomas200593.mini_retail_app.core.ui.common.CustomIcons
import com.thomas200593.mini_retail_app.core.ui.common.CustomIcons.Country.country
import com.thomas200593.mini_retail_app.core.ui.common.CustomThemes
import com.thomas200593.mini_retail_app.core.ui.component.CustomButton.Common.AppIconButton
import com.thomas200593.mini_retail_app.core.ui.component.CustomDialog.AlertDialogContext
import com.thomas200593.mini_retail_app.core.ui.component.CustomDialog.AppAlertDialog
import com.thomas200593.mini_retail_app.core.ui.component.CustomPanel.TextContentWithIcon
import com.thomas200593.mini_retail_app.core.ui.component.CustomScreenUtil.LockScreenOrientation
import com.thomas200593.mini_retail_app.features.app_conf.app_config.entity.AppConfig.ConfigCurrent
import com.thomas200593.mini_retail_app.features.app_conf.app_config.navigation.navToAppConfig
import com.thomas200593.mini_retail_app.features.auth.entity.AuthSessionToken
import com.thomas200593.mini_retail_app.features.auth.entity.OAuth2UserMetadata.Google
import com.thomas200593.mini_retail_app.features.auth.entity.OAuthProvider
import com.thomas200593.mini_retail_app.features.auth.entity.OAuthProvider.GOOGLE
import com.thomas200593.mini_retail_app.features.auth.entity.UserData
import com.thomas200593.mini_retail_app.features.auth.navigation.navToAuth
import com.thomas200593.mini_retail_app.features.business.biz.navigation.DestBiz
import com.thomas200593.mini_retail_app.features.business.biz.navigation.navToBiz
import com.thomas200593.mini_retail_app.features.business.biz_profile.entity.BizName
import com.thomas200593.mini_retail_app.features.business.biz_profile.entity.BizProfileShort
import com.thomas200593.mini_retail_app.features.user_profile.ui.VMUserProfile.DialogState
import com.thomas200593.mini_retail_app.features.user_profile.ui.VMUserProfile.UiEvents.ButtonEvents.BtnAppConfigEvents
import com.thomas200593.mini_retail_app.features.user_profile.ui.VMUserProfile.UiEvents.ButtonEvents.BtnBizProfileEvents
import com.thomas200593.mini_retail_app.features.user_profile.ui.VMUserProfile.UiEvents.ButtonEvents.BtnSignOutEvents
import com.thomas200593.mini_retail_app.features.user_profile.ui.VMUserProfile.UiEvents.OnOpenEvents
import com.thomas200593.mini_retail_app.features.user_profile.ui.VMUserProfile.UiState
import com.thomas200593.mini_retail_app.features.user_profile.ui.VMUserProfile.UiStateUserProfile.Idle
import com.thomas200593.mini_retail_app.features.user_profile.ui.VMUserProfile.UiStateUserProfile.Loading
import com.thomas200593.mini_retail_app.features.user_profile.ui.VMUserProfile.UiStateUserProfile.Success
import com.thomas200593.mini_retail_app.work.workers.session_monitor.manager.ManagerWorkSessionMonitor
import kotlinx.coroutines.launch
import ulid.ULID
import java.time.Instant

@Composable
fun ScrUserProfile(
    vm: VMUserProfile = hiltViewModel(),
    stateApp: StateApp = LocalStateApp.current
) {
    LockScreenOrientation(SCREEN_ORIENTATION_PORTRAIT)

    val appContext = LocalContext.current.applicationContext
    val coroutineScope = rememberCoroutineScope()
    val uiState by vm.uiState.collectAsStateWithLifecycle()
    val sessionState by stateApp.isSessionValid.collectAsStateWithLifecycle()

    LaunchedEffect(sessionState) { vm.onEvent(OnOpenEvents(sessionState)) }

    ScrUserProfile(
        uiState = uiState,
        onNavToAppConfig = {
            vm.onEvent(BtnAppConfigEvents.OnClick)
                .also { coroutineScope.launch { stateApp.navController.navToAppConfig() } }
        },
        onNavToBizProfile = {
            val navOptions = navOptions{ launchSingleTop=true; restoreState=true }
            vm.onEvent(BtnBizProfileEvents.OnClick).also {
                coroutineScope.launch {
                    stateApp.navController.navToBiz(navOptions, DestBiz.BIZ_PROFILE)
                }
            }
        },
        onBtnSignOutClicked = {
            ManagerWorkSessionMonitor.terminate(appContext)
            vm.onEvent(BtnSignOutEvents.OnClick)
                .also{ coroutineScope.launch { stateApp.navController.navToAuth() } }
        }
    )
}

@Composable
private fun ScrUserProfile(
    uiState: UiState,
    onNavToAppConfig: () -> Unit,
    onNavToBizProfile: () -> Unit,
    onBtnSignOutClicked: () -> Unit
) {
    HandleDialogs(
        uiState = uiState,
        onBtnSignOutClicked = onBtnSignOutClicked
    )
    ScreenContent(
        uiState = uiState,
        onNavToAppConfig = onNavToAppConfig,
        onNavToBizProfile = onNavToBizProfile,
        onBtnSignOutClicked = onBtnSignOutClicked
    )
}

@Composable
private fun HandleDialogs(
    uiState: UiState,
    onBtnSignOutClicked: () -> Unit
) {
    AppAlertDialog(
        showDialog = uiState.dialogState.dlgDenySession,
        dialogContext = AlertDialogContext.ERROR,
        showTitle = true,
        title = { Text(text = stringResource(id = R.string.str_session_expired)) },
        showBody = true,
        body = {
            Column(
                modifier = Modifier.fillMaxWidth().verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) { Text(text = stringResource(id = R.string.str_deny_access_auth_required)) }
        },
        useDismissButton = true,
        dismissButton = {
            AppIconButton(
                onClick = onBtnSignOutClicked,
                icon = Icons.Default.Close,
                text = stringResource(id = R.string.str_close),
                containerColor = MaterialTheme.colorScheme.error,
                contentColor = MaterialTheme.colorScheme.onError
            )
        }
    )
}

@Composable
private fun ScreenContent(
    uiState: UiState,
    onNavToAppConfig: () -> Unit,
    onNavToBizProfile: () -> Unit,
    onBtnSignOutClicked: () -> Unit
) {
    Surface {
        Column(
            modifier = Modifier.fillMaxSize().padding(8.dp).verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.Top),
        ) {
            NavigationAppConfigSection(onNavToAppConfig = onNavToAppConfig)
            CurrentUserSessionSection(
                uiState = uiState,
                onBtnSignOutClicked = onBtnSignOutClicked
            )
            BizProfileSummaryData(
                uiState = uiState,
                onNavToBizProfile = onNavToBizProfile
            )
            SignOutSection(onBtnSignOutClicked = onBtnSignOutClicked)
        }
    }
}

@Composable
private fun NavigationAppConfigSection(
    onNavToAppConfig: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End
    ) {
        Surface(
            modifier = Modifier.size(ButtonDefaults.IconSize),
            onClick = { onNavToAppConfig() }
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = CustomIcons.Setting.settings),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier
            )
        }
    }
}

@Composable
private fun CurrentUserSessionSection(
    uiState: UiState,
    onBtnSignOutClicked : () -> Unit
) {
    when(uiState.userProfileData){
        Idle -> Unit
        Loading -> CircularProgressIndicator()
        is Success -> {
            val userData = uiState.userProfileData.data.first
            when(val provider = userData.authSessionToken?.authProvider) {
                GOOGLE ->
                    UserProfileGoogle(provider = provider, userData = userData.oAuth2UserMetadata as Google)
                null ->
                    onBtnSignOutClicked()
            }
        }
    }
}

@Composable
fun UserProfileGoogle(
    provider: OAuthProvider,
    userData: Google
) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterVertically)
    ) {
        var infoExpanded by remember { mutableStateOf(false) }
        AsyncImage(
            model = ImageRequest
                .Builder(LocalContext.current).crossfade(250)
                .data(data = userData.pictureUri).transformations(CircleCropTransformation()).build(),
            contentDescription = null,
            modifier = Modifier.size(100.dp).border(2.dp, Color.Gray, CircleShape),
            contentScale = ContentScale.Crop
        )
        Row(
            modifier = Modifier.fillMaxWidth(1.0f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ){
            Text(
                modifier = Modifier.weight(0.9f),
                text = userData.name,
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
                    imageVector =
                    if(!infoExpanded) Icons.Default.KeyboardArrowDown
                    else Icons.Default.KeyboardArrowUp,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onTertiaryContainer
                )
            }
        }

        if(infoExpanded){
            TextContentWithIcon(
                icon = Icons.Default.Email,
                text = userData.email
            )
            TextContentWithIcon(
                icon = Icons.Default.Check,
                text = when(userData.emailVerified){
                    "true" -> stringResource(id = R.string.str_email_verified)
                    "false" -> stringResource(id = R.string.str_email_not_verified)
                    else -> stringResource(id = R.string.str_email_not_verified)
                }
            )
            TextContentWithIcon(
                icon = Icons.Default.Lock,
                text = provider.title
            )
            TextContentWithIcon(
                icon = ImageVector.vectorResource(id = CustomIcons.Auth.session_expire),
                text = Instant.ofEpochSecond(userData.expiredAt.toLong()).toString()
            )
        }
        HorizontalDivider(thickness = 2.dp)
    }
}

@Composable
private fun BizProfileSummaryData(
    uiState: UiState,
    onNavToBizProfile: () -> Unit
) {
    when(uiState.userProfileData){
        Idle -> Unit
        Loading -> CircularProgressIndicator()
        is Success -> Surface(
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            shape = MaterialTheme.shapes.medium,
            color = MaterialTheme.colorScheme.secondaryContainer,
            contentColor = MaterialTheme.colorScheme.onSecondaryContainer
        ) {
            val bizProfile = uiState.userProfileData.data.second

            Column(
                modifier = Modifier.fillMaxWidth().padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.str_business_profile),
                    modifier = Modifier,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold
                )
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.medium,
                    color = MaterialTheme.colorScheme.secondaryContainer
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth().padding(8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        TextContentWithIcon(
                            icon = ImageVector.vectorResource(id = country),
                            iconBoxColor = MaterialTheme.colorScheme.secondaryContainer,
                            text = stringResource(R.string.str_biz_name),
                            fontWeight = FontWeight.Bold
                        )

                        bizProfile.bizName.legalName
                            .takeIf { it.isNotBlank() }?.let {
                                Column(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalAlignment = Alignment.Start,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Text(
                                        text = stringResource(R.string.str_biz_legal_name),
                                        maxLines = 1,
                                        fontWeight = FontWeight.Bold,
                                        style = MaterialTheme.typography.labelSmall,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                    Text(
                                        text = it,
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                }
                            }

                        bizProfile.bizName.commonName
                            .takeIf { it.isNotBlank() }?.let {
                                Column(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalAlignment = Alignment.Start,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Text(
                                        text = stringResource(R.string.str_biz_common_name),
                                        maxLines = 1,
                                        fontWeight = FontWeight.Bold,
                                        style = MaterialTheme.typography.labelSmall,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                    Text(
                                        text = it,
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                }
                            }
                    }
                }
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.medium,
                    color = MaterialTheme.colorScheme.secondaryContainer
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth().padding(8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        TextContentWithIcon(
                            icon = ImageVector.vectorResource(id = country),
                            iconBoxColor = MaterialTheme.colorScheme.secondaryContainer,
                            text = stringResource(R.string.str_biz_industry),
                            fontWeight = FontWeight.Bold
                        )

                        bizProfile.bizIndustry.identityKey.let {
                            StringArrayResource(BizIndustries).findByKey(it)?.let {
                                Column(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalAlignment = Alignment.Start,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Text(
                                        text = stringResource(R.string.str_biz_industry),
                                        maxLines = 1,
                                        fontWeight = FontWeight.Bold,
                                        style = MaterialTheme.typography.labelSmall,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                    Text(
                                        text = it,
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                }
                            }
                        }

                        bizProfile.bizIndustry.additionalInfo
                            .takeIf{ it.isNotBlank() }?.let {
                                Column(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalAlignment = Alignment.Start,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Text(
                                        text = stringResource(R.string.str_additional_info),
                                        maxLines = 1,
                                        fontWeight = FontWeight.Bold,
                                        style = MaterialTheme.typography.labelSmall,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                    Text(
                                        text = it,
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                }
                            }
                    }
                }
                AppIconButton(
                    onClick = onNavToBizProfile,
                    icon = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    text = stringResource(id = R.string.str_detail)
                )
            }
        }
    }
}

@Composable
private fun SignOutSection(
    onBtnSignOutClicked : () -> Unit
) {
    AppIconButton(
        onClick = onBtnSignOutClicked,
        icon = Icons.AutoMirrored.Filled.ExitToApp,
        text = stringResource(id = R.string.str_auth_sign_out),
        containerColor = MaterialTheme.colorScheme.errorContainer,
        contentColor = MaterialTheme.colorScheme.onErrorContainer
    )
}

@Composable
@Preview
private fun Preview() = CustomThemes.ApplicationTheme {
    ScrUserProfile(
        onNavToAppConfig = {},
        onNavToBizProfile = {},
        onBtnSignOutClicked = {},
        uiState = UiState(
            dialogState = DialogState(),
            userProfileData = //Loading
            Success(
                data = Triple(
                    first = UserData(
                        authSessionToken = AuthSessionToken(),
                        oAuth2UserMetadata = Google(
                            email = "thomas200593@gmail.com",
                            name = "Thomas Richard",
                            emailVerified = "true",
                            pictureUri = "",
                            expiredAt = "9999199999"
                        )
                    ),
                    second = BizProfileShort(
                        seqId = 0,
                        genId = ULID.randomULID(),
                        bizName = BizName(
                            legalName = "PT Company",
                            commonName = "My Company"
                        ),
                        bizIndustry = Industries(
                            identityKey = "biz_industry_00001",
                            additionalInfo = "Construction Industry"
                        ),
                        bizLegalType = LegalType(identifierKey = ""),
                        bizTaxation = Taxation(identifierKey = ""),
                        auditTrail = AuditTrail()
                    ),
                    third = ConfigCurrent()
                )
            )
        )
    )
}
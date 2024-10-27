package com.thomas200593.mini_retail_app.features.user_profile.ui

import android.content.pm.ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
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
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
import com.thomas200593.mini_retail_app.core.data.local.database.entity_common.LegalDocumentType
import com.thomas200593.mini_retail_app.core.data.local.database.entity_common.LegalType
import com.thomas200593.mini_retail_app.core.data.local.database.entity_common.Taxation
import com.thomas200593.mini_retail_app.core.design_system.util.HlpCountry
import com.thomas200593.mini_retail_app.core.design_system.util.HlpStringArray.Handler.StringArrayResource
import com.thomas200593.mini_retail_app.core.design_system.util.HlpStringArray.StringArrayResources.BizIndustries
import com.thomas200593.mini_retail_app.core.design_system.util.HlpStringArray.StringArrayResources.BizLegalType
import com.thomas200593.mini_retail_app.core.ui.common.CustomIcons
import com.thomas200593.mini_retail_app.core.ui.common.CustomThemes
import com.thomas200593.mini_retail_app.core.ui.component.CustomButton.Common.AppIconButton
import com.thomas200593.mini_retail_app.core.ui.component.CustomDialog.AlertDialogContext
import com.thomas200593.mini_retail_app.core.ui.component.CustomDialog.AppAlertDialog
import com.thomas200593.mini_retail_app.core.ui.component.CustomPanel.TextContentWithIcon
import com.thomas200593.mini_retail_app.core.ui.component.CustomPanel.ThreeRowCardItem
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
import kotlinx.datetime.Instant
import ulid.ULID

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
            vm.onEvent(BtnAppConfigEvents.OnClick).also {
                coroutineScope.launch { stateApp.navController.navToAppConfig() }
            }
        },
        onNavToBizProfile = {
            val navOptions = navOptions{ launchSingleTop=true; restoreState=true }
            vm.onEvent(BtnBizProfileEvents.OnClick).also {
                coroutineScope.launch { stateApp.navController.navToBiz(navOptions, DestBiz.BIZ_PROFILE) } 
            }
        },
        dlgBtnSignOutOnClick = {
            ManagerWorkSessionMonitor.terminate(appContext)
            vm.onEvent(BtnSignOutEvents.OnClick).also{
                coroutineScope.launch { stateApp.navController.navToAuth() }
            }
        }
    )
}

@Composable
private fun ScrUserProfile(
    uiState: UiState,
    onNavToAppConfig: () -> Unit,
    onNavToBizProfile: () -> Unit,
    dlgBtnSignOutOnClick: () -> Unit
) {
    HandleDialogs(
        uiState = uiState,
        dlgBtnSignOutOnClick = dlgBtnSignOutOnClick
    )
    ScreenContent(
        uiState = uiState,
        onNavToBizProfile = onNavToBizProfile,
        onNavToAppConfig = onNavToAppConfig,
        dlgBtnSignOutOnClick = dlgBtnSignOutOnClick
    )
}

@Composable
private fun HandleDialogs(
    uiState: UiState,
    dlgBtnSignOutOnClick: () -> Unit
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
                onClick = dlgBtnSignOutOnClick,
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
    dlgBtnSignOutOnClick: () -> Unit
) {
    val lazyListState = rememberLazyListState()
    val isAtTop by remember { derivedStateOf { lazyListState.firstVisibleItemIndex == 0 } }
    val rowHeight by animateDpAsState(
        targetValue = if(isAtTop && uiState.userProfileData is Success) 130.dp else 56.dp,
        animationSpec = tween(300),
        label = String()
    )
    val imageSize by animateDpAsState(
        targetValue = if(isAtTop && uiState.userProfileData is Success) 70.dp else 40.dp,
        animationSpec = tween(300),
        label = String()
    )
    val colImageWeight by animateFloatAsState(
        targetValue = if(isAtTop && uiState.userProfileData is Success) 0.3f else 0.2f,
        animationSpec = tween(300),
        label = String()
    )
    val colInfoWeight by animateFloatAsState(
        targetValue = if(isAtTop && uiState.userProfileData is Success) 0.7f else 0.8f,
        animationSpec = tween(300),
        label = String()
    )
    val leftHeaderVerticalAlignment by remember {
        derivedStateOf {
            if (isAtTop && uiState.userProfileData is Success) Alignment.CenterVertically
            else Alignment.Top
        }
    }
    val nameFontSize by animateFloatAsState(
        targetValue =
            if (isAtTop && uiState.userProfileData is Success) MaterialTheme.typography.bodyLarge.fontSize.value
            else MaterialTheme.typography.titleMedium.fontSize.value,
        animationSpec = tween(durationMillis = 300),
        label = String()
    )

    Surface(Modifier.fillMaxSize()) {
        Column(Modifier.fillMaxSize()) {
            PartHeader(
                uiState = uiState,
                isAtTop = isAtTop,
                imageSize = imageSize,
                rowHeight = rowHeight,
                colImageWeight = colImageWeight,
                colInfoWeight = colInfoWeight,
                onNavToAppConfig = onNavToAppConfig,
                dlgBtnSignOutOnClick = dlgBtnSignOutOnClick,
                leftHeaderVerticalAlignment = leftHeaderVerticalAlignment,
                nameFontSize = nameFontSize
            )
            LazyColumn(
                modifier = Modifier.weight(1.0f),
                state = lazyListState
            ) {
                item {
                    AnimatedVisibility(
                        visible = isAtTop,
                        enter = fadeIn() + expandHorizontally(),
                        exit = fadeOut() + shrinkHorizontally()
                    ) { HorizontalDivider(thickness = 2.dp, color = colorResource(R.color.charcoal_gray)) }
                }
                item {
                    PartBizProfileShort(
                        uiState = uiState,
                        onNavToBizProfile = onNavToBizProfile
                    )
                }
                item { PartSignOut(dlgBtnSignOutOnClick = dlgBtnSignOutOnClick) }
            }
        }
    }
}

@Composable
private fun PartHeader(
    uiState: UiState,
    isAtTop: Boolean,
    rowHeight: Dp,
    imageSize: Dp,
    colImageWeight: Float,
    colInfoWeight: Float,
    onNavToAppConfig: () -> Unit,
    dlgBtnSignOutOnClick: () -> Unit,
    leftHeaderVerticalAlignment: Alignment.Vertical,
    nameFontSize: Float
) {
    val targetColor1 =
        if (isAtTop && uiState.userProfileData is Success) MaterialTheme.colorScheme.surface.copy(0.3f)
        else MaterialTheme.colorScheme.tertiaryContainer.copy(0.3f)
    val targetColor2 =
        if (isAtTop && uiState.userProfileData is Success) MaterialTheme.colorScheme.tertiaryContainer.copy(0.3f)
        else MaterialTheme.colorScheme.surface.copy(0.3f)
    val animatedColor1 by animateColorAsState(targetValue = targetColor1, label = String())
    val animatedColor2 by animateColorAsState(targetValue = targetColor2, label = String())

    val gradientBrush = Brush.radialGradient(
        colors = listOf(animatedColor1, animatedColor2), radius = 500f, center = Offset.Infinite
    )
    Row(modifier = Modifier.fillMaxWidth().height(rowHeight).background(gradientBrush)) {
        PartCurrentUserSession(
            uiState = uiState,
            modifier = Modifier.weight(0.9f).padding(start = 8.dp, top = 8.dp, bottom = 8.dp, end = 4.dp),
            isAtTop = isAtTop,
            colImageWeight = colImageWeight,
            imageSize = imageSize,
            colInfoWeight = colInfoWeight,
            dlgBtnSignOutOnClick = dlgBtnSignOutOnClick,
            leftHeaderVerticalAlignment = leftHeaderVerticalAlignment,
            nameFontSize = nameFontSize
        )
        PartAppConfig(
            modifier = Modifier.weight(0.1f).padding(start = 4.dp, top = 8.dp, bottom = 8.dp, end = 8.dp),
            btnAppConfigOnClick = onNavToAppConfig
        )
    }
}

@Composable
private fun PartCurrentUserSession(
    uiState: UiState,
    modifier: Modifier,
    isAtTop: Boolean,
    colImageWeight: Float,
    imageSize: Dp,
    colInfoWeight: Float,
    dlgBtnSignOutOnClick: () -> Unit,
    leftHeaderVerticalAlignment: Alignment.Vertical,
    nameFontSize: Float
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = leftHeaderVerticalAlignment
    ) {
        when(uiState.userProfileData) {
            Idle -> Unit
            Loading -> PartCurrentUserSessionLoading()
            is Success -> {
                val userData = uiState.userProfileData.data.first
                when(val provider = userData.authSessionToken?.authProvider) {
                    GOOGLE -> UserProfileGoogle(
                        isAtTop = isAtTop,
                        imageSize = imageSize,
                        colImageModifier = Modifier.weight(colImageWeight),
                        colInfoModifier = Modifier.weight(colInfoWeight),
                        provider = provider,
                        userData = userData.oAuth2UserMetadata as Google,
                        nameFontSize = nameFontSize
                    )
                    null -> dlgBtnSignOutOnClick()
                }
            }
        }
    }
}

@Composable
private fun PartCurrentUserSessionLoading() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) { CircularProgressIndicator() }
}

@Composable
private fun UserProfileGoogle(
    isAtTop: Boolean,
    imageSize: Dp,
    colImageModifier: Modifier,
    colInfoModifier: Modifier,
    provider: OAuthProvider,
    userData: Google,
    nameFontSize: Float
) {
    Column(
        modifier = colImageModifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current).crossfade(250)
                .data(data = userData.pictureUri).transformations(CircleCropTransformation()).build(),
            contentDescription = null,
            modifier = Modifier.border(2.dp, colorResource(R.color.charcoal_gray), CircleShape).size(imageSize),
            contentScale = ContentScale.Crop
        )
    }
    Column(
        modifier = colInfoModifier,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = userData.name,
            fontSize = nameFontSize.sp,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            textAlign = TextAlign.Start,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.fillMaxWidth()
        )
        AnimatedVisibility(
            visible = !isAtTop,
            enter = fadeIn() + expandHorizontally(),
            exit = fadeOut() + shrinkHorizontally()
        ) { HorizontalDivider(thickness = 2.dp, color = colorResource(R.color.charcoal_gray)) }
        AnimatedVisibility (
            visible = isAtTop,
            enter = fadeIn() + expandVertically(),
            exit = fadeOut() + shrinkVertically()
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                TextContentWithIcon(
                    icon = Icons.Default.Email,
                    iconBoxColor = Color.Transparent,
                    iconTint = MaterialTheme.colorScheme.onSurface,
                    text = userData.email,
                    fontSize = MaterialTheme.typography.labelLarge.fontSize,
                    iconWidthRatio = 0.1f,
                    textWidthRatio = 0.9f
                )
                TextContentWithIcon(
                    icon = Icons.Default.Check,
                    iconBoxColor = Color.Transparent,
                    iconTint = MaterialTheme.colorScheme.onSurface,
                    text = when(userData.emailVerified){
                        "true" -> stringResource(id = R.string.str_email_verified)
                        "false" -> stringResource(id = R.string.str_email_not_verified)
                        else -> stringResource(id = R.string.str_email_not_verified)
                    },
                    fontSize = MaterialTheme.typography.labelLarge.fontSize,
                    iconWidthRatio = 0.1f,
                    textWidthRatio = 0.9f
                )
                TextContentWithIcon(
                    icon = Icons.Default.Lock,
                    iconBoxColor = Color.Transparent,
                    iconTint = MaterialTheme.colorScheme.onSurface,
                    text = provider.title,
                    fontSize = MaterialTheme.typography.labelLarge.fontSize,
                    iconWidthRatio = 0.1f,
                    textWidthRatio = 0.9f
                )
                TextContentWithIcon(
                    icon = ImageVector.vectorResource(CustomIcons.Auth.session_expire),
                    iconBoxColor = Color.Transparent,
                    iconTint = MaterialTheme.colorScheme.onSurface,
                    text = Instant.fromEpochSeconds(userData.expiredAt.toLong()).toString(),
                    fontSize = MaterialTheme.typography.labelLarge.fontSize,
                    iconWidthRatio = 0.1f,
                    textWidthRatio = 0.9f
                )
            }
        }
    }
}

@Composable
private fun PartAppConfig(
    modifier: Modifier = Modifier,
    btnAppConfigOnClick: () -> Unit
) {
    Row(
        modifier = modifier.fillMaxHeight(1.0f),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.Top,
    ) {
        Surface(
            modifier = Modifier,
            onClick = btnAppConfigOnClick,
            color = Color.Transparent
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(CustomIcons.Setting.settings),
                contentDescription = null,
                modifier = Modifier.size(ButtonDefaults.IconSize),
                tint = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Composable
private fun PartBizProfileShort(uiState: UiState, onNavToBizProfile: () -> Unit) {
    when(uiState.userProfileData) {
        Idle, Loading -> CircularProgressIndicator()
        is Success -> {
            var expanded by remember { mutableStateOf(false) }
            val bizProfileShort = uiState.userProfileData.data.second
            Surface(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                shape = MaterialTheme.shapes.medium,
                color = MaterialTheme.colorScheme.secondaryContainer
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth().padding(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    ThreeRowCardItem(
                        cardBorder = null,
                        cardColor = Color.Transparent,
                        firstRowContent = {
                            Surface(
                                modifier = Modifier.size(ButtonDefaults.IconSize),
                                color = MaterialTheme.colorScheme.secondaryContainer
                            ) {
                                Icon(
                                    imageVector = ImageVector.vectorResource(CustomIcons.Business.business_profile),
                                    contentDescription = null
                                )
                            }
                        },
                        secondRowContent = {
                            Text(
                                text = stringResource(R.string.str_business_profile),
                                fontSize = MaterialTheme.typography.titleMedium.fontSize,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center
                            )
                        },
                        thirdRowContent = {
                            Surface(
                                modifier = Modifier.fillMaxWidth().size(ButtonDefaults.IconSize),
                                color = MaterialTheme.colorScheme.secondaryContainer,
                                onClick = { expanded = expanded.not() }
                            ) {
                                Icon(
                                    imageVector =
                                        if(expanded) Icons.Default.KeyboardArrowUp
                                        else Icons.Default.KeyboardArrowDown,
                                    contentDescription = null
                                )
                            }
                        }
                    )

                    AnimatedVisibility(visible = expanded) {
                        Column(
                            modifier = Modifier.fillMaxWidth().padding(8.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            HorizontalDivider(thickness = 2.dp, color = colorResource(R.color.charcoal_gray))

                            Text(
                                text = stringResource(R.string.str_biz_name),
                                fontSize = MaterialTheme.typography.titleMedium.fontSize,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center
                            )

                            bizProfileShort.bizName.legalName.let {
                                Column(modifier = Modifier.fillMaxWidth()) {
                                    Text(
                                        text = stringResource(R.string.str_company_legal_name),
                                        fontSize = MaterialTheme.typography.labelSmall.fontSize
                                    )
                                    Text(text = it, fontWeight = FontWeight.Bold)
                                }
                            }

                            bizProfileShort.bizName.commonName.let {
                                Column(modifier = Modifier.fillMaxWidth()) {
                                    Text(
                                        text = stringResource(R.string.str_company_common_name),
                                        fontSize = MaterialTheme.typography.labelSmall.fontSize
                                    )
                                    Text(text = it, fontWeight = FontWeight.Bold)
                                }
                            }

                            HorizontalDivider(thickness = 2.dp, color = colorResource(R.color.charcoal_gray))

                            Text(
                                text = stringResource(R.string.str_biz_industry),
                                fontSize = MaterialTheme.typography.titleMedium.fontSize,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center
                            )

                            bizProfileShort.bizIndustry.identityKey.let {
                                StringArrayResource(BizIndustries).findByKey(it)?.let { industry ->
                                    Column(modifier = Modifier.fillMaxWidth()) {
                                        Text(
                                            text = stringResource(R.string.str_biz_industry),
                                            fontSize = MaterialTheme.typography.labelSmall.fontSize
                                        )
                                        Text(text = industry, fontWeight = FontWeight.Bold)
                                    }
                                }
                            }

                            bizProfileShort.bizIndustry.additionalInfo.takeIf { it.isNotBlank() }
                                ?.let { data ->
                                    Column(modifier = Modifier.fillMaxWidth()) {
                                        Text(
                                            text = stringResource(R.string.str_additional_info),
                                            fontSize = MaterialTheme.typography.labelSmall.fontSize
                                        )
                                        Text(text = data, fontWeight = FontWeight.Bold)
                                    }
                                }

                            HorizontalDivider(thickness = 2.dp, color = colorResource(R.color.charcoal_gray))

                            Text(
                                text = stringResource(R.string.str_biz_legal),
                                fontSize = MaterialTheme.typography.titleMedium.fontSize,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center
                            )

                            bizProfileShort.bizLegalType.identifierKey.let {
                                StringArrayResource(BizLegalType).findByKey(it)?.let { legalType ->
                                    Column(modifier = Modifier.fillMaxWidth()) {
                                        Text(
                                            text = stringResource(R.string.str_biz_legal),
                                            fontSize = MaterialTheme.typography.labelSmall.fontSize
                                        )
                                        Text(text = legalType, fontWeight = FontWeight.Bold)
                                    }
                                }
                            }

                            bizProfileShort.bizLegalType.additionalInfo.takeIf { it.isNotBlank() }
                                ?.let { data ->
                                    Column(modifier = Modifier.fillMaxWidth()) {
                                        Text(
                                            text = stringResource(R.string.str_additional_info),
                                            fontSize = MaterialTheme.typography.labelSmall.fontSize
                                        )
                                        Text(text = data, fontWeight = FontWeight.Bold)
                                    }
                                }

                            Text(bizProfileShort.bizLegalType.legalDocumentType?.identifierKey.orEmpty())
                            Text(bizProfileShort.bizLegalType.legalDocumentType?.additionalInfo.orEmpty())
                            Text(bizProfileShort.bizTaxation.identifierKey)
                            Text(bizProfileShort.bizTaxation.taxIdDocNumber)
                            Text(bizProfileShort.bizTaxation.taxIssuerCountry.toString())
                            Text(bizProfileShort.bizTaxation.taxRatePercentage.toString())
                            Text(bizProfileShort.bizTaxation.taxIncluded.toString())
                        }
                    }
                    
                    AppIconButton(
                        onClick = onNavToBizProfile,
                        icon = ImageVector.vectorResource(CustomIcons.Business.business_profile),
                        text = stringResource(R.string.str_detail)
                    )
                }
            }
        }
    }
}

@Composable
private fun PartSignOut(
    dlgBtnSignOutOnClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(1.0f),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        AppIconButton(
            modifier = Modifier.padding(8.dp),
            onClick = dlgBtnSignOutOnClick,
            icon = Icons.AutoMirrored.Filled.ExitToApp,
            text = stringResource(id = R.string.str_auth_sign_out),
            containerColor = MaterialTheme.colorScheme.errorContainer,
            contentColor = MaterialTheme.colorScheme.onErrorContainer
        )
    }
}

@Composable
@Preview
private fun Preview() = CustomThemes.ApplicationTheme {
    ScrUserProfile(
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
                        bizLegalType = LegalType(
                            identifierKey = "biz_legal_type_00001",
                            additionalInfo = "Test Biz Legal Type",
                            legalDocumentType = LegalDocumentType(
                                identifierKey = "biz_legal_doc_type_00001",
                                additionalInfo = "Test Biz Legal Doc Type"
                            )
                        ),
                        bizTaxation = Taxation(
                            identifierKey = "biz_tax_type_00001",
                            taxIdDocNumber = "1234567890",
                            taxIssuerCountry = HlpCountry.COUNTRY_INDONESIA,
                            taxRatePercentage = 0.11,
                            taxIncluded = true
                        ),
                        auditTrail = AuditTrail()
                    ),
                    third = ConfigCurrent()
                )
            )
        ),
        onNavToAppConfig = {},
        onNavToBizProfile = {},
        dlgBtnSignOutOnClick = {}
    )
}
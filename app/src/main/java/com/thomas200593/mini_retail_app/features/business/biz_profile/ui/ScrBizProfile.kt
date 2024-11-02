package com.thomas200593.mini_retail_app.features.business.biz_profile.ui

import android.content.pm.ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowDown
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.thomas200593.mini_retail_app.R
import com.thomas200593.mini_retail_app.app.navigation.ScrGraphs
import com.thomas200593.mini_retail_app.app.ui.LocalStateApp
import com.thomas200593.mini_retail_app.app.ui.StateApp
import com.thomas200593.mini_retail_app.core.data.local.database.entity_common.Address
import com.thomas200593.mini_retail_app.core.data.local.database.entity_common.Contact
import com.thomas200593.mini_retail_app.core.data.local.database.entity_common.Link
import com.thomas200593.mini_retail_app.core.data.local.session.SessionState
import com.thomas200593.mini_retail_app.core.ui.common.CustomIcons.Country.country
import com.thomas200593.mini_retail_app.core.ui.common.CustomIcons.Emotion.neutral
import com.thomas200593.mini_retail_app.core.ui.component.app_bar.CustomAppBar.ProvideTopAppBarAction
import com.thomas200593.mini_retail_app.core.ui.component.app_bar.CustomAppBar.ProvideTopAppBarNavigationIcon
import com.thomas200593.mini_retail_app.core.ui.component.app_bar.CustomAppBar.ProvideTopAppBarTitle
import com.thomas200593.mini_retail_app.core.ui.component.CustomButton.Common.AppIconButton
import com.thomas200593.mini_retail_app.core.ui.component.dialog.CustomDialog.AlertDialogContext
import com.thomas200593.mini_retail_app.core.ui.component.dialog.CustomDialog.AppAlertDialog
import com.thomas200593.mini_retail_app.core.ui.component.CustomScreenUtil.LockScreenOrientation
import com.thomas200593.mini_retail_app.features.business.biz_profile.entity.BizProfile
import com.thomas200593.mini_retail_app.features.business.biz_profile.ui.VMBizProfile.UiEvents.ButtonEvents.BizIdNameBtnEvents
import com.thomas200593.mini_retail_app.features.business.biz_profile.ui.VMBizProfile.UiEvents.ButtonEvents.BtnNavBackEvents
import com.thomas200593.mini_retail_app.features.business.biz_profile.ui.VMBizProfile.UiEvents.ButtonEvents.BtnScrDescEvents
import com.thomas200593.mini_retail_app.features.business.biz_profile.ui.VMBizProfile.UiEvents.DialogEvents.DlgDenySessionEvents
import com.thomas200593.mini_retail_app.features.business.biz_profile.ui.VMBizProfile.UiEvents.OnOpenEvents
import com.thomas200593.mini_retail_app.features.business.biz_profile.ui.VMBizProfile.UiStateBizProfile.Loading
import com.thomas200593.mini_retail_app.features.business.biz_profile.ui.VMBizProfile.UiStateBizProfile.Success
import kotlinx.coroutines.launch

@Composable
fun ScrBizProfile(
    vm: VMBizProfile = hiltViewModel(),
    stateApp: StateApp = LocalStateApp.current
) {
    LockScreenOrientation(SCREEN_ORIENTATION_PORTRAIT)

    val coroutineScope = rememberCoroutineScope()
    val uiState by vm.uiState.collectAsStateWithLifecycle()
    val sessionState by stateApp.isSessionValid.collectAsStateWithLifecycle()
    val currentScreen = ScrGraphs.getByRoute(stateApp.destCurrent)

    LaunchedEffect(sessionState, currentScreen)
    { currentScreen?.let { vm.onEvent(OnOpenEvents(sessionState, it)) } }

    ScrBizProfile(
        uiState = uiState,
        currentScreen = currentScreen,
        onNavigateBack = {
            vm.onEvent(BtnNavBackEvents.OnClick)
                .also { coroutineScope.launch { stateApp.onNavUp() } }
        },
        onShowScrDesc = { vm.onEvent(BtnScrDescEvents.OnClick) },
        onDismissDlgScrDesc = { vm.onEvent(BtnScrDescEvents.OnDismiss) },
        onUpdateBizIdName = {
            currentScreen?.let {
                when(sessionState) {
                    SessionState.Loading -> Unit
                    is SessionState.Invalid ->
                        if(it.usesAuth) vm.onEvent(DlgDenySessionEvents.OnShow)
                        else vm.onEvent(BizIdNameBtnEvents.BtnUpdateEvents.OnClick)
                            .also { coroutineScope.launch { /*TODO*/ } }
                    is SessionState.Valid -> vm.onEvent(BizIdNameBtnEvents.BtnUpdateEvents.OnClick)
                        .also { coroutineScope.launch { /*TODO*/ } }
                }
            }
        },
        onResetBizIdName = { /*TODO*/ },
        onUpdateBizIdIndustry = { /*TODO*/ },
        onResetBizIdIndustry = { /*TODO*/ },
        onUpdateBizIdLegal = { /*TODO*/ },
        onResetBizIdLegal = { /*TODO*/ },
        onUpdateBizIdTaxation = { /*TODO*/ },
        onResetBizIdTaxation = { /*TODO*/ },
        onCreateBizAddress = { /*TODO*/ },
        onUpdateBizAddress = { /*TODO*/ },
        onDeleteBizAddress = { /*TODO*/ },
        onDeleteAllBizAddresses = { /*TODO*/ },
        onCreateBizContact = { /*TODO*/ },
        onUpdateBizContact = { /*TODO*/ },
        onDeleteBizContact = { /*TODO*/ },
        onDeleteAllBizContacts = { /*TODO*/ },
        onCreateBizLink = { /*TODO*/ },
        onUpdateBizLink = { /*TODO*/ },
        onDeleteBizLink = { /*TODO*/ },
        onDeleteAllBizLinks = { /*TODO*/ }
    )
}

@Composable
private fun ScrBizProfile(
    uiState: VMBizProfile.UiState,
    currentScreen: ScrGraphs?,
    //Btn Nav Back
    onNavigateBack: () -> Unit,
    //Screen Description
    onShowScrDesc: (String) -> Unit, onDismissDlgScrDesc: () -> Unit,
    //BizName
    onUpdateBizIdName: () -> Unit, onResetBizIdName: () -> Unit,
    //BizIndustry
    onUpdateBizIdIndustry: () -> Unit, onResetBizIdIndustry: () -> Unit,
    //BizLegal
    onUpdateBizIdLegal: () -> Unit, onResetBizIdLegal: () -> Unit,
    //BizTaxation
    onUpdateBizIdTaxation: () -> Unit, onResetBizIdTaxation: () -> Unit,
    //BizAddress
    onCreateBizAddress: () -> Unit, onUpdateBizAddress: (Address) -> Unit,
    onDeleteBizAddress: (Address) -> Unit, onDeleteAllBizAddresses: () -> Unit,
    //BizContact
    onCreateBizContact: () -> Unit, onUpdateBizContact: (Contact) -> Unit,
    onDeleteBizContact: (Contact) -> Unit, onDeleteAllBizContacts: () -> Unit,
    //BizLink
    onCreateBizLink: () -> Unit, onUpdateBizLink: (Link) -> Unit,
    onDeleteBizLink: (Link) -> Unit, onDeleteAllBizLinks: () -> Unit
) {
    currentScreen?.let {
        HandleDialogs(
            uiState = uiState,
            currentScreen = it,
            onDismissDlgScrDesc = onDismissDlgScrDesc
        )
        TopAppBar(
            scrGraphs = it,
            onNavigateBack = onNavigateBack,
            onShowScrDesc = onShowScrDesc
        )
    }
    when (uiState.bizProfile) {
        Loading -> Unit
        is Success -> ScreenContent(
            bizProfile = uiState.bizProfile.bizProfile,
            onUpdateBizIdName = onUpdateBizIdName,
            onResetBizIdName = onResetBizIdName,
            onUpdateBizIdIndustry = onUpdateBizIdIndustry,
            onResetBizIdIndustry = onResetBizIdIndustry,
            onUpdateBizIdLegal = onUpdateBizIdLegal,
            onResetBizIdLegal = onResetBizIdLegal,
            onUpdateBizIdTaxation = onUpdateBizIdTaxation,
            onResetBizIdTaxation = onResetBizIdTaxation,
            onCreateBizAddress = onCreateBizAddress,
            onUpdateBizAddress = onUpdateBizAddress,
            onDeleteBizAddress = onDeleteBizAddress,
            onDeleteAllBizAddresses = onDeleteAllBizAddresses,
            onCreateBizContact = onCreateBizContact,
            onUpdateBizContact = onUpdateBizContact,
            onDeleteBizContact = onDeleteBizContact,
            onDeleteAllBizContacts = onDeleteAllBizContacts,
            onCreateBizLink = onCreateBizLink,
            onUpdateBizLink = onUpdateBizLink,
            onDeleteBizLink = onDeleteBizLink,
            onDeleteAllBizLinks = onDeleteAllBizLinks
        )
    }
}

@Composable
private fun HandleDialogs(
    uiState: VMBizProfile.UiState,
    currentScreen: ScrGraphs,
    onDismissDlgScrDesc: () -> Unit
) {
    AppAlertDialog(
        showDialog = uiState.dialogState.dlgLoading,
        dialogContext = AlertDialogContext.INFORMATION,
        showTitle = true,
        title = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) { CircularProgressIndicator() }
        },
        showBody = true,
        body = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) { Text(text = stringResource(id = R.string.str_loading)) }
        }
    )
    AppAlertDialog(
        showDialog = uiState.dialogState.dlgScrDesc,
        dialogContext = AlertDialogContext.INFORMATION,
        showIcon = true,
        showTitle = true,
        title = { currentScreen.title?.let { Text(text = stringResource(id = it)) } },
        showBody = true,
        body = {
            currentScreen.description?.let {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) { Text(text = stringResource(id = it)) }
            }
        },
        useDismissButton = true,
        dismissButton = {
            AppIconButton(
                onClick = onDismissDlgScrDesc,
                icon = Icons.Default.Close,
                text = stringResource(id = R.string.str_close)
            )
        }
    )
}

@Composable
private fun TopAppBar(
    scrGraphs: ScrGraphs,
    onNavigateBack: () -> Unit,
    onShowScrDesc: (String) -> Unit
) {
    ProvideTopAppBarNavigationIcon {
        Surface(
            onClick = onNavigateBack,
            modifier = Modifier
        ) {
            Icon(
                modifier = Modifier,
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                contentDescription = null
            )
        }
    }
    ProvideTopAppBarTitle {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            scrGraphs.iconRes?.let {
                Icon(
                    modifier = Modifier.sizeIn(maxHeight = ButtonDefaults.IconSize),
                    imageVector = ImageVector.vectorResource(id = it),
                    contentDescription = null
                )
            }
            scrGraphs.title?.let {
                Text(
                    text = stringResource(id = it),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
    scrGraphs.description?.let {
        ProvideTopAppBarAction {
            Row(
                modifier = Modifier,
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                val desc = stringResource(id = it)
                Surface(
                    onClick = { onShowScrDesc(desc) },
                    modifier = Modifier.sizeIn(maxHeight = ButtonDefaults.IconSize)
                ) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = null
                    )
                }
            }
        }
    }
}

@Composable
private fun ScreenContent(
    bizProfile: BizProfile,
    //BizName
    onUpdateBizIdName: () -> Unit, onResetBizIdName: () -> Unit,
    //BizIndustry
    onUpdateBizIdIndustry: () -> Unit, onResetBizIdIndustry: () -> Unit,
    //BizLegal
    onUpdateBizIdLegal: () -> Unit, onResetBizIdLegal: () -> Unit,
    //BizTaxation
    onUpdateBizIdTaxation: () -> Unit, onResetBizIdTaxation: () -> Unit,
    //BizAddress
    onCreateBizAddress: () -> Unit, onUpdateBizAddress: (Address) -> Unit,
    onDeleteBizAddress: (Address) -> Unit, onDeleteAllBizAddresses: () -> Unit,
    //BizContact
    onCreateBizContact: () -> Unit, onUpdateBizContact: (Contact) -> Unit,
    onDeleteBizContact: (Contact) -> Unit, onDeleteAllBizContacts: () -> Unit,
    //BizLink
    onCreateBizLink: () -> Unit, onUpdateBizLink: (Link) -> Unit,
    onDeleteBizLink: (Link) -> Unit, onDeleteAllBizLinks: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.Top)
    ) {
        /*Biz Identity*/
        BizIdentitySection(
            bizProfile = bizProfile,
            onUpdateBizIdName = onUpdateBizIdName,
            onResetBizIdName = onResetBizIdName,
            onUpdateBizIdIndustry = onUpdateBizIdIndustry,
            onResetBizIdIndustry = onResetBizIdIndustry,
            onUpdateBizIdLegal = onUpdateBizIdLegal,
            onResetBizIdLegal = onResetBizIdLegal,
            onUpdateBizIdTaxation = onUpdateBizIdTaxation,
            onResetBizIdTaxation = onResetBizIdTaxation
        )
        /*Biz Addresses*/
        BizAddressesSection(
            addresses = bizProfile.addresses,
            onCreateBizAddress = onCreateBizAddress,
            onUpdateBizAddress = onUpdateBizAddress,
            onDeleteBizAddress = onDeleteBizAddress,
            onDeleteAllBizAddresses = onDeleteAllBizAddresses
        )
        /*Biz Contacts*/
        BizContactsSection(
            contacts = bizProfile.contacts,
            onCreateBizContact = onCreateBizContact, onUpdateBizContact = onUpdateBizContact,
            onDeleteBizContact = onDeleteBizContact, onDeleteAllBizContacts = onDeleteAllBizContacts
        )
        /*Biz Links*/
        BizLinksSection(
            links = bizProfile.links,
            onCreateBizLink = onCreateBizLink, onUpdateBizLink = onUpdateBizLink,
            onDeleteBizLink = onDeleteBizLink, onDeleteAllBizLinks = onDeleteAllBizLinks
        )
    }
}

@Composable
private fun BizIdentitySection(
    bizProfile: BizProfile,
    onUpdateBizIdName: () -> Unit, onResetBizIdName: () -> Unit,
    onUpdateBizIdIndustry: () -> Unit, onResetBizIdIndustry: () -> Unit,
    onUpdateBizIdLegal: () -> Unit, onResetBizIdLegal: () -> Unit,
    onUpdateBizIdTaxation: () -> Unit, onResetBizIdTaxation: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(1.0f),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(modifier = Modifier.weight(0.1f)) {
                    Icon(
                        modifier = Modifier.size(20.dp),
                        imageVector = ImageVector.vectorResource(id = country),
                        contentDescription = null
                    )
                }
                Text(
                    text = stringResource(R.string.str_biz_identity),
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    modifier = Modifier
                        .weight(0.8f)
                        .fillMaxWidth()
                )
                Surface(
                    modifier = Modifier
                        .weight(0.1f)
                        .size(20.dp),
                    color = MaterialTheme.colorScheme.tertiaryContainer,
                    onClick = { expanded = expanded.not() }
                ) {
                    Icon(
                        imageVector =
                        if (expanded) Icons.Default.KeyboardArrowDown
                        else Icons.AutoMirrored.Default.KeyboardArrowRight,
                        contentDescription = null
                    )
                }
            }

            if (expanded) {
                HorizontalDivider()
                /*Gen ID*/
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = stringResource(R.string.str_gen_id),
                        maxLines = 1,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.labelSmall,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = bizProfile.genId,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(1.0f),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Surface(modifier = Modifier.weight(0.1f), color = Color.Transparent) {
                                Icon(
                                    modifier = Modifier.size(20.dp),
                                    imageVector = ImageVector.vectorResource(id = country),
                                    contentDescription = null
                                )
                            }
                            Text(
                                text = stringResource(R.string.str_biz_name),
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Start,
                                overflow = TextOverflow.Ellipsis,
                                maxLines = 1,
                                modifier = Modifier
                                    .weight(0.8f)
                                    .fillMaxWidth()
                            )
                            Surface(
                                onClick = { onUpdateBizIdName() },
                                modifier = Modifier
                                    .weight(0.1f)
                                    .size(20.dp),
                                color = Color.Transparent
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Edit,
                                    contentDescription = null
                                )
                            }
                            Surface(
                                onClick = { onResetBizIdName() },
                                modifier = Modifier
                                    .weight(0.1f)
                                    .size(20.dp),
                                color = Color.Transparent
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Clear,
                                    contentDescription = null
                                )
                            }
                        }

                        HorizontalDivider()

                        bizProfile.bizIdentity.bizName.legalName
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

                        bizProfile.bizIdentity.bizName.commonName
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
                    shape = MaterialTheme.shapes.medium
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(1.0f),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Surface(modifier = Modifier.weight(0.1f), color = Color.Transparent) {
                                Icon(
                                    modifier = Modifier.size(20.dp),
                                    imageVector = ImageVector.vectorResource(id = country),
                                    contentDescription = null
                                )
                            }
                            Text(
                                text = stringResource(R.string.str_biz_industry),
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Start,
                                overflow = TextOverflow.Ellipsis,
                                maxLines = 1,
                                modifier = Modifier
                                    .weight(0.8f)
                                    .fillMaxWidth()
                            )
                            Surface(
                                onClick = { onUpdateBizIdIndustry() },
                                modifier = Modifier
                                    .weight(0.1f)
                                    .size(20.dp),
                                color = Color.Transparent
                            ) {
                                Icon(
                                    modifier = Modifier.size(20.dp),
                                    imageVector = Icons.Default.Edit,
                                    contentDescription = null
                                )
                            }
                            Surface(
                                onClick = { onResetBizIdIndustry() },
                                modifier = Modifier
                                    .weight(0.1f)
                                    .size(20.dp),
                                color = Color.Transparent
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Clear,
                                    contentDescription = null
                                )
                            }
                        }

                        HorizontalDivider()

                        bizProfile.bizIdentity.industries.identityKey.let {
                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalAlignment = Alignment.Start,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = stringResource(R.string.str_biz_industry_id),
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

                        bizProfile.bizIdentity.industries.additionalInfo
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

                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(1.0f),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Surface(modifier = Modifier.weight(0.1f), color = Color.Transparent) {
                                Icon(
                                    modifier = Modifier.size(20.dp),
                                    imageVector = ImageVector.vectorResource(id = country),
                                    contentDescription = null
                                )
                            }
                            Text(
                                text = stringResource(R.string.str_biz_legal),
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Start,
                                overflow = TextOverflow.Ellipsis,
                                maxLines = 1,
                                modifier = Modifier
                                    .weight(0.8f)
                                    .fillMaxWidth()
                            )
                            Surface(
                                onClick = { onUpdateBizIdLegal() },
                                modifier = Modifier
                                    .weight(0.1f)
                                    .size(20.dp),
                                color = Color.Transparent
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Edit,
                                    contentDescription = null
                                )
                            }
                            Surface(
                                onClick = { onResetBizIdLegal() },
                                modifier = Modifier
                                    .weight(0.1f)
                                    .size(20.dp),
                                color = Color.Transparent
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Clear,
                                    contentDescription = null
                                )
                            }
                        }

                        HorizontalDivider()

                        bizProfile.bizIdentity.legalType.identifierKey.let {
                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalAlignment = Alignment.Start,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = stringResource(R.string.str_biz_legal_id),
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

                        bizProfile.bizIdentity.legalType.additionalInfo
                            .takeIf { it.isNotBlank() }?.let {
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

                        if (bizProfile.bizIdentity.legalType.legalDocumentType != null) {
                            bizProfile.bizIdentity.legalType.legalDocumentType.identifierKey.let {
                                Column(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalAlignment = Alignment.Start,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Text(
                                        text = stringResource(R.string.str_biz_legal_doc_type_id),
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

                            bizProfile.bizIdentity.legalType.legalDocumentType.additionalInfo
                                .takeIf { it.isNotBlank() }?.let {
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
                }

                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(1.0f),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Surface(
                                modifier = Modifier.weight(0.1f),
                                color = Color.Transparent
                            ) {
                                Icon(
                                    modifier = Modifier.size(20.dp),
                                    imageVector = ImageVector.vectorResource(id = country),
                                    contentDescription = null
                                )
                            }
                            Text(
                                text = stringResource(R.string.str_biz_taxation),
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Start,
                                overflow = TextOverflow.Ellipsis,
                                maxLines = 1,
                                modifier = Modifier
                                    .weight(0.8f)
                                    .fillMaxWidth()
                            )
                            Surface(
                                onClick = { onUpdateBizIdTaxation() },
                                modifier = Modifier
                                    .weight(0.1f)
                                    .size(20.dp),
                                color = Color.Transparent
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Edit,
                                    contentDescription = null
                                )
                            }
                            Surface(
                                onClick = { onResetBizIdTaxation() },
                                modifier = Modifier
                                    .weight(0.1f)
                                    .size(20.dp),
                                color = Color.Transparent
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Clear,
                                    contentDescription = null
                                )
                            }
                        }

                        HorizontalDivider()

                        bizProfile.bizIdentity.taxation.identifierKey.let {
                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalAlignment = Alignment.Start,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = stringResource(R.string.str_biz_tax_type_id),
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

                        bizProfile.bizIdentity.taxation.taxIdDocNumber
                            .takeIf{ it.isNotBlank() }?.let {
                                Column(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalAlignment = Alignment.Start,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Text(
                                        text = stringResource(R.string.str_biz_tax_id_doc_number),
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

                        bizProfile.bizIdentity.taxation.taxIssuerCountry.let {
                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalAlignment = Alignment.Start,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = stringResource(R.string.str_biz_tax_id_issuer_country),
                                    maxLines = 1,
                                    fontWeight = FontWeight.Bold,
                                    style = MaterialTheme.typography.labelSmall,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Text(
                                    text = it.displayName,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }

                        bizProfile.bizIdentity.taxation.taxRatePercentage.let {
                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalAlignment = Alignment.Start,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = stringResource(R.string.str_biz_tax_rate_percentage),
                                    maxLines = 1,
                                    fontWeight = FontWeight.Bold,
                                    style = MaterialTheme.typography.labelSmall,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Text(
                                    text = "$it %",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }

                        bizProfile.bizIdentity.taxation.taxIncluded.let {
                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalAlignment = Alignment.Start,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = stringResource(R.string.str_biz_tax_included),
                                    maxLines = 1,
                                    fontWeight = FontWeight.Bold,
                                    style = MaterialTheme.typography.labelSmall,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Text(
                                    text =
                                    if (it) stringResource(id = R.string.str_yes)
                                    else stringResource(id = R.string.str_no),
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun BizAddressesSection(
    addresses: List<Address>?,
    onCreateBizAddress: () -> Unit,
    onUpdateBizAddress: (Address) -> Unit,
    onDeleteBizAddress: (Address) -> Unit,
    onDeleteAllBizAddresses: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(1.0f),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(modifier = Modifier.weight(0.1f)) {
                    Icon(
                        modifier = Modifier.size(20.dp),
                        imageVector = ImageVector.vectorResource(id = country),
                        contentDescription = null
                    )
                }
                Text(
                    text = stringResource(R.string.str_biz_addresses),
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    modifier = Modifier
                        .weight(0.8f)
                        .fillMaxWidth()
                )
                Surface(
                    modifier = Modifier
                        .weight(0.1f)
                        .size(20.dp),
                    color = MaterialTheme.colorScheme.tertiaryContainer,
                    onClick = { expanded = expanded.not() }
                ) {
                    Icon(
                        modifier = Modifier,
                        imageVector =
                        if (expanded) Icons.Default.KeyboardArrowDown
                        else Icons.AutoMirrored.Default.KeyboardArrowRight,
                        contentDescription = null
                    )
                }
            }

            if (expanded) {
                if (addresses.isNullOrEmpty()) {
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        shape = MaterialTheme.shapes.medium
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(
                                8.dp,
                                Alignment.CenterVertically
                            )
                        ) {
                            Surface(
                                modifier = Modifier.size(48.dp),
                                contentColor = MaterialTheme.colorScheme.error
                            ) {
                                Icon(
                                    imageVector = ImageVector.vectorResource(id = neutral),
                                    contentDescription = null
                                )
                            }
                            Text(
                                text = stringResource(R.string.str_biz_address_not_set),
                                textAlign = TextAlign.Justify
                            )
                            AppIconButton(
                                onClick = { onCreateBizAddress() },
                                icon = Icons.Default.Add,
                                text = stringResource(R.string.str_add)
                            )
                        }
                    }
                } else {
                    HorizontalDivider()
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(if (addresses.size >= 5) 0.1f else 0.2f),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            if (addresses.size < 5) {
                                Surface(
                                    onClick = { onCreateBizAddress() },
                                    modifier = Modifier
                                        .size(20.dp)
                                        .weight(0.1f)
                                ) {
                                    Icon(
                                        modifier = Modifier,
                                        imageVector = Icons.Default.Add,
                                        contentDescription = null
                                    )
                                }
                            }
                            Surface(
                                onClick = { onDeleteAllBizAddresses() },
                                modifier = Modifier
                                    .size(20.dp)
                                    .weight(0.1f)
                            ) {
                                Icon(
                                    modifier = Modifier,
                                    imageVector = Icons.Default.Clear,
                                    contentDescription = null
                                )
                            }
                        }
                    }
                    HorizontalDivider()
                    addresses.forEach { address ->
                        Surface(
                            modifier = Modifier.fillMaxWidth(),
                            shape = MaterialTheme.shapes.medium,
                            border = BorderStroke(1.dp, colorResource(R.color.charcoal_gray))
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(
                                    8.dp,
                                    Alignment.CenterVertically
                                )
                            ) {
                                //Interaction Buttons
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.End,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row(
                                        modifier = Modifier.weight(0.8f),
                                        horizontalArrangement = Arrangement.Start,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        address.label
                                            .takeIf { it.isNotBlank() }?.let {
                                                Text(
                                                    text = it,
                                                    fontWeight = FontWeight.Bold,
                                                    style = MaterialTheme.typography.titleMedium
                                                )
                                            }
                                    }
                                    Row(
                                        modifier = Modifier.weight(0.2f),
                                        horizontalArrangement = Arrangement.Center,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Surface(
                                            onClick = { onUpdateBizAddress(address) },
                                            modifier = Modifier
                                                .size(20.dp)
                                                .weight(0.1f)
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Edit,
                                                contentDescription = null
                                            )
                                        }
                                        Surface(
                                            onClick = { onDeleteBizAddress(address) },
                                            modifier = Modifier
                                                .size(20.dp)
                                                .weight(0.1f)
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Clear,
                                                contentDescription = null
                                            )
                                        }
                                    }
                                }

                                HorizontalDivider()

                                //Left Row
                                Row(modifier = Modifier.fillMaxWidth(1.0f)) {
                                    Column(
                                        modifier = Modifier
                                            .weight(0.5f)
                                            .padding(8.dp),
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.spacedBy(
                                            8.dp,
                                            Alignment.CenterVertically
                                        )
                                    ) {
                                        //Gen ID
                                        address.genId.let {
                                            Column(
                                                modifier = Modifier.fillMaxWidth(),
                                                horizontalAlignment = Alignment.Start,
                                                verticalArrangement = Arrangement.Center
                                            ) {
                                                Text(
                                                    text = stringResource(R.string.str_gen_id),
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

                                        //Label
                                        address.label
                                            .takeIf{ it.isNotBlank() }?.let {
                                                Column(
                                                    modifier = Modifier.fillMaxWidth(),
                                                    horizontalAlignment = Alignment.Start,
                                                    verticalArrangement = Arrangement.Center
                                                ) {
                                                    Text(
                                                        text = stringResource(R.string.str_address_label),
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

                                        //Street Line
                                        address.streetLine
                                            .takeIf { it.isNotBlank() }?.let {
                                                Column(
                                                    modifier = Modifier.fillMaxWidth(),
                                                    horizontalAlignment = Alignment.Start,
                                                    verticalArrangement = Arrangement.Center
                                                ) {
                                                    Text(
                                                        text = stringResource(R.string.str_address_street_line),
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

                                        //Postal Code
                                        address.postalCode
                                            .takeIf{ it.isNotBlank() }?.let {
                                                Column(
                                                    modifier = Modifier.fillMaxWidth(),
                                                    horizontalAlignment = Alignment.Start,
                                                    verticalArrangement = Arrangement.Center
                                                ) {
                                                    Text(
                                                        text = stringResource(R.string.str_postal_code),
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

                                    //Right Row
                                    Column(
                                        modifier = Modifier
                                            .weight(0.5f)
                                            .padding(8.dp),
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.spacedBy(
                                            8.dp,
                                            Alignment.CenterVertically
                                        )
                                    ) {
                                        //ConfGenCountry
                                        address.country.let {
                                            Column(
                                                modifier = Modifier.fillMaxWidth(),
                                                horizontalAlignment = Alignment.Start,
                                                verticalArrangement = Arrangement.Center
                                            ) {
                                                Text(
                                                    text = stringResource(R.string.str_country),
                                                    maxLines = 1,
                                                    fontWeight = FontWeight.Bold,
                                                    style = MaterialTheme.typography.labelSmall,
                                                    overflow = TextOverflow.Ellipsis
                                                )
                                                Text(
                                                    text = it.displayName,
                                                    style = MaterialTheme.typography.bodyMedium
                                                )
                                            }
                                        }

                                        //Additional Info
                                        address.additionalInfo
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

                                        //Audit Trail
                                        address.auditTrail.modifiedAt.let {
                                            Column(
                                                modifier = Modifier.fillMaxWidth(),
                                                horizontalAlignment = Alignment.Start,
                                                verticalArrangement = Arrangement.Center
                                            ) {
                                                Text(
                                                    text = stringResource(R.string.str_modified_at),
                                                    maxLines = 1,
                                                    fontWeight = FontWeight.Bold,
                                                    style = MaterialTheme.typography.labelSmall,
                                                    overflow = TextOverflow.Ellipsis
                                                )
                                                Text(
                                                    text = it.toString(),
                                                    style = MaterialTheme.typography.bodyMedium
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun BizContactsSection(
    contacts: List<Contact>?,
    onCreateBizContact: () -> Unit,
    onUpdateBizContact: (Contact) -> Unit,
    onDeleteBizContact: (Contact) -> Unit,
    onDeleteAllBizContacts: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(1.0f),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(modifier = Modifier.weight(0.1f)) {
                    Icon(
                        modifier = Modifier.size(20.dp),
                        imageVector = ImageVector.vectorResource(id = country),
                        contentDescription = null
                    )
                }
                Text(
                    text = stringResource(R.string.str_biz_contacts),
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    modifier = Modifier
                        .weight(0.8f)
                        .fillMaxWidth()
                )
                Surface(
                    modifier = Modifier
                        .weight(0.1f)
                        .size(20.dp),
                    color = MaterialTheme.colorScheme.tertiaryContainer,
                    onClick = { expanded = expanded.not() }
                ) {
                    Icon(
                        modifier = Modifier,
                        imageVector =
                        if (expanded) Icons.Default.KeyboardArrowDown
                        else Icons.AutoMirrored.Default.KeyboardArrowRight,
                        contentDescription = null
                    )
                }
            }

            if (expanded) {
                if (contacts.isNullOrEmpty()) {
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        shape = MaterialTheme.shapes.medium,
                        border = BorderStroke(1.dp, colorResource(R.color.charcoal_gray))
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(
                                8.dp,
                                Alignment.CenterVertically
                            )
                        ) {
                            Surface(
                                modifier = Modifier.size(48.dp),
                                contentColor = MaterialTheme.colorScheme.error
                            ) {
                                Icon(
                                    imageVector = ImageVector.vectorResource(id = neutral),
                                    contentDescription = null
                                )
                            }
                            Text(
                                text = stringResource(R.string.str_biz_address_not_set),
                                textAlign = TextAlign.Justify
                            )
                            AppIconButton(
                                onClick = { onCreateBizContact() },
                                icon = Icons.Default.Add,
                                text = stringResource(R.string.str_add)
                            )
                        }
                    }
                } else {
                    HorizontalDivider()
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(if (contacts.size >= 5) 0.1f else 0.2f),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            if (contacts.size < 5) {
                                Surface(
                                    onClick = { onCreateBizContact() },
                                    modifier = Modifier
                                        .size(20.dp)
                                        .weight(0.1f)
                                ) {
                                    Icon(
                                        modifier = Modifier,
                                        imageVector = Icons.Default.Add,
                                        contentDescription = null
                                    )
                                }
                            }
                            Surface(
                                onClick = { onDeleteAllBizContacts() },
                                modifier = Modifier
                                    .size(20.dp)
                                    .weight(0.1f)
                            ) {
                                Icon(
                                    modifier = Modifier,
                                    imageVector = Icons.Default.Clear,
                                    contentDescription = null
                                )
                            }
                        }
                    }
                    HorizontalDivider()
                    contacts.forEach { contact ->
                        Surface(
                            modifier = Modifier.fillMaxWidth(),
                            shape = MaterialTheme.shapes.medium,
                            border = BorderStroke(1.dp, colorResource(R.color.charcoal_gray))
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(
                                    8.dp,
                                    Alignment.CenterVertically
                                )
                            ) {
                                //Interaction Buttons
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.End,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row(
                                        modifier = Modifier.weight(0.8f),
                                        horizontalArrangement = Arrangement.Start,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        contact.label
                                            .takeIf{ it.isNotBlank() }?.let {
                                                Text(
                                                    text = it,
                                                    fontWeight = FontWeight.Bold,
                                                    style = MaterialTheme.typography.titleMedium
                                                )
                                            }
                                    }
                                    Row(
                                        modifier = Modifier.weight(0.2f),
                                        horizontalArrangement = Arrangement.Center,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Surface(
                                            onClick = { onUpdateBizContact(contact) },
                                            modifier = Modifier
                                                .size(20.dp)
                                                .weight(0.1f)
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Edit,
                                                contentDescription = null
                                            )
                                        }
                                        Surface(
                                            onClick = { onDeleteBizContact(contact) },
                                            modifier = Modifier
                                                .size(20.dp)
                                                .weight(0.1f)
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Clear,
                                                contentDescription = null
                                            )
                                        }
                                    }
                                }

                                HorizontalDivider()

                                //Left Row
                                Row(modifier = Modifier.fillMaxWidth(1.0f)) {
                                    Column(
                                        modifier = Modifier
                                            .weight(0.5f)
                                            .padding(8.dp),
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.spacedBy(
                                            8.dp,
                                            Alignment.CenterVertically
                                        )
                                    ) {
                                        //Gen ID
                                        contact.genId.let {
                                            Column(
                                                modifier = Modifier.fillMaxWidth(),
                                                horizontalAlignment = Alignment.Start,
                                                verticalArrangement = Arrangement.Center
                                            ) {
                                                Text(
                                                    text = stringResource(R.string.str_gen_id),
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

                                        //Label
                                        contact.label
                                            .takeIf{ it.isNotBlank() }?.let {
                                                Column(
                                                    modifier = Modifier.fillMaxWidth(),
                                                    horizontalAlignment = Alignment.Start,
                                                    verticalArrangement = Arrangement.Center
                                                ) {
                                                    Text(
                                                        text = stringResource(R.string.str_address_label),
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

                                        //Additional Info
                                        contact.additionalInfo
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

                                    //Right Row
                                    Column(
                                        modifier = Modifier
                                            .weight(0.5f)
                                            .padding(8.dp),
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.spacedBy(
                                            8.dp,
                                            Alignment.CenterVertically
                                        )
                                    ) {
                                        //Media Type
                                        contact.mediaIdentifierKey.let {
                                            Column(
                                                modifier = Modifier.fillMaxWidth(),
                                                horizontalAlignment = Alignment.Start,
                                                verticalArrangement = Arrangement.Center
                                            ) {
                                                Text(
                                                    text = stringResource(R.string.str_media_contact_type),
                                                    maxLines = 1,
                                                    fontWeight = FontWeight.Bold,
                                                    style = MaterialTheme.typography.labelSmall,
                                                    overflow = TextOverflow.Ellipsis
                                                )
                                                Text(
                                                    text = it.toString(),
                                                    style = MaterialTheme.typography.bodyMedium
                                                )
                                            }
                                        }

                                        //Contact Value
                                        contact.contactValue
                                            .takeIf{ it.isNotBlank() }?.let {
                                                Column(
                                                    modifier = Modifier.fillMaxWidth(),
                                                    horizontalAlignment = Alignment.Start,
                                                    verticalArrangement = Arrangement.Center
                                                ) {
                                                    Text(
                                                        text = stringResource(R.string.str_contact),
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

                                        //Audit Trail
                                        contact.auditTrail.modifiedAt.let {
                                            Column(
                                                modifier = Modifier.fillMaxWidth(),
                                                horizontalAlignment = Alignment.Start,
                                                verticalArrangement = Arrangement.Center
                                            ) {
                                                Text(
                                                    text = stringResource(R.string.str_modified_at),
                                                    maxLines = 1,
                                                    fontWeight = FontWeight.Bold,
                                                    style = MaterialTheme.typography.labelSmall,
                                                    overflow = TextOverflow.Ellipsis
                                                )
                                                Text(
                                                    text = it.toString(),
                                                    style = MaterialTheme.typography.bodyMedium
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BizLinksSection(
    links: List<Link>?,
    onCreateBizLink: () -> Unit,
    onUpdateBizLink: (Link) -> Unit,
    onDeleteBizLink: (Link) -> Unit,
    onDeleteAllBizLinks: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(1.0f),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(modifier = Modifier.weight(0.1f)) {
                    Icon(
                        modifier = Modifier.size(20.dp),
                        imageVector = ImageVector.vectorResource(id = country),
                        contentDescription = null
                    )
                }
                Text(
                    text = stringResource(R.string.str_biz_links),
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    modifier = Modifier
                        .weight(0.8f)
                        .fillMaxWidth()
                )
                Surface(
                    modifier = Modifier
                        .weight(0.1f)
                        .size(20.dp),
                    color = MaterialTheme.colorScheme.tertiaryContainer,
                    onClick = { expanded = expanded.not() }
                ) {
                    Icon(
                        modifier = Modifier,
                        imageVector =
                        if (expanded) Icons.Default.KeyboardArrowDown
                        else Icons.AutoMirrored.Default.KeyboardArrowRight,
                        contentDescription = null
                    )
                }
            }

            if (expanded) {
                if (links.isNullOrEmpty()) {
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        shape = MaterialTheme.shapes.medium,
                        border = BorderStroke(1.dp, colorResource(R.color.charcoal_gray))
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(
                                8.dp,
                                Alignment.CenterVertically
                            )
                        ) {
                            Surface(
                                modifier = Modifier.size(48.dp),
                                contentColor = MaterialTheme.colorScheme.error
                            ) {
                                Icon(
                                    imageVector = ImageVector.vectorResource(id = neutral),
                                    contentDescription = null
                                )
                            }
                            Text(
                                text = stringResource(R.string.str_biz_links_not_set),
                                textAlign = TextAlign.Justify
                            )
                            AppIconButton(
                                onClick = onCreateBizLink,
                                icon = Icons.Default.Add,
                                text = stringResource(R.string.str_add)
                            )
                        }
                    }
                } else {
                    HorizontalDivider()
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(if (links.size >= 5) 0.1f else 0.2f),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            if (links.size < 5) {
                                Surface(
                                    onClick = onCreateBizLink,
                                    modifier = Modifier
                                        .size(20.dp)
                                        .weight(0.1f)
                                ) {
                                    Icon(
                                        modifier = Modifier,
                                        imageVector = Icons.Default.Add,
                                        contentDescription = null
                                    )
                                }
                            }
                            Surface(
                                onClick = onDeleteAllBizLinks,
                                modifier = Modifier
                                    .size(20.dp)
                                    .weight(0.1f)
                            ) {
                                Icon(
                                    modifier = Modifier,
                                    imageVector = Icons.Default.Clear,
                                    contentDescription = null
                                )
                            }
                        }
                    }
                    HorizontalDivider()
                    links.forEach { link ->
                        Surface(
                            modifier = Modifier.fillMaxWidth(),
                            shape = MaterialTheme.shapes.medium,
                            border = BorderStroke(1.dp, colorResource(R.color.charcoal_gray))
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(
                                    8.dp,
                                    Alignment.CenterVertically
                                )
                            ) {
                                //Interaction Buttons
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.End,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row(
                                        modifier = Modifier.weight(0.8f),
                                        horizontalArrangement = Arrangement.Start,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        link.label
                                            .takeIf{ it.isNotBlank() }?.let {
                                                Text(
                                                    text = it,
                                                    fontWeight = FontWeight.Bold,
                                                    style = MaterialTheme.typography.titleMedium
                                                )
                                            }
                                    }
                                    Row(
                                        modifier = Modifier.weight(0.2f),
                                        horizontalArrangement = Arrangement.Center,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Surface(
                                            onClick = { onUpdateBizLink(link) },
                                            modifier = Modifier
                                                .size(20.dp)
                                                .weight(0.1f)
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Edit,
                                                contentDescription = null
                                            )
                                        }
                                        Surface(
                                            onClick = { onDeleteBizLink(link) },
                                            modifier = Modifier
                                                .size(20.dp)
                                                .weight(0.1f)
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Clear,
                                                contentDescription = null
                                            )
                                        }
                                    }
                                }

                                HorizontalDivider()

                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(8.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.spacedBy(
                                        8.dp,
                                        Alignment.CenterVertically
                                    )
                                ) {
                                    //Gen ID
                                    link.genId.let {
                                        Column(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalAlignment = Alignment.Start,
                                            verticalArrangement = Arrangement.Center
                                        ) {
                                            Text(
                                                text = stringResource(R.string.str_gen_id),
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

                                    //Label
                                    link.label
                                        .takeIf{ it.isNotBlank() }?.let {
                                            Column(
                                                modifier = Modifier.fillMaxWidth(),
                                                horizontalAlignment = Alignment.Start,
                                                verticalArrangement = Arrangement.Center
                                            ) {
                                                Text(
                                                    text = stringResource(R.string.str_address_label),
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

                                    //URI
                                    link.uri
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

                                    //Audit Trail
                                    link.auditTrail.modifiedAt.let {
                                        Column(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalAlignment = Alignment.Start,
                                            verticalArrangement = Arrangement.Center
                                        ) {
                                            Text(
                                                text = stringResource(R.string.str_modified_at),
                                                maxLines = 1,
                                                fontWeight = FontWeight.Bold,
                                                style = MaterialTheme.typography.labelSmall,
                                                overflow = TextOverflow.Ellipsis
                                            )
                                            Text(
                                                text = it.toString(),
                                                style = MaterialTheme.typography.bodyMedium
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
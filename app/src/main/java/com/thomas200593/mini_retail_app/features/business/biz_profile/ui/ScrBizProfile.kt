package com.thomas200593.mini_retail_app.features.business.biz_profile.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons.AutoMirrored
import androidx.compose.material.icons.Icons.Default
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.ButtonDefaults.IconSize
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.MaterialTheme.typography
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
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.style.TextAlign.Companion.Justify
import androidx.compose.ui.text.style.TextAlign.Companion.Start
import androidx.compose.ui.text.style.TextOverflow.Companion.Ellipsis
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.thomas200593.mini_retail_app.R.string
import com.thomas200593.mini_retail_app.app.ui.LocalStateApp
import com.thomas200593.mini_retail_app.app.ui.StateApp
import com.thomas200593.mini_retail_app.core.data.local.database.entity_common.Address
import com.thomas200593.mini_retail_app.core.data.local.database.entity_common.AuditTrail
import com.thomas200593.mini_retail_app.core.data.local.database.entity_common.Contact
import com.thomas200593.mini_retail_app.core.data.local.database.entity_common.Industries
import com.thomas200593.mini_retail_app.core.data.local.database.entity_common.LegalDocumentType
import com.thomas200593.mini_retail_app.core.data.local.database.entity_common.LegalType
import com.thomas200593.mini_retail_app.core.data.local.database.entity_common.Link
import com.thomas200593.mini_retail_app.core.data.local.database.entity_common.Taxation
import com.thomas200593.mini_retail_app.core.ui.common.CustomIcons.Country.country
import com.thomas200593.mini_retail_app.core.ui.common.CustomIcons.Emotion.neutral
import com.thomas200593.mini_retail_app.core.ui.common.CustomThemes.ApplicationTheme
import com.thomas200593.mini_retail_app.core.ui.component.CustomAppBar.ProvideTopAppBarAction
import com.thomas200593.mini_retail_app.core.ui.component.CustomAppBar.ProvideTopAppBarNavigationIcon
import com.thomas200593.mini_retail_app.core.ui.component.CustomAppBar.ProvideTopAppBarTitle
import com.thomas200593.mini_retail_app.core.ui.component.CustomButton.Common.AppIconButton
import com.thomas200593.mini_retail_app.core.ui.component.CustomPanel.ErrorScreen
import com.thomas200593.mini_retail_app.core.ui.component.CustomPanel.LoadingScreen
import com.thomas200593.mini_retail_app.features.app_conf.app_config.entity.AppConfig
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_country.entity.Country
import com.thomas200593.mini_retail_app.features.business.biz_profile.entity.BizIdentity
import com.thomas200593.mini_retail_app.features.business.biz_profile.entity.BizName
import com.thomas200593.mini_retail_app.features.business.biz_profile.entity.BizProfile
import com.thomas200593.mini_retail_app.features.business.biz_profile.ui.VMBizProfile.UiEvents.ButtonEvents.BtnNavBackEvents
import com.thomas200593.mini_retail_app.features.business.biz_profile.ui.VMBizProfile.UiEvents.OnOpenEvents
import com.thomas200593.mini_retail_app.features.business.biz_profile.ui.VMBizProfile.UiState
import com.thomas200593.mini_retail_app.features.business.biz_profile.ui.VMBizProfile.UiStateBizProfile.Error
import com.thomas200593.mini_retail_app.features.business.biz_profile.ui.VMBizProfile.UiStateBizProfile.Loading
import com.thomas200593.mini_retail_app.features.business.biz_profile.ui.VMBizProfile.UiStateBizProfile.Success
import ulid.ULID

@Composable
fun ScrBizProfile(
    vm: VMBizProfile = hiltViewModel(),
    stateApp: StateApp = LocalStateApp.current
){
    val sessionState by stateApp.isSessionValid.collectAsStateWithLifecycle()
    val uiState by vm.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(sessionState) { vm.onEvent(OnOpenEvents(sessionState)) }

    ScrBizProfile(
        uiState = uiState,
        //BtnNavBack
        onNavigateBack = { BtnNavBackEvents.OnClick.apply { stateApp.onNavUp() } },
        //BizIdName
        onUpdateBizIdName = {},
        onResetBizIdName = {},
        onUpdateBizIdIndustry = {},
        onResetBizIdIndustry = {},
        onUpdateBizIdLegal = {},
        onResetBizIdLegal = {},
        onUpdateBizIdTaxation = {},
        onResetBizIdTaxation = {},
        onCreateBizAddress = {},
        onUpdateBizAddress = {},
        onDeleteBizAddress = {},
        onDeleteAllBizAddresses = {},
        onCreateBizContact = {},
        onUpdateBizContact = {},
        onDeleteBizContact = {},
        onDeleteAllBizContacts = {},
        onCreateBizLink = {},
        onUpdateBizLink = {},
        onDeleteBizLink = {},
        onDeleteAllBizLinks = {}
    )
}

@Composable
private fun ScrBizProfile(
    uiState: UiState,
    onNavigateBack: () -> Unit,
    //BizIdName
    onUpdateBizIdName: () -> Unit, onResetBizIdName: () -> Unit,
    //BizIdIndustry
    onUpdateBizIdIndustry: () -> Unit, onResetBizIdIndustry: () -> Unit,
    //BizIdLegal
    onUpdateBizIdLegal: () -> Unit, onResetBizIdLegal: () -> Unit,
    //BizIdTaxation
    onUpdateBizIdTaxation: () -> Unit, onResetBizIdTaxation: () -> Unit,
    //BizAddresses
    onCreateBizAddress: () -> Unit, onUpdateBizAddress: (Address) -> Unit,
    onDeleteBizAddress: (Address) -> Unit, onDeleteAllBizAddresses: () -> Unit,
    //BizContacts
    onCreateBizContact: () -> Unit, onUpdateBizContact: (Contact) -> Unit,
    onDeleteBizContact: (Contact) -> Unit, onDeleteAllBizContacts: () -> Unit,
    //BizLinks
    onCreateBizLink: () -> Unit, onUpdateBizLink: (Link) -> Unit,
    onDeleteBizLink: (Link) -> Unit, onDeleteAllBizLinks: () -> Unit
){
    TopAppBar(onNavigateBack = onNavigateBack)
    when(uiState.bizProfile){
        Loading -> LoadingScreen()
        is Error -> ErrorScreen(
            title = stringResource(id = string.str_error),
            errorMessage = uiState.bizProfile.t.toString(),
            showIcon = true
        )
        is Success -> ScreenContent(
            bizProfile = uiState.bizProfile.bizProfile,
            onUpdateBizIdName = onUpdateBizIdName, onResetBizIdName = onResetBizIdName,
            onUpdateBizIdIndustry = onUpdateBizIdIndustry, onResetBizIdIndustry = onResetBizIdIndustry,
            onUpdateBizIdLegal = onUpdateBizIdLegal, onResetBizIdLegal = onResetBizIdLegal,
            onUpdateBizIdTaxation = onUpdateBizIdTaxation, onResetBizIdTaxation = onResetBizIdTaxation,
            onCreateBizAddress = onCreateBizAddress, onUpdateBizAddress = onUpdateBizAddress,
            onDeleteBizAddress = onDeleteBizAddress, onDeleteAllBizAddresses = onDeleteAllBizAddresses,
            onCreateBizContact = onCreateBizContact, onUpdateBizContact = onUpdateBizContact,
            onDeleteBizContact = onDeleteBizContact, onDeleteAllBizContacts = onDeleteAllBizContacts,
            onCreateBizLink = onCreateBizLink, onUpdateBizLink = onUpdateBizLink,
            onDeleteBizLink = onDeleteBizLink, onDeleteAllBizLinks = onDeleteAllBizLinks
        )
    }
}

@Composable
private fun TopAppBar(onNavigateBack: () -> Unit) {
    ProvideTopAppBarNavigationIcon {
        Surface(onClick = onNavigateBack, modifier = Modifier) {
            Icon(
                modifier = Modifier,
                imageVector = AutoMirrored.Filled.KeyboardArrowLeft,
                contentDescription = null
            )
        }
    }
    ProvideTopAppBarTitle {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ){
            Icon(
                modifier = Modifier.sizeIn(maxHeight = IconSize),
                imageVector = ImageVector.vectorResource(id = country),
                contentDescription = null
            )
            Text(
                text = stringResource(id = string.str_business_profile),
                maxLines = 1,
                overflow = Ellipsis
            )
        }
    }
    ProvideTopAppBarAction {
        Row(
            modifier = Modifier.padding(end = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ){
            Icon(
                modifier = Modifier.sizeIn(maxHeight = IconSize),
                imageVector = Default.Info,
                contentDescription = null
            )
        }
    }
}

@Composable
private fun ScreenContent(
    bizProfile: BizProfile,
    //BizIdName
    onUpdateBizIdName: () -> Unit, onResetBizIdName: () -> Unit,
    //BizIdIndustry
    onUpdateBizIdIndustry: () -> Unit, onResetBizIdIndustry: () -> Unit,
    //BizIdLegal
    onUpdateBizIdLegal: () -> Unit, onResetBizIdLegal: () -> Unit,
    //BizIdTaxation
    onUpdateBizIdTaxation: () -> Unit, onResetBizIdTaxation: () -> Unit,
    //BizAddresses
    onCreateBizAddress: () -> Unit, onUpdateBizAddress: (Address) -> Unit,
    onDeleteBizAddress: (Address) -> Unit, onDeleteAllBizAddresses: () -> Unit,
    //BizContacts
    onCreateBizContact: () -> Unit, onUpdateBizContact: (Contact) -> Unit,
    onDeleteBizContact: (Contact) -> Unit, onDeleteAllBizContacts: () -> Unit,
    //BizLinks
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
        BizIdentitySection(
            bizProfile = bizProfile,
            onUpdateBizIdName = onUpdateBizIdName, onResetBizIdName = onResetBizIdName,
            onUpdateBizIdIndustry = onUpdateBizIdIndustry, onResetBizIdIndustry = onResetBizIdIndustry,
            onUpdateBizIdLegal = onUpdateBizIdLegal, onResetBizIdLegal = onResetBizIdLegal,
            onUpdateBizIdTaxation = onUpdateBizIdTaxation, onResetBizIdTaxation = onResetBizIdTaxation
        )
        BizAddressesSection(
            addresses = bizProfile.addresses,
            onCreateBizAddress = onCreateBizAddress, onUpdateBizAddress = onUpdateBizAddress,
            onDeleteBizAddress = onDeleteBizAddress, onDeleteAllBizAddresses = onDeleteAllBizAddresses
        )
        BizContactsSection(
            contacts = bizProfile.contacts,
            onCreateBizContact = onCreateBizContact, onUpdateBizContact = onUpdateBizContact,
            onDeleteBizContact = onDeleteBizContact, onDeleteAllBizContacts = onDeleteAllBizContacts
        )
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
        shape = shapes.medium
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
                    text = stringResource(string.str_biz_identity),
                    fontWeight = Bold,
                    textAlign = Start,
                    overflow = Ellipsis,
                    maxLines = 1,
                    modifier = Modifier
                        .weight(0.8f)
                        .fillMaxWidth()
                )
                Surface(
                    modifier = Modifier
                        .weight(0.1f)
                        .size(20.dp),
                    color = colorScheme.tertiaryContainer,
                    onClick = { expanded = expanded.not() }
                ) {
                    Icon(
                        imageVector =
                        if(expanded) Default.KeyboardArrowDown
                        else AutoMirrored.Default.KeyboardArrowRight,
                        contentDescription = null
                    )
                }
            }

            if(expanded){
                HorizontalDivider()
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = stringResource(string.str_gen_id),
                        maxLines = 1,
                        fontWeight = Bold,
                        style = typography.labelSmall,
                        overflow = Ellipsis
                    )
                    Text(
                        text = bizProfile.genId,
                        style = typography.bodyMedium
                    )
                }

                /*Business Name*/
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = shapes.medium
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
                            Surface(modifier = Modifier.weight(0.1f), color = Transparent) {
                                Icon(
                                    modifier = Modifier.size(20.dp),
                                    imageVector = ImageVector.vectorResource(id = country),
                                    contentDescription = null
                                )
                            }
                            Text(
                                text = stringResource(string.str_biz_name),
                                fontWeight = Bold,
                                textAlign = Start,
                                overflow = Ellipsis,
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
                                color = Transparent
                            ) {
                                Icon(
                                    imageVector = Default.Edit,
                                    contentDescription = null
                                )
                            }
                            Surface(
                                onClick = { onResetBizIdName() },
                                modifier = Modifier
                                    .weight(0.1f)
                                    .size(20.dp),
                                color = Transparent
                            ) {
                                Icon(
                                    imageVector = Default.Clear,
                                    contentDescription = null
                                )
                            }
                        }

                        HorizontalDivider()

                        bizProfile.bizIdentity.bizName.legalName?.let {
                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalAlignment = Alignment.Start,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = stringResource(string.str_biz_legal_name),
                                    maxLines = 1,
                                    fontWeight = Bold,
                                    style = typography.labelSmall,
                                    overflow = Ellipsis
                                )
                                Text(
                                    text = it,
                                    style = typography.bodyMedium
                                )
                            }
                        }

                        bizProfile.bizIdentity.bizName.commonName?.let {
                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalAlignment = Alignment.Start,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = stringResource(string.str_biz_common_name),
                                    maxLines = 1,
                                    fontWeight = Bold,
                                    style = typography.labelSmall,
                                    overflow = Ellipsis
                                )
                                Text(
                                    text = it,
                                    style = typography.bodyMedium
                                )
                            }
                        }
                    }
                }

                /*Business Industry*/
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = shapes.medium
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
                            Surface(modifier = Modifier.weight(0.1f), color = Transparent) {
                                Icon(
                                    modifier = Modifier.size(20.dp),
                                    imageVector = ImageVector.vectorResource(id = country),
                                    contentDescription = null
                                )
                            }
                            Text(
                                text = stringResource(string.str_biz_industry),
                                fontWeight = Bold,
                                textAlign = Start,
                                overflow = Ellipsis,
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
                                color = Transparent
                            ) {
                                Icon(
                                    modifier = Modifier.size(20.dp),
                                    imageVector = Default.Edit,
                                    contentDescription = null
                                )
                            }
                            Surface(
                                onClick = { onResetBizIdIndustry() },
                                modifier = Modifier
                                    .weight(0.1f)
                                    .size(20.dp),
                                color = Transparent
                            ) {
                                Icon(
                                    imageVector = Default.Clear,
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
                                    text = stringResource(string.str_biz_industry_id),
                                    maxLines = 1,
                                    fontWeight = Bold,
                                    style = typography.labelSmall,
                                    overflow = Ellipsis
                                )
                                Text(
                                    text = it.toString(),
                                    style = typography.bodyMedium
                                )
                            }
                        }

                        bizProfile.bizIdentity.industries.additionalInfo?.let {
                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalAlignment = Alignment.Start,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = stringResource(string.str_additional_info),
                                    maxLines = 1,
                                    fontWeight = Bold,
                                    style = typography.labelSmall,
                                    overflow = Ellipsis
                                )
                                Text(
                                    text = it,
                                    style = typography.bodyMedium
                                )
                            }
                        }
                    }
                }

                /*Business Legal*/
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = shapes.medium
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
                            Surface(modifier = Modifier.weight(0.1f), color = Transparent) {
                                Icon(
                                    modifier = Modifier.size(20.dp),
                                    imageVector = ImageVector.vectorResource(id = country),
                                    contentDescription = null
                                )
                            }
                            Text(
                                text = stringResource(string.str_biz_legal),
                                fontWeight = Bold,
                                textAlign = Start,
                                overflow = Ellipsis,
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
                                color = Transparent
                            ) {
                                Icon(
                                    imageVector = Default.Edit,
                                    contentDescription = null
                                )
                            }
                            Surface(
                                onClick = { onResetBizIdLegal() },
                                modifier = Modifier
                                    .weight(0.1f)
                                    .size(20.dp),
                                color = Transparent
                            ) {
                                Icon(
                                    imageVector = Default.Clear,
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
                                    text = stringResource(string.str_biz_legal_id),
                                    maxLines = 1,
                                    fontWeight = Bold,
                                    style = typography.labelSmall,
                                    overflow = Ellipsis
                                )
                                Text(
                                    text = it.toString(),
                                    style = typography.bodyMedium
                                )
                            }
                        }

                        bizProfile.bizIdentity.legalType.additionalInfo?.let {
                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalAlignment = Alignment.Start,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = stringResource(string.str_additional_info),
                                    maxLines = 1,
                                    fontWeight = Bold,
                                    style = typography.labelSmall,
                                    overflow = Ellipsis
                                )
                                Text(
                                    text = it,
                                    style = typography.bodyMedium
                                )
                            }
                        }

                        if(bizProfile.bizIdentity.legalType.legalDocumentType != null){
                            bizProfile.bizIdentity.legalType.legalDocumentType.identifierKey.let {
                                Column(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalAlignment = Alignment.Start,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Text(
                                        text = stringResource(string.str_biz_legal_doc_type_id),
                                        maxLines = 1,
                                        fontWeight = Bold,
                                        style = typography.labelSmall,
                                        overflow = Ellipsis
                                    )
                                    Text(
                                        text = it.toString(),
                                        style = typography.bodyMedium
                                    )
                                }
                            }

                            bizProfile.bizIdentity.legalType.legalDocumentType.additionalInfo?.let {
                                Column(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalAlignment = Alignment.Start,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Text(
                                        text = stringResource(string.str_additional_info),
                                        maxLines = 1,
                                        fontWeight = Bold,
                                        style = typography.labelSmall,
                                        overflow = Ellipsis
                                    )
                                    Text(
                                        text = it,
                                        style = typography.bodyMedium
                                    )
                                }
                            }
                        }
                    }
                }

                /*Business Taxation*/
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = shapes.medium
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
                                color = Transparent
                            ) {
                                Icon(
                                    modifier = Modifier.size(20.dp),
                                    imageVector = ImageVector.vectorResource(id = country),
                                    contentDescription = null
                                )
                            }
                            Text(
                                text = stringResource(string.str_biz_taxation),
                                fontWeight = Bold,
                                textAlign = Start,
                                overflow = Ellipsis,
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
                                color = Transparent
                            ) {
                                Icon(
                                    imageVector = Default.Edit,
                                    contentDescription = null
                                )
                            }
                            Surface(
                                onClick = { onResetBizIdTaxation() },
                                modifier = Modifier
                                    .weight(0.1f)
                                    .size(20.dp),
                                color = Transparent
                            ) {
                                Icon(
                                    imageVector = Default.Clear,
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
                                    text = stringResource(string.str_biz_tax_type_id),
                                    maxLines = 1,
                                    fontWeight = Bold,
                                    style = typography.labelSmall,
                                    overflow = Ellipsis
                                )
                                Text(
                                    text = it.toString(),
                                    style = typography.bodyMedium
                                )
                            }
                        }

                        bizProfile.bizIdentity.taxation.taxIdDocNumber?.let {
                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalAlignment = Alignment.Start,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = stringResource(string.str_biz_tax_id_doc_number),
                                    maxLines = 1,
                                    fontWeight = Bold,
                                    style = typography.labelSmall,
                                    overflow = Ellipsis
                                )
                                Text(
                                    text = it,
                                    style = typography.bodyMedium
                                )
                            }
                        }

                        bizProfile.bizIdentity.taxation.taxIssuerCountry?.let {
                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalAlignment = Alignment.Start,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = stringResource(string.str_biz_tax_id_issuer_country),
                                    maxLines = 1,
                                    fontWeight = Bold,
                                    style = typography.labelSmall,
                                    overflow = Ellipsis
                                )
                                Text(
                                    text = it.displayName,
                                    style = typography.bodyMedium
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
                                    text = stringResource(string.str_biz_tax_rate_percentage),
                                    maxLines = 1,
                                    fontWeight = Bold,
                                    style = typography.labelSmall,
                                    overflow = Ellipsis
                                )
                                Text(
                                    text = it.toString(),
                                    style = typography.bodyMedium
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
                                    text = stringResource(string.str_biz_tax_included),
                                    maxLines = 1,
                                    fontWeight = Bold,
                                    style = typography.labelSmall,
                                    overflow = Ellipsis
                                )
                                Text(
                                    text = it.toString(),
                                    style = typography.bodyMedium
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
        shape = shapes.medium
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ){
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
                    text = stringResource(string.str_biz_addresses),
                    fontWeight = Bold,
                    textAlign = Start,
                    overflow = Ellipsis,
                    maxLines = 1,
                    modifier = Modifier
                        .weight(0.8f)
                        .fillMaxWidth()
                )
                Surface(
                    modifier = Modifier
                        .weight(0.1f)
                        .size(20.dp),
                    color = colorScheme.tertiaryContainer,
                    onClick = { expanded = expanded.not() }
                ) {
                    Icon(
                        modifier = Modifier,
                        imageVector =
                        if(expanded) Default.KeyboardArrowDown
                        else AutoMirrored.Default.KeyboardArrowRight,
                        contentDescription = null
                    )
                }
            }

            if(expanded){
                if(addresses.isNullOrEmpty()){
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        shape = shapes.medium
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically)
                        ) {
                            Surface(
                                modifier = Modifier.size(48.dp),
                                contentColor = colorScheme.error
                            ) {
                                Icon(
                                    imageVector = ImageVector.vectorResource(id = neutral),
                                    contentDescription = null
                                )
                            }
                            Text(
                                text = stringResource(string.str_biz_address_not_set),
                                textAlign = Justify
                            )
                            AppIconButton(
                                onClick = { onCreateBizAddress() },
                                icon = Default.Add,
                                text = stringResource(string.str_add)
                            )
                        }
                    }
                } else{
                    HorizontalDivider()
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(
                                if(addresses.size >= 5){ 0.1f }else{ 0.2f }
                            ),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            if(addresses.size < 5){
                                Surface(
                                    onClick = { onCreateBizAddress() },
                                    modifier = Modifier
                                        .size(20.dp)
                                        .weight(0.1f)
                                ) {
                                    Icon(
                                        modifier = Modifier,
                                        imageVector = Default.Add,
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
                                    imageVector = Default.Clear,
                                    contentDescription = null
                                )
                            }
                        }
                    }
                    HorizontalDivider()
                    addresses.forEach{ address ->
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth(),
                            shape = shapes.medium,
                            border = BorderStroke(1.dp, DarkGray)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically)
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
                                    ){
                                        address.label?.let {
                                            Text(
                                                text = it,
                                                fontWeight = Bold,
                                                style = typography.titleMedium
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
                                                imageVector = Default.Edit,
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
                                                imageVector = Default.Clear,
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
                                        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically)
                                    ) {
                                        //Gen ID
                                        address.genId.let {
                                            Column(
                                                modifier = Modifier.fillMaxWidth(),
                                                horizontalAlignment = Alignment.Start,
                                                verticalArrangement = Arrangement.Center
                                            ) {
                                                Text(
                                                    text = stringResource(string.str_gen_id),
                                                    maxLines = 1,
                                                    fontWeight = Bold,
                                                    style = typography.labelSmall,
                                                    overflow = Ellipsis
                                                )
                                                Text(
                                                    text = it,
                                                    style = typography.bodyMedium
                                                )
                                            }
                                        }

                                        //Label
                                        address.label?.let {
                                            Column(
                                                modifier = Modifier.fillMaxWidth(),
                                                horizontalAlignment = Alignment.Start,
                                                verticalArrangement = Arrangement.Center
                                            ) {
                                                Text(
                                                    text = stringResource(string.str_address_label),
                                                    maxLines = 1,
                                                    fontWeight = Bold,
                                                    style = typography.labelSmall,
                                                    overflow = Ellipsis
                                                )
                                                Text(
                                                    text = it,
                                                    style = typography.bodyMedium
                                                )
                                            }
                                        }

                                        //Street Line
                                        address.streetLine?.let {
                                            Column(
                                                modifier = Modifier.fillMaxWidth(),
                                                horizontalAlignment = Alignment.Start,
                                                verticalArrangement = Arrangement.Center
                                            ) {
                                                Text(
                                                    text = stringResource(string.str_address_street_line),
                                                    maxLines = 1,
                                                    fontWeight = Bold,
                                                    style = typography.labelSmall,
                                                    overflow = Ellipsis
                                                )
                                                Text(
                                                    text = it,
                                                    style = typography.bodyMedium
                                                )
                                            }
                                        }

                                        //Postal Code
                                        address.postalCode?.let {
                                            Column(
                                                modifier = Modifier.fillMaxWidth(),
                                                horizontalAlignment = Alignment.Start,
                                                verticalArrangement = Arrangement.Center
                                            ) {
                                                Text(
                                                    text = stringResource(string.str_postal_code),
                                                    maxLines = 1,
                                                    fontWeight = Bold,
                                                    style = typography.labelSmall,
                                                    overflow = Ellipsis
                                                )
                                                Text(
                                                    text = it,
                                                    style = typography.bodyMedium
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
                                        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically)
                                    ) {
                                        //Country
                                        address.country?.let {
                                            Column(
                                                modifier = Modifier.fillMaxWidth(),
                                                horizontalAlignment = Alignment.Start,
                                                verticalArrangement = Arrangement.Center
                                            ) {
                                                Text(
                                                    text = stringResource(string.str_country),
                                                    maxLines = 1,
                                                    fontWeight = Bold,
                                                    style = typography.labelSmall,
                                                    overflow = Ellipsis
                                                )
                                                Text(
                                                    text = it.displayName,
                                                    style = typography.bodyMedium
                                                )
                                            }
                                        }

                                        //Additional Info
                                        address.additionalInfo?.let {
                                            Column(
                                                modifier = Modifier.fillMaxWidth(),
                                                horizontalAlignment = Alignment.Start,
                                                verticalArrangement = Arrangement.Center
                                            ) {
                                                Text(
                                                    text = stringResource(string.str_additional_info),
                                                    maxLines = 1,
                                                    fontWeight = Bold,
                                                    style = typography.labelSmall,
                                                    overflow = Ellipsis
                                                )
                                                Text(
                                                    text = it,
                                                    style = typography.bodyMedium
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
                                                    text = stringResource(string.str_modified_at),
                                                    maxLines = 1,
                                                    fontWeight = Bold,
                                                    style = typography.labelSmall,
                                                    overflow = Ellipsis
                                                )
                                                Text(
                                                    text = it.toString(),
                                                    style = typography.bodyMedium
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
        shape = shapes.medium
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ){
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
                    text = stringResource(string.str_biz_contacts),
                    fontWeight = Bold,
                    textAlign = Start,
                    overflow = Ellipsis,
                    maxLines = 1,
                    modifier = Modifier
                        .weight(0.8f)
                        .fillMaxWidth()
                )
                Surface(
                    modifier = Modifier
                        .weight(0.1f)
                        .size(20.dp),
                    color = colorScheme.tertiaryContainer,
                    onClick = { expanded = expanded.not() }
                ) {
                    Icon(
                        modifier = Modifier,
                        imageVector =
                        if(expanded) Default.KeyboardArrowDown
                        else AutoMirrored.Default.KeyboardArrowRight,
                        contentDescription = null
                    )
                }
            }

            if(expanded){
                if(contacts.isNullOrEmpty()){
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        shape = shapes.medium,
                        border = BorderStroke(1.dp, color = DarkGray)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically)
                        ) {
                            Surface(
                                modifier = Modifier.size(48.dp),
                                contentColor = colorScheme.error
                            ) {
                                Icon(
                                    imageVector = ImageVector.vectorResource(id = neutral),
                                    contentDescription = null
                                )
                            }
                            Text(
                                text = stringResource(string.str_biz_address_not_set),
                                textAlign = Justify
                            )
                            AppIconButton(
                                onClick = { onCreateBizContact() },
                                icon = Default.Add,
                                text = stringResource(string.str_add)
                            )
                        }
                    }
                } else{
                    HorizontalDivider()
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(
                                if(contacts.size >= 5){ 0.1f }else{ 0.2f }
                            ),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            if(contacts.size < 5){
                                Surface(
                                    onClick = { onCreateBizContact() },
                                    modifier = Modifier
                                        .size(20.dp)
                                        .weight(0.1f)
                                ) {
                                    Icon(
                                        modifier = Modifier,
                                        imageVector = Default.Add,
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
                                    imageVector = Default.Clear,
                                    contentDescription = null
                                )
                            }
                        }
                    }
                    HorizontalDivider()
                    contacts.forEach{ contact ->
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth(),
                            shape = shapes.medium,
                            border = BorderStroke(1.dp, DarkGray)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically)
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
                                    ){
                                        contact.label?.let {
                                            Text(
                                                text = it,
                                                fontWeight = Bold,
                                                style = typography.titleMedium
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
                                                imageVector = Default.Edit,
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
                                                imageVector = Default.Clear,
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
                                        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically)
                                    ) {
                                        //Gen ID
                                        contact.genId.let {
                                            Column(
                                                modifier = Modifier.fillMaxWidth(),
                                                horizontalAlignment = Alignment.Start,
                                                verticalArrangement = Arrangement.Center
                                            ) {
                                                Text(
                                                    text = stringResource(string.str_gen_id),
                                                    maxLines = 1,
                                                    fontWeight = Bold,
                                                    style = typography.labelSmall,
                                                    overflow = Ellipsis
                                                )
                                                Text(
                                                    text = it,
                                                    style = typography.bodyMedium
                                                )
                                            }
                                        }

                                        //Label
                                        contact.label?.let {
                                            Column(
                                                modifier = Modifier.fillMaxWidth(),
                                                horizontalAlignment = Alignment.Start,
                                                verticalArrangement = Arrangement.Center
                                            ) {
                                                Text(
                                                    text = stringResource(string.str_address_label),
                                                    maxLines = 1,
                                                    fontWeight = Bold,
                                                    style = typography.labelSmall,
                                                    overflow = Ellipsis
                                                )
                                                Text(
                                                    text = it,
                                                    style = typography.bodyMedium
                                                )
                                            }
                                        }

                                        //Additional Info
                                        contact.additionalInfo?.let {
                                            Column(
                                                modifier = Modifier.fillMaxWidth(),
                                                horizontalAlignment = Alignment.Start,
                                                verticalArrangement = Arrangement.Center
                                            ) {
                                                Text(
                                                    text = stringResource(string.str_additional_info),
                                                    maxLines = 1,
                                                    fontWeight = Bold,
                                                    style = typography.labelSmall,
                                                    overflow = Ellipsis
                                                )
                                                Text(
                                                    text = it,
                                                    style = typography.bodyMedium
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
                                        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically)
                                    ) {
                                        //Media Type
                                        contact.mediaIdentifierKey.let {
                                            Column(
                                                modifier = Modifier.fillMaxWidth(),
                                                horizontalAlignment = Alignment.Start,
                                                verticalArrangement = Arrangement.Center
                                            ) {
                                                Text(
                                                    text = stringResource(string.str_media_contact_type),
                                                    maxLines = 1,
                                                    fontWeight = Bold,
                                                    style = typography.labelSmall,
                                                    overflow = Ellipsis
                                                )
                                                Text(
                                                    text = it.toString(),
                                                    style = typography.bodyMedium
                                                )
                                            }
                                        }

                                        //Contact Value
                                        contact.contactValue?.let {
                                            Column(
                                                modifier = Modifier.fillMaxWidth(),
                                                horizontalAlignment = Alignment.Start,
                                                verticalArrangement = Arrangement.Center
                                            ) {
                                                Text(
                                                    text = stringResource(string.str_contact),
                                                    maxLines = 1,
                                                    fontWeight = Bold,
                                                    style = typography.labelSmall,
                                                    overflow = Ellipsis
                                                )
                                                Text(
                                                    text = it,
                                                    style = typography.bodyMedium
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
                                                    text = stringResource(string.str_modified_at),
                                                    maxLines = 1,
                                                    fontWeight = Bold,
                                                    style = typography.labelSmall,
                                                    overflow = Ellipsis
                                                )
                                                Text(
                                                    text = it.toString(),
                                                    style = typography.bodyMedium
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
        shape = shapes.medium
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ){
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
                    text = stringResource(string.str_biz_links),
                    fontWeight = Bold,
                    textAlign = Start,
                    overflow = Ellipsis,
                    maxLines = 1,
                    modifier = Modifier
                        .weight(0.8f)
                        .fillMaxWidth()
                )
                Surface(
                    modifier = Modifier
                        .weight(0.1f)
                        .size(20.dp),
                    color = colorScheme.tertiaryContainer,
                    onClick = { expanded = expanded.not() }
                ) {
                    Icon(
                        modifier = Modifier,
                        imageVector =
                        if(expanded) Default.KeyboardArrowDown
                        else AutoMirrored.Default.KeyboardArrowRight,
                        contentDescription = null
                    )
                }
            }

            if(expanded){
                if(links.isNullOrEmpty()){
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        shape = shapes.medium,
                        border = BorderStroke(1.dp, color = DarkGray)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically)
                        ) {
                            Surface(
                                modifier = Modifier.size(48.dp),
                                contentColor = colorScheme.error
                            ) {
                                Icon(
                                    imageVector = ImageVector.vectorResource(id = neutral),
                                    contentDescription = null
                                )
                            }
                            Text(
                                text = stringResource(string.str_biz_links_not_set),
                                textAlign = Justify
                            )
                            AppIconButton(
                                onClick = onCreateBizLink,
                                icon = Default.Add,
                                text = stringResource(string.str_add)
                            )
                        }
                    }
                } else{
                    HorizontalDivider()
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(
                                if(links.size >= 5){ 0.1f }else{ 0.2f }
                            ),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            if(links.size < 5){
                                Surface(
                                    onClick = onCreateBizLink,
                                    modifier = Modifier
                                        .size(20.dp)
                                        .weight(0.1f)
                                ) {
                                    Icon(
                                        modifier = Modifier,
                                        imageVector = Default.Add,
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
                                    imageVector = Default.Clear,
                                    contentDescription = null
                                )
                            }
                        }
                    }
                    HorizontalDivider()
                    links.forEach{ link ->
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth(),
                            shape = shapes.medium,
                            border = BorderStroke(1.dp, DarkGray)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically)
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
                                    ){
                                        link.label?.let {
                                            Text(
                                                text = it,
                                                fontWeight = Bold,
                                                style = typography.titleMedium
                                            )
                                        }
                                    }
                                    Row(
                                        modifier = Modifier.weight(0.2f),
                                        horizontalArrangement = Arrangement.Center,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Surface(
                                            onClick = {onUpdateBizLink(link)},
                                            modifier = Modifier
                                                .size(20.dp)
                                                .weight(0.1f)
                                        ) {
                                            Icon(
                                                imageVector = Default.Edit,
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
                                                imageVector = Default.Clear,
                                                contentDescription = null
                                            )
                                        }
                                    }
                                }

                                HorizontalDivider()

                                Column(
                                    modifier = Modifier.fillMaxWidth()
                                        .padding(8.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically)
                                ) {
                                    //Gen ID
                                    link.genId.let {
                                        Column(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalAlignment = Alignment.Start,
                                            verticalArrangement = Arrangement.Center
                                        ) {
                                            Text(
                                                text = stringResource(string.str_gen_id),
                                                maxLines = 1,
                                                fontWeight = Bold,
                                                style = typography.labelSmall,
                                                overflow = Ellipsis
                                            )
                                            Text(
                                                text = it,
                                                style = typography.bodyMedium
                                            )
                                        }
                                    }

                                    //Label
                                    link.label?.let {
                                        Column(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalAlignment = Alignment.Start,
                                            verticalArrangement = Arrangement.Center
                                        ) {
                                            Text(
                                                text = stringResource(string.str_address_label),
                                                maxLines = 1,
                                                fontWeight = Bold,
                                                style = typography.labelSmall,
                                                overflow = Ellipsis
                                            )
                                            Text(
                                                text = it,
                                                style = typography.bodyMedium
                                            )
                                        }
                                    }

                                    //URI
                                    link.uri?.let {
                                        Column(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalAlignment = Alignment.Start,
                                            verticalArrangement = Arrangement.Center
                                        ) {
                                            Text(
                                                text = stringResource(string.str_additional_info),
                                                maxLines = 1,
                                                fontWeight = Bold,
                                                style = typography.labelSmall,
                                                overflow = Ellipsis
                                            )
                                            Text(
                                                text = it,
                                                style = typography.bodyMedium
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
                                                text = stringResource(string.str_modified_at),
                                                maxLines = 1,
                                                fontWeight = Bold,
                                                style = typography.labelSmall,
                                                overflow = Ellipsis
                                            )
                                            Text(
                                                text = it.toString(),
                                                style = typography.bodyMedium
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

@Preview(showBackground = true)
@Composable
private fun Preview(){
    ApplicationTheme {
        Column(modifier = Modifier.fillMaxSize()) {
            ScrBizProfile(
                onNavigateBack = {},
                onUpdateBizIdName = {}, onResetBizIdName = {},
                onUpdateBizIdIndustry = {}, onResetBizIdIndustry = {},
                onUpdateBizIdLegal = {}, onResetBizIdLegal = {},
                onUpdateBizIdTaxation = {}, onResetBizIdTaxation = {},
                onCreateBizAddress = {}, onUpdateBizAddress = {}, onDeleteBizAddress = {}, onDeleteAllBizAddresses = {},
                onCreateBizContact = {}, onUpdateBizContact = {}, onDeleteBizContact = {}, onDeleteAllBizContacts = {},
                onCreateBizLink = {}, onUpdateBizLink = {}, onDeleteBizLink = {}, onDeleteAllBizLinks = {},
                uiState = UiState(
                    bizProfile = Success(
                        configCurrent = AppConfig.ConfigCurrent(),
                        bizProfile = BizProfile(
                            seqId = 1,
                            genId = ULID.randomULID(),
                            auditTrail = AuditTrail(),
                            bizIdentity = BizIdentity(
                                bizName = BizName(
                                    legalName = "Future Ltd",
                                    commonName = "Future corp",
                                    auditTrail = AuditTrail()
                                ),
                                industries = Industries(
                                    identityKey = 0,
                                    additionalInfo = "Others",
                                    auditTrail = AuditTrail()
                                ),
                                legalType = LegalType(
                                    identifierKey = 0,
                                    auditTrail = AuditTrail(),
                                    additionalInfo = "PT",
                                    legalDocumentType = LegalDocumentType(
                                        identifierKey = 0,
                                        additionalInfo = "Doc No Comp-00001",
                                        auditTrail = AuditTrail()
                                    )
                                ),
                                taxation = Taxation(
                                    identifierKey = 0,
                                    auditTrail = AuditTrail(),
                                    taxIdDocNumber = "3374012005930001",
                                    taxIncluded = true,
                                    taxRatePercentage = 11.0,
                                    taxIssuerCountry = Country(
                                        isoCode = "ID",
                                        iso03Country = "INA",
                                        displayName = "Republic of Indonesia"
                                    )
                                ),
                                auditTrail = AuditTrail()
                            ),
                            addresses = listOf(
                                Address(
                                    genId = ULID.randomULID(),
                                    identifierKey = 0,
                                    auditTrail = AuditTrail(),
                                    label = "Primary Address",
                                    streetLine = "Rose st. no. 31",
                                    postalCode = "50136",
                                    additionalInfo = "Additional Info 0",
                                    country = Country(
                                        isoCode = "ID",
                                        iso03Country = "INA",
                                        displayName = "Republic of Indonesia"
                                    )
                                )
                            ),
                            contacts = listOf(
                                Contact(
                                    genId = ULID.randomULID(),
                                    identifierKey = 0,
                                    auditTrail = AuditTrail(),
                                    label = "Primary Hand Phone",
                                    mediaIdentifierKey = 0,
                                    additionalInfo = "Land Line",
                                    contactValue = "083842400262"
                                )
                            ),
                            links = listOf(
                                Link(
                                    genId = ULID.randomULID(),
                                    identifierKey = 0,
                                    label = "Facebook",
                                    uri = "https://facebook.com/thomas200593",
                                    auditTrail = AuditTrail()
                                )
                            )
                        )
                    )
                ),
            )
        }
    }
}
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.Icons.Default
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.ButtonDefaults.IconSize
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
import com.thomas200593.mini_retail_app.features.app_conf.app_config.entity.AppConfig.ConfigCurrent
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_country.entity.Country
import com.thomas200593.mini_retail_app.features.business.biz_profile.entity.BizIdentity
import com.thomas200593.mini_retail_app.features.business.biz_profile.entity.BizName
import com.thomas200593.mini_retail_app.features.business.biz_profile.entity.BizProfile
import com.thomas200593.mini_retail_app.features.business.biz_profile.ui.VMBizProfile.UiEvents.OnOpenEvents
import com.thomas200593.mini_retail_app.features.business.biz_profile.ui.VMBizProfile.UiState
import com.thomas200593.mini_retail_app.features.business.biz_profile.ui.VMBizProfile.UiStateBizProfile.Error
import com.thomas200593.mini_retail_app.features.business.biz_profile.ui.VMBizProfile.UiStateBizProfile.Loading
import com.thomas200593.mini_retail_app.features.business.biz_profile.ui.VMBizProfile.UiStateBizProfile.Success

@Composable
fun ScrBizProfile(
    vm: VMBizProfile = hiltViewModel(),
    stateApp: StateApp = LocalStateApp.current
){
    val sessionState by stateApp.isSessionValid.collectAsStateWithLifecycle()
    val uiState by vm.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(sessionState) { vm.onEvent(OnOpenEvents(sessionState)) }

    ScrBizProfile(
        onNavigateBack = {},
        uiState = uiState
    )
}

@Composable
private fun ScrBizProfile(
    onNavigateBack: () -> Unit,
    uiState: UiState
){
    TopAppBar(onNavigateBack = onNavigateBack)
    when(uiState.bizProfile){
        Loading -> LoadingScreen()
        is Error -> ErrorScreen(
            title = stringResource(id = string.str_error),
            errorMessage = stringResource(id = string.str_error_fetching_preferences),
            showIcon = true
        )
        is Success -> ScreenContent(
            bizProfile = uiState.bizProfile.bizProfile
        )
    }
}

@Composable
private fun TopAppBar(onNavigateBack: () -> Unit) {
    ProvideTopAppBarNavigationIcon {
        Surface(onClick = onNavigateBack, modifier = Modifier) {
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
                imageVector = Icons.Default.Info,
                contentDescription = null
            )
        }
    }
}

@Composable
private fun ScreenContent(
    bizProfile: BizProfile
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.Top)
    ) {
        BusinessIdentitySection(bizProfile = bizProfile)
        BusinessAddressesSection(addresses = bizProfile.addresses)
        BusinessContactsSection(contacts = bizProfile.contacts)
        BusinessLinksSection(links = bizProfile.links)
    }
}

@Composable
private fun BusinessIdentitySection(bizProfile: BizProfile) {
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
                    text = "Business Identity",
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
                        if(expanded) Icons.Default.KeyboardArrowDown
                            else Icons.AutoMirrored.Default.KeyboardArrowRight,
                        contentDescription = null
                    )
                }
            }

            if(expanded){
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Gen ID",
                        maxLines = 1,
                        fontWeight = Bold,
                        style = typography.labelSmall,
                        overflow = Ellipsis,
                        modifier = Modifier
                    )
                    Text(
                        text = bizProfile.genId,
                        style = typography.bodyMedium,
                        modifier = Modifier
                    )
                }

                /*Business Name*/
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = shapes.medium,
                    color = colorScheme.errorContainer
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
                                text = "Business Name",
                                fontWeight = Bold,
                                textAlign = Start,
                                overflow = Ellipsis,
                                maxLines = 1,
                                modifier = Modifier
                                    .weight(0.8f)
                                    .fillMaxWidth()
                            )
                            Surface(modifier = Modifier.weight(0.1f), color = Transparent) {
                                Icon(
                                    modifier = Modifier.size(20.dp),
                                    imageVector = Default.Edit,
                                    contentDescription = null
                                )
                            }
                            Surface(modifier = Modifier.weight(0.1f), color = Transparent) {
                                Icon(
                                    modifier = Modifier.size(20.dp),
                                    imageVector = Default.Clear,
                                    contentDescription = null
                                )
                            }
                        }

                        bizProfile.bizIdentity.bizName.legalName?.let {
                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalAlignment = Alignment.Start,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = "Legal Name",
                                    maxLines = 1,
                                    fontWeight = Bold,
                                    style = typography.labelSmall,
                                    overflow = Ellipsis,
                                    modifier = Modifier
                                )
                                Text(
                                    text = bizProfile.bizIdentity.bizName.legalName,
                                    style = typography.bodyMedium,
                                    modifier = Modifier
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
                                    text = "Common Name",
                                    maxLines = 1,
                                    fontWeight = Bold,
                                    style = typography.labelSmall,
                                    overflow = Ellipsis,
                                    modifier = Modifier
                                )
                                Text(
                                    text = bizProfile.bizIdentity.bizName.commonName,
                                    style = typography.bodyMedium,
                                    modifier = Modifier
                                )
                            }
                        }
                    }
                }

                /*Business Industry*/
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = shapes.medium,
                    color = colorScheme.errorContainer
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
                                text = "Business Industry",
                                fontWeight = Bold,
                                textAlign = Start,
                                overflow = Ellipsis,
                                maxLines = 1,
                                modifier = Modifier
                                    .weight(0.8f)
                                    .fillMaxWidth()
                            )
                            Surface(modifier = Modifier.weight(0.1f), color = Transparent) {
                                Icon(
                                    modifier = Modifier.size(20.dp),
                                    imageVector = Default.Edit,
                                    contentDescription = null
                                )
                            }
                            Surface(modifier = Modifier.weight(0.1f), color = Transparent) {
                                Icon(
                                    modifier = Modifier.size(20.dp),
                                    imageVector = Default.Clear,
                                    contentDescription = null
                                )
                            }
                        }

                        bizProfile.bizIdentity.industries.identityKey.let {
                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalAlignment = Alignment.Start,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = "Industry ID",
                                    maxLines = 1,
                                    fontWeight = Bold,
                                    style = typography.labelSmall,
                                    overflow = Ellipsis,
                                    modifier = Modifier
                                )
                                Text(
                                    text = bizProfile.bizIdentity.industries.identityKey.toString(),
                                    style = typography.bodyMedium,
                                    modifier = Modifier
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
                                    text = "Industry Additional Info",
                                    maxLines = 1,
                                    fontWeight = Bold,
                                    style = typography.labelSmall,
                                    overflow = Ellipsis,
                                    modifier = Modifier
                                )
                                Text(
                                    text = bizProfile.bizIdentity.industries.additionalInfo,
                                    style = typography.bodyMedium,
                                    modifier = Modifier
                                )
                            }
                        }
                    }
                }

                /*Business Legal*/
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = shapes.medium,
                    color = colorScheme.errorContainer
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
                                text = "Business Legal",
                                fontWeight = Bold,
                                textAlign = Start,
                                overflow = Ellipsis,
                                maxLines = 1,
                                modifier = Modifier
                                    .weight(0.8f)
                                    .fillMaxWidth()
                            )
                            Surface(modifier = Modifier.weight(0.1f), color = Transparent) {
                                Icon(
                                    modifier = Modifier.size(20.dp),
                                    imageVector = Default.Edit,
                                    contentDescription = null
                                )
                            }
                            Surface(modifier = Modifier.weight(0.1f), color = Transparent) {
                                Icon(
                                    modifier = Modifier.size(20.dp),
                                    imageVector = Default.Clear,
                                    contentDescription = null
                                )
                            }
                        }

                        bizProfile.bizIdentity.legalType.identifierKey.let {
                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalAlignment = Alignment.Start,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = "Biz Legal ID",
                                    maxLines = 1,
                                    fontWeight = Bold,
                                    style = typography.labelSmall,
                                    overflow = Ellipsis,
                                    modifier = Modifier
                                )
                                Text(
                                    text = bizProfile.bizIdentity.legalType.identifierKey.toString(),
                                    style = typography.bodyMedium,
                                    modifier = Modifier
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
                                    text = "Biz Legal Additional Info",
                                    maxLines = 1,
                                    fontWeight = Bold,
                                    style = typography.labelSmall,
                                    overflow = Ellipsis,
                                    modifier = Modifier
                                )
                                Text(
                                    text = bizProfile.bizIdentity.legalType.additionalInfo,
                                    style = typography.bodyMedium,
                                    modifier = Modifier
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
                                        text = "Biz Legal Doc Type ID",
                                        maxLines = 1,
                                        fontWeight = Bold,
                                        style = typography.labelSmall,
                                        overflow = Ellipsis,
                                        modifier = Modifier
                                    )
                                    Text(
                                        text = bizProfile.bizIdentity.legalType.legalDocumentType.identifierKey.toString(),
                                        style = typography.bodyMedium,
                                        modifier = Modifier
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
                                        text = "Biz Legal Doc Additional Info Type ID",
                                        maxLines = 1,
                                        fontWeight = Bold,
                                        style = typography.labelSmall,
                                        overflow = Ellipsis,
                                        modifier = Modifier
                                    )
                                    Text(
                                        text = bizProfile.bizIdentity.legalType.legalDocumentType.additionalInfo,
                                        style = typography.bodyMedium,
                                        modifier = Modifier
                                    )
                                }
                            }
                        }
                    }
                }

                /*Business Taxation*/
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = shapes.medium,
                    color = colorScheme.errorContainer
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
                                text = "Business Taxation",
                                fontWeight = Bold,
                                textAlign = Start,
                                overflow = Ellipsis,
                                maxLines = 1,
                                modifier = Modifier
                                    .weight(0.8f)
                                    .fillMaxWidth()
                            )
                            Surface(modifier = Modifier.weight(0.1f), color = Transparent) {
                                Icon(
                                    modifier = Modifier.size(20.dp),
                                    imageVector = Default.Edit,
                                    contentDescription = null
                                )
                            }
                            Surface(modifier = Modifier.weight(0.1f), color = Transparent) {
                                Icon(
                                    modifier = Modifier.size(20.dp),
                                    imageVector = Default.Clear,
                                    contentDescription = null
                                )
                            }
                        }

                        bizProfile.bizIdentity.taxation.identifierKey.let {
                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalAlignment = Alignment.Start,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = "Tax Type ID",
                                    maxLines = 1,
                                    fontWeight = Bold,
                                    style = typography.labelSmall,
                                    overflow = Ellipsis,
                                    modifier = Modifier
                                )
                                Text(
                                    text = bizProfile.bizIdentity.taxation.identifierKey.toString(),
                                    style = typography.bodyMedium,
                                    modifier = Modifier
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
                                    text = "Tax ID Doc Number Key",
                                    maxLines = 1,
                                    fontWeight = Bold,
                                    style = typography.labelSmall,
                                    overflow = Ellipsis,
                                    modifier = Modifier
                                )
                                Text(
                                    text = "Tax ID Doc Number Value",
                                    style = typography.bodyMedium,
                                    modifier = Modifier
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
                                    text = "Tax ID Issuer Country Key",
                                    maxLines = 1,
                                    fontWeight = Bold,
                                    style = typography.labelSmall,
                                    overflow = Ellipsis,
                                    modifier = Modifier
                                )
                                Text(
                                    text = bizProfile.bizIdentity.taxation.taxIssuerCountry.displayName,
                                    style = typography.bodyMedium,
                                    modifier = Modifier
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
                                    text = "Tax Rate Percentage Key",
                                    maxLines = 1,
                                    fontWeight = Bold,
                                    style = typography.labelSmall,
                                    overflow = Ellipsis,
                                    modifier = Modifier
                                )
                                Text(
                                    text = bizProfile.bizIdentity.taxation.taxRatePercentage.toString(),
                                    style = typography.bodyMedium,
                                    modifier = Modifier
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
                                    text = "Tax Included Key",
                                    maxLines = 1,
                                    fontWeight = Bold,
                                    style = typography.labelSmall,
                                    overflow = Ellipsis,
                                    modifier = Modifier
                                )
                                Text(
                                    text = bizProfile.bizIdentity.taxation.taxIncluded.toString(),
                                    style = typography.bodyMedium,
                                    modifier = Modifier
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
private fun BusinessAddressesSection(addresses: List<Address>?) {
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
                    text = "Business Addresses",
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
                        if(expanded) Icons.Default.KeyboardArrowDown
                        else Icons.AutoMirrored.Default.KeyboardArrowRight,
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
                                text = "You have not set any business address, would you mind to set it?",
                                textAlign = Justify
                            )
                            AppIconButton(
                                onClick = {},
                                icon = Default.Add,
                                text = "Add address"
                            )
                        }
                    }
                } else{
                    if(addresses.size < 5){
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(0.2f),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Surface(modifier = Modifier
                                    .size(20.dp)
                                    .weight(0.1f)) {
                                    Icon(
                                        modifier = Modifier,
                                        imageVector = Default.Add,
                                        contentDescription = null
                                    )
                                }
                                Surface(modifier = Modifier
                                    .size(20.dp)
                                    .weight(0.1f)) {
                                    Icon(
                                        modifier = Modifier,
                                        imageVector = Default.Clear,
                                        contentDescription = null
                                    )
                                }
                            }
                        }
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
                                    /**
                                     * Interaction Button
                                     */
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.End,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Row(
                                            modifier = Modifier.fillMaxWidth(0.2f),
                                            horizontalArrangement = Arrangement.Center,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Surface(modifier = Modifier
                                                .size(20.dp)
                                                .weight(0.1f)) {
                                                Icon(
                                                    modifier = Modifier,
                                                    imageVector = Default.Edit,
                                                    contentDescription = null
                                                )
                                            }
                                            Surface(modifier = Modifier
                                                .size(20.dp)
                                                .weight(0.1f)) {
                                                Icon(
                                                    modifier = Modifier,
                                                    imageVector = Default.Clear,
                                                    contentDescription = null
                                                )
                                            }
                                        }
                                    }

                                    /**
                                     * Label
                                     */
                                    address.label?.let {
                                        Column(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalAlignment = Alignment.Start,
                                            verticalArrangement = Arrangement.Center
                                        ) {
                                            Text(
                                                text = "Address Label",
                                                maxLines = 1,
                                                fontWeight = Bold,
                                                style = typography.labelSmall,
                                                overflow = Ellipsis,
                                                modifier = Modifier
                                            )
                                            Text(
                                                text = it,
                                                style = typography.bodyMedium,
                                                modifier = Modifier
                                            )
                                        }
                                    }

                                    address.label?.let {
                                        Column(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalAlignment = Alignment.Start,
                                            verticalArrangement = Arrangement.Center
                                        ) {
                                            Text(
                                                text = "Address Label",
                                                maxLines = 1,
                                                fontWeight = Bold,
                                                style = typography.labelSmall,
                                                overflow = Ellipsis,
                                                modifier = Modifier
                                            )
                                            Text(
                                                text = it,
                                                style = typography.bodyMedium,
                                                modifier = Modifier
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

@Composable
private fun BusinessContactsSection(contacts: List<Contact>?) {
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
                    text = "Business Contacts",
                    fontWeight = Bold,
                    textAlign = Start,
                    overflow = Ellipsis,
                    maxLines = 1,
                    modifier = Modifier
                        .weight(0.7f)
                        .fillMaxWidth()
                )
                Row(
                    modifier = Modifier.weight(0.2f),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(modifier = Modifier.weight(0.1f)) {
                        Icon(
                            modifier = Modifier.size(20.dp),
                            imageVector = Default.Add,
                            contentDescription = null
                        )
                    }
                    Surface(modifier = Modifier.weight(0.1f)) {
                        Icon(
                            modifier = Modifier.size(20.dp),
                            imageVector = Default.Clear,
                            contentDescription = null
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun BusinessLinksSection(links: List<Link>?) {
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
                    text = "Business Links",
                    fontWeight = Bold,
                    textAlign = Start,
                    overflow = Ellipsis,
                    maxLines = 1,
                    modifier = Modifier
                        .weight(0.7f)
                        .fillMaxWidth()
                )
                Row(
                    modifier = Modifier.weight(0.2f),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(modifier = Modifier.weight(0.1f)) {
                        Icon(
                            modifier = Modifier.size(20.dp),
                            imageVector = Default.Add,
                            contentDescription = null
                        )
                    }
                    Surface(modifier = Modifier.weight(0.1f)) {
                        Icon(
                            modifier = Modifier.size(20.dp),
                            imageVector = Default.Clear,
                            contentDescription = null
                        )
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
                uiState = UiState(
                    bizProfile = Success(
                        bizProfile = BizProfile(
                            seqId = 0,
                            genId = "Gen ID",
                            auditTrail = AuditTrail(),
                            bizIdentity = BizIdentity(
                                bizName = BizName(legalName = "My Company", commonName = "Company"),
                                industries = Industries(identityKey = 0, additionalInfo = "My Industry"),
                                legalType = LegalType(
                                    identifierKey = 0,
                                    additionalInfo = "PT",
                                    legalDocumentType = LegalDocumentType(identifierKey = 0, additionalInfo = "My Document"),
                                    auditTrail = AuditTrail()
                                ),
                                taxation = Taxation(identifierKey = 0)
                            ),
                            addresses = listOf(
                                Address(
                                    identifierKey = 0,
                                    additionalInfo = "Additional Info",
                                    label = "Alamat Utama",
                                    streetLine = "Jalan Jakarta",
                                    postalCode = "51012",
                                    country = Country(
                                        isoCode = "US",
                                        iso03Country = "USA",
                                        displayName = "America"
                                    ),
                                    auditTrail = AuditTrail()
                                ),
                                Address(
                                    identifierKey = 0,
                                    additionalInfo = "Additional Info",
                                    label = "Alamat 2nd",
                                    streetLine = "Jalan Semarang",
                                    postalCode = "12345",
                                    country = Country(
                                        isoCode = "US",
                                        iso03Country = "USA",
                                        displayName = "America"
                                    ),
                                    auditTrail = AuditTrail()
                                )
                            )
                        ),
                        configCurrent = ConfigCurrent()
                    )
                )
            )
        }
    }
}
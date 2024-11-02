package com.thomas200593.mini_retail_app.features.app_conf.conf_gen_country.ui

import android.content.pm.ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.outlined.KeyboardArrowRight
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
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
import com.thomas200593.mini_retail_app.core.data.local.session.SessionState
import com.thomas200593.mini_retail_app.core.ui.component.CustomPanel.ThreeRowCardItem
import com.thomas200593.mini_retail_app.core.ui.component.CustomScreenUtil.LockScreenOrientation
import com.thomas200593.mini_retail_app.core.ui.component.app_bar.CustomAppBar.ProvideTopAppBarAction
import com.thomas200593.mini_retail_app.core.ui.component.app_bar.CustomAppBar.ProvideTopAppBarNavigationIcon
import com.thomas200593.mini_retail_app.core.ui.component.app_bar.CustomAppBar.ProvideTopAppBarTitle
import com.thomas200593.mini_retail_app.core.ui.component.dialog.DlgError
import com.thomas200593.mini_retail_app.core.ui.component.dialog.DlgInformation
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_country.entity.ConfigCountry
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_country.entity.Country
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_country.ui.VMConfGenCountry.UiEvents.ButtonEvents.BtnNavBackEvents
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_country.ui.VMConfGenCountry.UiEvents.ButtonEvents.BtnScrDescEvents
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_country.ui.VMConfGenCountry.UiEvents.ButtonEvents.BtnSetPrefCountryEvents
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_country.ui.VMConfGenCountry.UiEvents.DialogEvents.DlgDenySetDataEvents
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_country.ui.VMConfGenCountry.UiEvents.OnOpenEvents
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_country.ui.VMConfGenCountry.UiState
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_country.ui.VMConfGenCountry.UiStateConfigCountry.Loading
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_country.ui.VMConfGenCountry.UiStateConfigCountry.Success
import kotlinx.coroutines.launch

@Composable
fun ScrConfGenCountry(
    vm: VMConfGenCountry = hiltViewModel(),
    stateApp: StateApp = LocalStateApp.current
) {
    LockScreenOrientation(SCREEN_ORIENTATION_PORTRAIT)

    val coroutineScope = rememberCoroutineScope()
    val uiState by vm.uiState.collectAsStateWithLifecycle()
    val sessionState by stateApp.isSessionValid.collectAsStateWithLifecycle()
    val currentScreen = ScrGraphs.getByRoute(stateApp.destCurrent)

    LaunchedEffect(sessionState, currentScreen)
    { currentScreen?.let { vm.onEvent(OnOpenEvents(sessionState, it)) } }

    ScrConfGenCountry(
        uiState = uiState,
        currentScreen = currentScreen,
        onNavigateBack = {
            vm.onEvent(BtnNavBackEvents.OnClick)
                .also { coroutineScope.launch { stateApp.onNavUp() } }
        },
        onShowScrDesc = { vm.onEvent(BtnScrDescEvents.OnClick) },
        onDismissDlgScrDesc = { vm.onEvent(BtnScrDescEvents.OnDismiss) },
        onSetData = { country ->
            currentScreen?.let {
                when (sessionState) {
                    SessionState.Loading -> Unit
                    is SessionState.Invalid ->
                        if (it.usesAuth) vm.onEvent(BtnSetPrefCountryEvents.OnDeny)
                        else vm.onEvent(BtnSetPrefCountryEvents.OnAllow(country))
                    is SessionState.Valid ->
                        vm.onEvent(BtnSetPrefCountryEvents.OnAllow(country))
                }
            }
        },
        onDismissDlgDenySetData = {
            vm.onEvent(DlgDenySetDataEvents.OnDismiss)
                .also { coroutineScope.launch { stateApp.onNavUp() } }
        }
    )
}

@Composable
private fun ScrConfGenCountry(
    uiState: UiState,
    currentScreen: ScrGraphs?,
    onNavigateBack: () -> Unit,
    onShowScrDesc: (String) -> Unit,
    onDismissDlgScrDesc: () -> Unit,
    onSetData: (Country) -> Unit,
    onDismissDlgDenySetData: () -> Unit,
) {
    currentScreen?.let {
        HandleDialogs(
            uiState = uiState,
            currentScreen = it,
            onDismissDlgScrDesc = onDismissDlgScrDesc,
            onDismissDlgDenySetData = onDismissDlgDenySetData
        )
        TopAppBar(
            scrGraphs = it,
            onNavigateBack = onNavigateBack,
            onShowScrDesc = onShowScrDesc
        )
    }
    when (uiState.configCountry) {
        Loading -> Unit
        is Success -> ScreenContent(
            configCountry = uiState.configCountry.configCountry,
            onSetData = onSetData
        )
    }
}

@Composable
private fun HandleDialogs(
    uiState: UiState,
    currentScreen: ScrGraphs,
    onDismissDlgScrDesc: () -> Unit,
    onDismissDlgDenySetData: () -> Unit
) {
    DlgInformation.Auth(showDialog = uiState.dialogState.dlgLoadingAuth)
    DlgInformation.GetData(showDialog = uiState.dialogState.dlgLoadingGetData)
    DlgInformation.ScrDesc(
        showDialog = uiState.dialogState.dlgScrDesc,
        currentScreen = currentScreen,
        onDismiss = onDismissDlgScrDesc
    )
    DlgError.SessionInvalid(
        showDialog = uiState.dialogState.dlgDenySetData,
        onDismiss = onDismissDlgDenySetData
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
                    modifier = Modifier.sizeIn(maxHeight = ButtonDefaults.IconSize),
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
    configCountry: ConfigCountry,
    onSetData: (Country) -> Unit
) {
    val currentData = configCountry.configCurrent.country
    val preferencesList = configCountry.countries
    Column(
        modifier = Modifier.fillMaxSize().padding(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Surface(
            modifier = Modifier,
            color = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        ) {
            Text(
                text = "${stringResource(id = R.string.str_country)}: ${currentData.flag} ${currentData.displayName}",
                modifier = Modifier.fillMaxWidth().padding(4.dp),
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center,
            )
        }
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(count = preferencesList.count()) {
                val data = preferencesList[it]
                ThreeRowCardItem(
                    firstRowContent = {
                        Text(
                            text = data.flag,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    },
                    secondRowContent = {
                        Text(
                            text = data.displayName,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Start,
                            fontWeight = FontWeight.Bold,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    },
                    thirdRowContent = {
                        Surface(
                            modifier = Modifier.fillMaxWidth(),
                            onClick = { onSetData(data) }
                        ) {
                            Icon(
                                imageVector =
                                    if (data == currentData) Icons.Default.CheckCircle
                                    else Icons.AutoMirrored.Outlined.KeyboardArrowRight,
                                contentDescription = null,
                                tint =
                                    if (data == currentData) Color.Green
                                    else MaterialTheme.colorScheme.onTertiaryContainer
                            )
                        }
                    }
                )
            }
        }
    }
}
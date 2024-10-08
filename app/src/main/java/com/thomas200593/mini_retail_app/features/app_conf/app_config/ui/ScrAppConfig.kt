package com.thomas200593.mini_retail_app.features.app_conf.app_config.ui

import android.content.pm.ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.thomas200593.mini_retail_app.R
import com.thomas200593.mini_retail_app.app.navigation.ScrGraphs
import com.thomas200593.mini_retail_app.app.ui.LocalStateApp
import com.thomas200593.mini_retail_app.app.ui.StateApp
import com.thomas200593.mini_retail_app.core.data.local.session.SessionState
import com.thomas200593.mini_retail_app.core.ui.common.CustomThemes
import com.thomas200593.mini_retail_app.core.ui.component.CustomAppBar.ProvideTopAppBarAction
import com.thomas200593.mini_retail_app.core.ui.component.CustomAppBar.ProvideTopAppBarNavigationIcon
import com.thomas200593.mini_retail_app.core.ui.component.CustomAppBar.ProvideTopAppBarTitle
import com.thomas200593.mini_retail_app.core.ui.component.CustomButton.Common.AppIconButton
import com.thomas200593.mini_retail_app.core.ui.component.CustomDialog.AlertDialogContext
import com.thomas200593.mini_retail_app.core.ui.component.CustomDialog.AppAlertDialog
import com.thomas200593.mini_retail_app.core.ui.component.CustomPanel.ClickableCardItem
import com.thomas200593.mini_retail_app.core.ui.component.CustomScreenUtil.LockScreenOrientation
import com.thomas200593.mini_retail_app.features.app_conf.app_config.navigation.DestAppConfig
import com.thomas200593.mini_retail_app.features.app_conf.app_config.navigation.navToAppConfig
import com.thomas200593.mini_retail_app.features.app_conf.app_config.ui.VMAppConfig.DialogState
import com.thomas200593.mini_retail_app.features.app_conf.app_config.ui.VMAppConfig.UiEvents.ButtonEvents.BtnMenuSelectionEvents
import com.thomas200593.mini_retail_app.features.app_conf.app_config.ui.VMAppConfig.UiEvents.ButtonEvents.BtnNavBackEvents
import com.thomas200593.mini_retail_app.features.app_conf.app_config.ui.VMAppConfig.UiEvents.ButtonEvents.BtnScrDescEvents
import com.thomas200593.mini_retail_app.features.app_conf.app_config.ui.VMAppConfig.UiEvents.DialogEvents.DlgDenyAccessEvents
import com.thomas200593.mini_retail_app.features.app_conf.app_config.ui.VMAppConfig.UiEvents.OnOpenEvents
import com.thomas200593.mini_retail_app.features.app_conf.app_config.ui.VMAppConfig.UiState
import com.thomas200593.mini_retail_app.features.app_conf.app_config.ui.VMAppConfig.UiStateDestAppConfig.Loading
import com.thomas200593.mini_retail_app.features.app_conf.app_config.ui.VMAppConfig.UiStateDestAppConfig.Success
import kotlinx.coroutines.launch

@Composable
fun ScrAppConfig(
    vm: VMAppConfig = hiltViewModel(),
    stateApp: StateApp = LocalStateApp.current
) {
    LockScreenOrientation(SCREEN_ORIENTATION_PORTRAIT)

    val coroutineScope = rememberCoroutineScope()
    val uiState by vm.uiState.collectAsStateWithLifecycle()
    val sessionState by stateApp.isSessionValid.collectAsStateWithLifecycle()
    val currentScreen = ScrGraphs.getByRoute(stateApp.destCurrent)

    LaunchedEffect(sessionState) { vm.onEvent(OnOpenEvents(sessionState)) }

    ScrAppConfig(
        uiState = uiState,
        currentScreen = currentScreen,
        onNavigateBack = {
            vm.onEvent(BtnNavBackEvents.OnClick)
                .also { coroutineScope.launch { stateApp.onNavUp() } }
        },
        onShowScrDesc = { vm.onEvent(BtnScrDescEvents.OnClick) },
        onDismissDlgScrDesc = { vm.onEvent(BtnScrDescEvents.OnDismiss) },
        onNavToMenu = { menu ->
            when (sessionState) {
                SessionState.Loading -> Unit
                is SessionState.Invalid ->
                    if (menu.scrGraphs.usesAuth) vm.onEvent(BtnMenuSelectionEvents.OnDeny)
                    else vm.onEvent(BtnMenuSelectionEvents.OnAllow)
                        .also { coroutineScope.launch { stateApp.navController.navToAppConfig(menu) } }
                is SessionState.Valid -> vm.onEvent(BtnMenuSelectionEvents.OnAllow)
                    .also { coroutineScope.launch { stateApp.navController.navToAppConfig(menu) } }
            }
        },
        onDismissDlgDenyAccessMenu = {
            vm.onEvent(DlgDenyAccessEvents.OnDismiss)
                .also { coroutineScope.launch { stateApp.onNavUp() } }
        }
    )
}

@Composable
private fun ScrAppConfig(
    uiState: UiState,
    currentScreen: ScrGraphs?,
    onNavigateBack: () -> Unit,
    onShowScrDesc: (String) -> Unit,
    onDismissDlgScrDesc: () -> Unit,
    onNavToMenu: (DestAppConfig) -> Unit,
    onDismissDlgDenyAccessMenu: () -> Unit
) {
    currentScreen?.let {
        HandleDialogs(
            uiState = uiState,
            currentScreen = it,
            onDismissDlgScrDesc = onDismissDlgScrDesc,
            onDismissDlgDenyAccessMenu = onDismissDlgDenyAccessMenu
        )
        TopAppBar(
            scrGraphs = it,
            onNavigateBack = onNavigateBack,
            onShowScrDesc = onShowScrDesc
        )
    }
    when (uiState.destAppConfig) {
        Loading -> Unit
        is Success -> ScreenContent(
            menuPreferences = uiState.destAppConfig.destAppConfig,
            onNavToMenu = onNavToMenu
        )
    }
}

@Composable
private fun HandleDialogs(
    uiState: UiState,
    currentScreen: ScrGraphs,
    onDismissDlgScrDesc: () -> Unit,
    onDismissDlgDenyAccessMenu: () -> Unit
) {
    AppAlertDialog(
        showDialog = uiState.dialogState.dlgLoadingAuth,
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
            ) { Text(text = stringResource(id = R.string.str_authenticating)) }
        }
    )
    AppAlertDialog(
        showDialog = uiState.dialogState.dlgLoadingGetMenu,
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
            ) { Text(text = stringResource(id = R.string.str_loading_data)) }
        }
    )
    AppAlertDialog(
        showDialog = uiState.dialogState.dlgSessionInvalid,
        dialogContext = AlertDialogContext.ERROR,
        showTitle = true,
        title = { Text(text = stringResource(id = R.string.str_error)) },
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
                onClick = onDismissDlgDenyAccessMenu,
                icon = Icons.Default.Close,
                text = stringResource(id = R.string.str_close),
                containerColor = MaterialTheme.colorScheme.error,
                contentColor = MaterialTheme.colorScheme.onError
            )
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
                    modifier = Modifier.fillMaxWidth().verticalScroll(rememberScrollState()),
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
    menuPreferences: Set<DestAppConfig>,
    onNavToMenu: (DestAppConfig) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(count = menuPreferences.count()) {
            val menu = menuPreferences.elementAt(it)
            ClickableCardItem(
                onClick = { onNavToMenu(menu) },
                icon = menu.scrGraphs.iconRes
                    ?.let { icon -> ImageVector.vectorResource(id = icon) } ?: Icons.Default.Info,
                title = menu.scrGraphs.title
                    ?.let { title -> stringResource(id = title) }.orEmpty(),
                subtitle = menu.scrGraphs.description
                    ?.let { desc -> stringResource(id = desc) }.orEmpty()
            )
        }
    }
}

@Composable
@Preview
private fun Preview() = CustomThemes.ApplicationTheme {
    ScrAppConfig(
        currentScreen = ScrGraphs.AppConfig,
        onNavigateBack = {},
        onShowScrDesc = {},
        onDismissDlgScrDesc = {},
        onDismissDlgDenyAccessMenu = {},
        onNavToMenu = {},
        uiState = UiState(
            dialogState = DialogState(),
            destAppConfig = Success(
                destAppConfig = setOf(
                    DestAppConfig.APP_CONFIG_GENERAL,
                    DestAppConfig.APP_CONFIG_DATA
                )
            )
        )
    )
}
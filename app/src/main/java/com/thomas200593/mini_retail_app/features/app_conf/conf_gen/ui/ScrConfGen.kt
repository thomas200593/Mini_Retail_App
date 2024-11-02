package com.thomas200593.mini_retail_app.features.app_conf.conf_gen.ui

import android.content.pm.ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.thomas200593.mini_retail_app.app.navigation.ScrGraphs
import com.thomas200593.mini_retail_app.app.ui.LocalStateApp
import com.thomas200593.mini_retail_app.app.ui.StateApp
import com.thomas200593.mini_retail_app.core.data.local.session.SessionState
import com.thomas200593.mini_retail_app.core.ui.component.CustomPanel.ClickableCardItem
import com.thomas200593.mini_retail_app.core.ui.component.CustomScreenUtil.LockScreenOrientation
import com.thomas200593.mini_retail_app.core.ui.component.app_bar.CustomAppBar.ProvideTopAppBarAction
import com.thomas200593.mini_retail_app.core.ui.component.app_bar.CustomAppBar.ProvideTopAppBarNavigationIcon
import com.thomas200593.mini_retail_app.core.ui.component.app_bar.CustomAppBar.ProvideTopAppBarTitle
import com.thomas200593.mini_retail_app.core.ui.component.dialog.DlgError
import com.thomas200593.mini_retail_app.core.ui.component.dialog.DlgInformation
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen.navigation.DestConfGen
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen.navigation.navToConfGen
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen.ui.VMConfGen.UiEvents.ButtonEvents.BtnMenuSelectionEvents
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen.ui.VMConfGen.UiEvents.ButtonEvents.BtnNavBackEvents
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen.ui.VMConfGen.UiEvents.ButtonEvents.BtnScrDescEvents
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen.ui.VMConfGen.UiEvents.DialogEvents.DlgDenyAccessEvents
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen.ui.VMConfGen.UiEvents.OnOpenEvents
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen.ui.VMConfGen.UiStateDestConfGen.Loading
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen.ui.VMConfGen.UiStateDestConfGen.Success
import kotlinx.coroutines.launch

@Composable
fun ScrConfGen(
    vm: VMConfGen = hiltViewModel(),
    stateApp: StateApp = LocalStateApp.current
) {
    LockScreenOrientation(SCREEN_ORIENTATION_PORTRAIT)

    val coroutineScope = rememberCoroutineScope()
    val uiState by vm.uiState.collectAsStateWithLifecycle()
    val sessionState by stateApp.isSessionValid.collectAsStateWithLifecycle()
    val currentScreen = ScrGraphs.getByRoute(stateApp.destCurrent)

    LaunchedEffect(sessionState) { vm.onEvent(OnOpenEvents(sessionState)) }

    ScrConfGen(
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
                        .also { coroutineScope.launch { stateApp.navController.navToConfGen(menu) } }
                is SessionState.Valid -> vm.onEvent(BtnMenuSelectionEvents.OnAllow)
                    .also { coroutineScope.launch { stateApp.navController.navToConfGen(menu) } }
            }
        },
        onDismissDlgDenyAccessMenu = {
            vm.onEvent(DlgDenyAccessEvents.OnDismiss)
                .also { coroutineScope.launch { stateApp.onNavUp() } }
        }
    )
}

@Composable
private fun ScrConfGen(
    uiState: VMConfGen.UiState,
    currentScreen: ScrGraphs?,
    onNavigateBack: () -> Unit,
    onShowScrDesc: (String) -> Unit,
    onDismissDlgScrDesc: () -> Unit,
    onNavToMenu: (DestConfGen) -> Unit,
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
    when (uiState.destConfGen) {
        Loading -> Unit
        is Success -> ScreenContent(
            menuPreferences = uiState.destConfGen.destConfGen,
            onNavToMenu = onNavToMenu
        )
    }
}

@Composable
private fun HandleDialogs(
    uiState: VMConfGen.UiState,
    currentScreen: ScrGraphs,
    onDismissDlgScrDesc: () -> Unit,
    onDismissDlgDenyAccessMenu: () -> Unit
) {
    DlgInformation.Auth(showDialog = uiState.dialogState.dlgLoadingAuth)
    DlgInformation.GetData(showDialog = uiState.dialogState.dlgLoadingGetMenu)
    DlgInformation.ScrDesc(
        showDialog = uiState.dialogState.dlgScrDesc,
        currentScreen = currentScreen,
        onDismiss = onDismissDlgScrDesc
    )
    DlgError.SessionInvalid(
        showDialog = uiState.dialogState.dlgDenyAccessMenu,
        onDismiss = onDismissDlgDenyAccessMenu
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
    menuPreferences: Set<DestConfGen>,
    onNavToMenu: (DestConfGen) -> Unit
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
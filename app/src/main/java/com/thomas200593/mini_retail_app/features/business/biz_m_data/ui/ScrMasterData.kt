package com.thomas200593.mini_retail_app.features.business.biz_m_data.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.navOptions
import com.thomas200593.mini_retail_app.R
import com.thomas200593.mini_retail_app.app.navigation.ScrGraphs
import com.thomas200593.mini_retail_app.app.ui.LocalStateApp
import com.thomas200593.mini_retail_app.app.ui.StateApp
import com.thomas200593.mini_retail_app.core.data.local.session.SessionState
import com.thomas200593.mini_retail_app.core.ui.component.app_bar.CustomAppBar.ProvideTopAppBarAction
import com.thomas200593.mini_retail_app.core.ui.component.app_bar.CustomAppBar.ProvideTopAppBarNavigationIcon
import com.thomas200593.mini_retail_app.core.ui.component.app_bar.CustomAppBar.ProvideTopAppBarTitle
import com.thomas200593.mini_retail_app.core.ui.component.dialog.DlgAuth
import com.thomas200593.mini_retail_app.core.ui.component.dialog.DlgScrGraphs
import com.thomas200593.mini_retail_app.core.ui.component.dialog.DlgCommonInformation
import com.thomas200593.mini_retail_app.features.auth.navigation.navToAuth
import com.thomas200593.mini_retail_app.features.business.biz_m_data.navigation.DestMasterData
import com.thomas200593.mini_retail_app.features.business.biz_m_data.navigation.navToMasterData
import com.thomas200593.mini_retail_app.features.business.biz_m_data.ui.VMMasterData.UiEvents.ButtonEvents.BtnMenuSelectionEvents
import com.thomas200593.mini_retail_app.features.business.biz_m_data.ui.VMMasterData.UiEvents.ButtonEvents.BtnNavBackEvents
import com.thomas200593.mini_retail_app.features.business.biz_m_data.ui.VMMasterData.UiEvents.ButtonEvents.BtnScrDescEvents
import com.thomas200593.mini_retail_app.features.business.biz_m_data.ui.VMMasterData.UiEvents.DialogEvents.DlgDenyAccessEvents
import com.thomas200593.mini_retail_app.features.business.biz_m_data.ui.VMMasterData.UiEvents.OnOpenEvents
import com.thomas200593.mini_retail_app.features.business.biz_m_data.ui.VMMasterData.UiStateDestMasterData.Loading
import com.thomas200593.mini_retail_app.features.business.biz_m_data.ui.VMMasterData.UiStateDestMasterData.Success
import kotlinx.coroutines.launch

@Composable
fun ScrMasterData(
    vm: VMMasterData = hiltViewModel(),
    stateApp: StateApp = LocalStateApp.current
) {
    val coroutineScope = rememberCoroutineScope()
    val uiState by vm.uiState.collectAsStateWithLifecycle()
    val sessionState by stateApp.isSessionValid.collectAsStateWithLifecycle()
    val currentScreen = ScrGraphs.getByRoute(stateApp.destCurrent)

    LaunchedEffect(sessionState, currentScreen)
    { currentScreen?.let { vm.onEvent(OnOpenEvents(sessionState, it)) } }

    ScrMasterData(
        uiState = uiState,
        currentScreen = currentScreen,
        onNavigateBack = {
            vm.onEvent(BtnNavBackEvents.OnClick)
                .also { coroutineScope.launch { stateApp.onNavUp() } }
        },
        onShowScrDesc = { vm.onEvent(BtnScrDescEvents.OnClick) },
        onDismissDlgScrDesc = { vm.onEvent(BtnScrDescEvents.OnDismiss) },
        onDismissDlgDenyAccessMenu = {
            vm.onEvent(DlgDenyAccessEvents.OnDismiss)
                .also { coroutineScope.launch { stateApp.navController.navToAuth() } }
        },
        onNavToMenu = { menu ->
            val navOptions = navOptions { launchSingleTop = true; restoreState = true }
            when(sessionState) {
                SessionState.Loading -> Unit
                is SessionState.Invalid ->
                    if(menu.scrGraphs.usesAuth) vm.onEvent(BtnMenuSelectionEvents.OnDeny)
                    else vm.onEvent(BtnMenuSelectionEvents.OnAllow)
                        .also {
                            coroutineScope.launch {
                                stateApp.navController.navToMasterData(navOptions = navOptions, destMasterData = menu)
                            }
                        }
                is SessionState.Valid -> vm.onEvent(BtnMenuSelectionEvents.OnAllow)
                    .also {
                        coroutineScope.launch {
                            stateApp.navController.navToMasterData(navOptions = navOptions, destMasterData = menu)
                        }
                    }
            }
        }
    )
}

@Composable
private fun ScrMasterData(
    uiState: VMMasterData.UiState,
    currentScreen: ScrGraphs?,
    onNavigateBack: () -> Unit,
    onShowScrDesc: (String) -> Unit,
    onDismissDlgScrDesc: () -> Unit,
    onDismissDlgDenyAccessMenu: () -> Unit,
    onNavToMenu: (DestMasterData) -> Unit
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
    when(uiState.destMasterData) {
        Loading -> Unit
        is Success -> ScreenContent(
            menuData = uiState.destMasterData.destMasterData,
            onNavToMenu = onNavToMenu
        )
    }
}

@Composable
private fun HandleDialogs(
    uiState: VMMasterData.UiState,
    currentScreen: ScrGraphs,
    onDismissDlgScrDesc: () -> Unit,
    onDismissDlgDenyAccessMenu: () -> Unit
) {
    DlgAuth.AuthLoading(showDialog = uiState.dialogState.dlgLoadingAuth)
    DlgCommonInformation.GetData(showDialog = uiState.dialogState.dlgLoadingGetMenu)
    DlgScrGraphs.ScrDesc(
        showDialog = uiState.dialogState.dlgScrDesc,
        currentScreen = currentScreen,
        onDismiss = onDismissDlgScrDesc
    )
    DlgAuth.SessionInvalid(
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
    menuData: Set<DestMasterData>,
    onNavToMenu: (DestMasterData) -> Unit
) {
    LazyVerticalGrid(
        modifier = Modifier.fillMaxSize().padding(8.dp),
        columns = GridCells.Fixed(3)
    ) {
        items(count = menuData.count()){ index ->
            val menu = menuData.elementAt(index)
            Surface(
                modifier = Modifier.fillMaxWidth().padding(4.dp),
                onClick = { onNavToMenu(menu) },
                shape = MaterialTheme.shapes.medium,
                border = BorderStroke(1.dp, colorResource(R.color.charcoal_gray)),
            ) {
                Column (
                    modifier = Modifier.fillMaxSize().padding(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterVertically)
                ) {
                    Surface(
                        shape = MaterialTheme.shapes.medium,
                        color = MaterialTheme.colorScheme.tertiaryContainer,
                    ) {
                        Icon(
                            modifier = Modifier.padding(8.dp),
                            imageVector = menu.scrGraphs.iconRes
                                ?.let { icon -> ImageVector.vectorResource(icon) } ?: Icons.Default.Info,
                            contentDescription = null
                        )
                    }
                    Text(
                        text = menu.scrGraphs.title
                            ?.let { title -> stringResource(title) }.orEmpty(),
                        modifier = Modifier.fillMaxWidth(),
                        color = MaterialTheme.colorScheme.onTertiaryContainer,
                        textAlign = TextAlign.Center,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}
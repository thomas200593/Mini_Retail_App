package com.thomas200593.mini_retail_app.features.business.biz.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.thomas200593.mini_retail_app.app.navigation.ScrGraphs
import com.thomas200593.mini_retail_app.app.ui.LocalStateApp
import com.thomas200593.mini_retail_app.app.ui.StateApp
import com.thomas200593.mini_retail_app.features.business.biz.ui.VMBiz.UiStateDestBiz.Loading
import com.thomas200593.mini_retail_app.features.business.biz.ui.VMBiz.UiStateDestBiz.Success

@Composable
fun ScrBiz(
    vm: VMBiz = hiltViewModel(),
    stateApp: StateApp = LocalStateApp.current
) {
    val coroutineScope = rememberCoroutineScope()
    val uiState by vm.uiState.collectAsStateWithLifecycle()
    val sessionState by stateApp.isSessionValid.collectAsStateWithLifecycle()
    val currentScreen = ScrGraphs.getByRoute(stateApp.destCurrent)

    LaunchedEffect(sessionState, currentScreen) { /*TODO*/ }

    ScrBiz(
        uiState = uiState,
        currentScreen = currentScreen
    )
}

@Composable
private fun ScrBiz(
    uiState: VMBiz.UiState,
    currentScreen: ScrGraphs?
) {
    currentScreen?.let {
        HandleDialogs()
        TopAppBar()
    }
    when(uiState.destBiz) {
        Loading -> Unit
        is Success -> ScreenContent()
    }
}

@Composable
private fun HandleDialogs() { /*TODO*/ }

@Composable
private fun TopAppBar() { /*TODO*/ }

@Composable
private fun ScreenContent() { /*TODO*/ }

/*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.lazy.grid.GridCells.Fixed
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons.Default
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.ButtonDefaults.IconSize
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign.Companion.Center
import androidx.compose.ui.text.style.TextOverflow.Companion.Ellipsis
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.navOptions
import com.thomas200593.mini_retail_app.R.string
import com.thomas200593.mini_retail_app.app.ui.LocalStateApp
import com.thomas200593.mini_retail_app.app.ui.StateApp
import com.thomas200593.mini_retail_app.core.data.local.session.SessionState
import com.thomas200593.mini_retail_app.core.ui.common.CustomIcons
import com.thomas200593.mini_retail_app.core.ui.component.CustomAppBar.ProvideTopAppBarAction
import com.thomas200593.mini_retail_app.core.ui.component.CustomAppBar.ProvideTopAppBarTitle
import com.thomas200593.mini_retail_app.core.ui.component.CustomDialog.AlertDialogContext.ERROR
import com.thomas200593.mini_retail_app.core.ui.component.CustomDialog.AlertDialogContext.INFORMATION
import com.thomas200593.mini_retail_app.core.ui.component.CustomDialog.AppAlertDialog
import com.thomas200593.mini_retail_app.core.ui.component.CustomPanel.LoadingScreen
import com.thomas200593.mini_retail_app.features.business.biz.navigation.DestBiz
import com.thomas200593.mini_retail_app.features.business.biz.navigation.navToBiz
import com.thomas200593.mini_retail_app.features.business.biz.ui.VMBiz.UiEvents.ButtonEvents.BtnMenuEvents.OnAllow
import com.thomas200593.mini_retail_app.features.business.biz.ui.VMBiz.UiEvents.ButtonEvents.BtnMenuEvents.OnDeny
import com.thomas200593.mini_retail_app.features.business.biz.ui.VMBiz.UiEvents.OnOpenEvents
import com.thomas200593.mini_retail_app.features.business.biz.ui.VMBiz.UiStateDestBiz.Loading
import com.thomas200593.mini_retail_app.features.business.biz.ui.VMBiz.UiStateDestBiz.Success

@Composable
fun ScrBiz(
    vm: VMBiz = hiltViewModel(),
    stateApp: StateApp = LocalStateApp.current
){
    val uiState by vm.uiState.collectAsStateWithLifecycle()
    val sessionState by stateApp.isSessionValid.collectAsStateWithLifecycle()
    LaunchedEffect(sessionState) { vm.onEvent(OnOpenEvents(sessionState)) }
    TopAppBar()
    when(uiState.destBiz){
        Loading -> LoadingScreen()
        is Success -> ScreenContent(
            menuData = (uiState.destBiz as Success).destBiz,
            onNavigateToMenu = {
                when(sessionState){
                    SessionState.Loading -> Unit
                    is SessionState.Invalid -> {
                        if(it.usesAuth){ vm.onEvent(OnDeny) }
                        else { vm.onEvent(OnAllow)
                            .apply { stateApp.navController.navToBiz(
                                navOptions=navOptions{ launchSingleTop=true; restoreState=true },
                                destBiz=it
                            ) }
                        }
                    }
                    is SessionState.Valid -> {
                        vm.onEvent(OnAllow).apply { stateApp.navController.navToBiz(
                            navOptions=navOptions{ launchSingleTop=true; restoreState=true },
                            destBiz=it
                        ) }
                    }
                }
            }
        )
    }
    AppAlertDialog(
        showDialog = uiState.dialogState.dlgLoadMenuEnabled,
        dialogContext = INFORMATION,
        showIcon = true,
        showTitle = true,
        title = { Text(text = stringResource(id = string.str_loading))},
        showBody = true,
        body = { Text(text = stringResource(id = string.str_loading))},
    )
    AppAlertDialog(
        showDialog = uiState.dialogState.dlgDenyAccessMenuEnabled,
        dialogContext = ERROR,
        showIcon = true,
        showTitle = true,
        title = { Text(text = stringResource(id = string.str_error))},
        showBody = true,
        body = { Text("Forbidden Access") },
        useConfirmButton = true,
        confirmButton = { TextButton(onClick = { vm.onEvent(OnOpenEvents(sessionState)) })
            { Text(stringResource(id = string.str_ok)) }
        }
    )
}

@Composable
private fun TopAppBar() {
    ProvideTopAppBarTitle {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ){
            Icon(
                modifier = Modifier.sizeIn(maxHeight = IconSize),
                imageVector = ImageVector.vectorResource(id = CustomIcons.TopLevelDestinations.business),
                contentDescription = null
            )
            Text(
                text = stringResource(id = string.str_business),
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
    menuData: Set<DestBiz>,
    onNavigateToMenu: (DestBiz) -> Unit
) {
    LazyVerticalGrid(
        modifier = Modifier.fillMaxSize().padding(8.dp),
        columns = Fixed(3)
    ) {
        items(count = menuData.count()){ index ->
            val menu = menuData.elementAt(index)
            Surface(
                modifier = Modifier.fillMaxWidth().padding(4.dp),
                onClick = { onNavigateToMenu(menu) },
                shape = shapes.medium,
                border = BorderStroke(1.dp, DarkGray),
            ) {
                Column(
                    Modifier.fillMaxSize().padding(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterVertically)
                ) {
                    Surface(
                        shape = shapes.medium,
                        color = colorScheme.tertiaryContainer,
                    ) {
                        Icon(
                            modifier = Modifier.padding(8.dp),
                            imageVector = ImageVector.vectorResource(id = menu.iconRes),
                            contentDescription = null
                        )
                    }
                    Text(
                        text = stringResource(id = menu.title),
                        modifier = Modifier.fillMaxWidth(),
                        color = colorScheme.onTertiaryContainer,
                        textAlign = Center,
                        overflow = Ellipsis
                    )
                }
            }
        }
    }
}*/

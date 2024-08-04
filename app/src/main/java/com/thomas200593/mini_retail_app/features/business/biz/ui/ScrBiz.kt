package com.thomas200593.mini_retail_app.features.business.biz.ui

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
import androidx.compose.material.icons.Icons.Default
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.ButtonDefaults.IconSize
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.navOptions
import com.thomas200593.mini_retail_app.R
import com.thomas200593.mini_retail_app.app.ui.LocalStateApp
import com.thomas200593.mini_retail_app.app.ui.StateApp
import com.thomas200593.mini_retail_app.core.data.local.session.SessionState
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState.Empty
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState.Error
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState.Idle
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState.Loading
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState.Success
import com.thomas200593.mini_retail_app.core.ui.common.CustomIcons
import com.thomas200593.mini_retail_app.core.ui.component.CustomAppBar.ProvideTopAppBarAction
import com.thomas200593.mini_retail_app.core.ui.component.CustomAppBar.ProvideTopAppBarTitle
import com.thomas200593.mini_retail_app.core.ui.component.CustomDialog.AlertDialogContext.ERROR
import com.thomas200593.mini_retail_app.core.ui.component.CustomDialog.AlertDialogContext.INFORMATION
import com.thomas200593.mini_retail_app.core.ui.component.CustomDialog.AppAlertDialog
import com.thomas200593.mini_retail_app.core.ui.component.CustomPanel.EmptyScreen
import com.thomas200593.mini_retail_app.core.ui.component.CustomPanel.ErrorScreen
import com.thomas200593.mini_retail_app.core.ui.component.CustomPanel.LoadingScreen
import com.thomas200593.mini_retail_app.features.business.biz.navigation.DestBiz
import com.thomas200593.mini_retail_app.features.business.biz.navigation.navToBiz
import com.thomas200593.mini_retail_app.features.business.biz.ui.VMBiz.UiEvents.ButtonEvents.BtnMenuEvents.OnAllow
import com.thomas200593.mini_retail_app.features.business.biz.ui.VMBiz.UiEvents.ButtonEvents.BtnMenuEvents.OnDeny
import com.thomas200593.mini_retail_app.features.business.biz.ui.VMBiz.UiEvents.OnOpenEvents

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
        Idle, Loading -> LoadingScreen()
        Empty -> EmptyScreen(
            title = stringResource(id = R.string.str_empty_message_title),
            emptyMessage = stringResource(id = R.string.str_empty_message),
            showIcon = true
        )
        is Error -> ErrorScreen(
            title = stringResource(id = R.string.str_error),
            errorMessage = stringResource(id = R.string.str_error_fetching_preferences),
            showIcon = true
        )
        is Success -> ScreenContent(
            menuData = (uiState.destBiz as Success).data,
            onNavigateToMenu = { menu ->
                when(sessionState){
                    SessionState.Loading -> Unit
                    is SessionState.Invalid -> {
                        if(menu.usesAuth){ vm.onEvent(OnDeny) }
                        else
                        { vm.onEvent(OnAllow).also {
                            stateApp.navController.navToBiz(
                                navOptions=navOptions{ launchSingleTop=true; restoreState=true },
                                destBiz=menu
                            )
                        } }
                    }
                    is SessionState.Valid -> {
                        vm.onEvent(OnAllow).also {
                            stateApp.navController.navToBiz(
                                navOptions=navOptions{ launchSingleTop=true; restoreState=true },
                                destBiz=menu
                            )
                        }
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
        title = { Text(text = stringResource(id = R.string.str_loading))},
        showBody = true,
        body = { Text(text = stringResource(id = R.string.str_loading))},
    )
    AppAlertDialog(
        showDialog = uiState.dialogState.dlgDenyAccessMenuEnabled,
        dialogContext = ERROR,
        showIcon = true,
        showTitle = true,
        title = { Text(text = stringResource(id = R.string.str_error))},
        showBody = true,
        body = { Text("Forbidden Access") },
        useConfirmButton = true,
        confirmButton = {
            TextButton(onClick = { vm.onEvent(OnOpenEvents(sessionState)) })
            { Text(stringResource(id = R.string.str_ok)) }
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
                text = stringResource(id = R.string.str_business),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
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
        columns = GridCells.Fixed(3)
    ) {
        items(count = menuData.count()){ index ->
            val menu = menuData.elementAt(index)
            Surface(
                modifier = Modifier.fillMaxWidth().padding(4.dp),
                onClick = { onNavigateToMenu(menu) },
                shape = MaterialTheme.shapes.medium,
                border = BorderStroke(1.dp, Color.DarkGray),
            ) {
                Column(
                    Modifier.fillMaxSize().padding(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterVertically)
                ) {
                    Surface(
                        shape = MaterialTheme.shapes.medium,
                        color = MaterialTheme.colorScheme.tertiaryContainer,
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
                        color = MaterialTheme.colorScheme.onTertiaryContainer,
                        textAlign = TextAlign.Center,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}
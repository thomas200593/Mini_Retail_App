package com.thomas200593.mini_retail_app.features.app_conf.app_config.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.Icons.AutoMirrored.Filled
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.ButtonDefaults.IconSize
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow.Companion.Ellipsis
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.thomas200593.mini_retail_app.R.string.str_configuration
import com.thomas200593.mini_retail_app.R.string.str_empty_message
import com.thomas200593.mini_retail_app.R.string.str_empty_message_title
import com.thomas200593.mini_retail_app.R.string.str_error
import com.thomas200593.mini_retail_app.R.string.str_error_fetching_preferences
import com.thomas200593.mini_retail_app.app.ui.LocalStateApp
import com.thomas200593.mini_retail_app.app.ui.StateApp
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState.Empty
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState.Error
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState.Idle
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState.Loading
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState.Success
import com.thomas200593.mini_retail_app.core.ui.common.CustomIcons.Setting.settings
import com.thomas200593.mini_retail_app.core.ui.component.CustomAppBar.ProvideTopAppBarAction
import com.thomas200593.mini_retail_app.core.ui.component.CustomAppBar.ProvideTopAppBarNavigationIcon
import com.thomas200593.mini_retail_app.core.ui.component.CustomAppBar.ProvideTopAppBarTitle
import com.thomas200593.mini_retail_app.core.ui.component.CustomPanel.ClickableCardItem
import com.thomas200593.mini_retail_app.core.ui.component.CustomPanel.EmptyScreen
import com.thomas200593.mini_retail_app.core.ui.component.CustomPanel.ErrorScreen
import com.thomas200593.mini_retail_app.core.ui.component.CustomPanel.LoadingScreen
import com.thomas200593.mini_retail_app.features.app_conf.app_config.navigation.DestAppConfig
import com.thomas200593.mini_retail_app.features.app_conf.app_config.ui.VMAppConfig.UiEvents.ScreenEvents

@Composable
fun ScrAppConfig(
    vm: VMAppConfig = hiltViewModel(),
    stateApp: StateApp = LocalStateApp.current
){
    val sessionState by stateApp.isSessionValid.collectAsStateWithLifecycle()
    val uiState by vm.uiState.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) { vm.onEvent(ScreenEvents.OnOpen(sessionState)) }
    TopAppBar(onNavigateBack = { vm.onEvent(ScreenEvents.OnNavigateUp); stateApp.onNavUp() })
    ScreenContent(
        menuData = uiState.menuData,
        onNavToMenu = { vm.onEvent(VMAppConfig.UiEvents.MenuEvents.OnClick(it, sessionState)) }
    )
}

@Composable
private fun TopAppBar(onNavigateBack: () -> Unit) {
    ProvideTopAppBarNavigationIcon {
        Surface(onClick = onNavigateBack, modifier = Modifier) {
            Icon(
                modifier = Modifier,
                imageVector = Filled.KeyboardArrowLeft,
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
                imageVector = ImageVector.vectorResource(id = settings),
                contentDescription = null
            )
            Text(
                text = stringResource(id = str_configuration),
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
private fun ScreenContent(menuData: ResourceState<Set<DestAppConfig>>, onNavToMenu: (DestAppConfig) -> Unit) {
    when(menuData){
        Idle, Loading -> LoadingScreen()
        Empty -> EmptyScreen(
            title = stringResource(id = str_empty_message_title),
            emptyMessage = stringResource(id = str_empty_message),
            showIcon = true
        )
        is Error -> ErrorScreen(
            title = stringResource(id = str_error),
            errorMessage = stringResource(id = str_error_fetching_preferences),
            showIcon = true
        )
        is Success -> SuccessSection(
            menuPreferences = menuData.data,
            onNavToMenu = onNavToMenu
        )
    }
}

@Composable
fun SuccessSection(menuPreferences: Set<DestAppConfig>, onNavToMenu: (DestAppConfig) -> Unit) {
    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(count = menuPreferences.count()){ index ->
            val menu = menuPreferences.elementAt(index)
            ClickableCardItem(
                onClick = { onNavToMenu(menu) },
                icon = ImageVector.vectorResource(id = menu.iconRes),
                title = stringResource(id = menu.title),
                subtitle = stringResource(id = menu.description)
            )
        }
    }
}
package com.thomas200593.mini_retail_app.features.app_conf.conf_gen_font_size.ui

import android.content.pm.ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.thomas200593.mini_retail_app.app.navigation.ScrGraphs
import com.thomas200593.mini_retail_app.app.ui.LocalStateApp
import com.thomas200593.mini_retail_app.app.ui.StateApp
import com.thomas200593.mini_retail_app.core.ui.component.CustomScreenUtil.LockScreenOrientation
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_font_size.entity.ConfigFontSizes
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_font_size.entity.FontSize
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_font_size.ui.VMConfGenFontSize.UiStateConfigFontSize.Loading
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_font_size.ui.VMConfGenFontSize.UiStateConfigFontSize.Success

@Composable
fun ScrConfGenFontSize(
    vm: VMConfGenFontSize = hiltViewModel(),
    stateApp: StateApp = LocalStateApp.current
) {
    LockScreenOrientation(orientation = SCREEN_ORIENTATION_PORTRAIT)

    val coroutineScope = rememberCoroutineScope()
    val uiState by vm.uiState.collectAsStateWithLifecycle()
    val sessionState by stateApp.isSessionValid.collectAsStateWithLifecycle()
    val currentScreen = ScrGraphs.getByRoute(stateApp.destCurrent)

    LaunchedEffect(key1 = sessionState, key2 = currentScreen)
    { currentScreen?.let { /*TODO*/ } }

    ScrConfGenFontSize(
        uiState = uiState,
        currentScreen = currentScreen,
        onNavigateBack = {/*TODO*/},
        onShowScrDesc = {/*TODO*/},
        onDismissDlgScrDesc = {/*TODO*/},
        onSetData = {/*TODO*/},
        onDismissDlgDenySetData = {/*TODO*/}
    )
}

@Composable
private fun ScrConfGenFontSize(
    uiState: VMConfGenFontSize.UiState,
    currentScreen: ScrGraphs?,
    onNavigateBack: () -> Unit,
    onShowScrDesc: (String) -> Unit,
    onDismissDlgScrDesc: () -> Unit,
    onSetData: (FontSize) -> Unit,
    onDismissDlgDenySetData: () -> Unit
) {
    currentScreen?.let {
        HandleDialogs(
            uiState = uiState,
            currentScreen = currentScreen,
            onDismissDlgScrDesc = onDismissDlgScrDesc,
            onDismissDlgDenySetData = onDismissDlgDenySetData
        )
        TopAppBar(
            scrGraphs = it,
            onShowScrDesc = onShowScrDesc,
            onNavigateBack = onNavigateBack
        )
    }
    when(uiState.configFontSizes) {
        Loading -> Unit
        is Success -> ScreenContent(
            configFontSizes = uiState.configFontSizes.configFontSizes,
            onSetData = onSetData
        )
    }
}

@Composable
private fun HandleDialogs(
    uiState: VMConfGenFontSize.UiState,
    currentScreen: ScrGraphs,
    onDismissDlgScrDesc: () -> Unit,
    onDismissDlgDenySetData: () -> Unit
) {/*TODO*/}

@Composable
private fun TopAppBar(
    scrGraphs: ScrGraphs,
    onShowScrDesc: (String) -> Unit,
    onNavigateBack: () -> Unit
) {/*TODO*/}

@Composable
private fun ScreenContent(
    configFontSizes: ConfigFontSizes,
    onSetData: (FontSize) -> Unit
) {/*TODO*/}

/*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons.AutoMirrored
import androidx.compose.material.icons.Icons.Default
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.outlined.KeyboardArrowRight
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.ButtonDefaults.IconSize
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.style.TextAlign.Companion.Center
import androidx.compose.ui.text.style.TextAlign.Companion.Start
import androidx.compose.ui.text.style.TextOverflow.Companion.Ellipsis
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.thomas200593.mini_retail_app.R.string
import com.thomas200593.mini_retail_app.app.ui.LocalStateApp
import com.thomas200593.mini_retail_app.app.ui.StateApp
import com.thomas200593.mini_retail_app.core.ui.common.CustomIcons.Font.font
import com.thomas200593.mini_retail_app.core.ui.component.CustomAppBar.ProvideTopAppBarAction
import com.thomas200593.mini_retail_app.core.ui.component.CustomAppBar.ProvideTopAppBarNavigationIcon
import com.thomas200593.mini_retail_app.core.ui.component.CustomAppBar.ProvideTopAppBarTitle
import com.thomas200593.mini_retail_app.core.ui.component.CustomDialog.AlertDialogContext.ERROR
import com.thomas200593.mini_retail_app.core.ui.component.CustomDialog.AlertDialogContext.INFORMATION
import com.thomas200593.mini_retail_app.core.ui.component.CustomDialog.AppAlertDialog
import com.thomas200593.mini_retail_app.core.ui.component.CustomPanel.ErrorScreen
import com.thomas200593.mini_retail_app.core.ui.component.CustomPanel.ThreeRowCardItem
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_font_size.entity.ConfigFontSizes
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_font_size.entity.FontSize
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_font_size.ui.VMConfGenFontSize.UiEvents.BtnSelectFontSizeEvents
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_font_size.ui.VMConfGenFontSize.UiEvents.ButtonEvents.BtnNavBackEvents
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_font_size.ui.VMConfGenFontSize.UiEvents.OnOpenEvents
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_font_size.ui.VMConfGenFontSize.UiStateConfigFontSize.Error
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_font_size.ui.VMConfGenFontSize.UiStateConfigFontSize.Loading
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_font_size.ui.VMConfGenFontSize.UiStateConfigFontSize.Success

@Composable
fun ScrConfGenFontSize(
    vm: VMConfGenFontSize = hiltViewModel(),
    stateApp: StateApp = LocalStateApp.current
) {
    val uiState by vm.uiState.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) { vm.onEvent(OnOpenEvents) }
    TopAppBar(onNavigateBack = { vm.onEvent(BtnNavBackEvents.OnClick).also { stateApp.onNavUp() } })
    when(uiState.configFontSizes){
        Loading -> Unit
        is Error -> ErrorScreen(
            title = stringResource(id = string.str_error),
            errorMessage = stringResource(id = string.str_error_fetching_preferences),
            showIcon = true
        )
        is Success -> ScreenContent(
            configFontSize = (uiState.configFontSizes as Success).configFontSizes,
            onSaveSelectedFontSize = { vm.onEvent(BtnSelectFontSizeEvents.OnClick(it)) }
        )
    }
    AppAlertDialog(
        showDialog = uiState.dialogState.dlgLoadDataEnabled,
        dialogContext = INFORMATION,
        showIcon = true,
        showTitle = true,
        title = { Text(text = stringResource(id = string.str_loading))},
        showBody = true,
        body = { Text(text = stringResource(id = string.str_loading))},
    )
    AppAlertDialog(
        showDialog = uiState.dialogState.dlgLoadDataErrorEnabled,
        dialogContext = ERROR,
        showIcon = true,
        showTitle = true,
        title = { Text(text = stringResource(id = string.str_error))},
        showBody = true,
        body = { Text("Load Data Error") },
        useConfirmButton = true,
        confirmButton = {
            TextButton(onClick = { vm.onEvent(OnOpenEvents) })
            { Text(stringResource(id = string.str_ok)) }
        }
    )
}

@Composable
private fun TopAppBar(onNavigateBack: () -> Unit) {
    ProvideTopAppBarNavigationIcon {
        Surface(onClick = onNavigateBack, modifier = Modifier) {
            Icon(
                modifier = Modifier,
                imageVector = AutoMirrored.Default.KeyboardArrowLeft,
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
                imageVector = ImageVector.vectorResource(id = font),
                contentDescription = null
            )
            Text(
                text = stringResource(id = string.str_size_font),
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
    configFontSize: ConfigFontSizes,
    onSaveSelectedFontSize: (FontSize) -> Unit
) {
    val currentData = configFontSize.configCurrent.fontSize
    val preferencesList = configFontSize.fontSizes

    Column(
        modifier = Modifier.fillMaxSize().padding(4.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "${stringResource(id = string.str_size_font)} : ${stringResource(id = currentData.title)}",
            modifier = Modifier.fillMaxWidth().padding(4.dp),
            fontWeight = Bold,
            maxLines = 1,
            overflow = Ellipsis,
            textAlign = Center,
        )
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(4.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(count = preferencesList.count()){
                val data = preferencesList.elementAt(it)
                ThreeRowCardItem(
                    firstRowContent = {
                        Surface(modifier = Modifier.fillMaxWidth()) {
                            Icon(
                                imageVector = ImageVector.vectorResource(data.iconRes),
                                contentDescription = null
                            )
                        }
                    },
                    secondRowContent = {
                        Text(
                            text = stringResource(id = data.title),
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = Start,
                            fontWeight = Bold,
                            maxLines = 1,
                            overflow = Ellipsis
                        )
                    },
                    thirdRowContent = {
                        Surface(
                            modifier = Modifier.fillMaxWidth(),
                            onClick = { onSaveSelectedFontSize(data) }
                        ) {
                            Icon(
                                imageVector = if (data == currentData) Default.CheckCircle else AutoMirrored.Outlined.KeyboardArrowRight,
                                contentDescription = null,
                                tint = if (data == currentData) Green else colorScheme.onTertiaryContainer
                            )
                        }
                    }
                )
            }
        }
    }
}*/

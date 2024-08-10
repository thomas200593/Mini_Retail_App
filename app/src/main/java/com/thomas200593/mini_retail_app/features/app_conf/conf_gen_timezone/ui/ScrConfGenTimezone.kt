package com.thomas200593.mini_retail_app.features.app_conf.conf_gen_timezone.ui

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
import com.thomas200593.mini_retail_app.core.ui.common.CustomIcons.Timezone.timezone
import com.thomas200593.mini_retail_app.core.ui.component.CustomAppBar.ProvideTopAppBarAction
import com.thomas200593.mini_retail_app.core.ui.component.CustomAppBar.ProvideTopAppBarNavigationIcon
import com.thomas200593.mini_retail_app.core.ui.component.CustomAppBar.ProvideTopAppBarTitle
import com.thomas200593.mini_retail_app.core.ui.component.CustomDialog.AlertDialogContext.ERROR
import com.thomas200593.mini_retail_app.core.ui.component.CustomDialog.AlertDialogContext.INFORMATION
import com.thomas200593.mini_retail_app.core.ui.component.CustomDialog.AppAlertDialog
import com.thomas200593.mini_retail_app.core.ui.component.CustomPanel.ErrorScreen
import com.thomas200593.mini_retail_app.core.ui.component.CustomPanel.ThreeRowCardItem
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_timezone.entity.ConfigTimezones
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_timezone.entity.Timezone
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_timezone.ui.VMConfGenTimezone.UiEvents.BtnSelectTimezoneEvents
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_timezone.ui.VMConfGenTimezone.UiEvents.ButtonEvents.BtnNavBackEvents
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_timezone.ui.VMConfGenTimezone.UiEvents.OnOpenEvents
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_timezone.ui.VMConfGenTimezone.UiStateConfigTimezone.Error
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_timezone.ui.VMConfGenTimezone.UiStateConfigTimezone.Loading
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_timezone.ui.VMConfGenTimezone.UiStateConfigTimezone.Success

@Composable
fun ScrConfGenTimezone(
    vm: VMConfGenTimezone = hiltViewModel(),
    stateApp: StateApp = LocalStateApp.current
) {
    val uiState by vm.uiState.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) { vm.onEvent(OnOpenEvents) }
    TopAppBar(onNavigateBack = { vm.onEvent(BtnNavBackEvents.OnClick).also { stateApp.onNavUp() } })
    when(uiState.configTimezone){
        Loading -> Unit
        is Error -> ErrorScreen(
            title = stringResource(id = string.str_error),
            errorMessage = stringResource(id = string.str_error_fetching_preferences),
            showIcon = true
        )
        is Success -> ScreenContent(
            configTimezones = (uiState.configTimezone as Success).configTimezone,
            onSaveSelectedTimezone = { vm.onEvent(BtnSelectTimezoneEvents.OnClick(it)) }
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
                imageVector = ImageVector.vectorResource(id = timezone),
                contentDescription = null
            )
            Text(
                text = stringResource(id = string.str_timezone),
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
    configTimezones: ConfigTimezones,
    onSaveSelectedTimezone: (Timezone) -> Unit
) {
    val currentData = configTimezones.configCurrent.timezone
    val preferencesList = configTimezones.timezones

    Column(
        modifier = Modifier.fillMaxSize().padding(4.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "${stringResource(id = string.str_timezone)} : ${currentData.timezoneOffset}",
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
                val data = preferencesList[it]
                ThreeRowCardItem(
                    firstRowContent = {
                        Surface(modifier = Modifier.fillMaxWidth()) {
                            Icon(
                                imageVector = ImageVector.vectorResource(id = timezone),
                                contentDescription = null
                            )
                        }
                    },
                    secondRowContent = {
                        Text(
                            text = data.timezoneOffset,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = Start,
                            fontWeight = Bold
                        )
                    },
                    thirdRowContent = {
                        Surface(
                            modifier = Modifier.fillMaxWidth(),
                            onClick = { onSaveSelectedTimezone(data) }
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
}
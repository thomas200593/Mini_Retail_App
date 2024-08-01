package com.thomas200593.mini_retail_app.features.app_conf.conf_gen_country.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons.AutoMirrored.Filled
import androidx.compose.material.icons.Icons.AutoMirrored.Outlined
import androidx.compose.material.icons.Icons.Default
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.outlined.KeyboardArrowRight
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.ButtonDefaults.IconSize
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import com.thomas200593.mini_retail_app.R.string.str_country
import com.thomas200593.mini_retail_app.R.string.str_empty_message
import com.thomas200593.mini_retail_app.R.string.str_empty_message_title
import com.thomas200593.mini_retail_app.R.string.str_error
import com.thomas200593.mini_retail_app.R.string.str_error_fetching_preferences
import com.thomas200593.mini_retail_app.app.ui.LocalStateApp
import com.thomas200593.mini_retail_app.app.ui.StateApp
import com.thomas200593.mini_retail_app.core.data.local.session.SessionState
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState.Empty
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState.Error
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState.Idle
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState.Loading
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState.Success
import com.thomas200593.mini_retail_app.core.ui.common.CustomIcons.Currency.currency
import com.thomas200593.mini_retail_app.core.ui.component.CustomAppBar.ProvideTopAppBarAction
import com.thomas200593.mini_retail_app.core.ui.component.CustomAppBar.ProvideTopAppBarNavigationIcon
import com.thomas200593.mini_retail_app.core.ui.component.CustomAppBar.ProvideTopAppBarTitle
import com.thomas200593.mini_retail_app.core.ui.component.CustomPanel.EmptyScreen
import com.thomas200593.mini_retail_app.core.ui.component.CustomPanel.ErrorScreen
import com.thomas200593.mini_retail_app.core.ui.component.CustomPanel.ThreeRowCardItem
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_country.entity.ConfigCountry
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_country.entity.Country
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_country.ui.VMConfGenCountry.UiEvents.ScreenEvents.OnNavigateUp
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_country.ui.VMConfGenCountry.UiEvents.ScreenEvents.OnOpen

@Composable
fun ScrConfGenCountry(
    vm: VMConfGenCountry = hiltViewModel(),
    stateApp: StateApp = LocalStateApp.current
){
    val sessionState by stateApp.isSessionValid.collectAsStateWithLifecycle()
    val uiState by vm.uiState.collectAsStateWithLifecycle()
    LaunchedEffect(sessionState) { vm.onEvent(OnOpen(sessionState)) }
    TopAppBar(onNavigateBack = { vm.onEvent(OnNavigateUp); stateApp.onNavUp() })
    /*ScreenContent(
        configCountry = uiState.configCountry,
        sessionState = sessionState,
        onAllowSaveSelectedCountry = { vm.onEvent(OnAllow) },
        onDenySaveSelectedCountry = { vm.onEvent(OnDeny) }
    )*/
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
                imageVector = ImageVector.vectorResource(id = currency),
                contentDescription = null
            )
            Text(
                text = stringResource(id = str_country),
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
    configCountry: ResourceState<ConfigCountry>,
    sessionState: SessionState,
    onAllowSaveSelectedCountry: (Country) -> Unit,
    onDenySaveSelectedCountry: () -> Unit
) {
    when(configCountry){
        Idle, Loading -> Unit
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
            configCountry = configCountry.data,
            onSaveSelectedCountry = {
                when(sessionState){
                    SessionState.Loading -> Unit
                    is SessionState.Invalid -> onDenySaveSelectedCountry.invoke()
                    is SessionState.Valid -> onAllowSaveSelectedCountry.invoke(it)
                }
            }
        )
    }
}

@Composable
private fun SuccessSection(
    configCountry: ConfigCountry,
    onSaveSelectedCountry: (Country) -> Unit
) {
    val currentData = configCountry.configCurrent.country
    val preferencesList = configCountry.countries
    Column(
        modifier = Modifier.fillMaxSize().padding(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            text = "${stringResource(id = str_country)} : ${currentData.displayName}",
            modifier = Modifier.fillMaxWidth().padding(4.dp),
            fontWeight = Bold,
            maxLines = 1,
            overflow = Ellipsis,
            textAlign = Center,
        )
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(count = preferencesList.count()){ index ->
                val data = preferencesList[index]
                ThreeRowCardItem(
                    firstRowContent = {
                        Text(
                            text = data.isoCode,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = Center,
                            fontWeight = Bold,
                            maxLines = 1,
                            overflow = Ellipsis
                        )
                        HorizontalDivider()
                        Text(
                            text = data.iso03Country,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = Center,
                            fontWeight = Bold,
                            maxLines = 1,
                            overflow = Ellipsis
                        )
                    },
                    secondRowContent = {
                        Text(
                            text = data.displayName,
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
                            onClick = { onSaveSelectedCountry(data) }
                        ) {
                            Icon(
                                imageVector = if (data == currentData) { Default.CheckCircle }
                                else { Outlined.KeyboardArrowRight },
                                contentDescription = null,
                                tint = if (data == currentData) { Green }
                                else { colorScheme.onTertiaryContainer }
                            )
                        }
                    }
                )
            }
        }
    }
}
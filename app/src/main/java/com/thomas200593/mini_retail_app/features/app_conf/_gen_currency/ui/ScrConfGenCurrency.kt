package com.thomas200593.mini_retail_app.features.app_conf._gen_currency.ui

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
import androidx.compose.runtime.getValue
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
import com.thomas200593.mini_retail_app.app.ui.StateApp
import com.thomas200593.mini_retail_app.app.ui.LocalStateApp
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState
import com.thomas200593.mini_retail_app.core.ui.common.CustomIcons.Currency.currency
import com.thomas200593.mini_retail_app.core.ui.component.CustomAppBar
import com.thomas200593.mini_retail_app.core.ui.component.CustomPanel.EmptyScreen
import com.thomas200593.mini_retail_app.core.ui.component.CustomPanel.ErrorScreen
import com.thomas200593.mini_retail_app.core.ui.component.CustomPanel.LoadingScreen
import com.thomas200593.mini_retail_app.core.ui.component.CustomPanel.ThreeRowCardItem
import com.thomas200593.mini_retail_app.features.app_conf._gen_currency.entity.Currency
import com.thomas200593.mini_retail_app.features.app_conf._gen_currency.entity.ConfigCurrency

@Composable
fun ScrConfGenCurrency(
    vm: VMConfGenCurrency = hiltViewModel(),
    stateApp: StateApp = LocalStateApp.current
) {
    val confData by vm.confData.collectAsStateWithLifecycle()

    TopAppBar(onNavigateBack = stateApp::onNavUp)
    ScreenContent(
        configData = confData,
        onSaveSelectedCurrency = vm::setCurrency
    )
}

@Composable
private fun TopAppBar(onNavigateBack: () -> Unit) {
    CustomAppBar.ProvideTopAppBarNavigationIcon {
        Surface(
            onClick =  onNavigateBack,
            modifier = Modifier
        ) {
            Icon(
                modifier = Modifier,
                imageVector = Icons.AutoMirrored.Default.KeyboardArrowLeft,
                contentDescription = null
            )
        }
    }
    CustomAppBar.ProvideTopAppBarTitle {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ){
            Icon(
                modifier = Modifier.sizeIn(maxHeight = ButtonDefaults.IconSize),
                imageVector = ImageVector.vectorResource(id = currency),
                contentDescription = null
            )
            Text(
                text = stringResource(id = R.string.str_currency),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
    CustomAppBar.ProvideTopAppBarAction {
        Row(
            modifier = Modifier.padding(end = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ){
            Icon(
                modifier = Modifier.sizeIn(maxHeight = ButtonDefaults.IconSize),
                imageVector = Icons.Default.Info,
                contentDescription = null
            )
        }
    }
}

@Composable
private fun ScreenContent(
    configData: ResourceState<ConfigCurrency>,
    onSaveSelectedCurrency: (Currency) -> Unit
) {
    when(configData){
        ResourceState.Idle, ResourceState.Loading -> { LoadingScreen() }
        ResourceState.Empty -> {
            EmptyScreen(
                title = stringResource(id = R.string.str_empty_message_title),
                emptyMessage = stringResource(id = R.string.str_empty_message),
                showIcon = true
            )
        }
        is ResourceState.Error -> {
            ErrorScreen(
                title = stringResource(id = R.string.str_error),
                errorMessage = stringResource(id = R.string.str_error_fetching_preferences),
                showIcon = true
            )
        }
        is ResourceState.Success -> {
            val currentData = configData.data.configCurrent.currency
            val preferencesList = configData.data.currencies

            Column(
                modifier = Modifier.fillMaxSize().padding(4.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "${stringResource(id = R.string.str_currency)} : ${currentData.displayName}",
                    modifier = Modifier.fillMaxWidth().padding(4.dp),
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Center,
                )
                LazyColumn(
                    modifier = Modifier.fillMaxSize().padding(4.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    items(count = preferencesList.count()){ index ->
                        val data = preferencesList[index]
                        ThreeRowCardItem(
                            firstRowContent = {
                                Text(
                                    text = data.code,
                                    modifier = Modifier.fillMaxWidth(),
                                    textAlign = TextAlign.Center,
                                    fontWeight = FontWeight.Bold,
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
                                Text(
                                    text = data.symbol,
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
                                    onClick = { onSaveSelectedCurrency(data) }
                                ) {
                                    Icon(
                                        imageVector =
                                        if (data == currentData) { Icons.Default.CheckCircle }
                                        else { Icons.AutoMirrored.Outlined.KeyboardArrowRight },
                                        contentDescription = null,
                                        tint =
                                        if (data == currentData) { Color.Green }
                                        else { MaterialTheme.colorScheme.onTertiaryContainer }
                                    )
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}
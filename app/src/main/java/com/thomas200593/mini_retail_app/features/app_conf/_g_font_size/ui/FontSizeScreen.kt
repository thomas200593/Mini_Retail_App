package com.thomas200593.mini_retail_app.features.app_conf._g_font_size.ui

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
import com.thomas200593.mini_retail_app.core.ui.common.CustomIcons.Font.font
import com.thomas200593.mini_retail_app.core.ui.component.AppBar
import com.thomas200593.mini_retail_app.core.ui.component.CommonMessagePanel.EmptyScreen
import com.thomas200593.mini_retail_app.core.ui.component.CommonMessagePanel.ErrorScreen
import com.thomas200593.mini_retail_app.core.ui.component.CommonMessagePanel.LoadingScreen
import com.thomas200593.mini_retail_app.core.ui.component.CommonMessagePanel.ThreeRowCardItem
import com.thomas200593.mini_retail_app.features.app_conf.app_config.entity.AppConfig
import com.thomas200593.mini_retail_app.features.app_conf._g_font_size.entity.FontSize

@Composable
fun FontSizeScreen(
    viewModel: FontSizeViewModel = hiltViewModel(),
    stateApp: StateApp = LocalStateApp.current
) {
    val configData by viewModel.configData.collectAsStateWithLifecycle()

    TopAppBar(onNavigateBack = stateApp::onNavUp)
    ScreenContent(
        configData = configData,
        onSaveSelectedFontSize = viewModel::setFontSize
    )
}

@Composable
private fun TopAppBar(onNavigateBack: () -> Unit) {
    AppBar.ProvideTopAppBarNavigationIcon {
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
    AppBar.ProvideTopAppBarTitle {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ){
            Icon(
                modifier = Modifier.sizeIn(maxHeight = ButtonDefaults.IconSize),
                imageVector = ImageVector.vectorResource(id = font),
                contentDescription = null
            )
            Text(
                text = stringResource(id = R.string.str_size_font),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
    AppBar.ProvideTopAppBarAction {
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
    configData: ResourceState<AppConfig.ConfigFontSizes>,
    onSaveSelectedFontSize: (FontSize) -> Unit
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
            val currentData = configData.data.configCurrent.fontSize
            val preferencesList = configData.data.fontSizes

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(4.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "${stringResource(id = R.string.str_size_font)} : ${stringResource(id = currentData.title)}",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Center,
                )
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(4.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    items(count = preferencesList.count()){ index ->
                        val data = preferencesList.elementAt(index)
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
                                    textAlign = TextAlign.Start,
                                    fontWeight = FontWeight.Bold,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            },
                            thirdRowContent = {
                                Surface(
                                    modifier = Modifier.fillMaxWidth(),
                                    onClick = { onSaveSelectedFontSize(data) }
                                ) {
                                    Icon(
                                        imageVector = if (data == currentData) Icons.Default.CheckCircle else Icons.AutoMirrored.Outlined.KeyboardArrowRight,
                                        contentDescription = null,
                                        tint = if (data == currentData) Color.Green else MaterialTheme.colorScheme.onTertiaryContainer
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
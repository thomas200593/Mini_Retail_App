package com.thomas200593.mini_retail_app.features.app_conf._gen_font_size.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons.AutoMirrored
import androidx.compose.material.icons.Icons.AutoMirrored.Outlined
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
import androidx.compose.runtime.Composable
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
import com.thomas200593.mini_retail_app.R.string.str_empty_message
import com.thomas200593.mini_retail_app.R.string.str_empty_message_title
import com.thomas200593.mini_retail_app.R.string.str_error
import com.thomas200593.mini_retail_app.R.string.str_error_fetching_preferences
import com.thomas200593.mini_retail_app.R.string.str_size_font
import com.thomas200593.mini_retail_app.app.ui.LocalStateApp
import com.thomas200593.mini_retail_app.app.ui.StateApp
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState.Empty
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState.Error
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState.Idle
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState.Loading
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState.Success
import com.thomas200593.mini_retail_app.core.ui.common.CustomIcons.Font.font
import com.thomas200593.mini_retail_app.core.ui.component.CustomAppBar.ProvideTopAppBarAction
import com.thomas200593.mini_retail_app.core.ui.component.CustomAppBar.ProvideTopAppBarNavigationIcon
import com.thomas200593.mini_retail_app.core.ui.component.CustomAppBar.ProvideTopAppBarTitle
import com.thomas200593.mini_retail_app.core.ui.component.CustomPanel.EmptyScreen
import com.thomas200593.mini_retail_app.core.ui.component.CustomPanel.ErrorScreen
import com.thomas200593.mini_retail_app.core.ui.component.CustomPanel.LoadingScreen
import com.thomas200593.mini_retail_app.core.ui.component.CustomPanel.ThreeRowCardItem
import com.thomas200593.mini_retail_app.features.app_conf._gen_font_size.entity.ConfigFontSizes
import com.thomas200593.mini_retail_app.features.app_conf._gen_font_size.entity.FontSize

@Composable
fun ScrConfGenFontSize(
    viewModel: VMConfGenFontSize = hiltViewModel(),
    stateApp: StateApp = LocalStateApp.current
) {
    val configData by viewModel.configData.collectAsStateWithLifecycle()

    TopAppBar(onNavigateBack = stateApp::onNavUp)
    ScreenContent(configData = configData, onSaveSelectedFontSize = viewModel::setFontSize)
}

@Composable
private fun TopAppBar(onNavigateBack: () -> Unit) {
    ProvideTopAppBarNavigationIcon {
        Surface(onNavigateBack, Modifier) {
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
                text = stringResource(id = str_size_font),
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
    configData: ResourceState<ConfigFontSizes>,
    onSaveSelectedFontSize: (FontSize) -> Unit
) {
    when(configData){
        Idle, Loading -> { LoadingScreen() }
        Empty -> {
            EmptyScreen(
                title = stringResource(id = str_empty_message_title),
                emptyMessage = stringResource(id = str_empty_message),
                showIcon = true
            )
        }
        is Error -> {
            ErrorScreen(
                title = stringResource(id = str_error),
                errorMessage = stringResource(id = str_error_fetching_preferences),
                showIcon = true
            )
        }
        is Success -> {
            val currentData = configData.data.configCurrent.fontSize
            val preferencesList = configData.data.fontSizes

            Column(
                modifier = Modifier.fillMaxSize().padding(4.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "${stringResource(id = str_size_font)} : ${stringResource(id = currentData.title)}",
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
                                        imageVector = if (data == currentData) Default.CheckCircle else Outlined.KeyboardArrowRight,
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
    }
}
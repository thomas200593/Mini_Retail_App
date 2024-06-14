package com.thomas200593.mini_retail_app.features.app_config.ui.components.general_config.font_size

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.runtime.LaunchedEffect
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
import com.thomas200593.mini_retail_app.core.design_system.util.RequestState
import com.thomas200593.mini_retail_app.core.ui.common.Icons.Font.font
import com.thomas200593.mini_retail_app.core.ui.common.Shapes
import com.thomas200593.mini_retail_app.core.ui.component.AppBar
import com.thomas200593.mini_retail_app.features.app_config.entity.ConfigCurrent
import com.thomas200593.mini_retail_app.features.app_config.entity.FontSize

@Composable
fun AppConfigGeneralFontSizeScreen(
    viewModel: AppConfigGeneralFontSizeViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit
) {
    LaunchedEffect(Unit) {
        viewModel.onOpen()
    }

    val configCurrent by viewModel.configCurrentUiState.collectAsStateWithLifecycle()
    val fontSizePreferences by viewModel.fontSizePreferences

    TopAppBar(
        onNavigateBack = onNavigateBack
    )
    ScreenContent(
        fontSizeSizePreferences = fontSizePreferences,
        configCurrent = configCurrent,
        onSaveSelectedFontSize = viewModel::saveSelectedFontSize
    )
}

@Composable
private fun TopAppBar(
    onNavigateBack: () -> Unit
) {
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
                modifier = Modifier
                    .sizeIn(maxHeight = ButtonDefaults.IconSize),
                imageVector = ImageVector.vectorResource(id = font),
                contentDescription = null
            )
            Text(text = stringResource(id = R.string.str_size_font))
        }
    }
    AppBar.ProvideTopAppBarAction {
        Row(
            modifier = Modifier.padding(end = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ){
            Icon(
                modifier = Modifier
                    .sizeIn(maxHeight = ButtonDefaults.IconSize),
                imageVector = Icons.Default.Info,
                contentDescription = null
            )
        }
    }
}

@Composable
fun ScreenContent(
    fontSizeSizePreferences: RequestState<Set<FontSize>>,
    configCurrent: RequestState<ConfigCurrent>,
    onSaveSelectedFontSize: (FontSize) -> Unit
) {
    when(configCurrent){
        RequestState.Idle -> Unit
        RequestState.Loading -> {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Shapes.DotsLoadingAnimation()
                Text(text = "Loading your preferences data...")
            }
        }
        is RequestState.Error -> {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Error getting your preferences data...")
            }
        }
        is RequestState.Success -> {
            when(fontSizeSizePreferences){
                RequestState.Idle -> Unit
                RequestState.Loading -> {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Shapes.DotsLoadingAnimation()
                        Text(text = "Loading Theme Preferences...")
                    }
                }
                is RequestState.Error -> {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "Error getting preferences data...")
                    }
                }
                is RequestState.Success -> {
                    val currentFontSize = configCurrent.data?.currentFontSizeSize?:ConfigCurrent().currentFontSizeSize
                    val appFontSizePreferences = fontSizeSizePreferences.data ?: emptySet()

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(4.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Text FontSize Size - ${stringResource(id = currentFontSize.title)}",
                                modifier = Modifier,
                                fontWeight = FontWeight.Bold,
                                overflow = TextOverflow.Ellipsis,
                                textAlign = TextAlign.Center,
                            )
                        }
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(4.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ){
                            LazyColumn(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(4.dp),
                                verticalArrangement = Arrangement.spacedBy(10.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                items(count = appFontSizePreferences.count()){ index ->
                                    val data = appFontSizePreferences.elementAt(index)
                                    Surface(
                                        modifier = Modifier
                                            .fillMaxWidth(1.0f),
                                        shape = MaterialTheme.shapes.medium,
                                        border = BorderStroke(width = 1.dp, color = Color(0xFF747775))
                                    ) {
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth(1.0f)
                                                .padding(8.dp)
                                                .height(intrinsicSize = IntrinsicSize.Max),
                                            horizontalArrangement = Arrangement.Center,
                                            verticalAlignment = Alignment.CenterVertically
                                        ){
                                            Surface(modifier = Modifier.weight(0.2f)) {
                                                Icon(
                                                    imageVector = ImageVector.vectorResource(data.iconRes),
                                                    contentDescription = null
                                                )
                                            }
                                            Column(
                                                modifier = Modifier.weight(0.6f),
                                                verticalArrangement = Arrangement.spacedBy(8.dp)
                                            ){
                                                Text(
                                                    text = stringResource(id = data.title),
                                                    modifier = Modifier.fillMaxWidth(),
                                                    textAlign = TextAlign.Start,
                                                    fontWeight = FontWeight.Bold
                                                )
                                            }
                                            Surface(
                                                modifier = Modifier.weight(0.2f),
                                                onClick = { onSaveSelectedFontSize(data) }
                                            ) {
                                                Icon(
                                                    imageVector = if (data == currentFontSize) Icons.Default.CheckCircle else Icons.AutoMirrored.Outlined.KeyboardArrowRight,
                                                    contentDescription = null,
                                                    tint = if (data == currentFontSize) Color.Green else MaterialTheme.colorScheme.onTertiaryContainer
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
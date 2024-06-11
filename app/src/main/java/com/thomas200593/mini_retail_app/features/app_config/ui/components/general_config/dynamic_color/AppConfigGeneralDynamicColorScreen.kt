package com.thomas200593.mini_retail_app.features.app_config.ui.components.general_config.dynamic_color

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.outlined.KeyboardArrowRight
import androidx.compose.material.icons.filled.CheckCircle
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.thomas200593.mini_retail_app.R
import com.thomas200593.mini_retail_app.core.design_system.util.RequestState
import com.thomas200593.mini_retail_app.core.ui.common.Shapes
import com.thomas200593.mini_retail_app.core.ui.component.AppBar
import com.thomas200593.mini_retail_app.features.app_config.entity.ConfigCurrent
import com.thomas200593.mini_retail_app.features.app_config.entity.DynamicColor

@Composable
fun AppConfigGeneralDynamicColorScreen(
    viewModel: AppConfigGeneralDynamicColorViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit
) {
    LaunchedEffect(Unit) {
        viewModel.onOpen()
    }

    val configCurrent by viewModel.configCurrentUiState.collectAsStateWithLifecycle()
    val dynamicColorPreferences by viewModel.dynamicColorPreferences

    TopAppBar(
        onNavigateBack = onNavigateBack
    )
    ScreenContent(
        dynamicColorPreferences = dynamicColorPreferences,
        configCurrent = configCurrent,
        onSaveSelectedDynamicColor = viewModel::saveSelectedDynamicColor
    )
}

@Composable
private fun TopAppBar(
    onNavigateBack: () -> Unit
) {
    AppBar.ProvideTopAppBarNavigationIcon {
        Icon(
            modifier = Modifier.clickable(
                onClick = onNavigateBack
            ),
            imageVector = Icons.AutoMirrored.Default.ArrowBack,
            contentDescription = null
        )
    }
    AppBar.ProvideTopAppBarTitle {
        Row(
            modifier = Modifier.padding(start = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(text = stringResource(id = R.string.str_theme))
        }
    }
}

@Composable
private fun ScreenContent(
    dynamicColorPreferences: RequestState<Set<DynamicColor>>,
    configCurrent: RequestState<ConfigCurrent>,
    onSaveSelectedDynamicColor: (DynamicColor) -> Unit
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
            when(dynamicColorPreferences){
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
                        Text(text = "Loading Dynamic Color Preferences...")
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
                    val currentDynamicColor = configCurrent.data?.currentDynamicColor?:ConfigCurrent().currentDynamicColor
                    val appDynamicColorPreferences = dynamicColorPreferences.data ?: emptySet()

                    LazyColumn(
                        modifier = Modifier.fillMaxSize().padding(8.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        items(count = appDynamicColorPreferences.count()){ index ->
                            val data = appDynamicColorPreferences.elementAt(index)
                            Row(
                                modifier = Modifier.fillMaxWidth(1.0f).padding(4.dp).height(intrinsicSize = IntrinsicSize.Max),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ){
                                Surface(modifier = Modifier.weight(0.2f)) {
                                    Icon(
                                        imageVector = ImageVector.vectorResource(data.iconRes),
                                        contentDescription = null
                                    )
                                }
                                Column(modifier = Modifier.weight(0.6f)) {
                                    Text(
                                        text = stringResource(id = data.title),
                                        modifier = Modifier.fillMaxWidth(),
                                        textAlign = TextAlign.Start,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                                Surface(
                                    modifier = Modifier.weight(0.2f),
                                    onClick = { onSaveSelectedDynamicColor(data) }
                                ) {
                                    Icon(
                                        imageVector = if (data == currentDynamicColor) Icons.Default.CheckCircle else Icons.AutoMirrored.Outlined.KeyboardArrowRight,
                                        contentDescription = null,
                                        tint = if (data == currentDynamicColor) Color.Green else MaterialTheme.colorScheme.onTertiaryContainer
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
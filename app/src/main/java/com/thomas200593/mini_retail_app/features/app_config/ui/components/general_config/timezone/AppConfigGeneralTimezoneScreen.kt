package com.thomas200593.mini_retail_app.features.app_config.ui.components.general_config.timezone

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.Config
import com.thomas200593.mini_retail_app.R
import com.thomas200593.mini_retail_app.core.design_system.util.RequestState
import com.thomas200593.mini_retail_app.core.ui.common.Shapes
import com.thomas200593.mini_retail_app.core.ui.component.AppBar
import com.thomas200593.mini_retail_app.features.app_config.entity.ConfigCurrent
import com.thomas200593.mini_retail_app.features.app_config.entity.Timezone

@Composable
fun AppConfigGeneralTimezoneScreen(
    viewModel: AppConfigGeneralTimezoneViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit
) {
    LaunchedEffect(Unit) {
        viewModel.onOpen()
    }

    val configCurrent by viewModel.configCurrentUiState.collectAsStateWithLifecycle()
    val timezonePreferences by viewModel.timezonePreferences

    TopAppBar(
        onNavigateBack = onNavigateBack
    )
    ScreenContent(
        timezonePreferences = timezonePreferences,
        configCurrent = configCurrent,
        onSaveSelectedTimezone = viewModel::saveSelectedTimezone
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
            Text(text = stringResource(id = R.string.str_timezone))
        }
    }
}

@Composable
fun ScreenContent(
    timezonePreferences: RequestState<List<Timezone>>,
    configCurrent: RequestState<ConfigCurrent>,
    onSaveSelectedTimezone: (Timezone) -> Unit
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
            when(timezonePreferences){
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
                        Text(text = "Loading Timezone Preferences...")
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
                    val currentTimezone = configCurrent.data?.currentTimezone?: ConfigCurrent().currentTimezone
                    val appTimezonePreferences = timezonePreferences.data ?: emptyList()

                    LazyColumn(
                        modifier = Modifier.fillMaxSize().padding(8.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        items(count = appTimezonePreferences.count()){ index ->
                            val data = appTimezonePreferences[index]
                            Row(
                                modifier = Modifier.fillMaxWidth(1.0f).padding(4.dp).height(intrinsicSize = IntrinsicSize.Max),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ){
                                Column(modifier = Modifier.weight(0.8f)) {
                                    Text(
                                        text = data.timezoneOffset,
                                        modifier = Modifier.fillMaxWidth(),
                                        textAlign = TextAlign.Start,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                                Surface(
                                    modifier = Modifier.weight(0.2f),
                                    onClick = { onSaveSelectedTimezone(data) }
                                ) {
                                    Icon(
                                        imageVector = if (data == currentTimezone) Icons.Default.CheckCircle else Icons.AutoMirrored.Outlined.KeyboardArrowRight,
                                        contentDescription = null,
                                        tint = if (data == currentTimezone) Color.Green else MaterialTheme.colorScheme.onTertiaryContainer
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
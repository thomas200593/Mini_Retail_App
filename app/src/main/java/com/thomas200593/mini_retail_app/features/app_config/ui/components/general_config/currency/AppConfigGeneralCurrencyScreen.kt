package com.thomas200593.mini_retail_app.features.app_config.ui.components.general_config.currency

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
import com.thomas200593.mini_retail_app.app.ui.AppState
import com.thomas200593.mini_retail_app.app.ui.LocalAppState
import com.thomas200593.mini_retail_app.core.design_system.util.RequestState
import com.thomas200593.mini_retail_app.core.ui.common.Icons.Currency.currency
import com.thomas200593.mini_retail_app.core.ui.component.AppBar
import com.thomas200593.mini_retail_app.core.ui.component.CommonMessagePanel.LoadingScreen
import com.thomas200593.mini_retail_app.features.app_config.entity.ConfigCurrent
import com.thomas200593.mini_retail_app.features.app_config.entity.Currency
import timber.log.Timber

private const val TAG = "AppConfigGeneralCurrencyScreen"

@Composable
fun AppConfigGeneralCurrencyScreen(
    viewModel: AppConfigGeneralCurrencyViewModel = hiltViewModel(),
    appState: AppState = LocalAppState.current
) {
    Timber.d("Called: %s", TAG)
    val configCurrent by viewModel.configCurrentUiState.collectAsStateWithLifecycle()
    val currencyPreferences by viewModel.currencyPreferences

    LaunchedEffect(Unit) {
        viewModel.onOpen()
    }

    TopAppBar(
        onNavigateBack = appState::onNavigateUp
    )
    ScreenContent(
        currencyPreferences = currencyPreferences,
        configCurrent = configCurrent,
        onSaveSelectedCurrency = viewModel::saveSelectedCurrency
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
private fun ScreenContent(
    currencyPreferences: RequestState<List<Currency>>,
    configCurrent: RequestState<ConfigCurrent>,
    onSaveSelectedCurrency: (Currency) -> Unit
) {
    when(configCurrent){
        RequestState.Idle -> Unit
        RequestState.Loading -> {
            LoadingScreen()
        }
        is RequestState.Error -> {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(id = R.string.str_error),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
        is RequestState.Success -> {
            when(currencyPreferences){
                RequestState.Idle -> Unit
                RequestState.Loading -> {
                    LoadingScreen()
                }
                is RequestState.Error -> {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = stringResource(id = R.string.str_error),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
                is RequestState.Success -> {
                    val currentCurrency = configCurrent.data?.currentCurrency ?: ConfigCurrent().currentCurrency
                    val appCurrencyPreferences = currencyPreferences.data ?: emptyList()

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        items(count = appCurrencyPreferences.count()){ index ->
                            val data = appCurrencyPreferences[index]
                            Surface(
                                modifier = Modifier
                                    .fillMaxWidth(1.0f),
                                shape = MaterialTheme.shapes.medium,
                                border = BorderStroke(width = 1.dp, color = Color(0xFF747775))
                            ){
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth(1.0f)
                                        .padding(8.dp)
                                        .height(intrinsicSize = IntrinsicSize.Max),
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.CenterVertically
                                ){
                                    Column(
                                        modifier = Modifier.weight(0.2f),
                                        verticalArrangement = Arrangement.Center,
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Text(
                                            text = data.code,
                                            modifier = Modifier.fillMaxWidth(),
                                            textAlign = TextAlign.Center,
                                            fontWeight = FontWeight.Bold,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                    }
                                    Column(modifier = Modifier.weight(0.6f)) {
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
                                    }
                                    Surface(
                                        modifier = Modifier.weight(0.2f),
                                        onClick = { onSaveSelectedCurrency(data) }
                                    ) {
                                        Icon(
                                            imageVector = if (data == currentCurrency) Icons.Default.CheckCircle else Icons.AutoMirrored.Outlined.KeyboardArrowRight,
                                            contentDescription = null,
                                            tint = if (data == currentCurrency) Color.Green else MaterialTheme.colorScheme.onTertiaryContainer
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

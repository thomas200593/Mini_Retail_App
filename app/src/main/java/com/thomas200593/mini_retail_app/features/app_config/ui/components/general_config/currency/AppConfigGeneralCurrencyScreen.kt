package com.thomas200593.mini_retail_app.features.app_config.ui.components.general_config.currency

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.thomas200593.mini_retail_app.R
import com.thomas200593.mini_retail_app.core.design_system.util.RequestState
import com.thomas200593.mini_retail_app.core.ui.component.AppBar
import com.thomas200593.mini_retail_app.features.app_config.entity.ConfigCurrent
import com.thomas200593.mini_retail_app.features.app_config.entity.Currency

@Composable
fun AppConfigGeneralCurrencyScreen(
    viewModel: AppConfigGeneralCurrencyViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit
){
    LaunchedEffect(Unit) {
        viewModel.onOpen()
    }

    val configCurrent by viewModel.configCurrentUiState.collectAsStateWithLifecycle()
    val currencyPreferences by viewModel.currencyPreferences

    TopAppBar(
        onNavigateBack = onNavigateBack
    )
    ScreenContent(
        currencyPreferences = currencyPreferences,
        configCurrent = configCurrent
    ) {

    }
    /*Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        val curr = CurrencyUnit.registeredCurrencies()
        curr.forEach { it ->
            Text(text = "Code : ${it.code}")
            Text(text = "Symbol : ${it.symbol}")
            Text(text = "Display Name: ${it.toCurrency().displayName}")
            Text(text = "Numeric Code : ${it.toCurrency().numericCode}")
            Text(text = "Fraction Digit : ${it.toCurrency().defaultFractionDigits}")
            Text(text = "Decimal Places : [${it.decimalPlaces}]")
            HorizontalDivider(modifier = Modifier.padding(10.dp))
        }
    }*/
}

@Composable
fun TopAppBar(
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
            Text(text = stringResource(id = R.string.str_currency))
        }
    }
}

@Composable
fun ScreenContent(
    currencyPreferences: RequestState<List<Currency>>,
    configCurrent: RequestState<ConfigCurrent>,
    content: @Composable () -> Unit
) {

}

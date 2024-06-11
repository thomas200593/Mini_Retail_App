package com.thomas200593.mini_retail_app.features.app_config.ui.components.general_config.currency

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import org.joda.money.CurrencyUnit

@Composable
fun AppConfigGeneralCurrencyScreen(
    viewModel: AppConfigGeneralCurrencyViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit
){
    LaunchedEffect(Unit) {
        viewModel.onOpen()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        val curr = CurrencyUnit.registeredCurrencies()
        curr.forEach { it ->
            Text(text = it.code)
            Text(text = it.symbol)
            Text(text = it.toCurrency().displayName)
            HorizontalDivider(modifier = Modifier.padding(10.dp))
        }
    }
}
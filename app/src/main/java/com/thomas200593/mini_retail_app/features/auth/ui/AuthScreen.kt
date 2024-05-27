package com.thomas200593.mini_retail_app.features.auth.ui

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.thomas200593.mini_retail_app.core.ui.common.AppTheme

private const val TAG = "AuthScreen"

@Composable
fun AuthScreen(
    viewModel: AuthViewModel = hiltViewModel()
){
    ScreenContent()
}

@Composable
private fun ScreenContent(
    modifier: Modifier = Modifier,
){
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        //Top Column

        //Center Image Column

        //App Metadata Column

        //App Terms and Condition Column
    }
}
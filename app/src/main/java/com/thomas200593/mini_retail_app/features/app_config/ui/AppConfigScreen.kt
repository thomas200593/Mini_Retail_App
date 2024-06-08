package com.thomas200593.mini_retail_app.features.app_config.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun AppConfigScreen(
    viewModel: AppConfigViewModel = hiltViewModel()
) {
    //TODO in the first Access, the App AppConfig Setting Generally show General Settings
    LaunchedEffect(Unit) {
        viewModel.onOpen()
    }

    ScreenContent()
}

@Composable
private fun ScreenContent(

) {
    Text(text = "Setting Screen")
}

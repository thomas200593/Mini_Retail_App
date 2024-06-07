package com.thomas200593.mini_retail_app.features.app_config.ui

import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun AppConfigScreen(
    onNavKeyUp: () -> Unit
) {
    ScreenContent()
}

@Composable
private fun ScreenContent(
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
    ){
        Text(text = "App Config Screen")
    }
}

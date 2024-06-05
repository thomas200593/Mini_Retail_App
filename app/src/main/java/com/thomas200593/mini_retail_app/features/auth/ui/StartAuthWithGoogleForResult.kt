package com.thomas200593.mini_retail_app.features.auth.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect

@Composable
fun StartAuthWithGoogleForResult(
    key: Boolean,
    onStartAuthWithGoogleLaunched: () -> Unit,
) {
    LaunchedEffect(key1 = key) {
        onStartAuthWithGoogleLaunched()
    }
}
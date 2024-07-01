package com.thomas200593.mini_retail_app.features.app_config.ui.config_data

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import timber.log.Timber

private const val TAG = "AppConfigDataScreen"

@Composable
fun ConfigDataScreen(){
    Timber.d("Called : fun $TAG()")
    Text(text = "Submenu Data Related Configuration")
}
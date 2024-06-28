package com.thomas200593.mini_retail_app.features.app_config.ui.data_config

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import timber.log.Timber

private const val TAG = "AppConfigDataScreen"

@Composable
fun AppConfigDataScreen(){
    Timber.d("Called : fun $TAG()")
    Text(text = "Submenu Data Related Configuration")
}
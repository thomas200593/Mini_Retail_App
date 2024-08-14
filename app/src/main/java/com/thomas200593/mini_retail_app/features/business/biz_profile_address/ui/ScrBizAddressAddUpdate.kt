package com.thomas200593.mini_retail_app.features.business.biz_profile_address.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.thomas200593.mini_retail_app.app.ui.LocalStateApp
import com.thomas200593.mini_retail_app.app.ui.StateApp

@Composable
fun ScrBizAddressAddUpdate(
    stateApp: StateApp = LocalStateApp.current,
    genId: String? = null
){
    Text(text = "Gen ID: $genId")
}
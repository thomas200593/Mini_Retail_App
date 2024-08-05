package com.thomas200593.mini_retail_app.features.business.biz_profile.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.thomas200593.mini_retail_app.app.ui.LocalStateApp
import com.thomas200593.mini_retail_app.app.ui.StateApp

@Composable
fun ScrBizProfile(
    vm: VMBizProfile = hiltViewModel(),
    stateApp: StateApp = LocalStateApp.current
){
    val sessionState by stateApp.isSessionValid.collectAsStateWithLifecycle()
}
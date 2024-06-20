package com.thomas200593.mini_retail_app.features.user_profile.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.thomas200593.mini_retail_app.core.ui.common.Themes
import timber.log.Timber

private const val TAG = "UserProfileScreen"

@Composable
fun UserProfileScreen(
    viewModel: UserProfileViewModel = hiltViewModel()
){
    Timber.d("Called: %s", TAG)

    ScreenContent()
}

@Composable
private fun ScreenContent(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ProfileCard()
    }
}

@Composable
private fun ProfileCard() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

    }
}

@Preview
@Composable
fun PreviewProfileScreen(){
    Themes.ApplicationTheme {
        ScreenContent()
    }
}
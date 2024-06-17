package com.thomas200593.mini_retail_app.features.user_profile.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import timber.log.Timber

private const val TAG = "UserProfileScreen"

@Composable
fun UserProfileScreen(
    viewModel: UserProfileViewModel = hiltViewModel()
){
    ScreenContent()
}

@Composable
private fun ScreenContent(
    modifier: Modifier = Modifier
) {
    Timber.d("Called: %s", TAG)

    Surface(
        modifier = modifier
    ) {
        ConstraintLayout(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            val startGuideline = createGuidelineFromStart(16.dp)
            val endGuideline = createGuidelineFromEnd(16.dp)
            val topGuideline = createGuidelineFromTop(16.dp)
            val bottomGuideline = createGuidelineFromBottom(16.dp)

            val (
                surfaceShape
            ) = createRefs()

        }
    }
}

package com.thomas200593.mini_retail_app.features.onboarding.ui

import android.content.pm.ActivityInfo
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.thomas200593.mini_retail_app.R
import com.thomas200593.mini_retail_app.app.ui.AppState
import com.thomas200593.mini_retail_app.app.ui.LocalAppState
import com.thomas200593.mini_retail_app.core.design_system.util.RequestState
import com.thomas200593.mini_retail_app.core.ui.component.CommonMessagePanel.EmptyScreen
import com.thomas200593.mini_retail_app.core.ui.component.CommonMessagePanel.ErrorScreen
import com.thomas200593.mini_retail_app.core.ui.component.CommonMessagePanel.LoadingScreen
import com.thomas200593.mini_retail_app.core.ui.component.ScreenUtil
import com.thomas200593.mini_retail_app.features.initial.navigation.navigateToInitial
import com.thomas200593.mini_retail_app.features.onboarding.entity.Onboarding
import com.thomas200593.mini_retail_app.features.onboarding.entity.Onboarding.Tags.TAG_ONBOARD_SCREEN_IMAGE_VIEW
import com.thomas200593.mini_retail_app.features.onboarding.entity.Onboarding.Tags.TAG_ONBOARD_SCREEN_NAV_BUTTON
import com.thomas200593.mini_retail_app.features.onboarding.entity.Onboarding.Tags.TAG_ONBOARD_TAG_ROW
import timber.log.Timber

private const val TAG = "OnboardingScreen"

@Composable
fun OnboardingScreen(
    viewModel: OnboardingViewModel = hiltViewModel(),
    appState: AppState = LocalAppState.current
){
    Timber.d("Called: %s", TAG)
    ScreenUtil.LockScreenOrientation(orientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
    val onboardingPages by viewModel.onboardingPages
    val currentPage by viewModel.currentPage.collectAsStateWithLifecycle()
    val isOnboardingFinished by viewModel.isOnboardingFinished.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.onOpen()
    }
    LaunchedEffect(isOnboardingFinished) {
        if(isOnboardingFinished){ appState.navController.navigateToInitial() }
    }

    ScreenContent(
        onboardingPages = onboardingPages,
        currentPage = currentPage,
        onTabSelected = { index -> viewModel.onSelectedPage(index) },
        onNextClicked = { viewModel.onNextButtonClicked() },
        onFinishedOnboarding = { viewModel.hideOnboarding() }
    )
}

@Composable
private fun ScreenContent(
    onboardingPages: RequestState<List<Onboarding.OnboardingPage>>,
    currentPage: Int,
    onTabSelected: (Int) -> Unit,
    onNextClicked: () -> Unit,
    onFinishedOnboarding: () -> Unit,
) {
    when(onboardingPages){
        RequestState.Idle -> Unit
        RequestState.Loading -> { LoadingScreen() }
        is RequestState.Error -> {
            ErrorScreen(
                title = stringResource(id = R.string.str_error),
                errorMessage = stringResource(id = R.string.str_error_fetching_preferences),
                showIcon = true
            )
        }
        RequestState.Empty -> {
            EmptyScreen(
                title = stringResource(id = R.string.str_empty_message_title),
                emptyMessage = stringResource(id = R.string.str_empty_message),
                showIcon = true
            )
        }
        is RequestState.Success -> {
            val onboardingPagesData = onboardingPages.data

            Column(
                modifier = Modifier.fillMaxSize().testTag(Onboarding.Tags.TAG_ONBOARD_SCREEN)
            ) {
                OnboardingImageView(
                    modifier = Modifier.weight(1.0f).fillMaxWidth(),
                    currentPage = onboardingPagesData[currentPage]
                )
                OnboardingDetails(
                    modifier = Modifier.weight(1.0f).padding(16.dp),
                    currentPage = onboardingPagesData[currentPage]
                )
                OnboardNavButton(
                    modifier = Modifier.align(Alignment.CenterHorizontally).padding(top = 16.dp),
                    currentPage = currentPage,
                    onboardingPagesSize = onboardingPagesData.size,
                    onNextClicked = onNextClicked,
                    onFinishedOnboarding = onFinishedOnboarding
                )
                TabSelector(
                    modifier = Modifier.padding(top = 16.dp),
                    onboardingPagesData = onboardingPagesData,
                    currentPage = currentPage,
                    onTabSelected = onTabSelected
                )
            }
        }
    }
}


@Composable
fun OnboardingImageView(
    modifier: Modifier,
    currentPage: Onboarding.OnboardingPage
) {
    val imageRes = currentPage.imageRes
    Box(
        modifier = modifier.testTag(TAG_ONBOARD_SCREEN_IMAGE_VIEW + currentPage.title)
    ){
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillWidth
        )
        Box(modifier = Modifier.fillMaxSize().align(Alignment.BottomCenter).graphicsLayer { alpha = 0.6f }
            .background(
                Brush.verticalGradient(
                    colorStops = arrayOf(Pair(0.8f, Color.Transparent), Pair(1f, Color.White))
                )
            )
        )
    }
}

@Composable
fun OnboardingDetails(
    modifier: Modifier,
    currentPage: Onboarding.OnboardingPage
) {
    Column(modifier = modifier) {
        Text(
            text = currentPage.title,
            style = MaterialTheme.typography.displaySmall,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = currentPage.description,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun OnboardNavButton(
    modifier: Modifier,
    currentPage: Int,
    onboardingPagesSize: Int,
    onNextClicked: () -> Unit,
    onFinishedOnboarding: () -> Unit
) {
    Button(
        modifier = modifier.testTag(TAG_ONBOARD_SCREEN_NAV_BUTTON),
        onClick = {
            if (currentPage < onboardingPagesSize - 1) { onNextClicked() }
            else { onFinishedOnboarding() }
        },
        content = {
            Text(
                text = if (currentPage < onboardingPagesSize - 1) { stringResource(id = R.string.str_onboarding_next) }
                else { stringResource(id = R.string.str_onboarding_get_started) }
            )
        }
    )
}

@Composable
fun TabSelector(
    modifier: Modifier,
    onboardingPagesData: List<Onboarding.OnboardingPage>,
    currentPage: Int,
    onTabSelected: (Int) -> Unit
) {
    TabRow(
        selectedTabIndex = currentPage,
        modifier = modifier
            .fillMaxWidth().background(MaterialTheme.colorScheme.primary).testTag(TAG_ONBOARD_TAG_ROW)
    ) {
        onboardingPagesData.forEachIndexed { index, _ ->
            Tab(
                modifier = Modifier.padding(16.dp),
                selected = index == currentPage,
                onClick = {
                    onTabSelected(index)
                }
            ) {
                Box(
                    modifier = Modifier
                        .testTag("$TAG_ONBOARD_TAG_ROW$index")
                        .size(8.dp)
                        .background(
                            color = if (index == currentPage) MaterialTheme.colorScheme.onPrimary
                            else Color.LightGray, shape = RoundedCornerShape(4.dp)
                        )
                )
            }
        }
    }
}

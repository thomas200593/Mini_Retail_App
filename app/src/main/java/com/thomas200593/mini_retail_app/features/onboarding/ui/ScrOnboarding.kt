package com.thomas200593.mini_retail_app.features.onboarding.ui

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
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush.Companion.verticalGradient
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale.Companion.FillWidth
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign.Companion.Center
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.thomas200593.mini_retail_app.R.string
import com.thomas200593.mini_retail_app.app.ui.LocalStateApp
import com.thomas200593.mini_retail_app.app.ui.StateApp
import com.thomas200593.mini_retail_app.core.ui.component.CustomPanel.LoadingScreen
import com.thomas200593.mini_retail_app.features.initial.initialization.navigation.navToInitialization
import com.thomas200593.mini_retail_app.features.onboarding.entity.Onboarding.OnboardingPage
import com.thomas200593.mini_retail_app.features.onboarding.entity.Onboarding.Tags.TAG_ONBOARD_SCREEN
import com.thomas200593.mini_retail_app.features.onboarding.entity.Onboarding.Tags.TAG_ONBOARD_SCREEN_IMAGE_VIEW
import com.thomas200593.mini_retail_app.features.onboarding.entity.Onboarding.Tags.TAG_ONBOARD_SCREEN_NAV_BUTTON
import com.thomas200593.mini_retail_app.features.onboarding.entity.Onboarding.Tags.TAG_ONBOARD_TAG_ROW
import com.thomas200593.mini_retail_app.features.onboarding.ui.VMOnboarding.ScreenState
import com.thomas200593.mini_retail_app.features.onboarding.ui.VMOnboarding.UiEvents.ButtonEvents.ButtonNextEvents
import com.thomas200593.mini_retail_app.features.onboarding.ui.VMOnboarding.UiEvents.OnFinishedOnboardingEvent
import com.thomas200593.mini_retail_app.features.onboarding.ui.VMOnboarding.UiEvents.OnOpenEvents
import com.thomas200593.mini_retail_app.features.onboarding.ui.VMOnboarding.UiEvents.TabsEvents.TabPageSelection
import com.thomas200593.mini_retail_app.features.onboarding.ui.VMOnboarding.UiStateOnboardingPages.Loading
import com.thomas200593.mini_retail_app.features.onboarding.ui.VMOnboarding.UiStateOnboardingPages.Success

@Composable
fun ScrOnboarding(
    vm: VMOnboarding = hiltViewModel(),
    stateApp: StateApp = LocalStateApp.current
){
    val uiState by vm.uiState.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {vm.onEvent(OnOpenEvents)}
    when(uiState.onboardingPages){
        Loading -> LoadingScreen()
        is Success -> ScreenContent(
            onboardingPages = (uiState.onboardingPages as Success).onboardingPages,
            screenState = uiState.screenState,
            onBtnNextClicked = { vm.onEvent(ButtonNextEvents.OnClick) },
            onTabSelected = { vm.onEvent(TabPageSelection.OnSelect(it)) },
            onFinishedOnboarding =
            { vm.onEvent(OnFinishedOnboardingEvent).also { stateApp.navController.navToInitialization() } }
        )
    }
}

@Composable
private fun ScreenContent(
    onboardingPages: List<OnboardingPage>,
    screenState: ScreenState,
    onBtnNextClicked: () -> Unit,
    onTabSelected: (Int) -> Unit,
    onFinishedOnboarding: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize().testTag(TAG_ONBOARD_SCREEN)) {
        OnboardingImages(
            modifier = Modifier.weight(1.0f).fillMaxWidth(),
            currentPage = onboardingPages[screenState.currentPage]
        )
        OnboardingDetails(
            modifier = Modifier.weight(1.0f).padding(16.dp),
            currentPage = onboardingPages[screenState.currentPage]
        )
        OnboardingNavigation(
            modifier = Modifier.align(Alignment.CenterHorizontally).padding(top = 16.dp),
            currentPage = screenState.currentPage,
            pageSize = onboardingPages.size,
            onBtnNextClicked = onBtnNextClicked,
            onFinishedOnboarding = onFinishedOnboarding
        )
        OnboardingTabSelector(
            modifier = Modifier.padding(top = 16.dp),
            onboardingPages = onboardingPages,
            currentPage = screenState.currentPage,
            onTabSelected = onTabSelected
        )
    }
}

@Composable
private fun OnboardingImages(modifier: Modifier, currentPage: OnboardingPage) {
    Box(modifier = modifier.testTag(TAG_ONBOARD_SCREEN_IMAGE_VIEW + currentPage.title)){
        Image(
            painter = painterResource(id = currentPage.imageRes),
            contentDescription = null,
            contentScale = FillWidth,
            modifier = Modifier.fillMaxSize()
        )
        Box(modifier = Modifier.fillMaxSize().align(Alignment.BottomCenter).graphicsLayer{alpha=0.6f}
            .background(verticalGradient(colorStops = arrayOf(Pair(0.8f, Transparent), Pair(1f, White))))
        )
    }
}

@Composable
private fun OnboardingDetails(modifier: Modifier, currentPage: OnboardingPage) {
    Column(modifier = modifier) {
        Text(
            text = currentPage.title,
            style = typography.displaySmall,
            textAlign = Center,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = currentPage.description,
            style = typography.bodyMedium,
            textAlign = Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun OnboardingNavigation(
    modifier: Modifier,
    currentPage: Int,
    pageSize: Int,
    onBtnNextClicked: () -> Unit,
    onFinishedOnboarding: () -> Unit
) {
    Button(
        modifier = modifier.testTag(TAG_ONBOARD_SCREEN_NAV_BUTTON),
        onClick = {
            if (currentPage < pageSize - 1) onBtnNextClicked.invoke()
            else onFinishedOnboarding.invoke()
        },
        content = {
            Text(
                text = if (currentPage < pageSize - 1) stringResource(id = string.str_onboarding_next)
                else stringResource(id = string.str_onboarding_get_started)
            )
        }
    )
}

@Composable
private fun OnboardingTabSelector(
    modifier: Modifier,
    onboardingPages: List<OnboardingPage>,
    currentPage: Int,
    onTabSelected: (Int) -> Unit
) {
    TabRow(
        selectedTabIndex = currentPage,
        modifier = modifier.fillMaxWidth().background(colorScheme.primary).testTag(TAG_ONBOARD_TAG_ROW)
    ) {
        onboardingPages.forEachIndexed { index, _ ->
            Tab(
                modifier = Modifier.padding(16.dp),
                selected = index == currentPage,
                onClick = { onTabSelected.invoke(index) }
            ) {
                Box(
                    modifier = Modifier.testTag("$TAG_ONBOARD_TAG_ROW$index").size(8.dp)
                    .background(
                        color = if (index == currentPage) colorScheme.onPrimary else LightGray,
                        shape = RoundedCornerShape(4.dp)
                    )
                )
            }
        }
    }
}
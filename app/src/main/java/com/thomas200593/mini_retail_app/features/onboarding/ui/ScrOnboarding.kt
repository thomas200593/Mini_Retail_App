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
import com.thomas200593.mini_retail_app.R
import com.thomas200593.mini_retail_app.app.ui.LocalStateApp
import com.thomas200593.mini_retail_app.app.ui.StateApp
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState.Empty
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState.Error
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState.Idle
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState.Loading
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState.Success
import com.thomas200593.mini_retail_app.core.ui.component.CustomPanel.ErrorScreen
import com.thomas200593.mini_retail_app.core.ui.component.CustomPanel.LoadingScreen
import com.thomas200593.mini_retail_app.features.initial.initialization.navigation.navToInitialization
import com.thomas200593.mini_retail_app.features.onboarding.entity.Onboarding
import com.thomas200593.mini_retail_app.features.onboarding.entity.Onboarding.OnboardingPage
import com.thomas200593.mini_retail_app.features.onboarding.entity.Onboarding.Tags.TAG_ONBOARD_SCREEN_IMAGE_VIEW
import com.thomas200593.mini_retail_app.features.onboarding.entity.Onboarding.Tags.TAG_ONBOARD_SCREEN_NAV_BUTTON
import com.thomas200593.mini_retail_app.features.onboarding.entity.Onboarding.Tags.TAG_ONBOARD_TAG_ROW
import com.thomas200593.mini_retail_app.features.onboarding.ui.VMOnboarding.UiEvents.ButtonEvents.ButtonNextEvents
import com.thomas200593.mini_retail_app.features.onboarding.ui.VMOnboarding.UiEvents.OnFinishedOnboardingEvent
import com.thomas200593.mini_retail_app.features.onboarding.ui.VMOnboarding.UiEvents.OnOpenEvents
import com.thomas200593.mini_retail_app.features.onboarding.ui.VMOnboarding.UiEvents.TabsEvents.TabPageSelection

@Composable
fun ScrOnboarding(
    vm: VMOnboarding = hiltViewModel(),
    stateApp: StateApp = LocalStateApp.current
){
    val uiState by vm.uiState.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {vm.onEvent(OnOpenEvents)}
    when(uiState.onboardingPages){
        Idle, Loading, Empty -> LoadingScreen()
        is Error -> ErrorScreen(
            title = stringResource(id = R.string.str_error),
            errorMessage = stringResource(id = R.string.str_error_fetching_preferences),
            showIcon = true
        )
        is Success -> ScreenContent(
            onboardingPages = (uiState.onboardingPages as Success).data,
            screenState = uiState.screenState,
            onBtnNextClicked = { vm.onEvent(ButtonNextEvents.OnClick) },
            onTabSelected = { vm.onEvent(TabPageSelection.OnSelect(it)) },
            onFinishedOnboarding = {
                vm.onEvent(OnFinishedOnboardingEvent)
                    .also { stateApp.navController.navToInitialization() }
            }
        )
    }
}

@Composable
private fun ScreenContent(
    onboardingPages: List<OnboardingPage>,
    screenState: VMOnboarding.ScreenState,
    onBtnNextClicked: () -> Unit,
    onTabSelected: (Int) -> Unit,
    onFinishedOnboarding: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize().testTag(Onboarding.Tags.TAG_ONBOARD_SCREEN)) {
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
            .background(
                verticalGradient(colorStops = arrayOf(Pair(0.8f, Transparent), Pair(1f, White)))
            )
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
                text = if (currentPage < pageSize - 1) stringResource(id = R.string.str_onboarding_next)
                else stringResource(id = R.string.str_onboarding_get_started)
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
                Box(modifier = Modifier.testTag("$TAG_ONBOARD_TAG_ROW$index").size(8.dp)
                    .background(
                        color = if (index == currentPage) colorScheme.onPrimary else LightGray,
                        shape = RoundedCornerShape(4.dp)
                    )
                )
            }
        }
    }
}

/*import android.content.pm.ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
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
import com.thomas200593.mini_retail_app.R.string.str_empty_message
import com.thomas200593.mini_retail_app.R.string.str_empty_message_title
import com.thomas200593.mini_retail_app.R.string.str_error
import com.thomas200593.mini_retail_app.R.string.str_error_fetching_preferences
import com.thomas200593.mini_retail_app.R.string.str_onboarding_get_started
import com.thomas200593.mini_retail_app.R.string.str_onboarding_next
import com.thomas200593.mini_retail_app.app.ui.LocalStateApp
import com.thomas200593.mini_retail_app.app.ui.StateApp
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState.Empty
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState.Error
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState.Idle
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState.Loading
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState.Success
import com.thomas200593.mini_retail_app.core.ui.component.CustomPanel.EmptyScreen
import com.thomas200593.mini_retail_app.core.ui.component.CustomPanel.ErrorScreen
import com.thomas200593.mini_retail_app.core.ui.component.CustomPanel.LoadingScreen
import com.thomas200593.mini_retail_app.core.ui.component.CustomScreenUtil.LockScreenOrientation
import com.thomas200593.mini_retail_app.features.initial.initial.navigation.navToInitial
import com.thomas200593.mini_retail_app.features.onboarding.entity.Onboarding.OnboardingPage
import com.thomas200593.mini_retail_app.features.onboarding.entity.Onboarding.Tags
import com.thomas200593.mini_retail_app.features.onboarding.entity.Onboarding.Tags.TAG_ONBOARD_SCREEN_IMAGE_VIEW
import com.thomas200593.mini_retail_app.features.onboarding.entity.Onboarding.Tags.TAG_ONBOARD_SCREEN_NAV_BUTTON
import com.thomas200593.mini_retail_app.features.onboarding.entity.Onboarding.Tags.TAG_ONBOARD_TAG_ROW

@Composable
fun ScrOnboarding(
    viewModel: VMOnboarding = hiltViewModel(),
    stateApp: StateApp = LocalStateApp.current
){
    LockScreenOrientation(orientation = SCREEN_ORIENTATION_PORTRAIT)
    val onboardingPages by viewModel.onboardingPages
    val currentPage by viewModel.currentPage.collectAsStateWithLifecycle()
    val isOnboardingFinished by viewModel.isOnboardingFinished.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) { viewModel.onOpen() }
    LaunchedEffect(isOnboardingFinished) { if(isOnboardingFinished){ stateApp.navController.navToInitial() } }
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
    onboardingPages: ResourceState<List<OnboardingPage>>,
    currentPage: Int,
    onTabSelected: (Int) -> Unit,
    onNextClicked: () -> Unit,
    onFinishedOnboarding: () -> Unit,
) {
    when(onboardingPages){
        Idle -> Unit
        Loading -> { LoadingScreen() }
        is Error -> {
            ErrorScreen(
                title = stringResource(id = str_error),
                errorMessage = stringResource(id = str_error_fetching_preferences),
                showIcon = true
            )
        }
        Empty -> {
            EmptyScreen(
                title = stringResource(id = str_empty_message_title),
                emptyMessage = stringResource(id = str_empty_message),
                showIcon = true
            )
        }
        is Success -> {
            val onboardingPagesData = onboardingPages.data
            Column(modifier = Modifier.fillMaxSize().testTag(Tags.TAG_ONBOARD_SCREEN)) {
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
    currentPage: OnboardingPage
) {
    val imageRes = currentPage.imageRes
    Box(modifier = modifier.testTag(TAG_ONBOARD_SCREEN_IMAGE_VIEW + currentPage.title)){
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = FillWidth
        )
        Box(modifier = Modifier.fillMaxSize().align(Alignment.BottomCenter).graphicsLayer { alpha = 0.6f }
            .background(verticalGradient(colorStops = arrayOf(Pair(0.8f, Transparent), Pair(1f, White))))
        )
    }
}

@Composable
fun OnboardingDetails(
    modifier: Modifier,
    currentPage: OnboardingPage
) {
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
                text = if (currentPage < onboardingPagesSize - 1) { stringResource(id = str_onboarding_next) }
                else { stringResource(id = str_onboarding_get_started) }
            )
        }
    )
}

@Composable
fun TabSelector(
    modifier: Modifier,
    onboardingPagesData: List<OnboardingPage>,
    currentPage: Int,
    onTabSelected: (Int) -> Unit
) {
    TabRow(
        selectedTabIndex = currentPage,
        modifier = modifier.fillMaxWidth().background(colorScheme.primary).testTag(TAG_ONBOARD_TAG_ROW)
    ) {
        onboardingPagesData.forEachIndexed { index, _ ->
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
}*/

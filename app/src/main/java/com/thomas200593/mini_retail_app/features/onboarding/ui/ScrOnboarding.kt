package com.thomas200593.mini_retail_app.features.onboarding.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.Icons.AutoMirrored
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType.Companion.PrimaryNotEditable
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush.Companion.verticalGradient
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.thomas200593.mini_retail_app.R.string
import com.thomas200593.mini_retail_app.app.ui.LocalStateApp
import com.thomas200593.mini_retail_app.app.ui.StateApp
import com.thomas200593.mini_retail_app.core.ui.component.CustomButton.Common.AppIconButton
import com.thomas200593.mini_retail_app.core.ui.component.CustomPanel.LoadingScreen
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_language.entity.ConfigLanguages
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_language.entity.Language
import com.thomas200593.mini_retail_app.features.initial.initialization.navigation.navToInitialization
import com.thomas200593.mini_retail_app.features.onboarding.entity.Onboarding.OnboardingPage
import com.thomas200593.mini_retail_app.features.onboarding.entity.Onboarding.Tags
import com.thomas200593.mini_retail_app.features.onboarding.ui.VMOnboarding.ScreenState
import com.thomas200593.mini_retail_app.features.onboarding.ui.VMOnboarding.UiEvents.ButtonEvents.BtnNextEvents
import com.thomas200593.mini_retail_app.features.onboarding.ui.VMOnboarding.UiEvents.DropdownEvents.DropdownLanguagesEvents
import com.thomas200593.mini_retail_app.features.onboarding.ui.VMOnboarding.UiEvents.OnOpenEvents
import com.thomas200593.mini_retail_app.features.onboarding.ui.VMOnboarding.UiEvents.TabRowEvents.TabPageSelectionEvents
import com.thomas200593.mini_retail_app.features.onboarding.ui.VMOnboarding.UiState
import com.thomas200593.mini_retail_app.features.onboarding.ui.VMOnboarding.UiStateOnboardingPages.Loading
import com.thomas200593.mini_retail_app.features.onboarding.ui.VMOnboarding.UiStateOnboardingPages.Success
import kotlinx.coroutines.launch

@Composable
fun ScrOnboarding(
    vm: VMOnboarding = hiltViewModel(),
    stateApp: StateApp = LocalStateApp.current
) {
    val coroutineScope = rememberCoroutineScope()
    val uiState by vm.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) { vm.onEvent(OnOpenEvents) }

    ScrOnboarding(
        uiState = uiState,
        onSelectLanguage = { vm.onEvent(DropdownLanguagesEvents.OnSelectEvents(it)) },
        onBtnNextClicked = { vm.onEvent(BtnNextEvents.OnNextClickEvents) },
        onTabSelected = { vm.onEvent(TabPageSelectionEvents.OnSelectEvents(it)) },
        onFinishedOnboarding = {
            vm.onEvent(BtnNextEvents.OnFinishClickEvents)
                .also { coroutineScope.launch { stateApp.navController.navToInitialization() } }
        }
    )
}

@Composable
private fun ScrOnboarding(
    uiState: UiState,
    onSelectLanguage: (Language) -> Unit,
    onBtnNextClicked: () -> Unit,
    onTabSelected: (Int) -> Unit,
    onFinishedOnboarding: () -> Unit
) {
    when (uiState.onboardingPages) {
        Loading -> LoadingScreen()
        is Success -> ScreenContent(
            onboardingPages = uiState.onboardingPages.onboardingData.listOfOnboardingPages,
            configLanguages = uiState.onboardingPages.onboardingData.configLanguages,
            screenState = uiState.screenState,
            onSelectLanguage = onSelectLanguage,
            onBtnNextClicked = onBtnNextClicked,
            onTabSelected = onTabSelected,
            onFinishedOnboarding = onFinishedOnboarding
        )
    }
}

@Composable
private fun ScreenContent(
    onboardingPages: List<OnboardingPage>,
    configLanguages: ConfigLanguages,
    onSelectLanguage: (Language) -> Unit,
    screenState: ScreenState,
    onBtnNextClicked: () -> Unit,
    onTabSelected: (Int) -> Unit,
    onFinishedOnboarding: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize().testTag(Tags.TAG_ONBOARD_SCREEN)
    ) {
        OnboardingLanguages(
            configLanguages = configLanguages,
            onSelectLanguage = onSelectLanguage
        )
        OnboardingImages(
            modifier = Modifier.fillMaxWidth().weight(1.0f),
            currentPage = onboardingPages[screenState.currentPageIndex]
        )
        OnboardingDetails(
            modifier = Modifier.weight(1.0f).padding(16.dp),
            currentPage = onboardingPages[screenState.currentPageIndex]
        )
        OnboardingNavigation(
            modifier = Modifier.align(Alignment.CenterHorizontally).padding(top = 16.dp),
            currentPageIndex = screenState.currentPageIndex,
            maxPageIndex = screenState.maxPageIndex,
            onBtnNextClicked = onBtnNextClicked,
            onFinishedOnboarding = onFinishedOnboarding
        )
        OnboardingTabSelector(
            modifier = Modifier.padding(top = 16.dp),
            onboardingPages = onboardingPages,
            currentPage = screenState.currentPageIndex,
            onTabSelected = onTabSelected
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun OnboardingLanguages(
    configLanguages: ConfigLanguages,
    onSelectLanguage: (Language) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(1.0f),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {
        var expanded by remember { mutableStateOf(false) }
        ExposedDropdownMenuBox(
            modifier = Modifier.fillMaxWidth(0.4f),
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            TextButton(
                modifier = Modifier.fillMaxWidth().padding(4.dp).menuAnchor(PrimaryNotEditable, true),
                border = BorderStroke(1.dp, Color(0xFF747775)),
                shape = MaterialTheme.shapes.medium,
                onClick = { expanded = true }
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        modifier = Modifier.size(24.dp),
                        imageVector = ImageVector.vectorResource(id = configLanguages.configCurrent.language.iconRes),
                        contentDescription = null
                    )
                    Text(
                        modifier = Modifier.weight(0.8f),
                        text = stringResource(id = configLanguages.configCurrent.language.title),
                        textAlign = TextAlign.Center,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                configLanguages.languages.forEach {
                    DropdownMenuItem(
                        modifier = Modifier.fillMaxWidth(),
                        leadingIcon = {
                            Image(
                                modifier = Modifier.size(24.dp),
                                imageVector = ImageVector.vectorResource(id = it.iconRes),
                                contentDescription = null
                            )
                        },
                        text = {
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = stringResource(id = it.title)
                            )
                        },
                        onClick = {
                            expanded = false
                            onSelectLanguage(it)
                        },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                    )
                }
            }
        }
    }
}

@Composable
private fun OnboardingImages(modifier: Modifier, currentPage: OnboardingPage) {
    Box(
        modifier = modifier
            .testTag(Tags.TAG_ONBOARD_SCREEN_IMAGE_VIEW + currentPage.title)
    ) {
        Image(
            painter = painterResource(id = currentPage.imageRes),
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
            modifier = Modifier.fillMaxSize()
        )
        Box(modifier = Modifier.fillMaxSize().align(Alignment.BottomCenter)
            .graphicsLayer { alpha = 0.6f }.background(
                verticalGradient(
                    colorStops = arrayOf(
                        Pair(0.8f, Color.Transparent),
                        Pair(1f, Color.White)
                    )
                )
            )
        )
    }
}

@Composable
private fun OnboardingDetails(modifier: Modifier, currentPage: OnboardingPage) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Surface(
            color = Color.Transparent,
            contentColor = MaterialTheme.colorScheme.onSurface
        ) {
            Text(
                text = stringResource(id = currentPage.title),
                style = MaterialTheme.typography.displaySmall,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
        Surface(
            color = Color.Transparent,
            contentColor = MaterialTheme.colorScheme.onSurface
        ) {
            Text(
                text = stringResource(id = currentPage.description),
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
private fun OnboardingNavigation(
    modifier: Modifier,
    currentPageIndex: Int,
    maxPageIndex: Int,
    onBtnNextClicked: () -> Unit,
    onFinishedOnboarding: () -> Unit
) {
    AppIconButton(
        modifier = modifier.testTag(Tags.TAG_ONBOARD_SCREEN_NAV_BUTTON).fillMaxWidth(0.8f),
        onClick = {
            if (currentPageIndex < maxPageIndex) onBtnNextClicked()
            else onFinishedOnboarding()
        },
        icon =
            if (currentPageIndex < maxPageIndex) AutoMirrored.Filled.KeyboardArrowRight
            else Icons.Default.Check,
        text =
            if (currentPageIndex < maxPageIndex) stringResource(id = string.str_onboarding_next)
            else stringResource(id = string.str_onboarding_get_started)
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
        modifier = modifier.fillMaxWidth().background(MaterialTheme.colorScheme.primary)
            .testTag(Tags.TAG_ONBOARD_TAG_ROW)
    ) {
        onboardingPages.forEachIndexed { index, _ ->
            Tab(
                modifier = Modifier.padding(16.dp),
                selected = index == currentPage,
                onClick = { onTabSelected(index) }
            ) {
                Box(
                    modifier = Modifier.testTag(Tags.TAG_ONBOARD_TAG_ROW + index).size(8.dp)
                        .background(
                            color =
                                if (index == currentPage) MaterialTheme.colorScheme.onPrimary
                                else Color.LightGray,
                            shape = RoundedCornerShape(4.dp)
                        )
                )
            }
        }
    }
}
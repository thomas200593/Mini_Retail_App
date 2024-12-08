package com.thomas200593.mini_retail_app.features.onboarding.ui

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.runtime.derivedStateOf
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.thomas200593.mini_retail_app.R
import com.thomas200593.mini_retail_app.app.ui.LocalStateApp
import com.thomas200593.mini_retail_app.app.ui.StateApp
import com.thomas200593.mini_retail_app.core.ui.common.CustomThemes
import com.thomas200593.mini_retail_app.core.ui.component.CustomButton.Common.AppIconButton
import com.thomas200593.mini_retail_app.core.ui.component.CustomPanel.LoadingScreen
import com.thomas200593.mini_retail_app.features.app_conf.app_config.entity.AppConfig.ConfigCurrent
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_language.entity.ConfigLanguages
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_language.entity.Language
import com.thomas200593.mini_retail_app.features.initial.initialization.navigation.navToInitialization
import com.thomas200593.mini_retail_app.features.onboarding.entity.Onboarding
import com.thomas200593.mini_retail_app.features.onboarding.entity.Onboarding.OnboardingPage
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
    Surface {
        Column(modifier = Modifier.fillMaxSize()) {
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
                border = BorderStroke(1.dp, colorResource(R.color.charcoal_gray)),
                shape = MaterialTheme.shapes.medium,
                onClick = { expanded = true }
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(configLanguages.configCurrent.language.country.flag)
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
                            Text(it.country.flag)
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
    Box(modifier = modifier) {
        SubcomposeAsyncImage(
            model = ImageRequest
                .Builder(LocalContext.current).crossfade(250).data(currentPage.imageRes)
                .build(),
            loading = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) { CircularProgressIndicator() }
            },
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
    val btnNavState by remember(currentPageIndex, maxPageIndex) {
        derivedStateOf {
            if (currentPageIndex < maxPageIndex)
                Triple(onBtnNextClicked, Icons.AutoMirrored.Filled.KeyboardArrowRight, R.string.str_onboarding_next)
            else
                Triple(onFinishedOnboarding, Icons.Default.Check, R.string.str_onboarding_get_started)
        }
    }
    AppIconButton(
        modifier = modifier.fillMaxWidth(0.8f),
        onClick = btnNavState.first,
        icon = btnNavState.second,
        text = stringResource(btnNavState.third)
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
    ) {
        onboardingPages.forEachIndexed { index, _ ->
            Tab(
                modifier = Modifier.padding(16.dp),
                selected = index == currentPage,
                onClick = { onTabSelected(index) }
            ) {
                val opts = Pair(MaterialTheme.colorScheme.onPrimary, Color.LightGray)
                val color by remember(index, currentPage)
                { derivedStateOf { if(index == currentPage) opts.first else opts.second } }
                Box(
                    modifier = Modifier.size(8.dp).background(
                        color = color,
                        shape = RoundedCornerShape(4.dp)
                    )
                )
            }
        }
    }
}

@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun Preview() = CustomThemes.ApplicationTheme {
    ScrOnboarding(
        onSelectLanguage = {},
        onFinishedOnboarding = {},
        onBtnNextClicked = {},
        onTabSelected = {},
        uiState = UiState(
            onboardingPages = Success(
                onboardingData = Onboarding.OnboardingData(
                    listOfOnboardingPages = listOf(
                        OnboardingPage(
                            imageRes = R.drawable.onboard_image_1,
                            title = R.string.onboarding_title_1,
                            description = R.string.onboarding_desc_1
                        ),
                        OnboardingPage(
                            imageRes = R.drawable.onboard_image_2,
                            title =  R.string.onboarding_title_2,
                            description = R.string.onboarding_desc_2
                        ),
                        OnboardingPage(
                            imageRes = R.drawable.onboard_image_3,
                            title =  R.string.onboarding_title_3,
                            description = R.string.onboarding_desc_3
                        )
                    ),
                    configLanguages = ConfigLanguages(
                        configCurrent = ConfigCurrent(),
                        languages = setOf(
                            Language.EN,
                            Language.ID
                        )
                    )
                )
            )
        )
    )
}
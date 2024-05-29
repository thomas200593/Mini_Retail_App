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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.thomas200593.mini_retail_app.features.onboarding.entity.Onboarding
import com.thomas200593.mini_retail_app.features.onboarding.entity.Onboarding.Tags.TAG_ONBOARD_SCREEN_IMAGE_VIEW
import com.thomas200593.mini_retail_app.features.onboarding.entity.Onboarding.Tags.TAG_ONBOARD_SCREEN_NAV_BUTTON
import com.thomas200593.mini_retail_app.features.onboarding.entity.Onboarding.Tags.TAG_ONBOARD_TAG_ROW
import kotlinx.coroutines.launch

@Composable
fun OnboardingScreen(
    viewModel: OnboardingViewModel = hiltViewModel(),
    onOnboardingFinished: () -> Unit = {}
){
    val scope = rememberCoroutineScope()
    val onboardPages = Onboarding.pageList
    val currentPage = remember { mutableIntStateOf(0) }
    val onboardingFinished by viewModel.onboardingFinished.collectAsStateWithLifecycle()

    LaunchedEffect(onboardingFinished) { if(onboardingFinished) onOnboardingFinished() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .testTag(Onboarding.Tags.TAG_ONBOARD_SCREEN)
    ) {
        OnboardingImageView(
            modifier = Modifier.weight(1.0f).fillMaxWidth(),
            currentPage = onboardPages[currentPage.intValue]
        )

        OnboardingDetails(
            modifier = Modifier.weight(1.0f).padding(16.dp),
            currentPage = onboardPages[currentPage.intValue]
        )

        OnBoardNavButton(
            modifier = Modifier.align(Alignment.CenterHorizontally).padding(top = 16.dp),
            currentPage = currentPage.intValue,
            noOfPages = onboardPages.size,
            onNextClicked = { currentPage.intValue++ },
            onFinishedOnboarding = { scope.launch { viewModel.hideOnboarding() } }
        )

        TabSelector(
            onboardPages = onboardPages,
            currentPage = currentPage.intValue,
            onTabSelected = { index -> currentPage.intValue = index }
        )
    }
}

@Composable
fun OnboardingDetails(
    modifier: Modifier = Modifier,
    currentPage: Onboarding.OnboardingPage
) {
    Column(
        modifier = modifier
    ) {
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
fun OnBoardNavButton(
    modifier: Modifier = Modifier,
    currentPage: Int,
    noOfPages: Int,
    onNextClicked: () -> Unit,
    onFinishedOnboarding: () -> Unit
) {
    Button(
        modifier = modifier.testTag(TAG_ONBOARD_SCREEN_NAV_BUTTON),
        onClick = { if (currentPage < noOfPages - 1) onNextClicked() else onFinishedOnboarding() },
        content = { Text(text = if (currentPage < noOfPages - 1) "Next" else "Get Started") }
    )
}


@Composable
fun OnboardingImageView(modifier: Modifier = Modifier, currentPage: Onboarding.OnboardingPage) {
    val imageRes = currentPage.imageRes
    Box(
        modifier = modifier.testTag(TAG_ONBOARD_SCREEN_IMAGE_VIEW + currentPage.title)
    ) {
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillWidth
        )
        Box(modifier = Modifier
            .fillMaxSize()
            .align(Alignment.BottomCenter)
            .graphicsLayer {
                // Apply alpha to create the fading effect
                alpha = 0.6f
            }
            .background(
                Brush.verticalGradient(
                    colorStops = arrayOf(
                        Pair(0.8f, Color.Transparent), Pair(1f, Color.White)
                    )
                )
            ))
    }
}

@Composable
fun TabSelector(onboardPages: List<Onboarding.OnboardingPage>, currentPage: Int, onTabSelected: (Int) -> Unit) {
    TabRow(
        selectedTabIndex = currentPage,
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary)
            .testTag(TAG_ONBOARD_TAG_ROW)

    ) {
        onboardPages.forEachIndexed { index, _ ->
            Tab(selected = index == currentPage, onClick = {
                onTabSelected(index)
            }, modifier = Modifier.padding(16.dp), content = {
                Box(
                    modifier = Modifier
                        .testTag("$TAG_ONBOARD_TAG_ROW$index")
                        .size(8.dp)
                        .background(
                            color = if (index == currentPage) MaterialTheme.colorScheme.onPrimary
                            else Color.LightGray, shape = RoundedCornerShape(4.dp)
                        )
                )
            })
        }
    }
}
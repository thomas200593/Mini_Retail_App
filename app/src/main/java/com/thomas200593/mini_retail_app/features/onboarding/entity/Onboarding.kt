package com.thomas200593.mini_retail_app.features.onboarding.entity

import androidx.annotation.DrawableRes
import com.thomas200593.mini_retail_app.R.drawable.onboard_image_1
import com.thomas200593.mini_retail_app.R.drawable.onboard_image_2
import com.thomas200593.mini_retail_app.R.drawable.onboard_image_3
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

object Onboarding{
    data class OnboardingPage(
        @DrawableRes val imageRes: Int,
        val title: String,
        val description: String
    )

    suspend fun getOnboardingPages() = withContext(IO){
        listOf(
            OnboardingPage(
                imageRes = onboard_image_1,
                title = "Welcome to Mini Retail Application",
                description = "Welcome to Mini Retail Application, explore our feature within application."
            ),
            OnboardingPage(
                imageRes = onboard_image_2,
                title = "One-Solution for Your SME",
                description = "From registering items, categories, employees, and shift management in one app."
            ),
            OnboardingPage(
                imageRes = onboard_image_3,
                title = "Get Started",
                description = "Begin explore our app."
            )
        )
    }

    object Tags {
        const val TAG_ONBOARD_SCREEN = "onboard_screen"
        const val TAG_ONBOARD_SCREEN_IMAGE_VIEW = "onboard_screen_image"
        const val TAG_ONBOARD_SCREEN_NAV_BUTTON = "nav_button"
        const val TAG_ONBOARD_TAG_ROW = "tag_row"
    }
}
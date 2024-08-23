package com.thomas200593.mini_retail_app.features.onboarding.entity

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.thomas200593.mini_retail_app.R.drawable
import com.thomas200593.mini_retail_app.R.string
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_language.entity.ConfigLanguages
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

object Onboarding{
    data class OnboardingPage(
        @DrawableRes val imageRes: Int,
        @StringRes val title: Int,
        @StringRes val description: Int
    )

    data class OnboardingData(
        val listOfOnboardingPages: List<OnboardingPage>,
        val configLanguages: ConfigLanguages,
    )

    suspend fun getOnboardingPages() = withContext(IO){
        listOf(
            OnboardingPage(
                imageRes = drawable.onboard_image_1,
                title = string.onboarding_title_1,
                description = string.onboarding_desc_1
            ),
            OnboardingPage(
                imageRes = drawable.onboard_image_2,
                title =  string.onboarding_title_2,
                description = string.onboarding_desc_2
            ),
            OnboardingPage(
                imageRes = drawable.onboard_image_3,
                title =  string.onboarding_title_3,
                description = string.onboarding_desc_3
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
package com.thomas200593.mini_retail_app.features.onboarding.entity

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.thomas200593.mini_retail_app.R
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_language.entity.ConfigLanguages
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

object Onboarding {
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
        )
    }
}
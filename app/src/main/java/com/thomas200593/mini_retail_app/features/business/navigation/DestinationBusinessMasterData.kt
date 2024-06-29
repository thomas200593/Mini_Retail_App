package com.thomas200593.mini_retail_app.features.business.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

enum class DestinationBusinessMasterData(
    val route: String,
    @DrawableRes val iconRes: Int,
    @StringRes val title: Int,
    @StringRes val description: Int,
    val usesAuth: Boolean
)
package com.thomas200593.mini_retail_app.app.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.thomas200593.mini_retail_app.R.string
import com.thomas200593.mini_retail_app.app.navigation.ScrGraphs.Business
import com.thomas200593.mini_retail_app.app.navigation.ScrGraphs.Dashboard
import com.thomas200593.mini_retail_app.app.navigation.ScrGraphs.Reporting
import com.thomas200593.mini_retail_app.app.navigation.ScrGraphs.UserProfile
import com.thomas200593.mini_retail_app.core.ui.common.CustomIcons.TopLevelDestinations.business
import com.thomas200593.mini_retail_app.core.ui.common.CustomIcons.TopLevelDestinations.dashboard
import com.thomas200593.mini_retail_app.core.ui.common.CustomIcons.TopLevelDestinations.reporting
import com.thomas200593.mini_retail_app.core.ui.common.CustomIcons.TopLevelDestinations.user_profile

enum class DestTopLevel(
    val route: String,
    @DrawableRes val iconRes: Int,
    @StringRes val title: Int
){
    DASHBOARD(
        route = Dashboard.route,
        iconRes = dashboard,
        title = string.str_dashboard,
    ),
    BUSINESS(
        route = Business.route,
        iconRes = business,
        title = string.str_business,
    ),
    REPORTING(
        route = Reporting.route,
        iconRes = reporting,
        title = string.str_reporting,
    ),
    USER_PROFILE(
        route = UserProfile.route,
        iconRes = user_profile,
        title = string.str_user_profile,
    )
}
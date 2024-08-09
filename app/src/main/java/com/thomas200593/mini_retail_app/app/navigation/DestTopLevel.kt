package com.thomas200593.mini_retail_app.app.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.thomas200593.mini_retail_app.R
import com.thomas200593.mini_retail_app.core.ui.common.CustomIcons

enum class DestTopLevel(
    val route: String,
    @DrawableRes val selectedIcon: Int,
    @DrawableRes val unselectedIcon: Int,
    @StringRes val iconTextId: Int
){
    DASHBOARD(
        route = ScrGraphs.Dashboard.route,
        selectedIcon = CustomIcons.TopLevelDestinations.dashboard,
        unselectedIcon = CustomIcons.TopLevelDestinations.dashboard,
        iconTextId = R.string.str_dashboard,
    ),
    BUSINESS(
        route = ScrGraphs.Business.route,
        selectedIcon = CustomIcons.TopLevelDestinations.business,
        unselectedIcon = CustomIcons.TopLevelDestinations.business,
        iconTextId = R.string.str_business,
    ),
    REPORTING(
        route = ScrGraphs.Reporting.route,
        selectedIcon = CustomIcons.TopLevelDestinations.reporting,
        unselectedIcon = CustomIcons.TopLevelDestinations.reporting,
        iconTextId = R.string.str_reporting,
    ),
    USER_PROFILE(
        route = ScrGraphs.UserProfile.route,
        selectedIcon = CustomIcons.TopLevelDestinations.user_profile,
        unselectedIcon = CustomIcons.TopLevelDestinations.user_profile,
        iconTextId = R.string.str_user_profile,
    )
}
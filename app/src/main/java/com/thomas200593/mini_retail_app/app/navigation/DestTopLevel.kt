package com.thomas200593.mini_retail_app.app.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.thomas200593.mini_retail_app.R
import com.thomas200593.mini_retail_app.app.navigation.ScrGraphs.Business
import com.thomas200593.mini_retail_app.app.navigation.ScrGraphs.Dashboard
import com.thomas200593.mini_retail_app.app.navigation.ScrGraphs.Reporting
import com.thomas200593.mini_retail_app.app.navigation.ScrGraphs.UserProfile
import com.thomas200593.mini_retail_app.core.ui.common.CustomIcons.TopLevelDestinations

enum class DestTopLevel(
    val route: String,
    @DrawableRes val selectedIcon: Int,
    @DrawableRes val unselectedIcon: Int,
    @StringRes val iconTextId: Int
){
    DASHBOARD(
        route = Dashboard.route,
        selectedIcon = TopLevelDestinations.dashboard,
        unselectedIcon = TopLevelDestinations.dashboard,
        iconTextId = R.string.str_dashboard,
    ),
    BUSINESS(
        route = Business.route,
        selectedIcon = TopLevelDestinations.business,
        unselectedIcon = TopLevelDestinations.business,
        iconTextId = R.string.str_business,
    ),
    REPORTING(
        route = Reporting.route,
        selectedIcon = TopLevelDestinations.reporting,
        unselectedIcon = TopLevelDestinations.reporting,
        iconTextId = R.string.str_reporting,
    ),
    USER_PROFILE(
        route = UserProfile.route,
        selectedIcon = TopLevelDestinations.user_profile,
        unselectedIcon = TopLevelDestinations.user_profile,
        iconTextId = R.string.str_user_profile,
    )
}
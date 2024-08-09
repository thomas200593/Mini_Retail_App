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
    @DrawableRes val selectedIcon: Int,
    @DrawableRes val unselectedIcon: Int,
    @StringRes val iconTextId: Int
){
    DASHBOARD(
        route = Dashboard.route,
        selectedIcon = dashboard,
        unselectedIcon = dashboard,
        iconTextId = string.str_dashboard,
    ),
    BUSINESS(
        route = Business.route,
        selectedIcon = business,
        unselectedIcon = business,
        iconTextId = string.str_business,
    ),
    REPORTING(
        route = Reporting.route,
        selectedIcon = reporting,
        unselectedIcon = reporting,
        iconTextId = string.str_reporting,
    ),
    USER_PROFILE(
        route = UserProfile.route,
        selectedIcon = user_profile,
        unselectedIcon = user_profile,
        iconTextId = string.str_user_profile,
    )
}
package com.thomas200593.mini_retail_app.app.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.thomas200593.mini_retail_app.R
import com.thomas200593.mini_retail_app.app.navigation.ScreenGraphs.Business
import com.thomas200593.mini_retail_app.app.navigation.ScreenGraphs.Dashboard
import com.thomas200593.mini_retail_app.app.navigation.ScreenGraphs.Reporting
import com.thomas200593.mini_retail_app.app.navigation.ScreenGraphs.UserProfile
import com.thomas200593.mini_retail_app.core.ui.common.Icons.TopLevelDestinations.business
import com.thomas200593.mini_retail_app.core.ui.common.Icons.TopLevelDestinations.dashboard
import com.thomas200593.mini_retail_app.core.ui.common.Icons.TopLevelDestinations.reporting
import com.thomas200593.mini_retail_app.core.ui.common.Icons.TopLevelDestinations.user_profile

enum class DestinationTopLevel(
    val route: String,
    @DrawableRes val selectedIcon: Int,
    @DrawableRes val unselectedIcon: Int,
    @StringRes val iconTextId: Int
){
    DASHBOARD(
        route = Dashboard.route,
        selectedIcon = dashboard,
        unselectedIcon = dashboard,
        iconTextId = R.string.str_dashboard,
    ),
    BUSINESS(
        route = Business.route,
        selectedIcon = business,
        unselectedIcon = business,
        iconTextId = R.string.str_business,
    ),
    REPORTING(
        route = Reporting.route,
        selectedIcon = reporting,
        unselectedIcon = reporting,
        iconTextId = R.string.str_reporting,
    ),
    USER_PROFILE(
        route = UserProfile.route,
        selectedIcon = user_profile,
        unselectedIcon = user_profile,
        iconTextId = R.string.str_user_profile,
    )
}
package com.thomas200593.mini_retail_app.app.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.thomas200593.mini_retail_app.R.string.str_business
import com.thomas200593.mini_retail_app.R.string.str_dashboard
import com.thomas200593.mini_retail_app.R.string.str_reporting
import com.thomas200593.mini_retail_app.R.string.str_user_profile
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
        iconTextId = str_dashboard,
    ),
    BUSINESS(
        route = Business.route,
        selectedIcon = business,
        unselectedIcon = business,
        iconTextId = str_business,
    ),
    REPORTING(
        route = Reporting.route,
        selectedIcon = reporting,
        unselectedIcon = reporting,
        iconTextId = str_reporting,
    ),
    USER_PROFILE(
        route = UserProfile.route,
        selectedIcon = user_profile,
        unselectedIcon = user_profile,
        iconTextId = str_user_profile,
    )
}
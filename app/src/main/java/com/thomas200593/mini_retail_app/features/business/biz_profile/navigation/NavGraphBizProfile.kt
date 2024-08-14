package com.thomas200593.mini_retail_app.features.business.biz_profile.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import androidx.navigation.navigation
import com.thomas200593.mini_retail_app.app.navigation.NavGraph.G_BIZ_PROFILE
import com.thomas200593.mini_retail_app.app.navigation.ScrGraphs.BizProfile
import com.thomas200593.mini_retail_app.features.business.biz_profile.ui.ScrBizProfile

fun NavGraphBuilder.navGraphBizProfile(){
    navigation(
        route = G_BIZ_PROFILE,
        startDestination = BizProfile.route
    ){
        composable(
            route = BizProfile.route
        ){ ScrBizProfile() }
    }
}

fun NavController.navToBizProfile(
    destBizProfile: DestBizProfile?
) {
    val navOptions = navOptions { launchSingleTop = true; restoreState = true }
    this.navigate(
        route = destBizProfile?.route?: G_BIZ_PROFILE,
        navOptions = navOptions
    )
}
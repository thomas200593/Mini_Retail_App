package com.thomas200593.mini_retail_app.features.user_profile.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.thomas200593.mini_retail_app.app.navigation.NavigationGraphs.G_USER_PROFILE
import com.thomas200593.mini_retail_app.app.navigation.ScreenGraphs
import com.thomas200593.mini_retail_app.features.user_profile.ui.UserProfileScreen
import timber.log.Timber

private val TAG_NAV_GRAPH_BUILDER = NavGraphBuilder::class.simpleName
private val TAG_NAV_CONTROLLER = NavController::class.simpleName

fun NavGraphBuilder.navGraphUserProfile(){
    Timber.d("Called : fun $TAG_NAV_GRAPH_BUILDER.userProfileNavGraph()")
    navigation(
        route = G_USER_PROFILE,
        startDestination = ScreenGraphs.UserProfile.route
    ){
        composable(
            route = ScreenGraphs.UserProfile.route
        ){
            UserProfileScreen()
        }
    }
}

fun NavController.navigateToUserProfile(navOptions: NavOptions?) {
    Timber.d("Called : fun $TAG_NAV_CONTROLLER.navigateToUserProfile()")
    this.navigate(
        route = G_USER_PROFILE,
        navOptions
    )
}
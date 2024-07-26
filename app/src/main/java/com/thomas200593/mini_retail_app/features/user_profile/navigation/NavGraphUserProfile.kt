package com.thomas200593.mini_retail_app.features.user_profile.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.thomas200593.mini_retail_app.app.navigation.NavGraph.G_USER_PROFILE
import com.thomas200593.mini_retail_app.app.navigation.ScrGraphs.UserProfile
import com.thomas200593.mini_retail_app.features.user_profile.ui.ScrUserProfile

fun NavGraphBuilder.navGraphUserProfile(){
    navigation(
        route = G_USER_PROFILE,
        startDestination = UserProfile.route
    ){
        composable(
            route = UserProfile.route
        ){ ScrUserProfile() }
    }
}

fun NavController.navToUserProfile(navOptions: NavOptions?) {
    this.navigate(
        route = G_USER_PROFILE,
        navOptions
    )
}
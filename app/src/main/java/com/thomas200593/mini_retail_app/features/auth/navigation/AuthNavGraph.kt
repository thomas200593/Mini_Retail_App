package com.thomas200593.mini_retail_app.features.auth.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.thomas200593.mini_retail_app.features.auth.ui.AuthScreen
import com.thomas200593.mini_retail_app.main_app.navigation.NavigationGraphs
import com.thomas200593.mini_retail_app.main_app.navigation.ScreenGraphs

fun NavGraphBuilder.authNavGraph(
    onNavigateToInitial: () -> Unit
){
    navigation(
        route = NavigationGraphs.G_AUTH,
        startDestination = ScreenGraphs.Auth.route
    ){
        composable(
            route = ScreenGraphs.Auth.route
        ){
            AuthScreen(
                onNavigateToInitial = onNavigateToInitial
            )
        }
    }
}

fun NavController.navigateToAuthScreen(){
    this.navigate(
        route = NavigationGraphs.G_AUTH,
        navOptions = NavOptions.Builder()
            .setLaunchSingleTop(true)
            .setRestoreState(true)
            .build()
    )
}
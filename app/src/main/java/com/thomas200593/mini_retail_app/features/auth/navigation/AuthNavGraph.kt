package com.thomas200593.mini_retail_app.features.auth.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.thomas200593.mini_retail_app.features.auth.ui.AuthScreen
import com.thomas200593.mini_retail_app.main_app.navigation.NavigationGraphs.G_AUTH
import com.thomas200593.mini_retail_app.main_app.navigation.NavigationGraphs.G_INITIAL
import com.thomas200593.mini_retail_app.main_app.navigation.ScreenGraphs

fun NavGraphBuilder.authNavGraph(
    onNavigateToInitial: () -> Unit
){
    navigation(
        route = G_AUTH,
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
        route = G_AUTH
    ){
        launchSingleTop = true
        restoreState = true
        popUpTo(G_INITIAL){
            inclusive
        }
    }
}
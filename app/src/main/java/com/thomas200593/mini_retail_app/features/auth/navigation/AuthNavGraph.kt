package com.thomas200593.mini_retail_app.features.auth.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.thomas200593.mini_retail_app.features.auth.ui.AuthScreen
import com.thomas200593.mini_retail_app.main_app.navigation.NavigationGraphs
import com.thomas200593.mini_retail_app.main_app.navigation.ScreenGraphs

fun NavGraphBuilder.authNavGraph(){
    navigation(
        route = NavigationGraphs.G_AUTH,
        startDestination = ScreenGraphs.Auth.route
    ){
        composable(
            route = ScreenGraphs.Auth.route
        ){
            AuthScreen()
        }
    }
}
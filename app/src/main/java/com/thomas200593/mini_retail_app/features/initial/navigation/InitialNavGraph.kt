package com.thomas200593.mini_retail_app.features.initial.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.thomas200593.mini_retail_app.features.initial.ui.InitialScreen
import com.thomas200593.mini_retail_app.main_app.navigation.NavigationGraphs.G_INITIAL
import com.thomas200593.mini_retail_app.main_app.navigation.ScreenGraphs

fun NavGraphBuilder.initialNavGraph() {
    navigation(
        route = G_INITIAL,
        startDestination = ScreenGraphs.Initial.route
    ){
        composable(
            route = ScreenGraphs.Initial.route
        ){
            InitialScreen(
                //onNavigateToLogin
                //onNavigateToOnboardingPages
                //onNavigateToDashboard
            )
        }
    }
}
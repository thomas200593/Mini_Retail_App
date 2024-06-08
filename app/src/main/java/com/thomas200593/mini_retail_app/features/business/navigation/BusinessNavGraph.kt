package com.thomas200593.mini_retail_app.features.business.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.thomas200593.mini_retail_app.features.business.ui.BusinessScreen
import com.thomas200593.mini_retail_app.main_app.navigation.NavigationGraphs.G_BUSINESS
import com.thomas200593.mini_retail_app.main_app.navigation.ScreenGraphs

fun NavGraphBuilder.businessNavGraph(){
    navigation(
        route = G_BUSINESS,
        startDestination = ScreenGraphs.Business.route
    ){
        composable(
            route = ScreenGraphs.Business.route
        ){
            BusinessScreen()
        }
    }
}

fun NavController.navigateToBusiness(navOptions: NavOptions?) {
    this.navigate(
        route = G_BUSINESS,
        navOptions
    )
}
package com.thomas200593.mini_retail_app.features.app_config._data.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import androidx.navigation.navigation
import com.thomas200593.mini_retail_app.app.navigation.NavigationGraphs
import com.thomas200593.mini_retail_app.app.navigation.ScreenGraphs
import com.thomas200593.mini_retail_app.features.app_config._data.ui.ConfigDataScreen

fun NavGraphBuilder.navGraphConfigData() {
    navigation(
        route = NavigationGraphs.G_CONFIG_DATA,
        startDestination = ScreenGraphs.ConfigData.route
    ){
        composable(
            route = ScreenGraphs.ConfigData.route
        ){
            ConfigDataScreen()
        }

        //More Screen
    }
}

fun NavController.navigateToAppConfigData(
    destinationConfigData: DestinationConfigData?
){
    val navOptions = navOptions {
        launchSingleTop = true
        restoreState = true
    }
    this.navigate(
        route = destinationConfigData?.route?: NavigationGraphs.G_CONFIG_DATA,
        navOptions = navOptions
    )
}
package com.thomas200593.mini_retail_app.features.app_config.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import androidx.navigation.navigation
import com.thomas200593.mini_retail_app.app.navigation.NavigationGraphs.G_CONFIG_DATA
import com.thomas200593.mini_retail_app.app.navigation.ScreenGraphs.ConfigData
import com.thomas200593.mini_retail_app.features.app_config.ui.config_data.ConfigDataScreen


fun NavGraphBuilder.navGraphConfigData() {
    navigation(
        route = G_CONFIG_DATA,
        startDestination = ConfigData.route
    ){
        composable(
            route = ConfigData.route
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
        route = destinationConfigData?.route?: G_CONFIG_DATA,
        navOptions = navOptions
    )
}
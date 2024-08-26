package com.thomas200593.mini_retail_app.features.app_conf.conf_data.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import androidx.navigation.navigation
import com.thomas200593.mini_retail_app.app.navigation.NavGraph.G_CONFIG_DATA
import com.thomas200593.mini_retail_app.app.navigation.ScrGraphs.AppConfigData
import com.thomas200593.mini_retail_app.features.app_conf.conf_data.ui.ScrConfData

fun NavGraphBuilder.navGraphConfData() {
    navigation(
        route = G_CONFIG_DATA,
        startDestination = AppConfigData.route
    ){
        composable(
            route = AppConfigData.route
        ){ ScrConfData() }

        //More Screen
    }
}

fun NavController.navToConfData(
    destConfData: DestConfData?
){
    val navOptions = navOptions { launchSingleTop = true; restoreState = true }
    this.navigate(
        route = destConfData?.route?: G_CONFIG_DATA,
        navOptions = navOptions
    )
}
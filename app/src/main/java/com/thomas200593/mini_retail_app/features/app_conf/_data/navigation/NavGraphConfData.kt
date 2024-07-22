package com.thomas200593.mini_retail_app.features.app_conf._data.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import androidx.navigation.navigation
import com.thomas200593.mini_retail_app.app.navigation.NavGraph
import com.thomas200593.mini_retail_app.app.navigation.ScrGraphs
import com.thomas200593.mini_retail_app.features.app_conf._data.ui.ScrConfData

fun NavGraphBuilder.navGraphConfData() {
    navigation(
        route = NavGraph.G_CONFIG_DATA,
        startDestination = ScrGraphs.ConfigData.route
    ){
        composable(
            route = ScrGraphs.ConfigData.route
        ){
            ScrConfData()
        }

        //More Screen
    }
}

fun NavController.navToConfData(
    destConfData: DestConfData?
){
    val navOptions = navOptions {
        launchSingleTop = true
        restoreState = true
    }
    this.navigate(
        route = destConfData?.route?: NavGraph.G_CONFIG_DATA,
        navOptions = navOptions
    )
}
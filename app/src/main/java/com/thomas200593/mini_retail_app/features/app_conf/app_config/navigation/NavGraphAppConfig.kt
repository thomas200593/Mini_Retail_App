package com.thomas200593.mini_retail_app.features.app_conf.app_config.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import androidx.navigation.navigation
import com.thomas200593.mini_retail_app.app.navigation.NavGraph.G_APP_CONFIG
import com.thomas200593.mini_retail_app.app.navigation.ScrGraphs.AppConfig
import com.thomas200593.mini_retail_app.features.app_conf.app_config.ui.ScrAppConfig
import com.thomas200593.mini_retail_app.features.app_conf.conf_data.navigation.navGraphConfData
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen.navigation.navGraphConfGen

fun NavGraphBuilder.navGraphAppConfig(){
    navigation(
        route = G_APP_CONFIG,
        startDestination = AppConfig.route
    ){
        composable(
            route = AppConfig.route
        ){ ScrAppConfig() }
        navGraphConfGen()
        navGraphConfData()
    }
}

fun NavController.navToAppConfig(destAppConfig: DestAppConfig? = null){
    val navOptions = navOptions { launchSingleTop = true; restoreState = true }
    this.navigate(
        route = destAppConfig?.route ?: G_APP_CONFIG,
        navOptions = navOptions
    )
}
package com.thomas200593.mini_retail_app.features.app_config.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import androidx.navigation.navigation
import com.thomas200593.mini_retail_app.app.navigation.NavigationGraphs.G_CONFIG_DATA
import com.thomas200593.mini_retail_app.app.navigation.ScreenGraphs
import com.thomas200593.mini_retail_app.app.navigation.ScreenGraphs.ConfigData
import com.thomas200593.mini_retail_app.features.app_config.ui.data_config.AppConfigDataScreen
import timber.log.Timber

private val TAG_NAV_GRAPH_BUILDER = NavGraphBuilder::class.simpleName
private val TAG_NAV_CONTROLLER = NavController::class.simpleName

fun NavGraphBuilder.navGraphAppConfigData() {
    Timber.d("Called : fun $TAG_NAV_GRAPH_BUILDER.appConfigDataNavGraph()")
    navigation(
        route = G_CONFIG_DATA,
        startDestination = ConfigData.route
    ){
        composable(
            route = ConfigData.route
        ){
            AppConfigDataScreen()
        }

        //More Screen
    }
}

fun NavController.navigateToAppConfigData(
    destinationAppConfigData: DestinationAppConfigData?
){
    Timber.d("Called : fun $TAG_NAV_CONTROLLER.navigateToAppConfigData()")
    val navOptions = navOptions {
        launchSingleTop = true
        restoreState = true
    }
    this.navigate(
        route = destinationAppConfigData?.route?: G_CONFIG_DATA,
        navOptions = navOptions
    )
}
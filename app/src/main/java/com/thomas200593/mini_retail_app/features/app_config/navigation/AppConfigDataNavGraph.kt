package com.thomas200593.mini_retail_app.features.app_config.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import androidx.navigation.navigation
import com.thomas200593.mini_retail_app.app.navigation.NavigationGraphs.G_APP_CONFIG_DATA
import com.thomas200593.mini_retail_app.app.navigation.ScreenGraphs
import com.thomas200593.mini_retail_app.features.app_config.ui.components.data_config.AppConfigDataScreen
import timber.log.Timber

private val TAG_NAV_GRAPH_BUILDER = NavGraphBuilder::class.simpleName
private val TAG_NAV_CONTROLLER = NavController::class.simpleName

fun NavGraphBuilder.appConfigDataNavGraph() {
    Timber.d("Called : fun $TAG_NAV_GRAPH_BUILDER.appConfigDataNavGraph()")
    navigation(
        route = G_APP_CONFIG_DATA,
        startDestination = ScreenGraphs.AppConfigData.route
    ){
        composable(
            route = ScreenGraphs.AppConfigData.route
        ){
            AppConfigDataScreen()
        }

        //More Screen
    }
}

fun NavController.navigateToAppConfigData(
    appConfigDataDestination: AppConfigDataDestination?
){
    Timber.d("Called : fun $TAG_NAV_CONTROLLER.navigateToAppConfigData()")
    val navOptions = navOptions {
        launchSingleTop = true
        restoreState = true
    }
    this.navigate(
        route = appConfigDataDestination?.route?: G_APP_CONFIG_DATA,
        navOptions = navOptions
    )
}
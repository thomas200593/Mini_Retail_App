package com.thomas200593.mini_retail_app.features.app_config.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import androidx.navigation.navigation
import com.thomas200593.mini_retail_app.app.navigation.NavigationGraphs.G_APP_CONFIG
import com.thomas200593.mini_retail_app.app.navigation.ScreenGraphs
import com.thomas200593.mini_retail_app.features.app_config.ui.AppConfigScreen
import timber.log.Timber

private val TAG_NAV_GRAPH_BUILDER = NavGraphBuilder::class.simpleName
private val TAG_NAV_CONTROLLER = NavController::class.simpleName

fun NavGraphBuilder.navGraphAppConfig(){
    Timber.d("Called : fun $TAG_NAV_GRAPH_BUILDER.appConfigNavGraph()")
    navigation(
        route = G_APP_CONFIG,
        startDestination = ScreenGraphs.AppConfig.route
    ){
        composable(
            route = ScreenGraphs.AppConfig.route
        ){
            AppConfigScreen()
        }
        navGraphAppConfigGeneral()
        navGraphAppConfigData()
    }
}

fun NavController.navigateToAppConfig(
    destinationAppConfig: DestinationAppConfig?
){
    Timber.d("Called : fun $TAG_NAV_CONTROLLER.navigateToAppConfig()")
    val navOptions = navOptions {
        launchSingleTop = true
        restoreState = true
    }
    this.navigate(
        route = destinationAppConfig?.route?: G_APP_CONFIG,
        navOptions = navOptions
    )
}
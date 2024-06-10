package com.thomas200593.mini_retail_app.features.app_config.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import androidx.navigation.navigation
import com.thomas200593.mini_retail_app.app.navigation.NavigationGraphs
import com.thomas200593.mini_retail_app.app.navigation.ScreenGraphs
import com.thomas200593.mini_retail_app.features.app_config.entity.AppConfigGeneralMenu
import com.thomas200593.mini_retail_app.features.app_config.ui.components.general_config.dynamic_color.AppConfigGeneralDynamicColorScreen
import com.thomas200593.mini_retail_app.features.app_config.ui.components.general_config.font_size.AppConfigGeneralFontSizeScreen
import com.thomas200593.mini_retail_app.features.app_config.ui.components.general_config.language.AppConfigGeneralLanguageScreen
import com.thomas200593.mini_retail_app.features.app_config.ui.components.general_config.AppConfigGeneralScreen
import com.thomas200593.mini_retail_app.features.app_config.ui.components.general_config.theme.AppConfigGeneralThemeScreen


fun NavGraphBuilder.appConfigGeneralNavGraph(onNavigateBack: () -> Unit, onNavigateToMenu:(AppConfigGeneralMenu) -> Unit) {
    navigation(
        route = NavigationGraphs.G_APP_CONFIG_GENERAL,
        startDestination = ScreenGraphs.AppConfigGeneral.route
    ){
        composable(
            route = ScreenGraphs.AppConfigGeneral.route
        ){
            AppConfigGeneralScreen(
                onNavigateBack = onNavigateBack,
                onNavigateToMenu = onNavigateToMenu
            )
        }

        composable(
            route = ScreenGraphs.AppConfigGeneralLanguage.route
        ){
            AppConfigGeneralLanguageScreen(
                onNavigateBack = onNavigateBack,
            )
        }

        composable(
            route = ScreenGraphs.AppConfigGeneralTheme.route
        ){
            AppConfigGeneralThemeScreen(
                onNavigateBack = onNavigateBack,
            )
        }

        composable(
            route = ScreenGraphs.AppConfigGeneralDynamicColor.route
        ){
            AppConfigGeneralDynamicColorScreen(
                onNavigateBack = onNavigateBack,
            )
        }

        composable(
            route = ScreenGraphs.AppConfigGeneralFontSize.route
        ){
            AppConfigGeneralFontSizeScreen(
                onNavigateBack = onNavigateBack,
            )
        }
    }
}

fun NavController.navigateToAppConfigGeneralMenu() {
    this.navigate(
        route = NavigationGraphs.G_APP_CONFIG_GENERAL
    ){
        launchSingleTop = true
        restoreState = true
    }
}

fun NavController.navigateToAppConfigGeneralMenuDestination(
    appConfigGeneralMenu: AppConfigGeneralMenu
) {
    val navOptions = navOptions {
        launchSingleTop = true
        restoreState = true
    }
    this.navigate(
        appConfigGeneralMenu.route,
        navOptions = navOptions
    )
}
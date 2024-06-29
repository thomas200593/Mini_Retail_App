package com.thomas200593.mini_retail_app.features.app_config.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import androidx.navigation.navigation
import com.thomas200593.mini_retail_app.app.navigation.NavigationGraphs.G_APP_CONFIG_GENERAL
import com.thomas200593.mini_retail_app.app.navigation.ScreenGraphs
import com.thomas200593.mini_retail_app.features.app_config.ui.general_config.AppConfigGeneralScreen
import com.thomas200593.mini_retail_app.features.app_config.ui.general_config.country.AppConfigGeneralCountryScreen
import com.thomas200593.mini_retail_app.features.app_config.ui.general_config.currency.AppConfigGeneralCurrencyScreen
import com.thomas200593.mini_retail_app.features.app_config.ui.general_config.dynamic_color.AppConfigGeneralDynamicColorScreen
import com.thomas200593.mini_retail_app.features.app_config.ui.general_config.font_size.AppConfigGeneralFontSizeScreen
import com.thomas200593.mini_retail_app.features.app_config.ui.general_config.language.AppConfigGeneralLanguageScreen
import com.thomas200593.mini_retail_app.features.app_config.ui.general_config.theme.AppConfigGeneralThemeScreen
import com.thomas200593.mini_retail_app.features.app_config.ui.general_config.timezone.AppConfigGeneralTimezoneScreen
import timber.log.Timber

private val TAG_NAV_GRAPH_BUILDER = NavGraphBuilder::class.simpleName
private val TAG_NAV_CONTROLLER = NavController::class.simpleName

fun NavGraphBuilder.navGraphAppConfigGeneral() {
    Timber.d("Called : fun $TAG_NAV_GRAPH_BUILDER.appConfigGeneralNavGraph()")
    navigation(
        route = G_APP_CONFIG_GENERAL,
        startDestination = ScreenGraphs.AppConfigGeneral.route
    ){
        composable(
            route = ScreenGraphs.AppConfigGeneral.route
        ){
            AppConfigGeneralScreen()
        }

        composable(
            route = ScreenGraphs.AppConfigGeneralCountry.route
        ){
            AppConfigGeneralCountryScreen()
        }

        composable(
            route = ScreenGraphs.AppConfigGeneralLanguage.route
        ){
            AppConfigGeneralLanguageScreen()
        }

        composable(
            route = ScreenGraphs.AppConfigGeneralTimezone.route
        ){
            AppConfigGeneralTimezoneScreen()
        }

        composable(
            route = ScreenGraphs.AppConfigGeneralTheme.route
        ){
            AppConfigGeneralThemeScreen()
        }

        composable(
            route = ScreenGraphs.AppConfigGeneralDynamicColor.route
        ){
            AppConfigGeneralDynamicColorScreen()
        }

        composable(
            route = ScreenGraphs.AppConfigGeneralFontSize.route
        ){
            AppConfigGeneralFontSizeScreen()
        }

        composable(
            route = ScreenGraphs.AppConfigGeneralCurrency.route
        ){
            AppConfigGeneralCurrencyScreen()
        }
    }
}

fun NavController.navigateToAppConfigGeneral(
    destinationAppConfigGeneral: DestinationAppConfigGeneral?
) {
    Timber.d("Called : fun $TAG_NAV_CONTROLLER.navigateToAppConfigGeneral()")
    val navOptions = navOptions {
        launchSingleTop = true
        restoreState = true
    }
    this.navigate(
        route = destinationAppConfigGeneral?.route?:G_APP_CONFIG_GENERAL,
        navOptions = navOptions
    )
}
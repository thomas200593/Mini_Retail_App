package com.thomas200593.mini_retail_app.features.app_config.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import androidx.navigation.navigation
import com.thomas200593.mini_retail_app.app.navigation.NavigationGraphs.G_CONFIG_GENERAL
import com.thomas200593.mini_retail_app.app.navigation.ScreenGraphs.ConfigGeneral
import com.thomas200593.mini_retail_app.app.navigation.ScreenGraphs.Country
import com.thomas200593.mini_retail_app.app.navigation.ScreenGraphs.Currency
import com.thomas200593.mini_retail_app.app.navigation.ScreenGraphs.DynamicColor
import com.thomas200593.mini_retail_app.app.navigation.ScreenGraphs.FontSize
import com.thomas200593.mini_retail_app.app.navigation.ScreenGraphs.Language
import com.thomas200593.mini_retail_app.app.navigation.ScreenGraphs.Theme
import com.thomas200593.mini_retail_app.app.navigation.ScreenGraphs.Timezone
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
        route = G_CONFIG_GENERAL,
        startDestination = ConfigGeneral.route
    ){
        composable(
            route = ConfigGeneral.route
        ){
            AppConfigGeneralScreen()
        }

        composable(
            route = Country.route
        ){
            AppConfigGeneralCountryScreen()
        }

        composable(
            route = Language.route
        ){
            AppConfigGeneralLanguageScreen()
        }

        composable(
            route = Timezone.route
        ){
            AppConfigGeneralTimezoneScreen()
        }

        composable(
            route = Theme.route
        ){
            AppConfigGeneralThemeScreen()
        }

        composable(
            route = DynamicColor.route
        ){
            AppConfigGeneralDynamicColorScreen()
        }

        composable(
            route = FontSize.route
        ){
            AppConfigGeneralFontSizeScreen()
        }

        composable(
            route = Currency.route
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
        route = destinationAppConfigGeneral?.route?:G_CONFIG_GENERAL,
        navOptions = navOptions
    )
}
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
import com.thomas200593.mini_retail_app.features.app_config.ui.config_general.ConfigGeneralScreen
import com.thomas200593.mini_retail_app.features.app_config.ui.config_general.country.CountryScreen
import com.thomas200593.mini_retail_app.features.app_config.ui.config_general.currency.CurrencyScreen
import com.thomas200593.mini_retail_app.features.app_config.ui.config_general.dynamic_color.DynamicColorScreen
import com.thomas200593.mini_retail_app.features.app_config.ui.config_general.font_size.FontSizeScreen
import com.thomas200593.mini_retail_app.features.app_config.ui.config_general.language.LanguageScreen
import com.thomas200593.mini_retail_app.features.app_config.ui.config_general.theme.ThemeScreen
import com.thomas200593.mini_retail_app.features.app_config.ui.config_general.timezone.TimezoneScreen
import timber.log.Timber

private val TAG_NAV_GRAPH_BUILDER = NavGraphBuilder::class.simpleName
private val TAG_NAV_CONTROLLER = NavController::class.simpleName

fun NavGraphBuilder.navGraphConfigGeneral() {
    Timber.d("Called : fun $TAG_NAV_GRAPH_BUILDER.appConfigGeneralNavGraph()")
    navigation(
        route = G_CONFIG_GENERAL,
        startDestination = ConfigGeneral.route
    ){
        composable(
            route = ConfigGeneral.route
        ){
            ConfigGeneralScreen()
        }

        composable(
            route = Country.route
        ){
            CountryScreen()
        }

        composable(
            route = Language.route
        ){
            LanguageScreen()
        }

        composable(
            route = Timezone.route
        ){
            TimezoneScreen()
        }

        composable(
            route = Theme.route
        ){
            ThemeScreen()
        }

        composable(
            route = DynamicColor.route
        ){
            DynamicColorScreen()
        }

        composable(
            route = FontSize.route
        ){
            FontSizeScreen()
        }

        composable(
            route = Currency.route
        ){
            CurrencyScreen()
        }
    }
}

fun NavController.navigateToConfigGeneral(
    destinationConfigGeneral: DestinationConfigGeneral?
) {
    Timber.d("Called : fun $TAG_NAV_CONTROLLER.navigateToAppConfigGeneral()")
    val navOptions = navOptions {
        launchSingleTop = true
        restoreState = true
    }
    this.navigate(
        route = destinationConfigGeneral?.route?:G_CONFIG_GENERAL,
        navOptions = navOptions
    )
}
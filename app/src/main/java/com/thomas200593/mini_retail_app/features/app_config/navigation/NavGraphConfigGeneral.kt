package com.thomas200593.mini_retail_app.features.app_config.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import androidx.navigation.navigation
import com.thomas200593.mini_retail_app.app.navigation.NavigationGraphs
import com.thomas200593.mini_retail_app.app.navigation.ScreenGraphs
import com.thomas200593.mini_retail_app.features.app_config.ui.config_general.ConfigGeneralScreen
import com.thomas200593.mini_retail_app.features.app_config.ui.config_general.country.CountryScreen
import com.thomas200593.mini_retail_app.features.app_config.ui.config_general.currency.CurrencyScreen
import com.thomas200593.mini_retail_app.features.app_config.ui.config_general.dynamic_color.DynamicColorScreen
import com.thomas200593.mini_retail_app.features.app_config.ui.config_general.font_size.FontSizeScreen
import com.thomas200593.mini_retail_app.features.app_config.ui.config_general.language.LanguageScreen
import com.thomas200593.mini_retail_app.features.app_config.ui.config_general.theme.ThemeScreen
import com.thomas200593.mini_retail_app.features.app_config.ui.config_general.timezone.TimezoneScreen

fun NavGraphBuilder.navGraphConfigGeneral() {
    navigation(
        route = NavigationGraphs.G_CONFIG_GENERAL,
        startDestination = ScreenGraphs.ConfigGeneral.route
    ){
        composable(
            route = ScreenGraphs.ConfigGeneral.route
        ){
            ConfigGeneralScreen()
        }

        composable(
            route = ScreenGraphs.Country.route
        ){
            CountryScreen()
        }

        composable(
            route = ScreenGraphs.Language.route
        ){
            LanguageScreen()
        }

        composable(
            route = ScreenGraphs.Timezone.route
        ){
            TimezoneScreen()
        }

        composable(
            route = ScreenGraphs.Theme.route
        ){
            ThemeScreen()
        }

        composable(
            route = ScreenGraphs.DynamicColor.route
        ){
            DynamicColorScreen()
        }

        composable(
            route = ScreenGraphs.FontSize.route
        ){
            FontSizeScreen()
        }

        composable(
            route = ScreenGraphs.Currency.route
        ){
            CurrencyScreen()
        }
    }
}

fun NavController.navigateToConfigGeneral(
    destinationConfigGeneral: DestinationConfigGeneral?
) {
    val navOptions = navOptions {
        launchSingleTop = true
        restoreState = true
    }
    this.navigate(
        route = destinationConfigGeneral?.route?: NavigationGraphs.G_CONFIG_GENERAL,
        navOptions = navOptions
    )
}
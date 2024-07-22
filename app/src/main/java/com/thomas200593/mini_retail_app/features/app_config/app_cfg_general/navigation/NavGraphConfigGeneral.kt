package com.thomas200593.mini_retail_app.features.app_config.app_cfg_general.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import androidx.navigation.navigation
import com.thomas200593.mini_retail_app.app.navigation.NavigationGraphs
import com.thomas200593.mini_retail_app.app.navigation.ScreenGraphs
import com.thomas200593.mini_retail_app.features.app_config.app_cfg_general.ui.ConfigGeneralScreen
import com.thomas200593.mini_retail_app.features.app_config.app_cfg_general_country.ui.CountryScreen
import com.thomas200593.mini_retail_app.features.app_config.app_cfg_general_currency.ui.CurrencyScreen
import com.thomas200593.mini_retail_app.features.app_config.app_cfg_general_dynamic_color.ui.DynamicColorScreen
import com.thomas200593.mini_retail_app.features.app_config.app_cfg_general_font_size.ui.FontSizeScreen
import com.thomas200593.mini_retail_app.features.app_config.app_cfg_general_language.ui.LanguageScreen
import com.thomas200593.mini_retail_app.features.app_config.app_cfg_general_theme.ui.ThemeScreen
import com.thomas200593.mini_retail_app.features.app_config.app_cfg_general_timezone.ui.TimezoneScreen

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
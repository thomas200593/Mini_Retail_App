package com.thomas200593.mini_retail_app.features.app_conf._general.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import androidx.navigation.navigation
import com.thomas200593.mini_retail_app.app.navigation.NavGraph
import com.thomas200593.mini_retail_app.app.navigation.ScrGraphs
import com.thomas200593.mini_retail_app.features.app_conf._general.ui.ConfigGeneralScreen
import com.thomas200593.mini_retail_app.features.app_conf._gen_country.ui.ScreenConfCountry
import com.thomas200593.mini_retail_app.features.app_conf._g_currency.ui.CurrencyScreen
import com.thomas200593.mini_retail_app.features.app_conf._g_dynamic_color.ui.DynamicColorScreen
import com.thomas200593.mini_retail_app.features.app_conf._g_font_size.ui.FontSizeScreen
import com.thomas200593.mini_retail_app.features.app_conf._g_language.ui.LanguageScreen
import com.thomas200593.mini_retail_app.features.app_conf._g_theme.ui.ThemeScreen
import com.thomas200593.mini_retail_app.features.app_conf._g_timezone.ui.TimezoneScreen

fun NavGraphBuilder.navGraphConfigGeneral() {
    navigation(
        route = NavGraph.G_CONFIG_GENERAL,
        startDestination = ScrGraphs.ConfigGeneral.route
    ){
        composable(
            route = ScrGraphs.ConfigGeneral.route
        ){
            ConfigGeneralScreen()
        }

        composable(
            route = ScrGraphs.Country.route
        ){
            ScreenConfCountry()
        }

        composable(
            route = ScrGraphs.Language.route
        ){
            LanguageScreen()
        }

        composable(
            route = ScrGraphs.Timezone.route
        ){
            TimezoneScreen()
        }

        composable(
            route = ScrGraphs.Theme.route
        ){
            ThemeScreen()
        }

        composable(
            route = ScrGraphs.DynamicColor.route
        ){
            DynamicColorScreen()
        }

        composable(
            route = ScrGraphs.FontSize.route
        ){
            FontSizeScreen()
        }

        composable(
            route = ScrGraphs.Currency.route
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
        route = destinationConfigGeneral?.route?: NavGraph.G_CONFIG_GENERAL,
        navOptions = navOptions
    )
}
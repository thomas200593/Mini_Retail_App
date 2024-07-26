package com.thomas200593.mini_retail_app.features.app_conf.conf_gen.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import androidx.navigation.navigation
import com.thomas200593.mini_retail_app.app.navigation.NavGraph.G_CONFIG_GENERAL
import com.thomas200593.mini_retail_app.app.navigation.ScrGraphs.ConfigGeneral
import com.thomas200593.mini_retail_app.app.navigation.ScrGraphs.Country
import com.thomas200593.mini_retail_app.app.navigation.ScrGraphs.Currency
import com.thomas200593.mini_retail_app.app.navigation.ScrGraphs.DynamicColor
import com.thomas200593.mini_retail_app.app.navigation.ScrGraphs.FontSize
import com.thomas200593.mini_retail_app.app.navigation.ScrGraphs.Language
import com.thomas200593.mini_retail_app.app.navigation.ScrGraphs.Theme
import com.thomas200593.mini_retail_app.app.navigation.ScrGraphs.Timezone
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen.ui.ScrConfGen
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_country.ui.ScrConfGenCountry
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_currency.ui.ScrConfGenCurrency
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_dynamic_color.ui.ScrConfGenDynamicColor
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_font_size.ui.ScrConfGenFontSize
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_language.ui.ScrConfGenLanguage
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_theme.ui.ScrConfGenTheme
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_timezone.ui.ScrConfGenTimezone

fun NavGraphBuilder.navGraphConfGen() {
    navigation(
        route = G_CONFIG_GENERAL,
        startDestination = ConfigGeneral.route
    ){
        composable(
            route = ConfigGeneral.route
        ){ ScrConfGen() }

        composable(
            route = Country.route
        ){ ScrConfGenCountry() }

        composable(
            route = Language.route
        ){ ScrConfGenLanguage() }

        composable(
            route = Timezone.route
        ){ ScrConfGenTimezone() }

        composable(
            route = Theme.route
        ){ ScrConfGenTheme() }

        composable(
            route = DynamicColor.route
        ){ ScrConfGenDynamicColor() }

        composable(
            route = FontSize.route
        ){ ScrConfGenFontSize() }

        composable(
            route = Currency.route
        ){ ScrConfGenCurrency() }
    }
}

fun NavController.navToConfGen(
    destConfGen: DestConfGen?
) {
    val navOptions = navOptions { launchSingleTop = true; restoreState = true }
    this.navigate(
        route = destConfGen?.route?: G_CONFIG_GENERAL,
        navOptions = navOptions
    )
}
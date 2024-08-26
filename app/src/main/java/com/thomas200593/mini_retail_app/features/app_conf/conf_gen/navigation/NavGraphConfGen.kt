package com.thomas200593.mini_retail_app.features.app_conf.conf_gen.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import androidx.navigation.navigation
import com.thomas200593.mini_retail_app.app.navigation.NavGraph.G_CONFIG_GENERAL
import com.thomas200593.mini_retail_app.app.navigation.ScrGraphs.AppConfigGeneral
import com.thomas200593.mini_retail_app.app.navigation.ScrGraphs.ConfGenCountry
import com.thomas200593.mini_retail_app.app.navigation.ScrGraphs.ConfGenCurrency
import com.thomas200593.mini_retail_app.app.navigation.ScrGraphs.ConfGenDynamicColor
import com.thomas200593.mini_retail_app.app.navigation.ScrGraphs.ConfGenFontSize
import com.thomas200593.mini_retail_app.app.navigation.ScrGraphs.ConfGenLanguage
import com.thomas200593.mini_retail_app.app.navigation.ScrGraphs.ConfGenTheme
import com.thomas200593.mini_retail_app.app.navigation.ScrGraphs.ConfGenTimezone
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
        startDestination = AppConfigGeneral.route
    ){
        composable(
            route = AppConfigGeneral.route
        ){ ScrConfGen() }

        composable(
            route = ConfGenCountry.route
        ){ ScrConfGenCountry() }

        composable(
            route = ConfGenLanguage.route
        ){ ScrConfGenLanguage() }

        composable(
            route = ConfGenTimezone.route
        ){ ScrConfGenTimezone() }

        composable(
            route = ConfGenTheme.route
        ){ ScrConfGenTheme() }

        composable(
            route = ConfGenDynamicColor.route
        ){ ScrConfGenDynamicColor() }

        composable(
            route = ConfGenFontSize.route
        ){ ScrConfGenFontSize() }

        composable(
            route = ConfGenCurrency.route
        ){ ScrConfGenCurrency() }
    }
}

fun NavController.navToConfGen(destConfGen: DestConfGen? = null) {
    val navOptions = navOptions { launchSingleTop = true; restoreState = true }
    this.navigate(
        route = destConfGen?.scrGraphs?.route ?: G_CONFIG_GENERAL,
        navOptions = navOptions
    )
}
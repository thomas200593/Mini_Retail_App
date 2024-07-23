package com.thomas200593.mini_retail_app.features.app_conf.conf_gen.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import androidx.navigation.navigation
import com.thomas200593.mini_retail_app.app.navigation.NavGraph
import com.thomas200593.mini_retail_app.app.navigation.ScrGraphs
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
        route = NavGraph.G_CONFIG_GENERAL,
        startDestination = ScrGraphs.ConfigGeneral.route
    ){
        composable(
            route = ScrGraphs.ConfigGeneral.route
        ){ ScrConfGen() }

        composable(
            route = ScrGraphs.Country.route
        ){ ScrConfGenCountry() }

        composable(
            route = ScrGraphs.Language.route
        ){ ScrConfGenLanguage() }

        composable(
            route = ScrGraphs.Timezone.route
        ){ ScrConfGenTimezone() }

        composable(
            route = ScrGraphs.Theme.route
        ){ ScrConfGenTheme() }

        composable(
            route = ScrGraphs.DynamicColor.route
        ){ ScrConfGenDynamicColor() }

        composable(
            route = ScrGraphs.FontSize.route
        ){ ScrConfGenFontSize() }

        composable(
            route = ScrGraphs.Currency.route
        ){ ScrConfGenCurrency() }
    }
}

fun NavController.navToConfGen(
    destConfGen: DestConfGen?
) {
    val navOptions = navOptions { launchSingleTop = true; restoreState = true }
    this.navigate(
        route = destConfGen?.route?: NavGraph.G_CONFIG_GENERAL,
        navOptions = navOptions
    )
}
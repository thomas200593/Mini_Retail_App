package com.thomas200593.mini_retail_app.features.app_config.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.thomas200593.mini_retail_app.R
import com.thomas200593.mini_retail_app.app.navigation.ScreenGraphs
import com.thomas200593.mini_retail_app.core.ui.common.Icons

enum class DestinationAppConfigGeneral(
    val route: String,
    @DrawableRes val iconRes: Int,
    @StringRes val title: Int,
    @StringRes val description: Int,
    val usesAuth: Boolean
){
    THEME(
        route = ScreenGraphs.Theme.route,
        iconRes = Icons.Theme.theme,
        title = R.string.str_theme,
        description = R.string.str_theme_desc,
        usesAuth = false
    ),
    DYNAMIC_COLOR(
        route = ScreenGraphs.DynamicColor.route,
        iconRes = Icons.DynamicColor.dynamic_color,
        title = R.string.str_dynamic_color,
        description = R.string.str_dynamic_color_desc,
        usesAuth = false
    ),
    FONT_SIZE(
        route = ScreenGraphs.FontSize.route,
        iconRes = Icons.Font.font,
        title = R.string.str_size_font,
        description = R.string.str_size_font_desc,
        usesAuth = false
    ),
    COUNTRY(
        route = ScreenGraphs.Country.route,
        iconRes = Icons.Country.country,
        title = R.string.str_country,
        description = R.string.str_country_desc,
        usesAuth = true
    ),
    LANGUAGE(
        route = ScreenGraphs.Language.route,
        iconRes = Icons.Language.language,
        title = R.string.str_lang,
        description = R.string.str_lang_desc,
        usesAuth = false
    ),
    TIMEZONE(
        route = ScreenGraphs.Timezone.route,
        iconRes = Icons.Timezone.timezone,
        title = R.string.str_timezone,
        description = R.string.str_timezone,
        usesAuth = false
    ),
    CURRENCY(
        route = ScreenGraphs.Currency.route,
        iconRes = Icons.Currency.currency,
        title = R.string.str_currency,
        description = R.string.str_currency_desc,
        usesAuth = false
    )
}
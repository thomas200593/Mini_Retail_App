package com.thomas200593.mini_retail_app.features.app_conf._general.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.thomas200593.mini_retail_app.R
import com.thomas200593.mini_retail_app.app.navigation.ScrGraphs
import com.thomas200593.mini_retail_app.core.ui.common.CustomIcons

enum class DestConfGen(
    val route: String,
    @DrawableRes val iconRes: Int,
    @StringRes val title: Int,
    @StringRes val description: Int,
    val usesAuth: Boolean
){
    THEME(
        route = ScrGraphs.Theme.route,
        iconRes = CustomIcons.Theme.theme,
        title = R.string.str_theme,
        description = R.string.str_theme_desc,
        usesAuth = false
    ),
    DYNAMIC_COLOR(
        route = ScrGraphs.DynamicColor.route,
        iconRes = CustomIcons.DynamicColor.dynamic_color,
        title = R.string.str_dynamic_color,
        description = R.string.str_dynamic_color_desc,
        usesAuth = false
    ),
    FONT_SIZE(
        route = ScrGraphs.FontSize.route,
        iconRes = CustomIcons.Font.font,
        title = R.string.str_size_font,
        description = R.string.str_size_font_desc,
        usesAuth = false
    ),
    COUNTRY(
        route = ScrGraphs.Country.route,
        iconRes = CustomIcons.Country.country,
        title = R.string.str_country,
        description = R.string.str_country_desc,
        usesAuth = true
    ),
    LANGUAGE(
        route = ScrGraphs.Language.route,
        iconRes = CustomIcons.Language.language,
        title = R.string.str_lang,
        description = R.string.str_lang_desc,
        usesAuth = false
    ),
    TIMEZONE(
        route = ScrGraphs.Timezone.route,
        iconRes = CustomIcons.Timezone.timezone,
        title = R.string.str_timezone,
        description = R.string.str_timezone,
        usesAuth = false
    ),
    CURRENCY(
        route = ScrGraphs.Currency.route,
        iconRes = CustomIcons.Currency.currency,
        title = R.string.str_currency,
        description = R.string.str_currency_desc,
        usesAuth = false
    )
}
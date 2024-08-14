package com.thomas200593.mini_retail_app.features.app_conf.conf_gen.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.thomas200593.mini_retail_app.R.string
import com.thomas200593.mini_retail_app.app.navigation.ScrGraphs.Country
import com.thomas200593.mini_retail_app.app.navigation.ScrGraphs.Currency
import com.thomas200593.mini_retail_app.app.navigation.ScrGraphs.DynamicColor
import com.thomas200593.mini_retail_app.app.navigation.ScrGraphs.FontSize
import com.thomas200593.mini_retail_app.app.navigation.ScrGraphs.Language
import com.thomas200593.mini_retail_app.app.navigation.ScrGraphs.Theme
import com.thomas200593.mini_retail_app.app.navigation.ScrGraphs.Timezone
import com.thomas200593.mini_retail_app.core.ui.common.CustomIcons.Country.country
import com.thomas200593.mini_retail_app.core.ui.common.CustomIcons.Currency.currency
import com.thomas200593.mini_retail_app.core.ui.common.CustomIcons.DynamicColor.dynamic_color
import com.thomas200593.mini_retail_app.core.ui.common.CustomIcons.Font.font
import com.thomas200593.mini_retail_app.core.ui.common.CustomIcons.Language.language
import com.thomas200593.mini_retail_app.core.ui.common.CustomIcons.Theme.theme
import com.thomas200593.mini_retail_app.core.ui.common.CustomIcons.Timezone.timezone

enum class DestConfGen(
    val route: String,
    @DrawableRes val iconRes: Int,
    @StringRes val title: Int,
    @StringRes val description: Int,
    val usesAuth: Boolean
){
    THEME(
        route = Theme.route,
        iconRes = theme,
        title = string.str_theme,
        description = string.str_theme_desc,
        usesAuth = false
    ),
    DYNAMIC_COLOR(
        route = DynamicColor.route,
        iconRes = dynamic_color,
        title = string.str_dynamic_color,
        description = string.str_dynamic_color_desc,
        usesAuth = false
    ),
    FONT_SIZE(
        route = FontSize.route,
        iconRes = font,
        title = string.str_size_font,
        description = string.str_size_font_desc,
        usesAuth = false
    ),
    COUNTRY(
        route = Country.route,
        iconRes = country,
        title = string.str_country,
        description = string.str_country_desc,
        usesAuth = true
    ),
    LANGUAGE(
        route = Language.route,
        iconRes = language,
        title = string.str_lang,
        description = string.str_lang_desc,
        usesAuth = false
    ),
    TIMEZONE(
        route = Timezone.route,
        iconRes = timezone,
        title = string.str_timezone,
        description = string.str_timezone,
        usesAuth = false
    ),
    CURRENCY(
        route = Currency.route,
        iconRes = currency,
        title = string.str_currency,
        description = string.str_currency_desc,
        usesAuth = false
    )
}
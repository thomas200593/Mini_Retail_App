package com.thomas200593.mini_retail_app.features.app_conf.conf_gen.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.thomas200593.mini_retail_app.R.string.str_country
import com.thomas200593.mini_retail_app.R.string.str_country_desc
import com.thomas200593.mini_retail_app.R.string.str_currency
import com.thomas200593.mini_retail_app.R.string.str_currency_desc
import com.thomas200593.mini_retail_app.R.string.str_dynamic_color
import com.thomas200593.mini_retail_app.R.string.str_dynamic_color_desc
import com.thomas200593.mini_retail_app.R.string.str_lang
import com.thomas200593.mini_retail_app.R.string.str_lang_desc
import com.thomas200593.mini_retail_app.R.string.str_size_font
import com.thomas200593.mini_retail_app.R.string.str_size_font_desc
import com.thomas200593.mini_retail_app.R.string.str_theme
import com.thomas200593.mini_retail_app.R.string.str_theme_desc
import com.thomas200593.mini_retail_app.R.string.str_timezone
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
        title = str_theme,
        description = str_theme_desc,
        usesAuth = false
    ),
    DYNAMIC_COLOR(
        route = DynamicColor.route,
        iconRes = dynamic_color,
        title = str_dynamic_color,
        description = str_dynamic_color_desc,
        usesAuth = false
    ),
    FONT_SIZE(
        route = FontSize.route,
        iconRes = font,
        title = str_size_font,
        description = str_size_font_desc,
        usesAuth = false
    ),
    COUNTRY(
        route = Country.route,
        iconRes = country,
        title = str_country,
        description = str_country_desc,
        usesAuth = true
    ),
    LANGUAGE(
        route = Language.route,
        iconRes = language,
        title = str_lang,
        description = str_lang_desc,
        usesAuth = false
    ),
    TIMEZONE(
        route = Timezone.route,
        iconRes = timezone,
        title = str_timezone,
        description = str_timezone,
        usesAuth = false
    ),
    CURRENCY(
        route = Currency.route,
        iconRes = currency,
        title = str_currency,
        description = str_currency_desc,
        usesAuth = false
    )
}
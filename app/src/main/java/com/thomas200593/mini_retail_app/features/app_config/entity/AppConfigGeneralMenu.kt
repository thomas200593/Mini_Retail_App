package com.thomas200593.mini_retail_app.features.app_config.entity

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.thomas200593.mini_retail_app.R
import com.thomas200593.mini_retail_app.app.navigation.ScreenGraphs
import com.thomas200593.mini_retail_app.core.ui.common.Icons
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

data class AppConfigGeneralMenu(
    val route: String,
    @DrawableRes val imageRes: Int,
    @StringRes val title: Int,
    @StringRes val description: Int,
)

suspend fun getAppConfigGeneralMenu() = withContext(Dispatchers.IO){
    setOf(
        //Language
        AppConfigGeneralMenu(
            route = ScreenGraphs.AppConfigGeneralLanguage.route,
            imageRes = Icons.Language.language,
            title = R.string.str_lang,
            description = R.string.str_lang_desc,
        ),
        //Theme
        AppConfigGeneralMenu(
            route = ScreenGraphs.AppConfigGeneralTheme.route,
            imageRes = Icons.Theme.theme,
            title = R.string.str_theme,
            description = R.string.str_theme_desc,
        ),
        //Dynamic Color
        AppConfigGeneralMenu(
            route = ScreenGraphs.AppConfigGeneralDynamicColor.route,
            imageRes = Icons.DynamicColor.dynamic_color,
            title = R.string.str_dynamic_color,
            description = R.string.str_dynamic_color_desc,
        ),
        //Font size
        AppConfigGeneralMenu(
            route = ScreenGraphs.AppConfigGeneralFontSize.route,
            imageRes = Icons.Font.font,
            title = R.string.str_size_font,
            description = R.string.str_size_font,
        )
    )
}
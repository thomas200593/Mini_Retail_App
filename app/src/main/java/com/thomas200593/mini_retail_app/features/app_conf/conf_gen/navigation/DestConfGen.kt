package com.thomas200593.mini_retail_app.features.app_conf.conf_gen.navigation

import com.thomas200593.mini_retail_app.app.navigation.ScrGraphs
import com.thomas200593.mini_retail_app.app.navigation.ScrGraphs.ConfGenCountry
import com.thomas200593.mini_retail_app.app.navigation.ScrGraphs.ConfGenCurrency
import com.thomas200593.mini_retail_app.app.navigation.ScrGraphs.ConfGenDynamicColor
import com.thomas200593.mini_retail_app.app.navigation.ScrGraphs.ConfGenFontSize
import com.thomas200593.mini_retail_app.app.navigation.ScrGraphs.ConfGenLanguage
import com.thomas200593.mini_retail_app.app.navigation.ScrGraphs.ConfGenTheme
import com.thomas200593.mini_retail_app.app.navigation.ScrGraphs.ConfGenTimezone

enum class DestConfGen(val scrGraphs: ScrGraphs) {
    CONF_GEN_THEME(scrGraphs = ConfGenTheme),
    CONF_GEN_DYNAMIC_COLOR(scrGraphs = ConfGenDynamicColor),
    CONF_GEN_FONT_SIZE(scrGraphs = ConfGenFontSize),
    CONF_GEN_COUNTRY(scrGraphs = ConfGenCountry),
    CONF_GEN_LANGUAGE(scrGraphs = ConfGenLanguage),
    CONF_GEN_TIMEZONE(scrGraphs = ConfGenTimezone),
    CONF_GEN_CURRENCY(scrGraphs = ConfGenCurrency)
}
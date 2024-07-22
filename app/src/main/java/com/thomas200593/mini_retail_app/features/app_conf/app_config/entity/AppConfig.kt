package com.thomas200593.mini_retail_app.features.app_conf.app_config.entity

import com.thomas200593.mini_retail_app.core.design_system.util.HlpCountry
import com.thomas200593.mini_retail_app.core.design_system.util.HlpCurrency
import com.thomas200593.mini_retail_app.core.design_system.util.HlpTimezone
import com.thomas200593.mini_retail_app.features.app_conf._gen_country.entity.Country
import com.thomas200593.mini_retail_app.features.app_conf._g_currency.entity.Currency
import com.thomas200593.mini_retail_app.features.app_conf._g_dynamic_color.entity.DynamicColor
import com.thomas200593.mini_retail_app.features.app_conf._g_font_size.entity.FontSize
import com.thomas200593.mini_retail_app.features.app_conf._g_language.entity.Language
import com.thomas200593.mini_retail_app.features.app_conf._g_theme.entity.Theme
import com.thomas200593.mini_retail_app.features.app_conf._g_timezone.entity.Timezone
import com.thomas200593.mini_retail_app.features.onboarding.entity.OnboardingStatus

object AppConfig{
    data class ConfigCurrent(
        val onboardingStatus: OnboardingStatus = OnboardingStatus.SHOW,
        val theme: Theme = Theme.SYSTEM,
        val dynamicColor: DynamicColor = DynamicColor.DISABLED,
        val fontSize: FontSize = FontSize.MEDIUM,
        val country: Country = HlpCountry.COUNTRY_DEFAULT,
        val language: Language = Language.EN,
        val timezone: Timezone = HlpTimezone.TIMEZONE_DEFAULT,
        val currency: Currency = HlpCurrency.CURRENCY_DEFAULT
    )

    data class ConfigCountry(
        val configCurrent: ConfigCurrent,
        val countries: List<Country>
    )

    data class ConfigCurrency(
        val configCurrent: ConfigCurrent,
        val currencies: List<Currency>
    )

    data class ConfigDynamicColor(
        val configCurrent: ConfigCurrent,
        val dynamicColors: Set<DynamicColor>
    )

    data class ConfigFontSizes(
        val configCurrent: ConfigCurrent,
        val fontSizes: Set<FontSize>
    )

    data class ConfigLanguages(
        val configCurrent: ConfigCurrent,
        val languages: Set<Language>
    )

    data class ConfigThemes(
        val configCurrent: ConfigCurrent,
        val themes: Set<Theme>
    )

    data class ConfigTimezones(
        val configCurrent: ConfigCurrent,
        val timezones: List<Timezone>
    )
}
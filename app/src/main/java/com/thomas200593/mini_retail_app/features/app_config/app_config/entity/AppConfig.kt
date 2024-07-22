package com.thomas200593.mini_retail_app.features.app_config.app_config.entity

import com.thomas200593.mini_retail_app.core.design_system.util.CountryHelper
import com.thomas200593.mini_retail_app.core.design_system.util.CurrencyHelper
import com.thomas200593.mini_retail_app.core.design_system.util.TimezoneHelper
import com.thomas200593.mini_retail_app.features.app_config.cfg_general_country.entity.Country
import com.thomas200593.mini_retail_app.features.app_config.cfg_general_currency.entity.Currency
import com.thomas200593.mini_retail_app.features.app_config.cfg_general_dynamic_color.entity.DynamicColor
import com.thomas200593.mini_retail_app.features.app_config.cfg_general_font_size.entity.FontSize
import com.thomas200593.mini_retail_app.features.app_config.cfg_general_language.entity.Language
import com.thomas200593.mini_retail_app.features.app_config.cfg_general_theme.entity.Theme
import com.thomas200593.mini_retail_app.features.app_config.cfg_general_timezone.entity.Timezone
import com.thomas200593.mini_retail_app.features.onboarding.entity.OnboardingStatus

object AppConfig{
    data class ConfigCurrent(
        val onboardingStatus: OnboardingStatus = OnboardingStatus.SHOW,
        val theme: Theme = Theme.SYSTEM,
        val dynamicColor: DynamicColor = DynamicColor.DISABLED,
        val fontSize: FontSize = FontSize.MEDIUM,
        val country: Country = CountryHelper.COUNTRY_DEFAULT,
        val language: Language = Language.EN,
        val timezone: Timezone = TimezoneHelper.TIMEZONE_DEFAULT,
        val currency: Currency = CurrencyHelper.CURRENCY_DEFAULT
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
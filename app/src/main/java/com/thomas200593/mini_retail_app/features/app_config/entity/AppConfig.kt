package com.thomas200593.mini_retail_app.features.app_config.entity

import com.thomas200593.mini_retail_app.core.design_system.util.CountryHelper
import com.thomas200593.mini_retail_app.core.design_system.util.CurrencyHelper
import com.thomas200593.mini_retail_app.core.design_system.util.TimezoneHelper

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
}
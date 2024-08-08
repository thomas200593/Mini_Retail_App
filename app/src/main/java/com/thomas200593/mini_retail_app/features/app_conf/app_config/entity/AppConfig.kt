package com.thomas200593.mini_retail_app.features.app_conf.app_config.entity

import com.thomas200593.mini_retail_app.core.design_system.util.HlpCountry.COUNTRY_DEFAULT
import com.thomas200593.mini_retail_app.core.design_system.util.HlpCurrency.CURRENCY_DEFAULT
import com.thomas200593.mini_retail_app.core.design_system.util.HlpDatetime.TIMEZONE_DEFAULT
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_dynamic_color.entity.DynamicColor
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_dynamic_color.entity.DynamicColor.DISABLED
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_font_size.entity.FontSize
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_font_size.entity.FontSize.MEDIUM
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_language.entity.Language
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_language.entity.Language.EN
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_theme.entity.Theme
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_theme.entity.Theme.SYSTEM
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_timezone.entity.Timezone
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_country.entity.Country
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_currency.entity.Currency
import com.thomas200593.mini_retail_app.features.onboarding.entity.OnboardingStatus
import com.thomas200593.mini_retail_app.features.onboarding.entity.OnboardingStatus.SHOW

object AppConfig{
    data class ConfigCurrent(
        val onboardingStatus: OnboardingStatus = SHOW,
        val theme: Theme = SYSTEM,
        val dynamicColor: DynamicColor = DISABLED,
        val fontSize: FontSize = MEDIUM,
        val country: Country = COUNTRY_DEFAULT,
        val language: Language = EN,
        val timezone: Timezone = TIMEZONE_DEFAULT,
        val currency: Currency = CURRENCY_DEFAULT
    )
}
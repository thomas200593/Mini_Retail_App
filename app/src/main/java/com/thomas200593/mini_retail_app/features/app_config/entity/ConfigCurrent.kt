package com.thomas200593.mini_retail_app.features.app_config.entity

import com.thomas200593.mini_retail_app.core.util.CountryHelper.COUNTRY_DEFAULT
import com.thomas200593.mini_retail_app.core.util.CurrencyHelper.CURRENCY_DEFAULT
import com.thomas200593.mini_retail_app.core.util.TimezoneHelper.TIMEZONE_DEFAULT
import com.thomas200593.mini_retail_app.features.app_config.entity.DynamicColor.DISABLED
import com.thomas200593.mini_retail_app.features.app_config.entity.FontSize.MEDIUM
import com.thomas200593.mini_retail_app.features.app_config.entity.Language.EN
import com.thomas200593.mini_retail_app.features.app_config.entity.OnboardingStatus.SHOW
import com.thomas200593.mini_retail_app.features.app_config.entity.Theme.SYSTEM

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
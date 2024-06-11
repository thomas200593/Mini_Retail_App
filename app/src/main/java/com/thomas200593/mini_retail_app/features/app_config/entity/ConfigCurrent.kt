package com.thomas200593.mini_retail_app.features.app_config.entity

import com.thomas200593.mini_retail_app.core.util.CurrencyHelper.CURRENCY_DEFAULT
import com.thomas200593.mini_retail_app.core.util.TimezoneHelper.TIMEZONE_DEFAULT
import com.thomas200593.mini_retail_app.features.app_config.entity.DynamicColor.DISABLED
import com.thomas200593.mini_retail_app.features.app_config.entity.Font.MEDIUM
import com.thomas200593.mini_retail_app.features.app_config.entity.Language.EN
import com.thomas200593.mini_retail_app.features.app_config.entity.Onboarding.SHOW
import com.thomas200593.mini_retail_app.features.app_config.entity.Theme.SYSTEM

data class ConfigCurrent(
    val showOnboardingPages: Onboarding = SHOW,
    val currentTheme: Theme = SYSTEM,
    val currentDynamicColor: DynamicColor = DISABLED,
    val currentFontSize: Font = MEDIUM,
    val currentLanguage: Language = EN,
    val currentTimezone: Timezone = TIMEZONE_DEFAULT,
    val currentCurrency: Currency = CURRENCY_DEFAULT
)
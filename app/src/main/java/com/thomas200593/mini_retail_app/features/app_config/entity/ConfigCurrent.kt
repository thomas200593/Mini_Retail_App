package com.thomas200593.mini_retail_app.features.app_config.entity

import com.thomas200593.mini_retail_app.core.util.CurrencyHelper.CURRENCY_DEFAULT
import com.thomas200593.mini_retail_app.core.util.TimezoneHelper.TIMEZONE_DEFAULT
import com.thomas200593.mini_retail_app.features.app_config.entity.DynamicColor.DISABLED
import com.thomas200593.mini_retail_app.features.app_config.entity.FontSize.MEDIUM
import com.thomas200593.mini_retail_app.features.app_config.entity.Language.EN
import com.thomas200593.mini_retail_app.features.app_config.entity.OnboardingStatus.SHOW
import com.thomas200593.mini_retail_app.features.app_config.entity.Theme.SYSTEM

data class ConfigCurrent(
    val onboardingPagesStatus: OnboardingStatus = SHOW,
    val currentTheme: Theme = SYSTEM,
    val currentDynamicColor: DynamicColor = DISABLED,
    val currentFontSizeSize: FontSize = MEDIUM,
    val currentLanguage: Language = EN,
    val currentTimezone: Timezone = TIMEZONE_DEFAULT,
    val currentCurrency: Currency = CURRENCY_DEFAULT
)
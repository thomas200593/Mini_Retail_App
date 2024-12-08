package com.thomas200593.mini_retail_app.core.ui.common

import androidx.annotation.DrawableRes
import com.thomas200593.mini_retail_app.R.drawable.app_icon_48x48px
import com.thomas200593.mini_retail_app.R.drawable.app_icon_business_48px
import com.thomas200593.mini_retail_app.R.drawable.app_icon_business_profile_48px
import com.thomas200593.mini_retail_app.R.drawable.app_icon_country_48px
import com.thomas200593.mini_retail_app.R.drawable.app_icon_currency_48px
import com.thomas200593.mini_retail_app.R.drawable.app_icon_customer_48px
import com.thomas200593.mini_retail_app.R.drawable.app_icon_dashboard_48px
import com.thomas200593.mini_retail_app.R.drawable.app_icon_data_48px
import com.thomas200593.mini_retail_app.R.drawable.app_icon_dynamic_color_48px
import com.thomas200593.mini_retail_app.R.drawable.app_icon_dynamic_color_disabled_48px
import com.thomas200593.mini_retail_app.R.drawable.app_icon_dynamic_color_enabled_48px
import com.thomas200593.mini_retail_app.R.drawable.app_icon_empty_24px
import com.thomas200593.mini_retail_app.R.drawable.app_icon_export_48px
import com.thomas200593.mini_retail_app.R.drawable.app_icon_google_g_48px
import com.thomas200593.mini_retail_app.R.drawable.app_icon_happy_48px
import com.thomas200593.mini_retail_app.R.drawable.app_icon_import_48px
import com.thomas200593.mini_retail_app.R.drawable.app_icon_language_48px
import com.thomas200593.mini_retail_app.R.drawable.app_icon_master_data_48px
import com.thomas200593.mini_retail_app.R.drawable.app_icon_neutral_48px
import com.thomas200593.mini_retail_app.R.drawable.app_icon_onboarding_48px
import com.thomas200593.mini_retail_app.R.drawable.app_icon_onboarding_hide_48px
import com.thomas200593.mini_retail_app.R.drawable.app_icon_onboarding_show_48px
import com.thomas200593.mini_retail_app.R.drawable.app_icon_reporting_48px
import com.thomas200593.mini_retail_app.R.drawable.app_icon_sad_48px
import com.thomas200593.mini_retail_app.R.drawable.app_icon_session_expired_48px
import com.thomas200593.mini_retail_app.R.drawable.app_icon_settings_48px
import com.thomas200593.mini_retail_app.R.drawable.app_icon_settings_general_48px
import com.thomas200593.mini_retail_app.R.drawable.app_icon_supplier_48px
import com.thomas200593.mini_retail_app.R.drawable.app_icon_text_lg_48px
import com.thomas200593.mini_retail_app.R.drawable.app_icon_text_md_48px
import com.thomas200593.mini_retail_app.R.drawable.app_icon_text_size_48px
import com.thomas200593.mini_retail_app.R.drawable.app_icon_text_sm_48px
import com.thomas200593.mini_retail_app.R.drawable.app_icon_text_xl_48px
import com.thomas200593.mini_retail_app.R.drawable.app_icon_theme_48px
import com.thomas200593.mini_retail_app.R.drawable.app_icon_theme_dark_48px
import com.thomas200593.mini_retail_app.R.drawable.app_icon_theme_light_48px
import com.thomas200593.mini_retail_app.R.drawable.app_icon_theme_system_48px
import com.thomas200593.mini_retail_app.R.drawable.app_icon_timezone_48px
import com.thomas200593.mini_retail_app.R.drawable.app_icon_user_profile_48px

object CustomIcons {
    object App{
        @DrawableRes val app = +app_icon_48x48px
    }
    object Auth{
        @DrawableRes val session_expire = +app_icon_session_expired_48px
    }
    object Business{
        @DrawableRes val business_profile = +app_icon_business_profile_48px
    }
    object Content{
        @DrawableRes val empty = +app_icon_empty_24px
    }
    object Country{
        @DrawableRes val country = +app_icon_country_48px
    }
    object Currency {
        @DrawableRes val currency = +app_icon_currency_48px
    }
    object Customer {
        @DrawableRes val customer = +app_icon_customer_48px
    }
    object Data {
        @DrawableRes val backup = +app_icon_export_48px
        @DrawableRes val restore = +app_icon_import_48px
        @DrawableRes val master_data = +app_icon_master_data_48px
    }
    object DynamicColor {
        @DrawableRes val dynamic_color = +app_icon_dynamic_color_48px
        @DrawableRes val disabled = +app_icon_dynamic_color_disabled_48px
        @DrawableRes val enabled = +app_icon_dynamic_color_enabled_48px
    }
    object Emotion {
        @DrawableRes val happy = +app_icon_happy_48px
        @DrawableRes val sad = +app_icon_sad_48px
        @DrawableRes val neutral = +app_icon_neutral_48px
    }
    object Font {
        @DrawableRes val font = +app_icon_text_size_48px
        @DrawableRes val small = +app_icon_text_sm_48px
        @DrawableRes val medium = +app_icon_text_md_48px
        @DrawableRes val large = +app_icon_text_lg_48px
        @DrawableRes val x_large = +app_icon_text_xl_48px
    }
    object Google {
        @DrawableRes val google_logo = +app_icon_google_g_48px
    }
    object Language {
        @DrawableRes val language = +app_icon_language_48px
    }
    object Onboarding {
        @DrawableRes val onboarding = +app_icon_onboarding_48px
        @DrawableRes val show = +app_icon_onboarding_show_48px
        @DrawableRes val hide = +app_icon_onboarding_hide_48px
    }
    object Setting{
        @DrawableRes val settings = +app_icon_settings_48px
        @DrawableRes val settings_general = +app_icon_settings_general_48px
        @DrawableRes val settings_data = +app_icon_data_48px
    }
    object Supplier{
        @DrawableRes val supplier = +app_icon_supplier_48px
    }
    object Theme{
        @DrawableRes val theme = +app_icon_theme_48px
        @DrawableRes val system = +app_icon_theme_system_48px
        @DrawableRes val light = +app_icon_theme_light_48px
        @DrawableRes val dark = +app_icon_theme_dark_48px
    }
    object Timezone{
        @DrawableRes val timezone = +app_icon_timezone_48px
    }
    object TopLevelDestinations{
        @DrawableRes val dashboard = +app_icon_dashboard_48px
        @DrawableRes val business = +app_icon_business_48px
        @DrawableRes val reporting = +app_icon_reporting_48px
        @DrawableRes val user_profile = +app_icon_user_profile_48px
    }
}
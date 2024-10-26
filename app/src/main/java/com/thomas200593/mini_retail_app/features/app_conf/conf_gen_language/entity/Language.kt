package com.thomas200593.mini_retail_app.features.app_conf.conf_gen_language.entity

import androidx.annotation.StringRes
import com.thomas200593.mini_retail_app.R.string.str_lang_en
import com.thomas200593.mini_retail_app.R.string.str_lang_in
import com.thomas200593.mini_retail_app.core.design_system.util.HlpCountry
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_country.entity.Country

enum class Language(
    val code: String,
    @StringRes val title: Int,
    val country: Country
) {
    EN(
        code = "en",
        title = str_lang_en,
        country = HlpCountry.COUNTRY_DEFAULT
    ),
    ID(
        code = "in",
        title = str_lang_in,
        country = HlpCountry.COUNTRY_INDONESIA
    )
}
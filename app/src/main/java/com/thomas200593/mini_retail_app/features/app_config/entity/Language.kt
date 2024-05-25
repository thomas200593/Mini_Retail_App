package com.thomas200593.mini_retail_app.features.app_config.entity

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.thomas200593.mini_retail_app.R
import com.thomas200593.mini_retail_app.core.ui.common.AppIcon.Language.en
import com.thomas200593.mini_retail_app.core.ui.common.AppIcon.Language.id

enum class Language(
    val langCode: String,
    @StringRes val langName: Int,
    @DrawableRes val langIcon: Int,
) {
    EN(
        langCode = "en",
        langName = R.string.str_lang_en,
        langIcon = en
    ),
    ID(
        langCode = "in",
        langName = R.string.str_lang_in,
        langIcon = id
    )
}
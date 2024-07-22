package com.thomas200593.mini_retail_app.features.app_conf._g_language.entity

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.thomas200593.mini_retail_app.R
import com.thomas200593.mini_retail_app.core.ui.common.CustomIcons

enum class Language(
    val code: String,
    @DrawableRes val iconRes: Int,
    @StringRes val title: Int,
) {
    EN(
        code = "en",
        iconRes = CustomIcons.Language.en,
        title = R.string.str_lang_en
    ),
    ID(
        code = "in",
        iconRes = CustomIcons.Language.id,
        title = R.string.str_lang_in
    )
}
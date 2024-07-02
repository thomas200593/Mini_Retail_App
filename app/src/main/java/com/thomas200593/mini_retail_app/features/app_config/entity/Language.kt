package com.thomas200593.mini_retail_app.features.app_config.entity

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.thomas200593.mini_retail_app.R
import com.thomas200593.mini_retail_app.core.ui.common.Icons

enum class Language(
    val code: String,
    @DrawableRes val iconRes: Int,
    @StringRes val title: Int,
) {
    EN(
        code = "en",
        iconRes = Icons.Language.en,
        title = R.string.str_lang_en
    ),
    ID(
        code = "in",
        iconRes = Icons.Language.id,
        title = R.string.str_lang_in
    )
}
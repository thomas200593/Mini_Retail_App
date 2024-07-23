package com.thomas200593.mini_retail_app.features.app_conf._gen_language.entity

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.thomas200593.mini_retail_app.R.string.str_lang_en
import com.thomas200593.mini_retail_app.R.string.str_lang_in
import com.thomas200593.mini_retail_app.core.ui.common.CustomIcons.Language.en
import com.thomas200593.mini_retail_app.core.ui.common.CustomIcons.Language.id

enum class Language(
    val code: String,
    @DrawableRes val iconRes: Int,
    @StringRes val title: Int,
) {
    EN(
        code = "en",
        iconRes = en,
        title = str_lang_en
    ),
    ID(
        code = "in",
        iconRes = id,
        title = str_lang_in
    )
}
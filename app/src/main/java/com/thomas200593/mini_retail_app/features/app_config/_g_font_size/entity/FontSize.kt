package com.thomas200593.mini_retail_app.features.app_config._g_font_size.entity

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.thomas200593.mini_retail_app.R
import com.thomas200593.mini_retail_app.core.ui.common.Icons

enum class FontSize(
    val code: String,
    val sizeFactor: Int,
    @StringRes val title: Int,
    @DrawableRes val iconRes: Int
) {
    SMALL(
        code = "font_size_small",
        sizeFactor = -2,
        title = R.string.str_size_small,
        iconRes = Icons.Font.small
    ),
    MEDIUM(
        code = "font_size_medium",
        sizeFactor = 0,
        title = R.string.str_size_medium,
        iconRes = Icons.Font.medium
    ),
    LARGE(
        code = "font_size_large",
        sizeFactor = 2,
        title = R.string.str_size_large,
        iconRes = Icons.Font.large
    ),
    EXTRA_LARGE(
        code = "font_size_x_large",
        sizeFactor = 4,
        title = R.string.str_size_extra_large,
        iconRes = Icons.Font.x_large
    )
}
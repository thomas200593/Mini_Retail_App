package com.thomas200593.mini_retail_app.features.app_config.entity

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.thomas200593.mini_retail_app.R.string.str_size_extra_large
import com.thomas200593.mini_retail_app.R.string.str_size_large
import com.thomas200593.mini_retail_app.R.string.str_size_medium
import com.thomas200593.mini_retail_app.R.string.str_size_small
import com.thomas200593.mini_retail_app.core.ui.common.Icons.Font.large
import com.thomas200593.mini_retail_app.core.ui.common.Icons.Font.medium
import com.thomas200593.mini_retail_app.core.ui.common.Icons.Font.small
import com.thomas200593.mini_retail_app.core.ui.common.Icons.Font.x_large

enum class FontSize(
    val code: String,
    val sizeFactor: Int,
    @StringRes val title: Int,
    @DrawableRes val iconRes: Int
) {
    SMALL(
        code = "font_size_small",
        sizeFactor = -2,
        title = str_size_small,
        iconRes = small
    ),
    MEDIUM(
        code = "font_size_medium",
        sizeFactor = 0,
        title = str_size_medium,
        iconRes = medium
    ),
    LARGE(
        code = "font_size_large",
        sizeFactor = 2,
        title = str_size_large,
        iconRes = large
    ),
    EXTRA_LARGE(
        code = "font_size_x_large",
        sizeFactor = 4,
        title = str_size_extra_large,
        iconRes = x_large
    )
}
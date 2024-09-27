package com.thomas200593.mini_retail_app.core.ui.component.form.state

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

sealed class UiText {
    data class DynamicString(val value: String): UiText()
    class StringResource(@StringRes val resId: Int, vararg val args: Any): UiText()

    @Composable
    fun asString(): String = when(this) {
        is DynamicString -> value
        is StringResource -> stringResource(id = resId, *args)
    }

    fun asString(context: Context): String = when(this) {
        is DynamicString -> value
        is StringResource -> context.getString(resId, *args)
    }
}
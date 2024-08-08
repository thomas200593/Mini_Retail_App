package com.thomas200593.mini_retail_app.core.design_system.util

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.graphics.Color.Companion.Yellow
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle

object HlpString {
    fun String.highlightText(query: String, highlightColor: Color = Yellow): AnnotatedString {
        val text = this
        return buildAnnotatedString {
            val startIndex = text.indexOf(query, ignoreCase = true)
            if (startIndex >= 0)
            {
                val endIndex = startIndex + query.length; append(text.substring(0, startIndex))
                withStyle(style = SpanStyle(color = highlightColor, background = Transparent))
                { append(text.substring(startIndex, endIndex)) }
                append(text.substring(endIndex))
            }
            else { append(text) }
        }
    }
}
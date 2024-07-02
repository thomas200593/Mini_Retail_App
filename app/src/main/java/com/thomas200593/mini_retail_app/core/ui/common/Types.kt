package com.thomas200593.mini_retail_app.core.ui.common

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.thomas200593.mini_retail_app.features.app_config.entity.FontSize

object Types{
    private const val LINE_HEIGHT_MULTIPLIER = 1.15
    fun personalizedTypography(fontSize: FontSize): Typography {
        return Typography(
            bodyLarge = TextStyle(
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.Normal,
                fontSize = (16 + fontSize.sizeFactor).sp,
                lineHeight = ((16 + fontSize.sizeFactor) * LINE_HEIGHT_MULTIPLIER).sp,
                letterSpacing = 0.5.sp
            ),
            titleLarge = TextStyle(
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.Bold,
                fontSize = (22 + fontSize.sizeFactor).sp,
                lineHeight = ((22 + fontSize.sizeFactor) * LINE_HEIGHT_MULTIPLIER).sp,
                letterSpacing = 0.sp
            ),
            titleMedium = TextStyle(
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.SemiBold,
                fontSize = (18 + fontSize.sizeFactor).sp,
                lineHeight = ((18 + fontSize.sizeFactor) * LINE_HEIGHT_MULTIPLIER).sp,
                letterSpacing = 0.sp
            ),
            labelSmall = TextStyle(
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.Medium,
                fontSize = (11 + fontSize.sizeFactor).sp,
                lineHeight = ((11 + fontSize.sizeFactor) * LINE_HEIGHT_MULTIPLIER).sp,
                letterSpacing = 0.5.sp
            ),
        )
    }
}
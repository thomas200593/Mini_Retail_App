package com.thomas200593.mini_retail_app.core.ui.common
import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily.Companion.Default
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.font.FontWeight.Companion.Medium
import androidx.compose.ui.text.font.FontWeight.Companion.Normal
import androidx.compose.ui.text.font.FontWeight.Companion.SemiBold
import androidx.compose.ui.unit.sp
import com.thomas200593.mini_retail_app.features.app_config.entity.FontSize
import timber.log.Timber

private val TAG = Types::class.simpleName

object Types{

    private const val LINE_HEIGHT_MULTIPLIER = 1.15

    fun personalizedTypography(fontSize: FontSize): Typography {
        Timber.d("Called : fun $TAG.personalizedTypography()")
        return Typography(
            bodyLarge = TextStyle(
                fontFamily = Default,
                fontWeight = Normal,
                fontSize = (16 + fontSize.sizeFactor).sp,
                lineHeight = ((16 + fontSize.sizeFactor) * LINE_HEIGHT_MULTIPLIER).sp,
                letterSpacing = 0.5.sp
            ),
            titleLarge = TextStyle(
                fontFamily = Default,
                fontWeight = Bold,
                fontSize = (22 + fontSize.sizeFactor).sp,
                lineHeight = ((22 + fontSize.sizeFactor) * LINE_HEIGHT_MULTIPLIER).sp,
                letterSpacing = 0.sp
            ),
            titleMedium = TextStyle(
                fontFamily = Default,
                fontWeight = SemiBold,
                fontSize = (18 + fontSize.sizeFactor).sp,
                lineHeight = ((18 + fontSize.sizeFactor) * LINE_HEIGHT_MULTIPLIER).sp,
                letterSpacing = 0.sp
            ),
            labelSmall = TextStyle(
                fontFamily = Default,
                fontWeight = Medium,
                fontSize = (11 + fontSize.sizeFactor).sp,
                lineHeight = ((11 + fontSize.sizeFactor) * LINE_HEIGHT_MULTIPLIER).sp,
                letterSpacing = 0.5.sp
            ),
        )
    }
}

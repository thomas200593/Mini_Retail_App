package com.thomas200593.mini_retail_app.core.ui.common
import android.graphics.Color.argb
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

object CustomColors {
    private val primaryLight = Color(0xFF00696A)
    private val onPrimaryLight = Color(0xFFFFFFFF)
    private val primaryContainerLight = Color(0xFF9CF1F1)
    private val onPrimaryContainerLight = Color(0xFF002020)
    private val secondaryLight = Color(0xFF4A6363)
    private val onSecondaryLight = Color(0xFFFFFFFF)
    private val secondaryContainerLight = Color(0xFFCCE8E7)
    private val onSecondaryContainerLight = Color(0xFF041F20)
    private val tertiaryLight = Color(0xFF4C607C)
    private val onTertiaryLight = Color(0xFFFFFFFF)
    private val tertiaryContainerLight = Color(0xFFD3E3FF)
    private val onTertiaryContainerLight = Color(0xFF051C35)
    private val errorLight = Color(0xFFBA1A1A)
    private val onErrorLight = Color(0xFFFFFFFF)
    private val errorContainerLight = Color(0xFFFFDAD6)
    private val onErrorContainerLight = Color(0xFF410002)
    private val backgroundLight = Color(0xFFF4FBFA)
    private val onBackgroundLight = Color(0xFF161D1D)
    private val surfaceLight = Color(0xFFF4FBFA)
    private val onSurfaceLight = Color(0xFF161D1D)
    private val surfaceVariantLight = Color(0xFFDAE5E4)
    private val onSurfaceVariantLight = Color(0xFF3F4948)
    private val outlineLight = Color(0xFF6F7979)
    private val outlineVariantLight = Color(0xFFBEC8C8)
    private val scrimLight = Color(0xFF000000)
    private val inverseSurfaceLight = Color(0xFF2B3231)
    private val inverseOnSurfaceLight = Color(0xFFECF2F1)
    private val inversePrimaryLight = Color(0xFF80D4D5)
    private val surfaceDimLight = Color(0xFFD5DBDB)
    private val surfaceBrightLight = Color(0xFFF4FBFA)
    private val surfaceContainerLowestLight = Color(0xFFFFFFFF)
    private val surfaceContainerLowLight = Color(0xFFEFF5F4)
    private val surfaceContainerLight = Color(0xFFE9EFEE)
    private val surfaceContainerHighLight = Color(0xFFE3E9E9)
    private val surfaceContainerHighestLight = Color(0xFFDDE4E3)

    private val primaryDark = Color(0xFF80D4D5)
    private val onPrimaryDark = Color(0xFF003737)
    private val primaryContainerDark = Color(0xFF004F50)
    private val onPrimaryContainerDark = Color(0xFF9CF1F1)
    private val secondaryDark = Color(0xFFB0CCCB)
    private val onSecondaryDark = Color(0xFF1B3435)
    private val secondaryContainerDark = Color(0xFF324B4B)
    private val onSecondaryContainerDark = Color(0xFFCCE8E7)
    private val tertiaryDark = Color(0xFFB3C8E9)
    private val onTertiaryDark = Color(0xFF1D314B)
    private val tertiaryContainerDark = Color(0xFF344863)
    private val onTertiaryContainerDark = Color(0xFFD3E3FF)
    private val errorDark = Color(0xFFFFB4AB)
    private val onErrorDark = Color(0xFF690005)
    private val errorContainerDark = Color(0xFF93000A)
    private val onErrorContainerDark = Color(0xFFFFDAD6)
    private val backgroundDark = Color(0xFF0E1514)
    private val onBackgroundDark = Color(0xFFDDE4E3)
    private val surfaceDark = Color(0xFF0E1514)
    private val onSurfaceDark = Color(0xFFDDE4E3)
    private val surfaceVariantDark = Color(0xFF3F4948)
    private val onSurfaceVariantDark = Color(0xFFBEC8C8)
    private val outlineDark = Color(0xFF889392)
    private val outlineVariantDark = Color(0xFF3F4948)
    private val scrimDark = Color(0xFF000000)
    private val inverseSurfaceDark = Color(0xFFDDE4E3)
    private val inverseOnSurfaceDark = Color(0xFF2B3231)
    private val inversePrimaryDark = Color(0xFF00696A)
    private val surfaceDimDark = Color(0xFF0E1514)
    private val surfaceBrightDark = Color(0xFF343A3A)
    private val surfaceContainerLowestDark = Color(0xFF090F0F)
    private val surfaceContainerLowDark = Color(0xFF161D1D)
    private val surfaceContainerDark = Color(0xFF1A2121)
    private val surfaceContainerHighDark = Color(0xFF252B2B)
    private val surfaceContainerHighestDark = Color(0xFF2F3636)

    /**
     * The default light scrim, as defined by androidx and the platform:
     * https://cs.android.com/androidx/platform/frameworks/support/+/androidx-main:activity/activity/src/main/java/androidx/activity/EdgeToEdge.kt;l=35-38;drc=27e7d52e8604a080133e8b842db10c89b4482598
     */
    val lightScrim = argb(0xe6, 0xFF, 0xFF, 0xFF)

    /**
     * The default dark scrim, as defined by androidx and the platform:
     * https://cs.android.com/androidx/platform/frameworks/support/+/androidx-main:activity/activity/src/main/java/androidx/activity/EdgeToEdge.kt;l=40-44;drc=27e7d52e8604a080133e8b842db10c89b4482598
     */
    val darkScrim = argb(0x80, 0x1b, 0x1b, 0x1b)

    val lightScheme = lightColorScheme(
        primary = primaryLight,
        onPrimary = onPrimaryLight,
        primaryContainer = primaryContainerLight,
        onPrimaryContainer = onPrimaryContainerLight,
        secondary = secondaryLight,
        onSecondary = onSecondaryLight,
        secondaryContainer = secondaryContainerLight,
        onSecondaryContainer = onSecondaryContainerLight,
        tertiary = tertiaryLight,
        onTertiary = onTertiaryLight,
        tertiaryContainer = tertiaryContainerLight,
        onTertiaryContainer = onTertiaryContainerLight,
        error = errorLight,
        onError = onErrorLight,
        errorContainer = errorContainerLight,
        onErrorContainer = onErrorContainerLight,
        background = backgroundLight,
        onBackground = onBackgroundLight,
        surface = surfaceLight,
        onSurface = onSurfaceLight,
        surfaceVariant = surfaceVariantLight,
        onSurfaceVariant = onSurfaceVariantLight,
        outline = outlineLight,
        outlineVariant = outlineVariantLight,
        scrim = scrimLight,
        inverseSurface = inverseSurfaceLight,
        inverseOnSurface = inverseOnSurfaceLight,
        inversePrimary = inversePrimaryLight,
        surfaceDim = surfaceDimLight,
        surfaceBright = surfaceBrightLight,
        surfaceContainerLowest = surfaceContainerLowestLight,
        surfaceContainerLow = surfaceContainerLowLight,
        surfaceContainer = surfaceContainerLight,
        surfaceContainerHigh = surfaceContainerHighLight,
        surfaceContainerHighest = surfaceContainerHighestLight,
    )

    val darkScheme = darkColorScheme(
        primary = primaryDark,
        onPrimary = onPrimaryDark,
        primaryContainer = primaryContainerDark,
        onPrimaryContainer = onPrimaryContainerDark,
        secondary = secondaryDark,
        onSecondary = onSecondaryDark,
        secondaryContainer = secondaryContainerDark,
        onSecondaryContainer = onSecondaryContainerDark,
        tertiary = tertiaryDark,
        onTertiary = onTertiaryDark,
        tertiaryContainer = tertiaryContainerDark,
        onTertiaryContainer = onTertiaryContainerDark,
        error = errorDark,
        onError = onErrorDark,
        errorContainer = errorContainerDark,
        onErrorContainer = onErrorContainerDark,
        background = backgroundDark,
        onBackground = onBackgroundDark,
        surface = surfaceDark,
        onSurface = onSurfaceDark,
        surfaceVariant = surfaceVariantDark,
        onSurfaceVariant = onSurfaceVariantDark,
        outline = outlineDark,
        outlineVariant = outlineVariantDark,
        scrim = scrimDark,
        inverseSurface = inverseSurfaceDark,
        inverseOnSurface = inverseOnSurfaceDark,
        inversePrimary = inversePrimaryDark,
        surfaceDim = surfaceDimDark,
        surfaceBright = surfaceBrightDark,
        surfaceContainerLowest = surfaceContainerLowestDark,
        surfaceContainerLow = surfaceContainerLowDark,
        surfaceContainer = surfaceContainerDark,
        surfaceContainerHigh = surfaceContainerHighDark,
        surfaceContainerHighest = surfaceContainerHighestDark,
    )
}
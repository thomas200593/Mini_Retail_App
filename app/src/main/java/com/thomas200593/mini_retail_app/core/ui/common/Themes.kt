package com.thomas200593.mini_retail_app.core.ui.common

import android.app.Activity
import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.S
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.thomas200593.mini_retail_app.app.UiStateMain
import com.thomas200593.mini_retail_app.features.app_config._g_dynamic_color.entity.DynamicColor
import com.thomas200593.mini_retail_app.features.app_config._g_font_size.entity.FontSize
import com.thomas200593.mini_retail_app.features.app_config._g_theme.entity.Theme

object Themes{
    private val lightScheme = Colors.lightScheme
    private val darkScheme = Colors.darkScheme
    private val shapes = Shapes()

    /**
     * Returns `true` if dark theme should be used, as a function of the [uiState] and the
     * current system context.
     */
    @Composable
    fun shouldUseDarkTheme(uiState: UiStateMain): Boolean {
        return when(uiState){
            UiStateMain.Loading -> isSystemInDarkTheme()
            is UiStateMain.Success -> when(uiState.configCurrent.theme){
                Theme.SYSTEM -> isSystemInDarkTheme()
                Theme.LIGHT -> false
                Theme.DARK -> true
            }
        }
    }

    /**
     * Returns `true` if the dynamic color is enabled, as a function of the [uiState].
     */
    @Composable
    fun shouldUseDynamicColor(uiState: UiStateMain): Boolean {
        return when (uiState) {
            UiStateMain.Loading -> false
            is UiStateMain.Success -> when(uiState.configCurrent.dynamicColor){
                DynamicColor.ENABLED -> true
                DynamicColor.DISABLED -> false
            }
        }
    }

    @Composable
    fun calculateInitialFontSize(uiState: UiStateMain): FontSize {
        return when(uiState) {
            UiStateMain.Loading -> FontSize.MEDIUM
            is UiStateMain.Success -> when(uiState.configCurrent.fontSize){
                FontSize.MEDIUM -> FontSize.MEDIUM
                FontSize.SMALL -> FontSize.SMALL
                FontSize.LARGE -> FontSize.LARGE
                FontSize.EXTRA_LARGE -> FontSize.EXTRA_LARGE
            }
        }
    }

    @Composable
    fun ApplicationTheme(
        darkTheme: Boolean = isSystemInDarkTheme(),
        // Dynamic color is available on Android 12+
        dynamicColor: Boolean = false,
        fontSize: FontSize = FontSize.MEDIUM,
        content: @Composable () -> Unit
    ) {
        val colorScheme = when {
            dynamicColor && SDK_INT >= S -> {
                val context = LocalContext.current
                if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
            }
            darkTheme -> darkScheme
            else -> lightScheme
        }
        val view = LocalView.current
        if (!view.isInEditMode) {
            SideEffect {
                val window = (view.context as Activity).window
                window.statusBarColor = colorScheme.primary.toArgb()
                WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
            }
        }

        MaterialTheme(
            colorScheme = colorScheme,
            typography = Types.personalizedTypography(fontSize),
            shapes = shapes,
            content = content
        )
    }
}
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
import androidx.core.view.WindowCompat.getInsetsController
import com.thomas200593.mini_retail_app.app.UiStateMain
import com.thomas200593.mini_retail_app.app.UiStateMain.Loading
import com.thomas200593.mini_retail_app.app.UiStateMain.Success
import com.thomas200593.mini_retail_app.core.ui.common.CustomTypes.personalizedTypography
import com.thomas200593.mini_retail_app.features.app_conf._gen_dynamic_color.entity.DynamicColor.DISABLED
import com.thomas200593.mini_retail_app.features.app_conf._gen_dynamic_color.entity.DynamicColor.ENABLED
import com.thomas200593.mini_retail_app.features.app_conf._gen_font_size.entity.FontSize
import com.thomas200593.mini_retail_app.features.app_conf._gen_font_size.entity.FontSize.EXTRA_LARGE
import com.thomas200593.mini_retail_app.features.app_conf._gen_font_size.entity.FontSize.LARGE
import com.thomas200593.mini_retail_app.features.app_conf._gen_font_size.entity.FontSize.MEDIUM
import com.thomas200593.mini_retail_app.features.app_conf._gen_font_size.entity.FontSize.SMALL
import com.thomas200593.mini_retail_app.features.app_conf._g_theme.entity.Theme.DARK
import com.thomas200593.mini_retail_app.features.app_conf._g_theme.entity.Theme.LIGHT
import com.thomas200593.mini_retail_app.features.app_conf._g_theme.entity.Theme.SYSTEM

object CustomThemes{
    private val lightScheme = CustomColors.lightScheme
    private val darkScheme = CustomColors.darkScheme
    private val shapes = Shapes()

    /**
     * Returns `true` if dark theme should be used, as a function of the [uiState] and the
     * current system context.
     */
    @Composable
    fun shouldUseDarkTheme(uiState: UiStateMain): Boolean =
        when(uiState){
            Loading -> isSystemInDarkTheme()
            is Success -> when(uiState.confCurrent.theme){
                SYSTEM -> isSystemInDarkTheme()
                LIGHT -> false
                DARK -> true
            }
        }

    /**
     * Returns `true` if the dynamic color is enabled, as a function of the [uiState].
     */
    @Composable
    fun shouldUseDynamicColor(uiState: UiStateMain): Boolean {
        return when (uiState) {
            Loading -> false
            is Success -> when(uiState.confCurrent.dynamicColor){
                ENABLED -> true
                DISABLED -> false
            }
        }
    }

    @Composable
    fun calculateInitialFontSize(uiState: UiStateMain): FontSize {
        return when(uiState) {
            Loading -> MEDIUM
            is Success -> when(uiState.confCurrent.fontSize){
                MEDIUM -> MEDIUM
                SMALL -> SMALL
                LARGE -> LARGE
                EXTRA_LARGE -> EXTRA_LARGE
            }
        }
    }

    @Composable
    fun ApplicationTheme(
        darkTheme: Boolean = isSystemInDarkTheme(),
        // Dynamic color is available on Android 12+
        dynamicColor: Boolean = false,
        fontSize: FontSize = MEDIUM,
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
                getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
            }
        }

        MaterialTheme(
            colorScheme = colorScheme,
            typography = personalizedTypography(fontSize),
            shapes = shapes,
            content = content
        )
    }
}
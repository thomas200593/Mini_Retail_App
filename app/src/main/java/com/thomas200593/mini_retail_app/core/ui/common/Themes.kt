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
import com.thomas200593.mini_retail_app.core.ui.common.Types.personalizedTypography
import com.thomas200593.mini_retail_app.features.app_config.entity.DynamicColor
import com.thomas200593.mini_retail_app.features.app_config.entity.Font
import com.thomas200593.mini_retail_app.features.app_config.entity.Font.MEDIUM
import com.thomas200593.mini_retail_app.features.app_config.entity.Theme
import com.thomas200593.mini_retail_app.main_app.MainActivityUiState

object Themes{
    private val lightScheme = Colors.lightScheme
    private val darkScheme = Colors.darkScheme
    private val shapes = Shapes()

    /**
     * Returns `true` if dark theme should be used, as a function of the [uiState] and the
     * current system context.
     */
    @Composable
    fun shouldUseDarkTheme(uiState: MainActivityUiState): Boolean =
        when(uiState){
            MainActivityUiState.Loading -> isSystemInDarkTheme()
            is MainActivityUiState.Success -> when(uiState.currentAppConfig.currentTheme){
                Theme.SYSTEM -> isSystemInDarkTheme()
                Theme.LIGHT -> false
                Theme.DARK -> true
            }
        }

    /**
     * Returns `true` if the dynamic color is enabled, as a function of the [uiState].
     */
    @Composable
    fun shouldUseDynamicColor(uiState: MainActivityUiState): Boolean =
        when (uiState) {
            MainActivityUiState.Loading -> false
            is MainActivityUiState.Success -> when(uiState.currentAppConfig.currentDynamicColor){
                DynamicColor.ENABLED -> true
                DynamicColor.DISABLED -> false
            }
        }

    @Composable
    fun calculateInitialFontSize(uiState: MainActivityUiState): Font =
        when(uiState) {
            MainActivityUiState.Loading -> MEDIUM
            is MainActivityUiState.Success -> when(uiState.currentAppConfig.currentFontSize){
                MEDIUM -> MEDIUM
                Font.SMALL -> Font.SMALL
                Font.LARGE -> Font.LARGE
                Font.EXTRA_LARGE -> Font.EXTRA_LARGE
            }
        }

    @Composable
    fun ApplicationTheme(
        darkTheme: Boolean = isSystemInDarkTheme(),
        // Dynamic color is available on Android 12+
        dynamicColor: Boolean = false,
        font: Font = MEDIUM,
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
            typography = personalizedTypography(font),
            shapes = shapes,
            content = content
        )
    }
}

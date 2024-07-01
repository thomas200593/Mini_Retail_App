package com.thomas200593.mini_retail_app.app.ui

import androidx.compose.runtime.staticCompositionLocalOf
import timber.log.Timber

val LocalAppState = staticCompositionLocalOf<AppState> {
    Timber.e(Throwable(message = "Error, No App State Provided"))
    error(message = "No AppState Class Provided!")
}
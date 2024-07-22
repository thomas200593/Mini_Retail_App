package com.thomas200593.mini_retail_app.app.ui

import androidx.compose.runtime.staticCompositionLocalOf
import timber.log.Timber

val LocalStateApp = staticCompositionLocalOf<StateApp> {
    Timber.e(Throwable(message = "Error, No App State Provided"))
    error(message = "No StateApp Class Provided!")
}
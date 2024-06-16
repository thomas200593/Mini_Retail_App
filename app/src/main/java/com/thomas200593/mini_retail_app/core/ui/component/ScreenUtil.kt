package com.thomas200593.mini_retail_app.core.ui.component

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalContext

object ScreenUtil {
    @Composable
    fun LockScreenOrientation(orientation: Int){
        val context = LocalContext.current
        DisposableEffect(key1 = orientation) {
            val activity = context.findActivity() ?: return@DisposableEffect onDispose {}
            val originalOrientation = activity.requestedOrientation
            activity.requestedOrientation = orientation
            onDispose {
                activity.requestedOrientation = originalOrientation
            }
        }
    }

    private tailrec fun Context.findActivity(): Activity? = when(this){
        is Activity -> this
        is ContextWrapper -> baseContext.findActivity()
        else -> null
    }
}
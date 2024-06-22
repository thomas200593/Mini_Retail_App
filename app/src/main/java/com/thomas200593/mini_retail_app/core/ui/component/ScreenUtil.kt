package com.thomas200593.mini_retail_app.core.ui.component

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalContext
import timber.log.Timber

private val TAG = ScreenUtil::class.simpleName

object ScreenUtil {
    @Composable
    fun LockScreenOrientation(orientation: Int){
        Timber.d("Called : fun $TAG.LockScreenOrientation()")
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

    private tailrec fun Context.findActivity(): Activity? {
        Timber.d("Called : fun $TAG.Context.findActivity()")
        return when(this){
            is Activity -> this
            is ContextWrapper -> baseContext.findActivity()
            else -> null
        }
    }
}
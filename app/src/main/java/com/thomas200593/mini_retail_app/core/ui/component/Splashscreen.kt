package com.thomas200593.mini_retail_app.core.ui.component

import android.animation.ObjectAnimator.ofFloat
import android.view.View.TRANSLATION_X
import android.view.animation.DecelerateInterpolator
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen
import timber.log.Timber

private val TAG = Splashscreen::class.simpleName

object Splashscreen {
    fun setupSplashscreen(splashscreen: SplashScreen) {
        Timber.d("Called : fun $TAG.setupSplashscreen()")
        splashscreen.setOnExitAnimationListener { splashScreenView ->
            val slideBack = ofFloat(
                splashScreenView.view,
                TRANSLATION_X,
                0f,
                -splashScreenView.view.width.toFloat()
            ).apply {
                interpolator = DecelerateInterpolator()
                duration = 400L
                doOnEnd { splashScreenView.remove() }
            }
            slideBack.start()
        }
    }
}
package com.thomas200593.mini_retail_app.core.ui.component

import android.animation.ObjectAnimator.ofFloat
import android.view.View.TRANSLATION_X
import android.view.animation.DecelerateInterpolator
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen

object AppSplashscreen {
    fun setupSplashscreen(splashscreen: SplashScreen) {
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
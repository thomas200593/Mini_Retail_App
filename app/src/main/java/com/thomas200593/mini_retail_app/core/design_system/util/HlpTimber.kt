package com.thomas200593.mini_retail_app.core.design_system.util

import com.thomas200593.mini_retail_app.BuildConfig
import timber.log.Timber

object HlpTimber {
    fun initDebugTree(){ if (Timber.treeCount == 0 && BuildConfig.DEBUG){ Timber.plant(tree = Timber.DebugTree()) } }
}
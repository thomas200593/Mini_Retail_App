package com.thomas200593.mini_retail_app.core.util

import com.thomas200593.mini_retail_app.BuildConfig
import timber.log.Timber

private const val TAG = "TimberHelper"

object TimberHelper {
    fun initializeDebugTree(){
        if (Timber.treeCount == 0 && BuildConfig.DEBUG){
            Timber.plant(Timber.DebugTree())
        }
        Timber.d("Called : %s.initializeDebugTree()", TAG)
    }
}
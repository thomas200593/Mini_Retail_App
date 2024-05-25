package com.thomas200593.mini_retail_app.core.util

import com.thomas200593.mini_retail_app.BuildConfig
import timber.log.Timber

private const val TAG = "TimberLog"

object TimberLog {
    fun initializeDebugTree(){
        if (Timber.treeCount == 0 && BuildConfig.DEBUG){
            Timber.plant(Timber.DebugTree())
        }
        Timber.d("Called : %s.initializeDebugTree()", TAG)
    }
}
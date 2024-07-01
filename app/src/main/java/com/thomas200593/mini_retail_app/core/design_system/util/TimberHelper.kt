package com.thomas200593.mini_retail_app.core.design_system.util

import com.thomas200593.mini_retail_app.BuildConfig
import timber.log.Timber

private val TAG = TimberHelper::class.simpleName

object TimberHelper {
    fun initializeDebugTree(){
        if (Timber.treeCount == 0 && BuildConfig.DEBUG){
            Timber.plant(tree = Timber.DebugTree())
        }
        Timber.d("Called : fun ${TAG}.initializeDebugTree()")
    }
}
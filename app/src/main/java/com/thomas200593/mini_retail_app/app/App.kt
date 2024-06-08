package com.thomas200593.mini_retail_app.app

import androidx.multidex.MultiDexApplication
import com.thomas200593.mini_retail_app.core.util.TimberHelper
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

private const val TAG = "App"
@HiltAndroidApp
class App: MultiDexApplication(){
    override fun onCreate() {
        super.onCreate()
        TimberHelper.initializeDebugTree()
        Timber.d("Called : %s.onCreate()", TAG)
    }
}
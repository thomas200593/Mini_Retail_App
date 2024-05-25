package com.thomas200593.mini_retail_app.main_app

import androidx.multidex.MultiDexApplication
import com.thomas200593.mini_retail_app.core.util.TimberLog
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

private const val TAG = "App"
@HiltAndroidApp
class App: MultiDexApplication(){
    override fun onCreate() {
        super.onCreate()
        TimberLog.initializeDebugTree()
        Timber.d("Called : %s.onCreate()", TAG)
    }
}
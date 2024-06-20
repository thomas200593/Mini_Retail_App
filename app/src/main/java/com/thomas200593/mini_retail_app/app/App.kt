package com.thomas200593.mini_retail_app.app

import androidx.multidex.MultiDexApplication
import androidx.work.Configuration
import com.thomas200593.mini_retail_app.core.util.TimberHelper
import com.thomas200593.mini_retail_app.work.factory.WorkWrapperFactory
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import javax.inject.Inject

private const val TAG = "App"
@HiltAndroidApp
class App: MultiDexApplication(), Configuration.Provider {

    @Inject lateinit var workWrapperFactory: WorkWrapperFactory

    override fun onCreate() {
        super.onCreate()
        TimberHelper.initializeDebugTree()
        Timber.d("Called : %s.onCreate()", TAG)
    }

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder().setWorkerFactory(workWrapperFactory).build()
}
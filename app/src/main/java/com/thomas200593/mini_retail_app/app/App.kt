package com.thomas200593.mini_retail_app.app

import androidx.multidex.MultiDexApplication
import androidx.work.Configuration
import com.thomas200593.mini_retail_app.core.design_system.util.HelperTimber
import com.thomas200593.mini_retail_app.work.factory.WorkWrapperFactory
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class App: MultiDexApplication(), Configuration.Provider {

    @Inject lateinit var workWrapperFactory: WorkWrapperFactory

    override fun onCreate() {
        super.onCreate()
        HelperTimber.initializeDebugTree()
    }

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder().setWorkerFactory(workerFactory = workWrapperFactory).build()
}
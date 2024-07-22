package com.thomas200593.mini_retail_app.app

import androidx.multidex.MultiDexApplication
import androidx.work.Configuration
import com.thomas200593.mini_retail_app.core.design_system.util.HlpTimber
import com.thomas200593.mini_retail_app.work.factory.FactoryWorkWrapper
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class App: MultiDexApplication(), Configuration.Provider {

    @Inject lateinit var facWorkWrapper: FactoryWorkWrapper

    override fun onCreate() {
        super.onCreate()
        HlpTimber.initDebugTree()
    }

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder().setWorkerFactory(workerFactory = facWorkWrapper).build()
}
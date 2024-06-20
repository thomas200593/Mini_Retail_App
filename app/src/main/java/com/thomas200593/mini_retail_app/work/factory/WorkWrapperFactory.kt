package com.thomas200593.mini_retail_app.work.factory

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import javax.inject.Inject
import javax.inject.Provider

class WorkWrapperFactory @Inject constructor(
    private val workerFactory:
    Map<Class<out ListenableWorker>, @JvmSuppressWildcards Provider<CustomWorkerFactory>>
) : WorkerFactory(){
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker {
        val entries = workerFactory.entries.find { Class.forName(workerClassName).isAssignableFrom(it.key) }
        val factoryProvider = entries?.value?: throw IllegalArgumentException("Unknown Worker Class: $workerClassName")
        return factoryProvider.get().create(appContext, workerParameters)
    }
}
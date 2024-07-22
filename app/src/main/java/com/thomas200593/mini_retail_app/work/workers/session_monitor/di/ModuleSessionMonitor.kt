package com.thomas200593.mini_retail_app.work.workers.session_monitor.di

import com.thomas200593.mini_retail_app.work.factory.di.WorkerKey
import com.thomas200593.mini_retail_app.work.factory.FactoryCustomWorker
import com.thomas200593.mini_retail_app.work.workers.session_monitor.factory.FactoryWorkerSessionMonitor
import com.thomas200593.mini_retail_app.work.workers.session_monitor.worker.WorkerSessionMonitor
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoMap

@InstallIn(SingletonComponent::class)
@Module
abstract class ModuleSessionMonitor{
    @Binds
    @IntoMap
    @WorkerKey(WorkerSessionMonitor::class)
    abstract fun binsImplementation(
        factory: FactoryWorkerSessionMonitor
    ): FactoryCustomWorker
}
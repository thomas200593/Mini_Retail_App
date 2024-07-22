package com.thomas200593.mini_retail_app.work.workers.session_monitor.di

import com.thomas200593.mini_retail_app.work.factory.di.WorkerKey
import com.thomas200593.mini_retail_app.work.factory.CustomWorkerFactory
import com.thomas200593.mini_retail_app.work.workers.session_monitor.factory.SessionMonitorWorkerFactory
import com.thomas200593.mini_retail_app.work.workers.session_monitor.worker.SessionMonitorWorker
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoMap

@InstallIn(SingletonComponent::class)
@Module
abstract class SessionMonitorModule{
    @Binds
    @IntoMap
    @WorkerKey(SessionMonitorWorker::class)
    abstract fun binsImplementation(
        factory: SessionMonitorWorkerFactory
    ): CustomWorkerFactory
}
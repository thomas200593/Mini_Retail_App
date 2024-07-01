package com.thomas200593.mini_retail_app.work.factory.di

import androidx.work.WorkerFactory
import com.thomas200593.mini_retail_app.work.factory.WorkWrapperFactory
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class WorkerModule {
    @Binds
    abstract fun bindWorkerFactory(
        impl: WorkWrapperFactory
    ): WorkerFactory
}
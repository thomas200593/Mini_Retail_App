package com.thomas200593.mini_retail_app.work.factory.di

import androidx.work.WorkerFactory
import com.thomas200593.mini_retail_app.work.factory.FactoryWorkWrapper
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class ModuleWorker {
    @Binds
    abstract fun bindsImplementation(
        impl: FactoryWorkWrapper
    ): WorkerFactory
}
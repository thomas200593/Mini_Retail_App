package com.thomas200593.mini_retail_app.core.di

import com.thomas200593.mini_retail_app.core.design_system.dispatchers.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.dispatchers.Dispatchers.Dispatchers.Default
import com.thomas200593.mini_retail_app.core.design_system.dispatchers.Dispatchers.Dispatchers.IO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
object DispatchersModule {
    @Provides
    @Dispatcher(IO)
    fun providesCoroutineDispatcherIO(): CoroutineDispatcher{
        return Dispatchers.IO
    }

    @Provides
    @Dispatcher(Default)
    fun providesCoroutineDispatcherDefault(): CoroutineDispatcher{
        return Dispatchers.Default
    }
}
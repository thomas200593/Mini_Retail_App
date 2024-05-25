package com.thomas200593.mini_retail_app.core.di

import com.thomas200593.mini_retail_app.core.design_system.dispatchers.AppDispatchers.Default
import com.thomas200593.mini_retail_app.core.design_system.dispatchers.AppDispatchers.IO
import com.thomas200593.mini_retail_app.core.design_system.dispatchers.Dispatcher
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
object AppDispatchersModule {
    @Provides
    @Dispatcher(IO)
    fun provideIODispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    @Dispatcher(Default)
    fun provideDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default
}
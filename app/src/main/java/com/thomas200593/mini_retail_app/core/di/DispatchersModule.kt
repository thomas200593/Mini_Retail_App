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
import timber.log.Timber

private val TAG = DispatchersModule::class.simpleName

@Module
@InstallIn(SingletonComponent::class)
object DispatchersModule {
    @Provides
    @Dispatcher(IO)
    fun provideIODispatcher(): CoroutineDispatcher{
        Timber.d("Called : fun $TAG.provideIODispatcher()")
        return Dispatchers.IO
    }

    @Provides
    @Dispatcher(Default)
    fun provideDefaultDispatcher(): CoroutineDispatcher{
        Timber.d("Called : fun $TAG.provideDefaultDispatcher()")
        return Dispatchers.Default
    }
}
package com.thomas200593.mini_retail_app.core.di

import com.thomas200593.mini_retail_app.core.design_system.util.NetworkMonitor
import com.thomas200593.mini_retail_app.core.design_system.util.NetworkMonitorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class NetworkModule {
    @Binds
    internal abstract fun bindNetworkMonitor(
        networkMonitor: NetworkMonitorImpl
    ) : NetworkMonitor
}
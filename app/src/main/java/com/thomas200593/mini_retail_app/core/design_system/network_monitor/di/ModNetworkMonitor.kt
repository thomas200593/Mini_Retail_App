package com.thomas200593.mini_retail_app.core.design_system.network_monitor.di

import com.thomas200593.mini_retail_app.core.design_system.network_monitor.NetworkMonitor
import com.thomas200593.mini_retail_app.core.design_system.network_monitor.NetworkMonitorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ModNetworkMonitor {
    @Binds
    internal abstract fun bindsImplementation(impl: NetworkMonitorImpl) : NetworkMonitor
}
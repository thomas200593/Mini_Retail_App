package com.thomas200593.mini_retail_app.core.design_system.coroutine_scopes.di

import com.thomas200593.mini_retail_app.core.design_system.coroutine_scopes.CoroutineScopes
import com.thomas200593.mini_retail_app.core.design_system.coroutine_scopes.CoroutineScopesJob
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

@Module
@InstallIn(SingletonComponent::class)
object ModCoroutineScope {
    @Provides
    @CoroutineScopes(CoroutineScopesJob.Options.IOSupervisor)
    fun providesScopeIoSupervisor() = CoroutineScope(Dispatchers.IO + SupervisorJob())

    @Provides
    @CoroutineScopes(CoroutineScopesJob.Options.DefaultSupervisor)
    fun providesScopeDefaultSupervisor() = CoroutineScope(Dispatchers.Default + SupervisorJob())
}
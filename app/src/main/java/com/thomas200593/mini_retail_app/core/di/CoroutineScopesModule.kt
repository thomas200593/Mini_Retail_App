package com.thomas200593.mini_retail_app.core.di

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
object CoroutineScopesModule {
    @Provides
    @CoroutineScopes(CoroutineScopesJob.Options.IOSupervisor)
    fun providesCoroutineScopeIo() = CoroutineScope(Dispatchers.IO + SupervisorJob())

    @Provides
    @CoroutineScopes(CoroutineScopesJob.Options.DefaultSupervisor)
    fun providesCoroutineScopeDefault() = CoroutineScope(Dispatchers.Default + SupervisorJob())
}
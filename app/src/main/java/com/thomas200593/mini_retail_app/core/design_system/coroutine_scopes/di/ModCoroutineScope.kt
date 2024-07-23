package com.thomas200593.mini_retail_app.core.design_system.coroutine_scopes.di

import com.thomas200593.mini_retail_app.core.design_system.coroutine_scopes.CoroutineScopes
import com.thomas200593.mini_retail_app.core.design_system.coroutine_scopes.CoroutineScopesJob.Options.DefaultSupervisor
import com.thomas200593.mini_retail_app.core.design_system.coroutine_scopes.CoroutineScopesJob.Options.IOSupervisor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.SupervisorJob

@Module
@InstallIn(SingletonComponent::class)
object ModCoroutineScope {
    @Provides
    @CoroutineScopes(IOSupervisor)
    fun providesScopeIoSupervisor() = CoroutineScope(IO + SupervisorJob())

    @Provides
    @CoroutineScopes(DefaultSupervisor)
    fun providesScopeDefaultSupervisor() = CoroutineScope(Default + SupervisorJob())
}
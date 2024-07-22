package com.thomas200593.mini_retail_app.core.di

import com.thomas200593.mini_retail_app.core.data.local.session.Session
import com.thomas200593.mini_retail_app.core.data.local.session.SessionImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class SessionModule {
    @Binds
    internal abstract fun bindsImplementation(
        impl: SessionImpl
    ): Session
}
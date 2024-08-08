package com.thomas200593.mini_retail_app.core.design_system.util.datetime.di

import com.thomas200593.mini_retail_app.core.design_system.util.datetime.HlpDateTime
import com.thomas200593.mini_retail_app.core.design_system.util.datetime.HlpDateTimeImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ModHlpDateTime {
    @Binds
    abstract fun bindsImplementation(impl: HlpDateTimeImpl): HlpDateTime
}
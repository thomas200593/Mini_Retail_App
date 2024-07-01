package com.thomas200593.mini_retail_app.features.app_config.di

import com.thomas200593.mini_retail_app.features.app_config.repository.ConfigGeneralRepository
import com.thomas200593.mini_retail_app.features.app_config.repository.ConfigGeneralRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class ConfigGeneralModule {
    @Binds
    internal abstract fun bindsConfigGeneralRepository(
        impl: ConfigGeneralRepositoryImpl
    ): ConfigGeneralRepository
}
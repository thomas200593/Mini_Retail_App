package com.thomas200593.mini_retail_app.features.app_config.di

import com.thomas200593.mini_retail_app.features.app_config.repository.AppConfigRepository
import com.thomas200593.mini_retail_app.features.app_config.repository.AppConfigRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AppConfigModule {
    @Binds
    internal abstract fun bindsAppConfigRepository(
        impl: AppConfigRepositoryImpl
    ):AppConfigRepository
}
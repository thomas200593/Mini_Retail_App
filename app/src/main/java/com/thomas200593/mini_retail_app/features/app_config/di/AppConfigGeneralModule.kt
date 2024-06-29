package com.thomas200593.mini_retail_app.features.app_config.di

import com.thomas200593.mini_retail_app.features.app_config.repository.AppConfigGeneralRepository
import com.thomas200593.mini_retail_app.features.app_config.repository.AppConfigGeneralRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class AppConfigGeneralModule {
    @Binds
    internal abstract fun bindsAppConfigGeneralRepository(
        appConfigGeneralRepositoryImpl: AppConfigGeneralRepositoryImpl
    ): AppConfigGeneralRepository
}
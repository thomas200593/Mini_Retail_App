package com.thomas200593.mini_retail_app.features.app_conf.app_config.di

import com.thomas200593.mini_retail_app.features.app_conf.app_config.repository.RepoAppConf
import com.thomas200593.mini_retail_app.features.app_conf.app_config.repository.RepoAppConfImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AppConfigModule {
    @Binds
    internal abstract fun bindsRepository(
        impl: RepoAppConfImpl
    ): RepoAppConf
}
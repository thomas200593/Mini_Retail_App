package com.thomas200593.mini_retail_app.features.app_conf._g_timezone.di

import com.thomas200593.mini_retail_app.features.app_conf._g_timezone.repository.RepositoryAppCfgGeneralTimezone
import com.thomas200593.mini_retail_app.features.app_conf._g_timezone.repository.RepositoryImplAppCfgGeneralTimezone
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ModuleAppCfgGeneralTimezone{
    @Binds
    internal abstract fun bindsRepository(
        impl: RepositoryImplAppCfgGeneralTimezone
    ): RepositoryAppCfgGeneralTimezone
}
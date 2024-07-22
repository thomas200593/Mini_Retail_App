package com.thomas200593.mini_retail_app.features.app_config._g_theme.di

import com.thomas200593.mini_retail_app.features.app_config._g_theme.repository.RepositoryAppCfgGeneralTheme
import com.thomas200593.mini_retail_app.features.app_config._g_theme.repository.RepositoryImplAppCfgGeneralTheme
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ModuleAppCfgGeneralTheme{
    @Binds
    internal abstract fun bindsRepository(
        impl: RepositoryImplAppCfgGeneralTheme
    ): RepositoryAppCfgGeneralTheme
}
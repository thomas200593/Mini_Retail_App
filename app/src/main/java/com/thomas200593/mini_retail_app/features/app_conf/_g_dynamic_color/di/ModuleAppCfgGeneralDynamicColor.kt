package com.thomas200593.mini_retail_app.features.app_conf._g_dynamic_color.di

import com.thomas200593.mini_retail_app.features.app_conf._g_dynamic_color.repository.RepositoryAppCfgGeneralDynamicColor
import com.thomas200593.mini_retail_app.features.app_conf._g_dynamic_color.repository.RepositoryImplAppCfgGeneralDynamicColor
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ModuleAppCfgGeneralDynamicColor {
    @Binds
    internal abstract fun bindsRepository(
        impl: RepositoryImplAppCfgGeneralDynamicColor
    ): RepositoryAppCfgGeneralDynamicColor
}
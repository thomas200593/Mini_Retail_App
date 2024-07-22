package com.thomas200593.mini_retail_app.features.app_config._g_font_size.di

import com.thomas200593.mini_retail_app.features.app_config._g_font_size.repository.RepositoryAppCfgGeneralFontSize
import com.thomas200593.mini_retail_app.features.app_config._g_font_size.repository.RepositoryImplAppCfgGeneralFontSize
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ModuleAppCfgGeneralFontSize {
    @Binds
    internal abstract fun bindsRepository(
        impl: RepositoryImplAppCfgGeneralFontSize
    ): RepositoryAppCfgGeneralFontSize
}
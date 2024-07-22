package com.thomas200593.mini_retail_app.features.app_config.app_cfg_general_language.di

import com.thomas200593.mini_retail_app.features.app_config.app_cfg_general_language.repository.RepositoryAppCfgGeneralLanguage
import com.thomas200593.mini_retail_app.features.app_config.app_cfg_general_language.repository.RepositoryImplAppCfgGeneralLanguage
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ModuleAppCfgGeneralLanguage{
    @Binds
    internal abstract fun bindsRepository(
        impl: RepositoryImplAppCfgGeneralLanguage
    ): RepositoryAppCfgGeneralLanguage
}
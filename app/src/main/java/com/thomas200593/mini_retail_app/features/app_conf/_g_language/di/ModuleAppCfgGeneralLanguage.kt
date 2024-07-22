package com.thomas200593.mini_retail_app.features.app_conf._g_language.di

import com.thomas200593.mini_retail_app.features.app_conf._g_language.repository.RepositoryAppCfgGeneralLanguage
import com.thomas200593.mini_retail_app.features.app_conf._g_language.repository.RepositoryImplAppCfgGeneralLanguage
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
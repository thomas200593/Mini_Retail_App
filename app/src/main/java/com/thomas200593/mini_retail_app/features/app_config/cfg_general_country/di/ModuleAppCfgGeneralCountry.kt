package com.thomas200593.mini_retail_app.features.app_config.cfg_general_country.di

import com.thomas200593.mini_retail_app.features.app_config.cfg_general_country.repository.RepositoryAppCfgGeneralCountry
import com.thomas200593.mini_retail_app.features.app_config.cfg_general_country.repository.RepositoryImplAppCfgGeneralCountry
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ModuleAppCfgGeneralCountry {
    @Binds
    internal abstract fun bindsRepository(
        impl: RepositoryImplAppCfgGeneralCountry
    ): RepositoryAppCfgGeneralCountry
}
package com.thomas200593.mini_retail_app.features.app_config._g_currency.di

import com.thomas200593.mini_retail_app.features.app_config._g_currency.repository.RepositoryAppCfgGeneralCurrency
import com.thomas200593.mini_retail_app.features.app_config._g_currency.repository.RepositoryImplAppCfgGeneralCurrency
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ModuleAppCfgGeneralCurrency{
    @Binds
    internal abstract fun bindsRepository(
        impl: RepositoryImplAppCfgGeneralCurrency
    ): RepositoryAppCfgGeneralCurrency
}